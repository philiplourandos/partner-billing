package com.mhgad.za.vitel.billing.batch.extract;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "extract")
public class ExtractProps {
    private int cachePrepStatements;
    private int prepStatementCacheSize;
    private int prepStatementCacheSqlLimit;
    private int fetchSize;
    private int chunkSize;
    private int pagingSize;
    
    private String outputPath;

    public ExtractProps() {
    }

    public int getCachePrepStatements() {
        return cachePrepStatements;
    }

    public int getPrepStatementCacheSize() {
        return prepStatementCacheSize;
    }

    public int getPrepStatementCacheSqlLimit() {
        return prepStatementCacheSqlLimit;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public int getPagingSize() {
        return pagingSize;
    }

    public String getOutputPath() {
        return outputPath;
    }
    
    public void setCachePrepStatements(final int cachePrepStatements) {
        this.cachePrepStatements = cachePrepStatements;
    }

    public void setPrepStatementCacheSize(final int prepStatementCacheSize) {
        this.prepStatementCacheSize = prepStatementCacheSize;
    }

    public void setPrepStatementCacheSqlLimit(final int prepStatementCacheSqlLimit) {
        this.prepStatementCacheSqlLimit = prepStatementCacheSqlLimit;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
