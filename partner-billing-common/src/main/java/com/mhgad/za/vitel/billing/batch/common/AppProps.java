package com.mhgad.za.vitel.billing.batch.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class AppProps {
    private String startDate;
    private String endDate;
    
    @Value("${fetch.size}")
    private Integer fetchSize;
    @Value("${chunk.size}")
    private Integer chunkSize;
    @Value("${paging.size}")
    private Integer pagingSize;
    
    @Value("${output.path}")
    private String outputPath;
    
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
