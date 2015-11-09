package tp6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

class SqliteWeatherDal implements IWeatherLoadingStoringDal {

	private final Connection connection;
	private final Statement statement;

	public SqliteWeatherDal(String connectionString) throws SQLException {
		this.connection = DriverManager.getConnection(connectionString);
		statement = connection.createStatement();
		statement.setQueryTimeout(30);

		statement.execute("CREATE TABLE IF NOT EXISTS WeatherInfo ("
				+ "Name STRING PRIMARY KEY, "
				+ "Temp DOUBLE, "
				+ "TempMin DOUBLE, "
				+ "TempMax DOUBLE, "
				+ "Humidity DOUBLE, "
				+ "Timestamp LONG"
				+ ")"
		);
	}

	@Override
	public WeatherInfo loadWeatherInfo(String city) {
		try {
			WeatherInfo res = new WeatherInfo();
			ResultSet rs = this.statement.executeQuery("SELECT * FROM WeatherInfo WHERE Name = \"" + city + "\";");
			if (!rs.next() || Calendar.getInstance().getTime().getTime() - rs.getLong("Timestamp") > 10 * 60 * 1000)
				return null;

			res.name = rs.getString("Name");
			res.info.temp = rs.getDouble("Temp");
			res.info.minTemp = rs.getDouble("TempMin");
			res.info.maxTemp = rs.getDouble("TempMax");
			res.info.humidity = rs.getInt("Humidity");
			return res;
		} catch (SQLException ex) {
			return null;
		}
	}

	@Override
	public void storeWeatherInfo(WeatherInfo info) {
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM WeatherInfo WHERE Name = \"" + info.name + "\";");
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			long curr = now.getTime();
			if (!rs.next())
				statement.execute("INSERT INTO WeatherInfo VALUES(\""
						+ info.name + "\", "
						+ info.info.temp + ", "
						+ info.info.minTemp + ", "
						+ info.info.maxTemp + ", "
						+ info.info.humidity + ", "
						+ curr + ")");
			else
				statement.execute("UPDATE WeatherInfo SET "
						//		+ "Name = \"" + info.name + "\","
						+ "Temp = " + info.info.temp + ", "
						+ "TempMin = " + info.info.minTemp + ", "
						+ "TempMax = " + info.info.maxTemp + ", "
						+ "Humidity = " + info.info.humidity + ", "
						+ "Timestamp = " + curr + " WHERE Name = \"" + info.name + "\";"
				);
		} catch (SQLException ex) {
			throw new RuntimeException("SQLException while storing data for " + info.name + " : " + ex.getMessage(), ex);
		}
	}

	public void sortWeatherInfoByCity() {
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM WeatherInfo ORDER BY Name ASC");
			while (rs.next()) {
				System.out.println("name = " + rs.getString("Name"));
				System.out.println("Temp = " + rs.getInt("Temp"));
				System.out.println("TempMin = " + rs.getInt("TempMin"));
				System.out.println("TempMax = " + rs.getInt("TempMax"));
				System.out.println("Humidity = " + rs.getInt("Humidity"));
			}
		} catch (SQLException ex) {
			throw new RuntimeException("SQLException while database requesting");
		}
	}

	public void sortWeatherInfoByTemp() {
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM WeatherInfo ORDER BY Temp DESC");
			while (rs.next()) {
				System.out.println("name = " + rs.getString("Name"));
				System.out.println("Temp = " + rs.getInt("Temp"));
				System.out.println("TempMin = " + rs.getInt("TempMin"));
				System.out.println("TempMax = " + rs.getInt("TempMax"));
				System.out.println("Humidity = " + rs.getInt("Humidity"));
			}
		} catch (SQLException ex) {
			throw new RuntimeException("SQLException while database requesting");
		}
	}
}
