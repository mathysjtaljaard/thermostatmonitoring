package org.taljaard.enums;

public enum ThermostatAction {
	HEAT("heat"),
	AC("ac"),
	FAN("fan"),
	AUX_HEAT("aux heat");
	
	private String actionDescription;
	
	private ThermostatAction(String actionDescription) {
		// TODO Auto-generated constructor stub
		this.actionDescription = actionDescription;
	}
	
	public String getDescription() {
		return this.actionDescription;
	}
	
	/*
	public ThermostatAction valueOf(String value) {
		for (ThermostatAction action: ThermostatAction.values()) {
			if (action.actionDescription == value) {
				return action;
			}
		}
		return null;
	}*/
}
