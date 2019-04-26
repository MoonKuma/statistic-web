package com.sincetimes.statisticweb.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author MoonKuma
 * @since 2019/4/24
 */

@Configuration
public class JdbcConfig {

    // *************** Source ****************
    @Bean(name = "statBaseSource")
    @Qualifier("statBaseSource")
    @ConfigurationProperties(prefix="spring.datasource.stat-base-source")
    public DataSource statBaseSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "statPaySource")
    @Qualifier("statPaySource")
    @ConfigurationProperties(prefix="spring.datasource.stat-pay-source")
    public DataSource statPaySource() {
        return DataSourceBuilder.create().build();
    }

    // *************** JdbcTemplate ****************
    @Bean(name = "statBaseJdbcTemplate")
    @Qualifier("statBaseJdbcTemplate")
    public JdbcTemplate statBaseJdbcTemplate(
            @Qualifier("statBaseSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean(name = "statPayJdbcTemplate")
    @Qualifier("statPayJdbcTemplate")
    public JdbcTemplate statPayJdbcTemplate(
            @Qualifier("statPaySource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // *************** NamedJdbcTemplate **************
    @Bean(name = "statBaseNamedParameterJdbcTemplate")
    @Qualifier("statBaseNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate statBaseNamedParameterJdbcTemplate(
            @Qualifier("statBaseJdbcTemplate") JdbcTemplate jdbcTemplate){
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Bean(name = "statPayNamedParameterJdbcTemplate")
    @Qualifier("statPayNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate statPayNamedParameterJdbcTemplate(
            @Qualifier("statPayJdbcTemplate") JdbcTemplate jdbcTemplate){
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

}
