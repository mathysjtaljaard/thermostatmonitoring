package org.taljaard.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.taljaard.mappers.ThermostatDataRowMapper;
import org.taljaard.model.ThermostatData;

public class ThermostatDAOImpl implements ThermostatDAO {
	
	@Autowired
	DataSource dataSource;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ThermostatDAOImpl(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public int saveData(ThermostatData data) {
		SqlParameterSource beanParameterSource = new BeanPropertySqlParameterSource(data);
		String sql = "INSERT INTO trackingData (create_time, fanOn, heatOn, auxOn, acOn, temp, rawData)" + 
					 " VALUES (:createTime, :fanOn, :heatOn, :auxHeatOn, :acOn, :temperature, :rawData)";
		this.namedParameterJdbcTemplate.execute(sql, beanParameterSource, new PreparedStatementCallback<Object>() {

			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				return ps.executeUpdate();
			}
		});
		return 0;
	}

	@Override
	public int deleteData(ThermostatData data) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<ThermostatData> getWeeklyData(DateTime dayOfMonth) {
		
		Timestamp startOfDay = new Timestamp(dayOfMonth.withTimeAtStartOfDay().getMillis());
		Timestamp endOfDay = new Timestamp(dayOfMonth.plusDays(1).getMillis());
		String sql = "Select * from trackingData where create_time between :startOfDay and :endOfDay";
		Map<String, Timestamp> parameterMap = Collections.singletonMap("startOfDay", startOfDay);
		parameterMap.put("endOfDay", endOfDay);
		
		return this.namedParameterJdbcTemplate.queryForList(sql, parameterMap, ThermostatData.class);
	}

	public List<ThermostatData> getMonthlyData(DateTime weekDay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThermostatData updateData(ThermostatData updatedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThermostatData> getDataWithinTimeRange(DateTime start, DateTime end) {
		Timestamp startTime = new Timestamp(start.getMillis());
		Timestamp endTime = new Timestamp(end.getMillis());
		String sql = "Select * from trackingData where create_time between :startTime and :endTime";
		Map<String, Timestamp> parameterMap = new HashMap<>();
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		
		return this.namedParameterJdbcTemplate.query(sql, parameterMap, new ThermostatDataRowMapper());
	}

	@Override
	public List<ThermostatData> getLastDataEntry() {
		String sql = "Select * from trackingData order by tracking_id DESC limit 1";
		return this.namedParameterJdbcTemplate.query(sql, new ThermostatDataRowMapper());
	}

}
