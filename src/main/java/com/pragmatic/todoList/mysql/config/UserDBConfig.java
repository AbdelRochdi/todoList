package com.pragmatic.todoList.mysql.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pragmatic.todoList.mysql", entityManagerFactoryRef = "entityManagerFactory")
public class UserDBConfig {

	@Value("${mysql.hikari.pool.config}")
	private int poolSize;
	
	
	@Primary
	@Bean(name = "datasourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource primaryDataSource(
			@Qualifier("datasourceProperties") DataSourceProperties primaryDataSourceProperties) {
		
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setMaximumPoolSize(this.poolSize);
		return hikariDataSource;
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource") DataSource dataSource) {
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("driverClassName", "com.mysql.cj.jdbc.Driver");
		return builder.dataSource(dataSource).properties(properties).packages("com.pragmatic.todoList.mysql")
				.persistenceUnit("UserEntity").build();
	}

//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource.hikari")
//	public HikariConfig hikariConfig() {
//		return new HikariConfig();
//	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

//	@Bean
//	public DataSource dataSource() {
//		return new HikariDataSource(hikariConfig());
//	}
}
