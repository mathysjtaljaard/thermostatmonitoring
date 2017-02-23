package org.taljaard.rest.client.services;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taljaard.dao.ThermostatDAO;
import org.taljaard.model.ThermostatData;

@Service
public class ThermostatServices {

	@Autowired
	ThermostatDAO thermostatDAO;
	
	public List<ThermostatData> getLast4HoursData() {
		DateTime end = new DateTime();
		DateTime start = end.minusHours(4);
		
		List<ThermostatData> data = thermostatDAO.getDataWithinTimeRange(start, end);
		
		return data;
	}
	
	public List<ThermostatData> getDataWithinRange(DateTime startDate, DateTime endDate) {
		
		DateTime start = startDate.withTimeAtStartOfDay();
		DateTime end = endDate.plusDays(1).withTimeAtStartOfDay();
		
		List<ThermostatData> data = thermostatDAO.getDataWithinTimeRange(start, end);
		
		return data;
	}
	
	public void saveData(ThermostatData receivedData) throws Exception {
		compareReceivedToLastStoredRecord(receivedData);
	}
	
	private void compareReceivedToLastStoredRecord(ThermostatData recievedData) throws Exception {
		
    	try {
	    	ThermostatData storedRecored = collectLastDataEntry();
			
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
			
			if (recievedData.getCreateTime() == null) {
				System.err.println("Create Time was null, Unable to write to database.");
			} else if (recievedData.getTemperature() == 0) {
				System.err.println("Temperature was 0, Unable to write data to database.");
				System.err.println("Raw data was -> " + recievedData.getRawData());
				
			} else {
				if (hasAcOnStatusChanged || hasAuxHeatOnStatusChanged || hasFanOnStatusChanged 
																		|| hasHeatOnStatusChanged || hasTemperatureChanged) {
					this.thermostatDAO.saveData(recievedData);
				}
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
