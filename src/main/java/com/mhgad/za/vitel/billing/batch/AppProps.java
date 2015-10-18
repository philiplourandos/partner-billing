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
}
