package com.mhgad.za.vitel.billing.batch.extract;

import com.mhgad.za.vitel.billing.batch.common.SqlConst;
import com.mhgad.za.vitel.billing.batch.extract.biz.CdrFieldExtractor;
import com.mhgad.za.vitel.billing.batch.extract.biz.CdrProcessor;
import com.mhgad.za.vitel.billing.batch.extract.decision.NextDatasourceDecision;
import com.mhgad.za.vitel.billing.batch.extract.decision.NextSiteDecision;
import com.mhgad.za.vitel.billing.batch.extract.mapper.CdrMapper;
import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.DatasourceSupplierTasklet;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.SiteSupplierTasklet;
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
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@ComponentScan(basePackages = {"com.mhgad.za.vitel.billing.batch.extract",
    "com.mhgad.za.vitel.billing.batch.common"})
@Configuration
public class PartnerBillingConfig {

    @Autowired
    private ExtractProps appProps;

    @Autowired
    private DatasourceSupplierTasklet dsSupplier;

    @Autowired
    private SiteSupplierTasklet siteSupplier;

    @Autowired
    private NextDatasourceDecision dsDecision;

    @Autowired
    private NextSiteDecision siteDecision;

    @Autowired
    private CdrProcessor costItemProc;

    @Bean
    public JdbcPagingItemReader cdrReader(final DataSource partnerBillingDs) throws Exception {
        final SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause(SqlConst.RETRIEVE_CDR_RECORDS_SELECT);
        queryProvider.setFromClause(SqlConst.RETRIEVE_CDR_RECORDS_FROM);
        queryProvider.setWhereClause(SqlConst.RETRIEVE_CDR_RECORDS_WHERE);
        queryProvider.setSortKey("uniqueid");
        queryProvider.setDataSource(partnerBillingDs);

        final Map<String, Date> params = new HashMap<>();

        final JdbcPagingItemReader reader = new JdbcPagingItemReader();
        // Use partner billing for setup, it will be replaced with 1 for the target machines
        reader.setDataSource(partnerBillingDs);
        reader.setFetchSize(appProps.getFetchSize());
        reader.setQueryProvider(queryProvider.getObject());
        reader.setPageSize(appProps.getPagingSize());
        reader.setParameterValues(params);
        reader.setRowMapper(new CdrMapper());

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter cdrWriter(final DataSource partnerBillingDs) {
        final JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(partnerBillingDs);
        writer.setItemPreparedStatementSetter((item, ps) -> {});
        writer.setSql(SqlConst.WRITE_CDR_QUERY);

        return writer;
    }

    @Bean(name = "fileoutReader")
    public JdbcCursorItemReader fileoutReader(final DataSource partnerBillingDs) throws Exception {
        final JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setFetchSize(appProps.getFetchSize());
        reader.setDataSource(partnerBillingDs);
        reader.setRowMapper(new CdrMapper());
        reader.setSql(SqlConst.FILE_OUT_CDR_RECORDS_SELECT);
        reader.setPreparedStatementSetter((ps) -> {});

        return reader;
    }

    @Bean
    public FlatFileItemWriter<Cdr> fileoutWriter() {
        final DelimitedLineAggregator lineAgg = new DelimitedLineAggregator();
        lineAgg.setDelimiter(",");
        lineAgg.setFieldExtractor(new CdrFieldExtractor());

        final FlatFileItemWriter<Cdr> writer = new FlatFileItemWriter<>();
        writer.setEncoding("UTF-8");
        writer.setLineAggregator(lineAgg);

        return writer;
    }

    @Bean
    public Job createJob(final StepBuilderFactory stepBuilderFactory, final JobBuilderFactory jobs,
            final DataSource partnerBillingDs) throws Exception {
        final Step getDatasource = stepBuilderFactory.get("getDatasource").tasklet(dsSupplier).build();
        final JdbcPagingItemReader reader = cdrReader(partnerBillingDs);

        final Step retrieveCdrs = stepBuilderFactory.get("stepRetrieveCdrs")
                .<Cdr, Cdr>chunk(appProps.getChunkSize())
                .reader(reader)
                .processor(costItemProc)
                .writer(cdrWriter(partnerBillingDs)).build();

        final Step getSite = stepBuilderFactory.get("getSites").tasklet(siteSupplier).build();
        final Step writeOutFiles = stepBuilderFactory.get("writeOutFiles")
                .<Cdr, Cdr>chunk(appProps.getChunkSize())
                .reader(fileoutReader(partnerBillingDs))
                .writer(fileoutWriter()).build();

        return jobs.get("partner-billing").incrementer(new RunIdIncrementer())
                .start(paramsStep(stepBuilderFactory, reader))
                .next(getDatasource)
                .next(retrieveCdrs)
                .next(dsDecision)
                .on(PartnerBillingConst.STATUS_CONTINUE)
                .to(getDatasource)
                .from(dsDecision).on(FlowExecutionStatus.COMPLETED.toString())
                .to(getSite)
                .next(writeOutFiles)
                .next(siteDecision)
                .on(PartnerBillingConst.STATUS_CONTINUE)
                .to(getSite)
                .from(siteDecision).on(FlowExecutionStatus.COMPLETED.toString())
                .to(endStep(stepBuilderFactory)).end().build();
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
