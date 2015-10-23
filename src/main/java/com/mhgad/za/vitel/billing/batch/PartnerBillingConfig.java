package com.mhgad.za.vitel.billing.batch;

import com.mhgad.za.vitel.billing.batch.biz.CdrProcessor;
import com.mhgad.za.vitel.billing.batch.decision.NextDatasourceDecision;
import com.mhgad.za.vitel.billing.batch.mapper.CdrMapper;
import com.mhgad.za.vitel.billing.batch.model.Cdr;
import com.mhgad.za.vitel.billing.batch.tasklet.DatasourceSupplierTasklet;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 *
 * @author plourand
 */
@ComponentScan(basePackages = {"com.mhgad.za.vitel"})
@Configuration
@EnableBatchProcessing
@EnableAspectJAutoProxy
public class PartnerBillingConfig {

    @Autowired
    private AppProps appProps;

    @Autowired
    private DatasourceSupplierTasklet dsSupplier;

    @Autowired
    private NextDatasourceDecision dsDecision;
    
    @Autowired
    private CdrProcessor costItemProc;

    @Bean
    @Profile("prod")
    public DataSource partnerBillingDs() {
        final HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(appProps.getPartnerBillingUrl());
        cfg.setUsername(appProps.getPartnerBillingUsername());
        cfg.setPassword(appProps.getPartnerBillingPassword());
        cfg.addDataSourceProperty("cachePrepStmts", appProps.getCachePrepStatements());
        cfg.addDataSourceProperty("prepStmtCacheSize", appProps.getPrepStatementCacheSize());
        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", appProps.getPrepStatementCacheSqlLimit());

        HikariDataSource ds = new HikariDataSource(cfg);

        return ds;
    }

    @Bean
    public JdbcTemplate partnerBillingTemplate(DataSource partnerBillingDs) {
        return new JdbcTemplate(partnerBillingDs);
    }

    @Bean
    public JdbcPagingItemReader cdrReader(DataSource partnerBillingDs) throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause(SqlConst.RETRIEVE_CDR_RECORDS_SELECT);
        queryProvider.setFromClause(SqlConst.RETRIEVE_CDR_RECORDS_FROM);
        queryProvider.setWhereClause(SqlConst.RETRIEVE_CDR_RECORDS_WHERE);
        queryProvider.setSortKey("uniqueid");
        queryProvider.setDataSource(partnerBillingDs);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Date> params = new HashMap<>();
        params.put("start", formatter.parse(appProps.getStartDate()));
        params.put("end", formatter.parse(appProps.getEndDate()));

        JdbcPagingItemReader reader = new JdbcPagingItemReader();
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
    public JdbcBatchItemWriter cdrWriter(DataSource partnerBillingDs) {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(partnerBillingDs);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        writer.setSql(SqlConst.WRITE_CDR_QUERY);

        return writer;
    }
    
    @Bean(name = "fileoutReader")
    public JdbcPagingItemReader fileoutReader(DataSource partnerBillingDs) throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause(SqlConst.FILE_OUT_CDR_RECORDS_SELECT);
        queryProvider.setFromClause(SqlConst.FILE_OUT_CDR_RECORDS_FROM);
        queryProvider.setWhereClause(SqlConst.FILE_OUT_CDR_RECORDS_WHERE);
        queryProvider.setSortKey("uniqueid");
        queryProvider.setDataSource(partnerBillingDs);
        
        JdbcPagingItemReader reader = new JdbcPagingItemReader();
        reader.setDataSource(partnerBillingDs);
        reader.setFetchSize(appProps.getFetchSize());
        reader.setQueryProvider(queryProvider.getObject());
        reader.setPageSize(appProps.getPagingSize());
        reader.setRowMapper(new CdrMapper());

        return reader;
    }

    @Bean
    public Job createJob(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobs,
            DataSource partnerBillingDs) throws Exception {
        Step getDatasource = stepBuilderFactory.get("getDatasource").tasklet(dsSupplier).build();
        Step retrieveCdrs = stepBuilderFactory.get("stepRetrieveCdrs")
                .<Cdr, Cdr>chunk(appProps.getChunkSize())
                .reader(cdrReader(partnerBillingDs))
                .processor(costItemProc)
                .writer(cdrWriter(partnerBillingDs)).build();

        return jobs.get("partner-billing").incrementer(new RunIdIncrementer())
                .start(getDatasource)
                .next(retrieveCdrs)
                .next(dsDecision)
                .on(PartnerBillingConst.STATUS_CONTINUE)
                .to(getDatasource)
                .from(dsDecision).on(FlowExecutionStatus.COMPLETED.toString()).to(endStep(stepBuilderFactory)).end().build();
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource partnerBillingDs) {
        return new DataSourceTransactionManager(partnerBillingDs);
    }
    
    public Step endStep(StepBuilderFactory stepBuilderFactory) {
        Tasklet task = (contribution, chunkContext) -> {return RepeatStatus.FINISHED;};
        
        return stepBuilderFactory.get("end").tasklet(task).build();
    }
}
