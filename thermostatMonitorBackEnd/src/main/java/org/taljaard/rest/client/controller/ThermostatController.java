package org.taljaard.rest.client.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taljaard.model.ThermostatData;

@RestController
@RequestMapping("/thermostat")
public class ThermostatController {

	@RequestMapping(method=RequestMethod.GET, value="realtime")
	public ThermostatData realtime() {
		ThermostatData data = new ThermostatData();
		data.setAcOn(true);
		data.setAuxHeatOn(false);
		data.setCreateTime(new Timestamp(new Date().getTime()));
		data.setFanOn(true);
		data.setHeatOn(false);
		data.setTemperature(77.80);
		
		return data;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="day/{date}")
	public List<ThermostatData> getSelectedDaysData(@PathVariable(required=true, name="date") String date) {
		System.out.println("Given date is => " + date);
		List<ThermostatData> returnData = new ArrayList<ThermostatData>();
		
		ThermostatData data = new ThermostatData();
		data.setAcOn(true);
		data.setAuxHeatOn(false);
		data.setCreateTime(new Timestamp(new Date().getTime()));
		data.setFanOn(true);
		data.setHeatOn(false);
		data.setTemperature(77.80);
		
		ThermostatData data1 = new ThermostatData();
		data.setAcOn(true);
		data.setAuxHeatOn(false);
		data.setCreateTime(new Timestamp(new Date().getTime()));
		data.setFanOn(true);
		data.setHeatOn(false);
		data.setTemperature(77.80);
		returnData.add(data);
		returnData.add(data1);
		
		return returnData;
	}
}
