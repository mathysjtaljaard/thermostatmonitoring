package org.taljaard.rest.client.app;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.taljaard.dao.ThermostatDAO;
import org.taljaard.dao.ThermostatDAOImpl;
import org.taljaard.datasource.IDataSource;
import org.taljaard.datasource.MySqlDataSource;
import org.taljaard.serial.reader.app.SerialReaderApp;

@SpringBootApplication
@ComponentScan()
public class ThermostatRestClient {

	public static void main(String[] args) {
		SpringApplication.run(ThermostatRestClient.class, args);
	}
	
	@Bean
	DataSource dataSource() {
		IDataSource dataSource = new MySqlDataSource();
		return dataSource.getDataSource();
	}
	
	@Bean
	ThermostatDAO thermostatDAO(DataSource dataSource) {
		return new ThermostatDAOImpl(dataSource);
	}
	
	@Bean
	SerialReaderApp serialReaderApp(ThermostatDAO thermostatDAO) {
		return new SerialReaderApp(thermostatDAO);
	}
}
