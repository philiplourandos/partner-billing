package com.mhgad.za.vitel.billing.batch.tasklet;

import javax.sql.DataSource;

/**
 *
 * @author plourand
 */
public class CdrSource {
    private DataSource ds;
    
    private Integer siteId;

    public CdrSource() {
    }

    public CdrSource(DataSource ds, Integer siteId) {
        this.ds = ds;
        this.siteId = siteId;
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}
