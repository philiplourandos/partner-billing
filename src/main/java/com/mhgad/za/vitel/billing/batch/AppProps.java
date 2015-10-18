package com.mhgad.za.vitel.billing.batch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class AppProps {
    @Value("${fetch.size}")
    private Integer fetchSize;
    @Value("${chunk.size}")
    private Integer chunkSize;
    @Value("${paging.size}")
    private Integer pagingSize;
    
    @Value("${partner.billing.jdbc.url}")
    private String partnerBillingUrl;
    @Value("${partner.billing.jdbc.datasource}")
    private String partnerBillingDatasource;
    @Value("${partner.billing.jdbc.username}")
    private String partnerBillingUsername;
    @Value("${partner.billing.jdbc.password}")
    private String partnerBillingPassword;

    @Value("${jdbc.cache.prepared.statements}")
    private Boolean cachePrepStatements;
    @Value("${jdbc.prepared.statement.cache.size}")
    private Integer prepStatementCacheSize;
    @Value("${jdbc.prepared.statement.cache.sql.limit}")
    private Integer prepStatementCacheSqlLimit;
    
    public AppProps() {
    }

    public Integer getChunkSize() {
        return chunkSize;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public Integer getPagingSize() {
        return pagingSize;
    }

    public Boolean getCachePrepStatements() {
        return cachePrepStatements;
    }

    public Integer getPrepStatementCacheSize() {
        return prepStatementCacheSize;
    }

    public Integer getPrepStatementCacheSqlLimit() {
        return prepStatementCacheSqlLimit;
    }

    public String getPartnerBillingUrl() {
        return partnerBillingUrl;
    }

    public String getPartnerBillingDatasource() {
        return partnerBillingDatasource;
    }

    public String getPartnerBillingUsername() {
        return partnerBillingUsername;
    }

    public String getPartnerBillingPassword() {
        return partnerBillingPassword;
    }
    
    
}
