package org.taljaard.rest.client.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.taljaard.model.ThermostatData;
import org.taljaard.rest.client.services.ThermostatServices;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/thermostat")
public class ThermostatController {

	@Autowired
	ThermostatServices service;
	
	@RequestMapping(method=RequestMethod.GET, value="realtime")
	public List<ThermostatData> getInitialDataSet() {

		List<ThermostatData> data = service.getLast4HoursData();
		return data;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="range")
	public List<ThermostatData> getDateRangeValues(
									@RequestParam("start") String startDate,
									@RequestParam("end") String endDate	) {
		
		DateTime start = new DateTime(startDate);
		DateTime end = new DateTime(endDate);
		List<ThermostatData> data = service.getDataWithinRange(start, end);
		
		return data;
	}
	
}
