package tp6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SqliteWeatherDal implements IWeatherLoadingDal, IWeatherStoringDal {

	private Connection connection;
	private Statement statement;

	public SqliteWeatherDal(String connectionString) throws SQLException {
		this.connection = DriverManager.getConnection(connectionString);
		statement = connection.createStatement();
		statement.setQueryTimeout(30);
	}

	@Override
	public WeatherInfo loadWeatherInfo(String city) {
		WeatherInfo res = new WeatherInfo();
		try {
			ResultSet rs = this.statement.executeQuery("SELECT * FROM WeatherInfo WHERE Name = \"" + city + "\";");
			if (!rs.next())
				throw new RuntimeException("No database entry for " + city + ".");

			res.name = rs.getString("Name");
			res.info.temp = rs.getDouble("Temp");
			res.info.minTemp = rs.getDouble("TempMin");
			res.info.maxTemp = rs.getDouble("TempMax");
			res.info.humidity = rs.getInt("Humidity");
			return res;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return res;
	}

	@Override
	public void storeWeatherInfo(WeatherInfo info) {
		String query = new StringBuilder("INSERT INTO WeatherInfo VALUES(\"")
				.append(info.name).append("\", ")
				.append(info.info.temp).append(", ")
				.append(info.info.minTemp).append(", ")
				.append(info.info.maxTemp).append(", ")
				.append(info.info.humidity)
				.append(");")
				.toString();
		try {
			statement.executeQuery(query);
		} catch (SQLException ex) {
			throw new RuntimeException("SQLException while storing data `" + query + "`" + " : " + ex.getMessage(), ex);
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
