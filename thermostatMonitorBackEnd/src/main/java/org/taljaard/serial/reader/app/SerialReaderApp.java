package org.taljaard.serial.reader.app;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.taljaard.dao.ThermostatDAO;
import org.taljaard.dao.ThermostatDAOImpl;
import org.taljaard.datasource.IDataSource;
import org.taljaard.datasource.MySqlDataSource;
import org.taljaard.serial.reader.events.SerialPortEventListener;

import com.fazecast.jSerialComm.SerialPort;

public class SerialReaderApp {

	private static ThermostatDAO thermostatDAO = null;

	public static void main(String[] args) {
		try {
			initializeDatabase();
			initializeSerialPort();
			
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}
		
	}
	
	private static void initializeSerialPort() throws Exception {
		SerialPort comPort = null;;
		
		try {
			
			String osName = System.getProperty("os.name");
			System.out.printf("Ports available on Operating system: %s\n\n", osName);
			for (SerialPort commPort: SerialPort.getCommPorts()) {
				System.out.printf("Available ports are -> %s\n", commPort.getDescriptivePortName());
			}
			
			comPort = SerialPort.getCommPorts()[0];
			comPort.setBaudRate(115200);
			comPort.openPort();
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 2000, 0);
			comPort.addDataListener(new SerialPortEventListener(comPort, thermostatDAO));
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			if (comPort != null) {
				comPort.closePort();
			}
			throw ex;
		}
	}

	static void initializeDatabase() throws Exception {
		try {
			IDataSource dataSource = new MySqlDataSource();
			thermostatDAO = new ThermostatDAOImpl(dataSource.getDataSource());
		} catch(Exception ex) {
			System.err.println("Exception during initialization");
			ex.printStackTrace(System.err);
			throw ex;
		}
	}
	
	@Bean
	DataSource dataSource() {
		IDataSource dataSource = new MySqlDataSource();
		return dataSource.getDataSource();
	}
	
	@Bean
	ThermostatDAO thermostatDAO(DataSource dataSource) {
		ThermostatDAO dao = new ThermostatDAOImpl(dataSource);
		return dao;
	}

}
