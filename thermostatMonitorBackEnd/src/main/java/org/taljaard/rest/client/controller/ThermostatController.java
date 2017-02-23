package org.taljaard.rest.client.controller;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.taljaard.model.ThermostatData;
import org.taljaard.rest.client.services.ThermostatServices;

import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/thermostat")
public class ThermostatController {

	@Autowired
	ThermostatServices service;
	
	Gson gson = new Gson();
	
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
	
	@RequestMapping(path="/data", method=RequestMethod.POST)
	public String consumeData(@RequestBody String data) {
		try {
			System.out.println("data received");
			System.out.println(data.toString());
			
			ThermostatData thermostatData = gson.fromJson(data, ThermostatData.class);
			DateTime dt = ISODateTimeFormat.dateTime().parseDateTime(thermostatData.getCreateTimeISO());
			thermostatData.setCreateTime(new Timestamp(dt.getMillis()));
			thermostatData.setRawData(data);
			service.saveData(thermostatData);
			return "{'response': 'success'}";
		} catch(Exception e) {
			return "{'response': 'failure'}";
		}
		
	}
}
