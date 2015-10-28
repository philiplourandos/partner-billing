package com.mhgad.za.vitel.billing.batch.extract.repo;

import com.mhgad.za.vitel.billing.batch.extract.mapper.DbServersMapper;
import com.mhgad.za.vitel.billing.batch.extract.mapper.SiteMapper;
import com.mhgad.za.vitel.billing.batch.extract.model.DbServer;
import com.mhgad.za.vitel.billing.batch.extract.model.Site;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 *
 * @author plourand
 */
@Repository
public class PartnerBillingRepo {
    private static final String FIND_ALL_SERVERS =
            "   SELECT "
            + "     dbs.id,"
            + "     dbs.jdbc_url,"
            + "     dbs.jdbc_username,"
            + "     dbs.jdbc_password, "
            + "     dbs.site_id "
            + " FROM "
            + "     DB_SERVERS dbs";
    
    private static final String FIND_ALL_SITES = 
            "   SELECT "
            + "     s.id, "
            + "     s.name, "
            + "     s.description, "
            + "     s.outputFile "
            + " FROM "
            + "     site s";

    @Autowired
    private JdbcOperations ops;

    public PartnerBillingRepo() {
    }

    public List<DbServer> findAllServers() {
        return ops.query(FIND_ALL_SERVERS, new DbServersMapper());
    }

    public List<Site> findAllSites() {
        return ops.query(FIND_ALL_SITES, new SiteMapper());
    }
}
