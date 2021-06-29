package com.pragmatic.todoList.postgres;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pragmatic.todoList.postgres", entityManagerFactoryRef = "secondTaskEntityManagerFactory", transactionManagerRef = "secondTaskTransactionManager")
public class SecondTaskDBConfig {

	@Bean(name = "secondTaskDatasource")
	@ConfigurationProperties(prefix = "spring.secondtask.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "secondTaskEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			@Qualifier("secondTaskDatasource") DataSource dataSource) {
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("driverClassName", "org.postgresql.Driver");
		return builder.dataSource(dataSource).properties(properties).packages("com.pragmatic.todoList.postgres")
				.persistenceUnit("SecondTaskEntity").build();
	}

	@Bean(name = "secondTaskTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("secondTaskEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
