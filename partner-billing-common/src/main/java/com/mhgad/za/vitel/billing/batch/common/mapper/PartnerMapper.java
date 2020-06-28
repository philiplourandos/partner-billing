package com.mhgad.za.vitel.billing.batch.common.mapper;

import com.mhgad.za.vitel.billing.batch.common.model.PartnerMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PartnerMapper implements RowMapper<PartnerMapping> {

    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_ACC_CODE = "ACCOUNT_CODE";
    
    public PartnerMapper() {
    }

    @Override
    public PartnerMapping mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final PartnerMapping bean = new PartnerMapping();
        bean.setDisciplineGroupId(rs.getInt(COLUMN_ID));
        bean.setName(rs.getString(COLUMN_NAME));
        bean.setAccountCode(rs.getInt(COLUMN_ACC_CODE));

        return bean;
    }
}
