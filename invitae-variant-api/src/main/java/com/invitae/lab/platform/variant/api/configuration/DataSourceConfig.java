package com.invitae.lab.platform.variant.api.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.impl.DataSourceConnectionProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

/**
 * Application configuration for data sources backing the Variant API
 *
 * @author dgoodman
 */
@Configuration
@ComponentScan("com.invitae.lab.platform.variant.store")
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "invitae.data-stores.postgres.variant-service.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "invitae.data-stores.postgres.variant-service.hikari")
    public DataSource dataSource(final DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public DataSourceConnectionProvider connectionProvider(final DataSource dataSource) {
        return new DataSourceConnectionProvider(
                new TransactionAwareDataSourceProxy(dataSource));
    }
}
