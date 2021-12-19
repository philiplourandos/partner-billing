package com.mhgad.za.vitel.billing.batch.aspivia.components;

import com.mhgad.za.vitel.billing.batch.aspivia.model.BillingItem;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Value;

@StepScope
public class AspiviaPrepStatementSetter implements ItemPreparedStatementSetter<BillingItem> {

    private static final int INDEX_EXTENSION = 1;
    private static final int INDEX_PBX_DATE_TIME = 2;
    private static final int INDEX_DURATION = 3;
    private static final int INDEX_ACC_CODE = 4;
    private static final int INDEX_DIGITS = 5;
    private static final int INDEX_COST = 6;
    private static final int INDEX_DEST = 7;
    private static final int INDEX_CARRIER = 8;
    private static final int INDEX_ATTRIBUTE = 9;
    private static final int INDEX_SITE = 10;

    @Value("#{jobExecutionContext['site.id']}")
    private Integer siteId;

    public AspiviaPrepStatementSetter() {
    }

    @Override
    public void setValues(final BillingItem item, final PreparedStatement ps) throws SQLException {
        ps.setString(INDEX_EXTENSION, item.getExtension());
        ps.setTimestamp(INDEX_PBX_DATE_TIME, item.getPbxDateTime());
        ps.setInt(INDEX_DURATION, item.getCallDuration());
        ps.setInt(INDEX_ACC_CODE, item.getAccountCode());
        ps.setString(INDEX_DIGITS, item.getDigits());
        ps.setBigDecimal(INDEX_COST, item.getCost());
        ps.setString(INDEX_DEST, item.getDestination());
        ps.setString(INDEX_CARRIER, item.getCarrier());
        ps.setInt(INDEX_ATTRIBUTE, item.getAttribute().getVal());
        ps.setInt(INDEX_SITE, siteId);
    }
}
