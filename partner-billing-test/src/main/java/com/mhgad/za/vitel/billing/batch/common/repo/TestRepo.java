package com.mhgad.za.vitel.billing.batch.common.repo;

import com.mhgad.za.vitel.billing.batch.common.SqlConst;
import com.mhgad.za.vitel.billing.batch.extract.mapper.CdrMapper;
import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepo {
    
    private static final String COUNT_CDR_ENTRIES =
            """
            SELECT
                COUNT(uniqueid) AS total
            FROM 
                cdr
            """;
            

    private static final String FIND_CDR_BY_DST_CHANNEL = SqlConst.RETRIEVE_CDR_RECORDS_SELECT 
            + " " + SqlConst.RETRIEVE_CDR_RECORDS_FROM
            + " WHERE "
            + "     dstchannel = ?";
    
    private static final String COUNT_ASPIVIA = 
            """
            SELECT
                COUNT(id)
            FROM
                aspivia
            """;

    @Autowired
    private JdbcOperations ops;

    public Integer countCdrs() {
        return ops.queryForObject(COUNT_CDR_ENTRIES, Integer.class);
    }
        
    public List<Cdr> findByDestChannel(final String destChannel) {
        return ops.query(FIND_CDR_BY_DST_CHANNEL, ps -> ps.setString(1, destChannel), new CdrMapper());
    }
    
    public Integer countAspivia() {
        return ops.queryForObject(COUNT_ASPIVIA, Integer.class);
    }
}
