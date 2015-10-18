package com.mhgad.za.vitel.billing.batch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author plourand
 */
@Configuration
@ComponentScan(basePackages = {"com.mhgad.za.vitel.billing.batch"})
@EnableBatchProcessing
public class PartnerBillingConfig {

    @Autowired
    private AppProps appProps;
    @Value("#{systemProperties['start']}")
    private String startDate;
    @Value("#{systemProperties['end']}")
    private String endDate;

    @Autowired
    private AppProps props;

    @Bean
    public DataSource partnerBillingDs() {
        final HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(props.getPartnerBillingUrl());
        cfg.setDataSourceClassName(props.getPartnerBillingDatasource());
        cfg.setUsername(props.getPartnerBillingUsername());
        cfg.setPassword(props.getPartnerBillingPassword());
        cfg.addDataSourceProperty("cachePrepStmts", props.getCachePrepStatements());
        cfg.addDataSourceProperty("prepStmtCacheSize", props.getPrepStatementCacheSize());
        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", props.getPrepStatementCacheSqlLimit());

        HikariDataSource ds = new HikariDataSource(cfg);

        return ds;
    }

    @Bean
    public JdbcTemplate partnerBillingTemplate() {
        return new JdbcTemplate(partnerBillingDs());
    }

    @Bean
    public JdbcPagingItemReader cdrReader() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause(SqlConst.RETRIEVE_CDR_RECORDS_SELECT);
        queryProvider.setFromClause(SqlConst.RETRIEVE_CDR_RECORDS_FROM);
        queryProvider.setWhereClause(SqlConst.RETRIEVE_CDR_RECORDS_WHERE);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Date> params = new HashMap<>();
        params.put("start", formatter.parse(startDate));
        params.put("end", formatter.parse(endDate));

        JdbcPagingItemReader reader = new JdbcPagingItemReader();
        reader.setFetchSize(appProps.getFetchSize());
        reader.setQueryProvider(queryProvider.getObject());
        reader.setPageSize(appProps.getPagingSize());
        reader.setParameterValues(params);

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter cdrWriter() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(partnerBillingDs());
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        writer.setSql(SqlConst.WRITE_CDR_QUERY);

        return writer;
    }
}
