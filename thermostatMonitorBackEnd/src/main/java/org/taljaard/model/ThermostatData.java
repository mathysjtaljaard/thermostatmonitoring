package org.taljaard.model;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class ThermostatData {

	private int id;
	private String createTimeISO;
	private Timestamp createTime;
	private boolean fanOn;
	private boolean heatOn;
	private boolean auxHeatOn;
	private boolean acOn;
	private double temperature;
	private String rawData;
	
	public ThermostatData() {
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getCreateTimeISO() {
		return createTimeISO;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		this.createTimeISO = new DateTime(createTime.getTime()).toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public boolean isFanOn() {
		return fanOn;
	}

	public void setFanOn(boolean fanOn) {
		this.fanOn = fanOn;
	}

	public boolean isHeatOn() {
		return heatOn;
	}

	public void setHeatOn(boolean heatOn) {
		this.heatOn = heatOn;
	}

	public boolean isAuxHeatOn() {
		return auxHeatOn;
	}

	public void setAuxHeatOn(boolean auxHeatOn) {
		this.auxHeatOn = auxHeatOn;
	}

	public boolean isAcOn() {
		return acOn;
	}

	public void setAcOn(boolean acOn) {
		this.acOn = acOn;
	}
	
	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("Data: ");
		builder.append("Create Time: " + getCreateTime() + "\n");
		builder.append("Is Fan on: " + isFanOn() + "\n");
		builder.append("Is Heat on: " + isHeatOn() + "\n");
		builder.append("Is Aux on: " + isAuxHeatOn() + "\n");
		builder.append("Is AC on: " + isAcOn() + "\n");
		builder.append("Temperature : " + getTemperature() + "\n");
		
		return builder.toString();
	}
}
