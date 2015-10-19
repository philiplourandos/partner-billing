package com.mhgad.za.vitel.billing.batch.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 *
 * @author plourand
 */
@Repository
public class TestRepo {
    
    private static final String COUNT_CDR_ENTRIES =
            "   SELECT "
            + "     COUNT(uniqueid) AS total"
            + " FROM "
            + "     cdr";
    
    @Autowired
    private JdbcOperations ops;

    public Integer countCdrs() {
        return ops.queryForObject(COUNT_CDR_ENTRIES, Integer.class);
    }
}
