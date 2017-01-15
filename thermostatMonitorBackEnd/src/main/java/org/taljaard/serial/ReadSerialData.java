package org.taljaard.serial;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.taljaard.dao.ThermostatDAOImpl;
import org.taljaard.model.ThermostatData;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * https://github.com/Fazecast/jSerialComm/wiki/Event-Based-Reading-Usage-Example
 * 
 * @author mathysjtaljaard
 *
 */
public class ReadSerialData {

	static String newLineCharacter = System.getProperty("line.separator");
	static ThermostatData cachedData = new ThermostatData();
	static DriverManagerDataSource dataSource;
	static ThermostatDAOImpl thermostatDAOImpl;
	
	public static void main(String args[]) {
		
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.200:3306/thermostatTrackingdb");
		dataSource.setUsername("mathysjt");
		dataSource.setPassword("UtE0*IIx9Hta^W&jCjT0X9J2sW@lSm");
		
		thermostatDAOImpl = new ThermostatDAOImpl(dataSource);
		
		String osName = System.getProperty("os.name");
		System.out.printf("Ports available on Operating system: %s\n\n", osName);
		for (SerialPort commPort: SerialPort.getCommPorts()) {
			System.out.printf("Available ports are -> %s\n", commPort.getDescriptivePortName());
		}
		
		SerialPort comPort = SerialPort.getCommPorts()[0];
		comPort.setBaudRate(115200);
		comPort.openPort();
		comPort.addDataListener(new SerialPortDataListener() {
		   @Override
		   public int getListeningEvents() { 
			   
			   return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
		   @Override
		   public void serialEvent(SerialPortEvent event)
		   {
		      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
		         return;
		      
		      int numberOfBytesToRead = comPort.bytesAvailable();
		      byte[] newData = new byte[numberOfBytesToRead];
		      int numRead = comPort.readBytes(newData, newData.length);

		      if (numRead > 0 && newData.length == numRead) {
		    	  parseData(newData);
		      } else {
		    	  System.err.println("Number of Bits doesn't match data size ");
		      }
		   }
		});
	}
	
	static void parseData(byte[] receivedData) {
		ThermostatData receivedThermostatData = new ThermostatData();
		String data = new String(receivedData);
		List<String> splitValues = Arrays.asList(StringUtils.split(data, newLineCharacter));
		
		for(String value: splitValues) {
			String[] splits = StringUtils.split(value, ">");
			receivedThermostatData.setCreateTime(new Timestamp(new DateTime().getMillis()));
			
			if (StringUtils.contains(splits[0], "Status")) {
				String statusOf = splits[0];
				
				boolean on = false;
				if (Integer.valueOf(StringUtils.trim(splits[1])) == 1) {
					on = true;
				}
				
				if (StringUtils.contains(statusOf, "Fan")) {
					receivedThermostatData.setFanOn(on);
				} else if(StringUtils.contains(statusOf, "Aux")) {
					receivedThermostatData.setAuxHeatOn(on);
				} else if (StringUtils.contains(statusOf, "Heat")) {
					receivedThermostatData.setHeatOn(on);
				} else  if (StringUtils.contains(statusOf, "Cooling")) {
					receivedThermostatData.setAcOn(on);
				}
			} 
			else if (StringUtils.contains(splits[0], "Temperature")) {
				receivedThermostatData.setTemperature(Double.valueOf(StringUtils.trim(splits[1])));
			}
		}
		compareReceivedToCachedData(receivedThermostatData);
	}
	
	static private void compareReceivedToCachedData(ThermostatData recievedData) {
		
		System.out.println("Cached Data:\n" + cachedData.toString());
		System.out.println("Received Data:\n" + recievedData.toString());
		
		boolean hasFanOnStatusChanged = (recievedData.isFanOn() != cachedData.isFanOn());
		boolean hasHeatOnStatusChanged = (recievedData.isHeatOn() != cachedData.isHeatOn());
		boolean hasAuxHeatOnStatusChanged = (recievedData.isAuxHeatOn() != cachedData.isAuxHeatOn());
		boolean hasAcOnStatusChanged = (recievedData.isAcOn() != cachedData.isAcOn());
		boolean hasTemperatureChanged = false;
		
		double diff = cachedData.getTemperature() - recievedData.getTemperature();
		
		if (diff < 0 ) {
			diff *= -1;
		}
		System.out.println("diff = " + diff);
		if (diff > .50) {
			hasTemperatureChanged = true;
		}
		
		if (recievedData.getCreateTime() != null && recievedData.getTemperature() != 0) {
			if (hasAcOnStatusChanged || hasAuxHeatOnStatusChanged || hasFanOnStatusChanged || hasHeatOnStatusChanged || hasTemperatureChanged) {
				System.out.println("data changed: new data\n");
				thermostatDAOImpl.saveData(recievedData);
				cachedData.setAcOn(recievedData.isAcOn());
				cachedData.setAuxHeatOn(recievedData.isAuxHeatOn());
				cachedData.setCreateTime(recievedData.getCreateTime());
				cachedData.setFanOn(recievedData.isFanOn());
				cachedData.setHeatOn(recievedData.isHeatOn());
				cachedData.setTemperature(recievedData.getTemperature());
			}
		} else {
			System.err.println("Unable to write data with null create time.");
		}
	}
}
