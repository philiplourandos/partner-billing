package com.mhgad.za.vitel.billing.batch.repo;

import com.mhgad.za.vitel.billing.batch.mapper.DbServersMapper;
import com.mhgad.za.vitel.billing.batch.model.DbServer;
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
    private static final String FIND_ALL_SERVER =
            "   SELECT "
            + "     dbs.id,"
            + "     dbs.jdbc_url,"
            + "     dbs.jdbc_username,"
            + "     dbs.jdbc_password,"
            + "     dbs.jdbc_catalog "
            + " FROM "
            + "     DB_SERVERS dbs";

    @Autowired
    private JdbcOperations ops;

    public PartnerBillingRepo() {
    }

    public List<DbServer> findAllServers() {
        return ops.query(FIND_ALL_SERVER, new DbServersMapper());
    }
}
