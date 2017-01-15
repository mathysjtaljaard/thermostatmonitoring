package org.taljaard.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public interface IDataSource {

	public DataSource getDataSource();
	
	public void initializeDataSource(Environment env);
	
}
