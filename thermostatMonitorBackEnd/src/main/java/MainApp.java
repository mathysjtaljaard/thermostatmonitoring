import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.taljaard.dao.ThermostatDAOImpl;
import org.taljaard.model.ThermostatData;

public class MainApp {

	public static void main(String[] args) {
		try {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://192.168.1.200:3306/thermostatTrackingdb");
			dataSource.setUsername("mathysjt");
			dataSource.setPassword("UtE0*IIx9Hta^W&jCjT0X9J2sW@lSm");
			
			ThermostatDAOImpl thermostatData = new ThermostatDAOImpl(dataSource);
			List<ThermostatData> dataList = thermostatData.getDailyData(new DateTime().plusDays(1));
			
			for (ThermostatData data: dataList) {
				System.out.println(data);
			}
			
		} catch (Exception e) {
			System.err.println("Here is something wrong: " + e);
		}
	}
	
	
}
