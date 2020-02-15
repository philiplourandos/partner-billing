package com.mhgad.za.vitel.billing.batch.aspivia.components;

import com.mhgad.za.vitel.billing.batch.aspivia.AttributeEnum;
import com.mhgad.za.vitel.billing.batch.aspivia.model.BillingItem;
import java.sql.Timestamp;
import java.text.ParseException;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 *
 * @author plourand
 */
public class AspiviaFieldSetter implements FieldSetMapper<BillingItem> {

    private static final Logger LOG = LogManager.getLogger(AspiviaFieldSetter.class);

    private static final int INDEX_EXTENSION = 0;
    private static final int INDEX_PBX_DATE_TIME = 1;
    private static final int INDEX_CALL_DURATION = 2;
    private static final int INDEX_ACCOUNT_CODE = 3;
    private static final int INDEX_DIGITS = 4;
    private static final int INDEX_COST = 5;
    private static final int INDEX_DEST = 6;
    private static final int INDEX_CARRIER = 7;
    private static final int INDEX_ATTR = 8;

    private final FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public AspiviaFieldSetter() {
    }

    @Override
    public BillingItem mapFieldSet(FieldSet fieldSet) throws BindException {
        BillingItem entry = new BillingItem();

        try {
            entry.setExtension(fieldSet.readString(INDEX_EXTENSION));
            entry.setPbxDateTime(new Timestamp(formatter.parse(
                    fieldSet.readString(INDEX_PBX_DATE_TIME)).getTime()));
            entry.setCallDuration(fieldSet.readInt(INDEX_CALL_DURATION));
            entry.setAccountCode(fieldSet.readInt(INDEX_ACCOUNT_CODE));
            entry.setDigits(fieldSet.readString(INDEX_DIGITS));
            entry.setCost(fieldSet.readBigDecimal(INDEX_COST));
            entry.setDestination(fieldSet.readString(INDEX_DEST));
            entry.setCarrier(fieldSet.readString(INDEX_CARRIER));
            entry.setAttribute(AttributeEnum.forVal(fieldSet.readInt(INDEX_ATTR)));
        } catch (ParseException pe) {
            LOG.error("Timestamp in file incorrect. Value is: {}",
                    fieldSet.readString(INDEX_PBX_DATE_TIME));
        }

        return entry;
    }
}
