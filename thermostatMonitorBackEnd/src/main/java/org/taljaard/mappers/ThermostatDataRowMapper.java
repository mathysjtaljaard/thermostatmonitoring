package org.taljaard.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.taljaard.model.ThermostatData;

public class ThermostatDataRowMapper implements RowMapper<ThermostatData> {

	@Override
	public ThermostatData mapRow(ResultSet rs, int rowNum) throws SQLException {
		ThermostatData data = new ThermostatData();

		data.setId(rs.getInt("tracking_id"));
		data.setAcOn(rs.getBoolean("acOn"));
		data.setAuxHeatOn(rs.getBoolean("auxOn"));
		data.setCreateTime(rs.getTimestamp("create_time"));
		data.setFanOn(rs.getBoolean("fanOn"));
		data.setHeatOn(rs.getBoolean("heatOn"));
		data.setTemperature(rs.getDouble("temp"));

		return data;
	}

}
