package com.mhgad.za.vitel.billing.batch.extract.biz;

import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class CdrPrepStatementSetter implements ItemPreparedStatementSetter<Cdr> {

    private static final int UNIQUE_ID_INDEX = 1;
    private static final int CALL_DATE_INDEX = 2;
    private static final int CLID_INDEX = 3;
    private static final int SRC_INDEX = 4;
    private static final int DEST_INDEX = 5;
    private static final int DCONTEXT_INDEX = 6;
    private static final int CHANNEL_INDEX = 7;
    private static final int DEST_CHANNEL_INDEX = 8;
    private static final int LASTAPP_INDEX = 9;
    private static final int LAST_DATA_INDEX = 10;
    private static final int DURATION_INDEX = 11;
    private static final int BILLING_SECONDS_INDEX = 12;
    private static final int DISPOSITION_INDEX = 13;
    private static final int AMA_FLAGS_INDEX = 14;
    private static final int ACCOUNT_CODE_INDEX = 15;
    private static final int USERFIELD_INDEX = 16;
    private static final int COST_INDEX = 17;
    private static final int SITE_INDEX = 18;
    
    private Integer siteId;
    
    public CdrPrepStatementSetter(final Integer siteId) {
        this.siteId = siteId;
    }

    @Override
    public void setValues(final Cdr item, final PreparedStatement ps) throws SQLException {
        ps.setString(UNIQUE_ID_INDEX, item.getUniqueid());
        ps.setTimestamp(CALL_DATE_INDEX, new Timestamp(item.getCallDate().getTime()));
        ps.setString(CLID_INDEX, item.getClid());
        ps.setString(SRC_INDEX, item.getSrc());
        ps.setString(DEST_INDEX, item.getDst());
        ps.setString(DCONTEXT_INDEX, item.getDcontext());
        ps.setString(CHANNEL_INDEX, item.getChannel());
        ps.setString(DEST_CHANNEL_INDEX, item.getDstchannel());
        ps.setString(LASTAPP_INDEX, item.getLastapp());
        ps.setString(LAST_DATA_INDEX, item.getLastdata());
        ps.setInt(DURATION_INDEX, item.getDuration());
        ps.setInt(BILLING_SECONDS_INDEX, item.getBillsec());
        ps.setString(DISPOSITION_INDEX, item.getDisposition());
        ps.setInt(AMA_FLAGS_INDEX, item.getAmaflags());
        ps.setString(ACCOUNT_CODE_INDEX, item.getAccountcode());
        ps.setString(USERFIELD_INDEX, item.getUserfield());
        ps.setDouble(COST_INDEX, item.getCost());
        ps.setInt(SITE_INDEX, siteId);
    }
}
