package org.taljaard.serial.reader.events;

import org.taljaard.dao.ThermostatDAO;
import org.taljaard.serial.reader.data.parsers.SerialDataParser;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialPortEventListener implements SerialPortDataListener {
	private SerialPort port;
	private ThermostatDAO thermostatDAO;
	
	public SerialPortEventListener(SerialPort port, ThermostatDAO thermostatDAO) throws Exception {
		System.out.println("Initializing SerialPortEventListener");
		this.port = port;
		this.thermostatDAO = thermostatDAO;
	}

	@Override
	public int getListeningEvents() {

		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		
		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
			return;
		}
		System.out.println(this.toString());
		try {
			int numberOfBytesToRead = this.port.bytesAvailable();
			System.out.println("number of bytes to read => " + numberOfBytesToRead);
			if (numberOfBytesToRead > 100) {
				
				byte[] newByteData = new byte[numberOfBytesToRead];
				this.port.readBytes(newByteData, newByteData.length);
				String newData = new String(newByteData);
				System.out.println("Data received in SerialPortEventListener: " + newData);
				SerialDataParser parser = new SerialDataParser(newData, this.thermostatDAO);
				parser.parseData();
			}
		} catch (Exception ex) {
			System.err.println("Exception during parseData. Exception was:");
			ex.printStackTrace(System.err);
		}
	}
	

}
