package org.taljaard.dao;

import org.taljaard.model.ThermostatData;

public interface ThermostatDAO {

	public int saveData(ThermostatData data);
	public ThermostatData updateData(ThermostatData updatedData);
	public int deleteData(ThermostatData data);
	
}
