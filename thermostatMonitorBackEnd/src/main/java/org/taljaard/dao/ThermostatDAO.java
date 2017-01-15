package org.taljaard.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.taljaard.model.ThermostatData;

@Repository
public interface ThermostatDAO {

	public int saveData(ThermostatData data);
	public ThermostatData updateData(ThermostatData updatedData);
	public int deleteData(ThermostatData data);
	public List<ThermostatData> getDataWithinTimeRange(DateTime start, DateTime end);
	
}
