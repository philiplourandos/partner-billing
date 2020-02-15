package com.mhgad.za.vitel.billing.batch.common.repo;

import com.mhgad.za.vitel.billing.batch.common.mapper.PartnerMapper;
import com.mhgad.za.vitel.billing.batch.common.model.PartnerMapping;
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
    
    private static final String FIND_PARTNER_NAME =
            "   SELECT "
            + "     p.name "
            + " FROM "
            + "     partner p,"
            + "     partner_group pg,"
            + "     site s "
            + " WHERE "
            + "     s.name = ?"
            + "     AND"
            + "     pg.site_id = s.id"
            + "     AND"
            + "     p.group_id = pg.id"
            + "     AND"
            + "     p.accountcode = ?";
    
    private static final String FIND_SITE_BY_NAME =
            "   SELECT "
            + "     id "
            + " FROM "
            + "     site s "
            + " WHERE "
            + "     s.name = ?";
    
    private static final String FIND_PARTNERS_BY_ACCOUNT_CODE_QUERY =
            "   SELECT "
            + "     p.name AS NAME,"
            + "     dg.id AS ID,"
            + "     p.accountCode AS ACCOUNT_CODE "
            + " FROM "
            + "     discipline_group dg,"
            + "     partner p"
            + " WHERE "
            + "     dg.site_id = ? "
            + "     AND "
            + "     dg.id = p.discipline_id "
            + "     AND "
            + "     p.accountCode = ? "
            + " ORDER BY "
            + "     p.name";

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

    public String findPartnerName(String siteName, Integer accountCode) {
        return ops.queryForObject(FIND_PARTNER_NAME, String.class, siteName, accountCode);
    }

    public Integer findSiteIdByName(String name) {
        return ops.queryForObject(FIND_SITE_BY_NAME, Integer.class, name);
    }

    public PartnerMapping findPartnerByAccountCode(Integer siteId, Integer accountCode) {
        return ops.queryForObject(FIND_PARTNERS_BY_ACCOUNT_CODE_QUERY, new PartnerMapper(),
                new Object[]{siteId, accountCode});
    }
}
