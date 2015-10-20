package com.mhgad.za.vitel.billing.batch.mapper;

import com.mhgad.za.vitel.billing.batch.model.DbServer;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author plourand
 */
public class DbServersMapper implements RowMapper<DbServer> {
    private static final int ID_INDEX = 1;
    private static final int URL_INDEX = 2;
    private static final int USERNAME_INDEX = 3;
    private static final int PASSWORD_INDEX = 4;
    private static final int CATALOG_INDEX = 5;

    public DbServersMapper() {
    }

    @Override
    public DbServer mapRow(ResultSet rs, int i) throws SQLException {
        DbServer pojo = new DbServer();
        pojo.setId(rs.getInt(ID_INDEX));
        pojo.setUrl(rs.getString(URL_INDEX));
        pojo.setUsername(rs.getString(USERNAME_INDEX));
        pojo.setPassword(rs.getString(PASSWORD_INDEX));
        pojo.setCatalog(rs.getString(CATALOG_INDEX));

        return pojo;
    }
}
