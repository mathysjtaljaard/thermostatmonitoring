package org.taljaard.serial.reader.data.parsers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.taljaard.dao.ThermostatDAO;
import org.taljaard.model.ThermostatData;

public class SerialDataParser {

	static String newLineCharacter = System.getProperty("line.separator");
	
	private String newData;
	private ThermostatDAO thermostatDAO;
	
	public SerialDataParser(String newData, ThermostatDAO thermostatDAO) {
		this.newData = newData;
		this.thermostatDAO = thermostatDAO;
	}

	public void parseData() throws Exception {
		ThermostatData receivedThermostatData = new ThermostatData();
		
		try {
			receivedThermostatData.setRawData(this.newData);
			List<String> splitValues = Arrays.asList(StringUtils.split(this.newData, newLineCharacter));
			
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
			
			compareReceivedToLastStoredRecord(receivedThermostatData);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			throw ex;
		}
		
	}
	
    private void compareReceivedToLastStoredRecord(ThermostatData recievedData) throws Exception {
		
    	try {
	    	ThermostatData storedRecored = collectLastDataEntry();
			System.out.println("Cached Data:\n" + storedRecored.toString());
			System.out.println("Received Data:\n" + recievedData.toString());
			
			boolean hasFanOnStatusChanged = (recievedData.isFanOn() != storedRecored.isFanOn());
			boolean hasHeatOnStatusChanged = (recievedData.isHeatOn() != storedRecored.isHeatOn());
			boolean hasAuxHeatOnStatusChanged = (recievedData.isAuxHeatOn() != storedRecored.isAuxHeatOn());
			boolean hasAcOnStatusChanged = (recievedData.isAcOn() != storedRecored.isAcOn());
			boolean hasTemperatureChanged = false;
			
			double diff = storedRecored.getTemperature() - recievedData.getTemperature();
			
			if (diff < 0 ) {
				diff *= -1;
			}
			System.out.println("diff = " + diff);
			if (diff > .50) {
				hasTemperatureChanged = true;
			}
			
			if (recievedData.getCreateTime() != null && recievedData.getTemperature() != 0) {
				if (hasAcOnStatusChanged || hasAuxHeatOnStatusChanged || hasFanOnStatusChanged 
																		|| hasHeatOnStatusChanged || hasTemperatureChanged) {
					this.thermostatDAO.saveData(recievedData);
				}
			} else {
				System.err.println("Unable to write data with null create time.");
			}
    	} catch(Exception ex) {
    		ex.printStackTrace(System.err);
    		throw ex;
    	}
	}

	private ThermostatData collectLastDataEntry() throws Exception {
		try {
			List<ThermostatData> response = this.thermostatDAO.getLastDataEntry();
			if (response != null && !response.isEmpty() && response.get(0) != null ) {
				return response.get(0);
			} else {
				throw new Exception("Unable to get previous Data. Please check database connection");
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			throw ex;
		}
		
	}
	
	
	
	
	
}
