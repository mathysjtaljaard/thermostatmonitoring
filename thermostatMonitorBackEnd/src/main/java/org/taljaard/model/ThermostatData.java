package org.taljaard.model;

import java.sql.Timestamp;

public class ThermostatData {

	private int acOn;
	private int acOnCounter;
	private Timestamp acOnTime;
	private int auxHeatOn;
	private int auxHeatOnCounter;
	private Timestamp auxHeatOnTime;
	private Timestamp createTime;
	private String createTimeISO;
	private int fanOn;
	private int fanOnCounter;
	private Timestamp fanOnTime;
	private int heatOn;
	private int heatOnCounter;
	private Timestamp heatOnTime;
	private int id;
	private String rawData;
	private double temperature;
	
	public ThermostatData() {
	}

	public int getAcOn() {
		return acOn;
	}

	public int getAcOnCounter() {
		return acOnCounter;
	}

	public Timestamp getAcOnTime() {
		return acOnTime;
	}

	public int getAuxHeatOn() {
		return auxHeatOn;
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

	public int getFanOn() {
		return fanOn;
	}

	public int getFanOnCounter() {
		return fanOnCounter;
	}

	public Timestamp getFanOnTime() {
		return fanOnTime;
	}

	public int getHeatOn() {
		return heatOn;
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

	public void setAcOn(int acOn) {
		this.acOn = acOn;
	}

	public void setAcOnCounter(int acOnCounter) {
		this.acOnCounter = acOnCounter;
	}

	public void setAcOnTime(Timestamp acOnTime) {
		this.acOnTime = acOnTime;
	}

	public void setAuxHeatOn(int auxHeatOn) {
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

	public void setFanOn(int fanOn) {
		this.fanOn = fanOn;
	}

	public void setFanOnCounter(int fanOnCounter) {
		this.fanOnCounter = fanOnCounter;
	}

	public void setFanOnTime(Timestamp fanOnTime) {
		this.fanOnTime = fanOnTime;
	}

	public void setHeatOn(int heatOn) {
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
		builder.append("Is Fan on: " + getFanOn() + "\n");
		builder.append("Is Heat on: " + getHeatOn() + "\n");
		builder.append("Is Aux on: " + getAuxHeatOn() + "\n");
		builder.append("Is AC on: " + getAcOn() + "\n");
		builder.append("Temperature : " + getTemperature() + "\n");
		
		return builder.toString();
	}
}
