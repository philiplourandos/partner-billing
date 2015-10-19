package com.mhgad.za.vitel.billing.batch;

import javax.annotation.PostConstruct;
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
@PropertySource(value="classpath:test.properties")
public class TestConfiguration {

    @PostConstruct
    public void initDb() {
        EmbeddedDatabase ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test-db.sql")
                .setName("partnerbilling")
                .build();
    }
}
