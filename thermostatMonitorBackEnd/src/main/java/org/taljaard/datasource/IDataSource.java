package org.taljaard.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jdbc.properties")
public interface IDataSource {

	public DataSource getDataSource();
	
	public void initializeDataSource(Environment env);
	
}
