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
	ThermostatDAO thermostatDataAccessObject;
	
	public List<ThermostatData> getLast4HoursData() {
		DateTime end = new DateTime();
		DateTime start = end.minusHours(4);
		
		List<ThermostatData> data = thermostatDataAccessObject.getDataWithinTimeRange(start, end);
		
		return data;
	}
	
	public List<ThermostatData> getDataWithinRange(DateTime startDate, DateTime endDate) {
		
		DateTime start = startDate.withTimeAtStartOfDay();
		DateTime end = endDate.plusDays(1).withTimeAtStartOfDay();
		
		List<ThermostatData> data = thermostatDataAccessObject.getDataWithinTimeRange(start, end);
		
		return data;
	}
	
}
