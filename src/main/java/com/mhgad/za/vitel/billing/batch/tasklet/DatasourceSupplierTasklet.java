package com.mhgad.za.vitel.billing.batch.tasklet;

import com.mhgad.za.vitel.billing.batch.model.DbServer;
import com.mhgad.za.vitel.billing.batch.repo.DbServersRepo;
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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class DatasourceSupplierTasklet implements Tasklet, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(DatasourceSupplierTasklet.class);
    
    private final Queue<DataSource> datasources;
    
    @Value("${jdbc.cache.prepared.statements}")
    private Boolean cachePrepStatements;
    @Value("${jdbc.prepared.statement.cache.size}")
    private Integer prepStatementCacheSize;
    @Value("${jdbc.prepared.statement.cache.sql.limit}")
    private Integer prepStatementCacheSqlLimit;
    
    @Autowired
    private DbServersRepo dbServersRepo;

    @Autowired
    private JdbcPagingItemReader cdrReader;
    
    public DatasourceSupplierTasklet() {
        datasources = new LinkedList<>();
    }

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        DataSource next = datasources.poll();

        cdrReader.setDataSource(next);

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<DbServer> dbServers = dbServersRepo.findAllServers();
        
        for(DbServer currentServer : dbServers) {
            final HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(currentServer.getUrl());
            cfg.setUsername(currentServer.getUsername());
            cfg.setPassword(currentServer.getPassword());
            cfg.addDataSourceProperty("cachePrepStmts", cachePrepStatements);
            cfg.addDataSourceProperty("prepStmtCacheSize", prepStatementCacheSize);
            cfg.addDataSourceProperty("prepStmtCacheSqlLimit", prepStatementCacheSqlLimit);

            HikariDataSource ds = new HikariDataSource(cfg);

            datasources.add(ds);
        }

        LOG.info("Loaded {} datasource", datasources.size());
    }
}
