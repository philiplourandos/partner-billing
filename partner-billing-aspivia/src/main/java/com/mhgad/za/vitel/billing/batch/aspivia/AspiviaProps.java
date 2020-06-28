package com.mhgad.za.vitel.billing.batch.aspivia;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aspivia")
public class AspiviaProps {
    private Integer chunkSize;
    
    public AspiviaProps() {
    }

    public void setChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Integer getChunkSize() {
        return chunkSize;
    }
}
