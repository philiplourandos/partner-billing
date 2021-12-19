package com.mhgad.za.vitel.billing.batch.extract.tasklet;

import com.mhgad.za.vitel.billing.batch.extract.biz.CdrPrepStatementSetter;
import com.mhgad.za.vitel.billing.batch.extract.model.DbServer;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import com.mhgad.za.vitel.billing.batch.extract.ExtractProps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class DatasourceSupplierTasklet implements Tasklet, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(DatasourceSupplierTasklet.class);
    
    private final Queue<CdrSource> cdrSources;
    
    private final PartnerBillingRepo dbServersRepo;

    private final JdbcPagingItemReader cdrReader;

    private final ExtractProps props;

    private final JdbcBatchItemWriter writer;

    public DatasourceSupplierTasklet(final PartnerBillingRepo dbServersRepo, final JdbcPagingItemReader cdrReader, 
            final ExtractProps props, final JdbcBatchItemWriter writer) {
        this.cdrSources = new LinkedList<>();
        this.dbServersRepo = dbServersRepo;
        this.cdrReader = cdrReader;
        this.props = props;
        this.writer = writer;
    }

    @Override
    public RepeatStatus execute(final StepContribution sc, final ChunkContext cc) throws Exception {
        final CdrSource next = cdrSources.poll();

        final DataSource ds = next.getDs();

        LOG.info("Processing {}", ds);

        cdrReader.setDataSource(ds);
        cdrReader.afterPropertiesSet();

        writer.setItemPreparedStatementSetter(new CdrPrepStatementSetter(next.getSiteId()));

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<DbServer> dbServers = dbServersRepo.findAllServers();

        dbServers.forEach(s -> {
            final HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(s.getUrl());
            cfg.setUsername(s.getUsername());
            cfg.setPassword(s.getPassword());
            cfg.addDataSourceProperty("cachePrepStmts", props.getCachePrepStatements());
            cfg.addDataSourceProperty("prepStmtCacheSize", props.getPrepStatementCacheSize());
            cfg.addDataSourceProperty("prepStmtCacheSqlLimit", props.getPrepStatementCacheSqlLimit());
            cfg.setPoolName(s.getUrl());

            final HikariDataSource ds = new HikariDataSource(cfg);

            cdrSources.add(new CdrSource(ds, s.getSiteId()));
        });

        LOG.info("Loaded {} datasources", cdrSources.size());
    }

    public Queue<CdrSource> getDatasources() {
        return cdrSources;
    }
}
