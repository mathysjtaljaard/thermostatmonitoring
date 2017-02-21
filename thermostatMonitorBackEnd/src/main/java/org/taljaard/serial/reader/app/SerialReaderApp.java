package org.taljaard.serial.reader.app;

import org.taljaard.dao.ThermostatDAO;
import org.taljaard.serial.reader.events.SerialPortEventListener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

public class SerialReaderApp {

	private ThermostatDAO thermostatDAO;
	
	public SerialReaderApp( ThermostatDAO thermostatDAO) {
		System.out.println("ThermostatDAO dao " + thermostatDAO);
		this.thermostatDAO = thermostatDAO;
		init();
	}
	
	private void init() {
		try {
			initializeSerialPort();
			
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	private void initializeSerialPort() throws Exception {
		SerialPort comPort = null;
		
		try {
 			String osName = System.getProperty("os.name");
			System.out.printf("Ports available on Operating system: %s\n\n", osName);

			if (SerialPort.getCommPorts() != null && SerialPort.getCommPorts().length > 0) {
				comPort = SerialPort.getCommPorts()[0];
				System.out.printf("Available ports are -> %s\n", comPort.getDescriptivePortName());
				comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0);
				comPort.setBaudRate(115200);
				comPort.openPort();
				comPort.addDataListener(new SerialPortEventListener(comPort, thermostatDAO));
			} else {
				throw new Exception(" No communication Ports found. Unable to start monitoring");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			if (comPort != null) {
				comPort.closePort();
			}
			throw ex;
		}
	}

}
//Fan On Status -> 0 | Heat On Status -> 0 | Cooling On Status -> 0 | Aux Heat On Status -> 0 | Temperature (F) -> 68.225

