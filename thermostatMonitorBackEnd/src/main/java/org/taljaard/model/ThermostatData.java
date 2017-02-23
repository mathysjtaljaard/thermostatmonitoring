package org.taljaard.model;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class ThermostatData {

	private boolean acOn;
	private int acOnCounter;
	private Timestamp acOnTime;
	private boolean auxHeatOn;
	private int auxHeatOnCounter;
	private Timestamp auxHeatOnTime;
	private Timestamp createTime;
	private String createTimeISO;
	private boolean fanOn;
	private int fanOnCounter;
	private Timestamp fanOnTime;
	private boolean heatOn;
	private int heatOnCounter;
	private Timestamp heatOnTime;
	private int id;
	private String rawData;
	private double temperature;
	
	public ThermostatData() {
	}

	public int getAcOnCounter() {
		return acOnCounter;
	}

	public Timestamp getAcOnTime() {
		return acOnTime;
	}

	public int getAuxHeatOnCounter() {
		return auxHeatOnCounter;
	}

	public Timestamp getAuxHeatOnTime() {
		return auxHeatOnTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getCreateTimeISO() {
		return createTimeISO;
	}

	public int getFanOnCounter() {
		return fanOnCounter;
	}

	public Timestamp getFanOnTime() {
		return fanOnTime;
	}

	public int getHeatOnCounter() {
		return heatOnCounter;
	}

	public Timestamp getHeatOnTime() {
		return heatOnTime;
	}

	public int getId() {
		return id;
	}

	public String getRawData() {
		return rawData;
	}

	public double getTemperature() {
		return temperature;
	}

	public boolean isAcOn() {
		return acOn;
	}

	public boolean isAuxHeatOn() {
		return auxHeatOn;
	}

	public boolean isFanOn() {
		return fanOn;
	}

	public boolean isHeatOn() {
		return heatOn;
	}

	public void setAcOn(boolean acOn) {
		this.acOn = acOn;
	}

	public void setAcOnCounter(int acOnCounter) {
		this.acOnCounter = acOnCounter;
	}

	public void setAcOnTime(Timestamp acOnTime) {
		this.acOnTime = acOnTime;
	}

	public void setAuxHeatOn(boolean auxHeatOn) {
		this.auxHeatOn = auxHeatOn;
	}

	public void setAuxHeatOnCounter(int auxHeatOnCounter) {
		this.auxHeatOnCounter = auxHeatOnCounter;
	}

	public void setAuxHeatOnTime(Timestamp auxHeatOnTime) {
		this.auxHeatOnTime = auxHeatOnTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCreateTimeISO(String createTimeISO) {
		this.createTimeISO = createTimeISO;
	}

	public void setFanOn(boolean fanOn) {
		this.fanOn = fanOn;
	}

	public void setFanOnCounter(int fanOnCounter) {
		this.fanOnCounter = fanOnCounter;
	}

	public void setFanOnTime(Timestamp fanOnTime) {
		this.fanOnTime = fanOnTime;
	}

	public void setHeatOn(boolean heatOn) {
		this.heatOn = heatOn;
	}

	public void setHeatOnCounter(int heatOnCounter) {
		this.heatOnCounter = heatOnCounter;
	}

	public void setHeatOnTime(Timestamp heatOnTime) {
		this.heatOnTime = heatOnTime;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
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
