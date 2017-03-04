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
		data.setAcOn(rs.getInt("acOn"));
		data.setAuxHeatOn(rs.getInt("auxOn"));
		data.setCreateTime(rs.getTimestamp("create_time"));
		data.setFanOn(rs.getInt("fanOn"));
		data.setHeatOn(rs.getInt("heatOn"));
		data.setTemperature(rs.getDouble("temp"));
		data.setRawData(rs.getString("rawData"));
		return data;
	}

}
