package com.mhgad.za.vitel.billing.batch.extract.mapper;

import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CdrMapper implements RowMapper<Cdr> {

    private static final String UNIQUE_ID_COLUMN_NAME = "uniqueid";
    private static final String CALL_DATE_COLUMN_NAME = "calldate";
    private static final String CLID_COLUMN_NAME = "clid";
    private static final String SRC_COLUMN_NAME = "src";
    private static final String DEST_COLUMN_NAME = "dst";
    private static final String DEST_CONTEXT_COLUMN_NAME = "dcontext";
    private static final String CHANNEL_COLUMN_NAME = "channel";
    private static final String DESTINATION_CHANNEL_COLUMN_NAME = "dstchannel";
    private static final String LAST_APP_COLUMN_NAME = "lastapp";
    private static final String LAST_DATA_COLUMN_NAME = "lastdata";
    private static final String DURATION_COLUMN_NAME = "duration";
    private static final String BILLING_SECONDS_COLUMN_NAME = "billsec";
    private static final String DISPOSITION_COLUMN_NAME = "disposition";
    private static final String AMA_FLAGS_COLUMN_NAME = "amaflags";
    private static final String ACC_CODE_COLUMN_NAME = "accountcode";
    private static final String USER_FIELD_COLUMN_NAME = "userfield";
    private static final String COST_COLUMN_NAME = "cost";
    
    public CdrMapper() {
    }

    @Override
    public Cdr mapRow(ResultSet rs, int i) throws SQLException {
        Cdr pojo = new Cdr();
        pojo.setAccountcode(rs.getString(ACC_CODE_COLUMN_NAME));
        pojo.setAmaflags(rs.getInt(AMA_FLAGS_COLUMN_NAME));
        pojo.setBillsec(rs.getInt(BILLING_SECONDS_COLUMN_NAME));
        pojo.setCallDate(rs.getTimestamp(CALL_DATE_COLUMN_NAME));
        pojo.setChannel(rs.getString(CHANNEL_COLUMN_NAME));
        pojo.setClid(rs.getString(CLID_COLUMN_NAME));
        pojo.setCost(rs.getDouble(COST_COLUMN_NAME));
        pojo.setDcontext(rs.getString(DEST_CONTEXT_COLUMN_NAME));
        pojo.setDisposition(rs.getString(DISPOSITION_COLUMN_NAME));
        pojo.setDst(rs.getString(DEST_COLUMN_NAME));
        pojo.setDstchannel(rs.getString(DESTINATION_CHANNEL_COLUMN_NAME));
        pojo.setDuration(rs.getInt(DURATION_COLUMN_NAME));
        pojo.setLastapp(rs.getString(LAST_APP_COLUMN_NAME));
        pojo.setLastdata(rs.getString(LAST_DATA_COLUMN_NAME));
        pojo.setSrc(rs.getString(SRC_COLUMN_NAME));
        pojo.setUniqueid(rs.getString(UNIQUE_ID_COLUMN_NAME));
        pojo.setUserfield(rs.getString(USER_FIELD_COLUMN_NAME));

        return pojo;
        
    }
}
