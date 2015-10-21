package com.mhgad.za.vitel.billing.batch.mapper;

import com.mhgad.za.vitel.billing.batch.model.Site;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author plourand
 */
public class SiteMapper implements RowMapper<Site> {
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int DESCRIPTION_INDEX = 3;
    private static final int OUTPUT_FILE_NAME = 4;

    public SiteMapper() {
    }

    @Override
    public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site();
        site.setId(rs.getInt(ID_INDEX));
        site.setName(rs.getString(NAME_INDEX));
        site.setDescription(rs.getString(DESCRIPTION_INDEX));
        site.setOutputFile(rs.getString(OUTPUT_FILE_NAME));

        return site;
    }
}
