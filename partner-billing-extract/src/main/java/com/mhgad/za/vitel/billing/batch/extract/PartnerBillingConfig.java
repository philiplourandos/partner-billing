package com.mhgad.za.vitel.billing.batch.extract;

import com.mhgad.za.vitel.billing.batch.common.SqlConst;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import com.mhgad.za.vitel.billing.batch.extract.biz.CdrFieldExtractor;
import com.mhgad.za.vitel.billing.batch.extract.biz.CdrProcessor;
import com.mhgad.za.vitel.billing.batch.extract.decision.NextDatasourceDecision;
import com.mhgad.za.vitel.billing.batch.extract.decision.NextSiteDecision;
import com.mhgad.za.vitel.billing.batch.extract.mapper.CdrMapper;
import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.DatasourceSupplierTasklet;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.SiteSupplierTasklet;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.mhgad.za.vitel.billing.batch.extract",
    "com.mhgad.za.vitel.billing.batch.common"})
@Configuration
public class PartnerBillingConfig {

    @Autowired
    private ExtractProps appProps;

    @Autowired
    private CdrProcessor costItemProc;

    @Bean
    public SiteSupplierTasklet siteSupplier(final PartnerBillingRepo repo, final DataSource partnerBillingDs)
            throws Exception {
        return new SiteSupplierTasklet(appProps, fileoutReader(partnerBillingDs), repo, fileoutWriter());
    }

    @Bean
    public DatasourceSupplierTasklet dsSupplier(final PartnerBillingRepo dbServersRepo, final DataSource partnerBillingDs)
            throws Exception{
        return new DatasourceSupplierTasklet(dbServersRepo, cdrReader(partnerBillingDs), appProps, cdrWriter(partnerBillingDs));
    }

    @Bean
    public JdbcPagingItemReader cdrReader(final DataSource partnerBillingDs) throws Exception {
        final SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause(SqlConst.RETRIEVE_CDR_RECORDS_SELECT);
        queryProvider.setFromClause(SqlConst.RETRIEVE_CDR_RECORDS_FROM);
        queryProvider.setWhereClause(SqlConst.RETRIEVE_CDR_RECORDS_WHERE);
        queryProvider.setSortKey("uniqueid");
        queryProvider.setDataSource(partnerBillingDs);

        final Map<String, Date> params = new HashMap<>();

        return new JdbcPagingItemReaderBuilder()
            .name("crd-reader")
            // Use partner billing for setup, it will be replaced with 1 for the target machines
            .dataSource(partnerBillingDs)
            .fetchSize(appProps.getFetchSize())
            .queryProvider(queryProvider.getObject())
            .pageSize(appProps.getPagingSize())
            .parameterValues(params)
            .rowMapper(new CdrMapper())
            .build();
    }

    @Bean
    public JdbcBatchItemWriter cdrWriter(final DataSource partnerBillingDs) {
        return new JdbcBatchItemWriterBuilder()
            .dataSource(partnerBillingDs)
            .itemPreparedStatementSetter((item, ps) -> {})
            .sql(SqlConst.WRITE_CDR_QUERY)
            .build();
    }

    @Bean(name = "fileoutReader")
    public JdbcCursorItemReader fileoutReader(final DataSource partnerBillingDs) throws Exception {
        return new JdbcCursorItemReaderBuilder()
            .name("fileout-reader")
            .fetchSize(appProps.getFetchSize())
            .dataSource(partnerBillingDs)
            .rowMapper(new CdrMapper())
            .sql(SqlConst.FILE_OUT_CDR_RECORDS_SELECT)
            .preparedStatementSetter((ps) -> {})
            .build();
    }

    @Bean
    public FlatFileItemWriter<Cdr> fileoutWriter() {
        final DelimitedLineAggregator lineAgg = new DelimitedLineAggregator();
        lineAgg.setDelimiter(",");
        lineAgg.setFieldExtractor(new CdrFieldExtractor());

        return new FlatFileItemWriterBuilder()
            .name("fileout-writer")
            .encoding(StandardCharsets.UTF_8.name())
            .lineAggregator(lineAgg)
            .build();
    }

    @Bean
    public Job createJob(final StepBuilderFactory stepBuilderFactory, final JobBuilderFactory jobs,
            final DataSource partnerBillingDs, final PartnerBillingRepo repo) throws Exception {
        final Step getDatasource = stepBuilderFactory.get("getDatasource").tasklet(dsSupplier(repo, partnerBillingDs)).build();
        final JdbcPagingItemReader reader = cdrReader(partnerBillingDs);

        final Step retrieveCdrs = stepBuilderFactory.get("stepRetrieveCdrs")
                .<Cdr, Cdr>chunk(appProps.getChunkSize())
                .reader(reader)
                .processor(costItemProc)
                .writer(cdrWriter(partnerBillingDs)).build();

        final Step getSite = stepBuilderFactory.get("getSites").tasklet(siteSupplier(repo, partnerBillingDs)).build();
        final Step writeOutFiles = stepBuilderFactory.get("writeOutFiles")
                .<Cdr, Cdr>chunk(appProps.getChunkSize())
                .reader(fileoutReader(partnerBillingDs))
                .writer(fileoutWriter()).build();

        return jobs.get("partner-billing").incrementer(new RunIdIncrementer())
                .start(paramsStep(stepBuilderFactory, reader))
                .next(getDatasource)
                .next(retrieveCdrs)
                .next(nextDsDecision(null))
                .on(PartnerBillingConst.STATUS_CONTINUE)
                .to(getDatasource)
                .from(nextDsDecision(null)).on(FlowExecutionStatus.COMPLETED.toString())
                .to(getSite)
                .next(writeOutFiles)
                .next(nextSiteDecision(null))
                .on(PartnerBillingConst.STATUS_CONTINUE)
                .to(getSite)
                .from(nextSiteDecision(null)).on(FlowExecutionStatus.COMPLETED.toString())
                .to(endStep(stepBuilderFactory)).end().build();
    }

    @Bean
    public NextSiteDecision nextSiteDecision(final SiteSupplierTasklet siteSupplier) throws Exception {
        return new NextSiteDecision(siteSupplier);
    }
    
    @Bean
    public NextDatasourceDecision nextDsDecision(final DatasourceSupplierTasklet dsSupplier) throws Exception {
        return new NextDatasourceDecision(dsSupplier);
    }
    
    public Step endStep(final StepBuilderFactory stepBuilderFactory) {
        final Tasklet task = (contribution, chunkContext) -> {return RepeatStatus.FINISHED;};

        return stepBuilderFactory.get("end").tasklet(task).build();
    }

    /**
     * Helper step to get the start and end date from the job context as the values
     * are now passed in via the job launcher.
     * 
     * @param stepBuilderFactory Builder to create step
     * @param reader jdbc reader we need to set start and end dates on
     * 
     * @return Step returned
     */
    public Step paramsStep(final StepBuilderFactory stepBuilderFactory, final JdbcPagingItemReader reader) {
        Tasklet task = (contribution, chunkContext) -> {
            final Map<String, Object> jobsParams = chunkContext.getStepContext().getJobParameters();
            final String startDate = (String) jobsParams.get(PartnerBillingConst.PARAM_START_DATE);
            final String endDate = (String) jobsParams.get(PartnerBillingConst.PARAM_END_DATE);

            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            final Map<String, Date> params = new HashMap<>();
            params.put("start", formatter.parse(startDate));
            params.put("end", formatter.parse(endDate));

            reader.setParameterValues(params);

            return RepeatStatus.FINISHED;
        };

        return stepBuilderFactory.get("params").tasklet(task).build();
    }
}
