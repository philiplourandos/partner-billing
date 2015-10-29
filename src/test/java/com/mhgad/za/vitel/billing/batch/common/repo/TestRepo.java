package com.mhgad.za.vitel.billing.batch.common.repo;

import com.mhgad.za.vitel.billing.batch.common.SqlConst;
import com.mhgad.za.vitel.billing.batch.extract.mapper.CdrMapper;
import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import java.util.List;
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

    private static final String FIND_CDR_BY_DST_CHANNEL = SqlConst.RETRIEVE_CDR_RECORDS_SELECT 
            + " " + SqlConst.RETRIEVE_CDR_RECORDS_FROM
            + " WHERE "
            + "     dstchannel = ?";

    @Autowired
    private JdbcOperations ops;

    public Integer countCdrs() {
        return ops.queryForObject(COUNT_CDR_ENTRIES, Integer.class);
    }
        
    public List<Cdr> findByDestChannel(String destChannel) {
        return ops.query(FIND_CDR_BY_DST_CHANNEL, new Object[]{destChannel}, new CdrMapper());
    }
}
