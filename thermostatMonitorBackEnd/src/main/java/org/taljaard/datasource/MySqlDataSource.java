package org.taljaard.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class MySqlDataSource implements IDataSource {

	@Autowired
	Environment env;
	
	private DataSource mySqlDataSource;
	
	@Override
	@Bean(name= "mysqlDataSource")
	public DataSource getDataSource() {

		if (mySqlDataSource == null) {
			initializeDataSource(env);
		}
		
		return mySqlDataSource;
	}

	@Override
	
	public void initializeDataSource(Environment env) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		mySqlDataSource = ds;

	}

}
