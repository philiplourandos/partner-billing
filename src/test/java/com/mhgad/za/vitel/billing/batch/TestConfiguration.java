package com.mhgad.za.vitel.billing.batch;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 *
 * @author plourand
 */
@Configuration
@PropertySource("classpath:test.properties")
public class TestConfiguration {

    @Bean
    public DataSource testDb() {
        EmbeddedDatabase ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test-db.sql")
                .build();

        return ds;
    }
}
