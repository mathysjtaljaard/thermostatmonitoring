package org.taljaard.rest.client.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.taljaard.model.ThermostatData;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/thermostat/realtime")
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
}
