package com.example.spring.securityjwt.config;


import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.spring.securityjwt.module",
            includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class)})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver relaxedPropertyResolver;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
        this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment,"spring.datasource.");
    }

    @Inject
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){return new JdbcTemplate((dataSource));}

    @Bean
    public  DataSource dataSource(){

        if(relaxedPropertyResolver.getProperty("url")==null && relaxedPropertyResolver.getProperty("databaseName")==null){
            Arrays.toString(environment.getActiveProfiles());
            throw  new ApplicationContextException("Database pool is not configured");
        }

        HikariConfig config  = new HikariConfig();
        config.setDataSourceClassName(relaxedPropertyResolver.getProperty("dataSourceClassName"));
        if(StringUtils.isEmpty(relaxedPropertyResolver.getProperty("url"))){
            config.addDataSourceProperty("databaseName",relaxedPropertyResolver.getProperty("databaseName"));
            config.addDataSourceProperty("serverName",relaxedPropertyResolver.getProperty("serverName"));
        } else {
            config.addDataSourceProperty("url",relaxedPropertyResolver.getProperty("url"));
        }
        config.addDataSourceProperty("user",relaxedPropertyResolver.getProperty("username"));
        config.addDataSourceProperty("password",relaxedPropertyResolver.getProperty("password"));
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(relaxedPropertyResolver.getProperty("max-active",Integer.class,10));
        config.setConnectionTimeout(15000);
        return new HikariDataSource(config);
    }

    @Bean
    public Flyway flyway(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setOutOfOrder(true);
        flyway.setValidateOnMigrate(false);
        flyway.setBaselineOnMigrate(true);
        flyway.repair();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Hibernate4Module hibernate4Module(){return new Hibernate4Module();}



}
