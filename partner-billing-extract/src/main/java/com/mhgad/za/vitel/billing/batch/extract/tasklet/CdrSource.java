package com.mhgad.za.vitel.billing.batch.extract.tasklet;

import javax.sql.DataSource;

/**
 *
 * @author plourand
 */
public class CdrSource {
    private final DataSource ds;
    
    private final Integer siteId;

    public CdrSource(final DataSource ds, final Integer siteId) {
        this.ds = ds;
        this.siteId = siteId;
    }

    public DataSource getDs() {
        return ds;
    }

    public Integer getSiteId() {
        return siteId;
    }
}
