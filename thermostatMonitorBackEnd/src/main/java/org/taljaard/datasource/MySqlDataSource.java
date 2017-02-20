package org.taljaard.datasource;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class MySqlDataSource implements IDataSource {

	private DataSource mySqlDataSource;
	
	public DataSource getDataSource() {
		if (mySqlDataSource == null) {
			initializeDataSource(null);
		}
		return this.mySqlDataSource;
	}
	
	@Override
	public void initializeDataSource(Environment env) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.1.200:3306/thermostatTrackingdb");
		ds.setUsername("mathysjt");
		ds.setPassword("UtE0*IIx9Hta^W&jCjT0X9J2sW@lSm");
		this.mySqlDataSource = ds;
	}

}
