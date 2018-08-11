package com.arjvik.arjmart.orderqueueconsumer.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.google.inject.Provider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseConnectionFactory implements Provider<DSLContext> {
	
	private DSLContext jooqConnection;
	
	public DatabaseConnectionFactory() {
		try {
			log.info("Initializing ConnectionFactory");
			Class.forName("com.mysql.jdbc.Driver");
			Properties properties = new Properties();
			InputStream in = DatabaseConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties");
			if (in == null)
				throw new RuntimeException("Could not read DB connection info, check if jdbc.properties exists in classpath");
			properties.load(in);
			String DB_URL = properties.getProperty("dburl");
			String DB_USER = properties.getProperty("dbuser");
			String DB_PASSWORD = properties.getProperty("dbpassword");
			if (DB_URL == null || DB_USER == null || DB_PASSWORD == null)
				throw new RuntimeException("Could not read DB connection info, check format of jdbc.properties ");
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(DB_URL);
			config.setUsername(DB_USER);
			config.setPassword(DB_PASSWORD);
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			HikariDataSource datasource = new HikariDataSource(config);
			jooqConnection = DSL.using(datasource, SQLDialect.MYSQL_5_7);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Error registering MySQL JDBC Driver", e);
		}
	}

	@Override
	public DSLContext get() {
		return jooqConnection;
	}
	
}
