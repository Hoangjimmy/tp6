package tp6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
			// create a database connection
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			statement.executeUpdate("drop table if exists WeatherInfo");
			statement.executeUpdate("create table WeatherInfo (Name string Primary key, Temp double, TempMin double, TempMax double, humidity int)");
			statement.executeUpdate("insert into WeatherInfo values(\"Paris\", 200,201,202,203)");
			//statement.executeUpdate("insert into person values(2, 'yui')");
			ResultSet rs = statement.executeQuery("select * from WeatherInfo");
			while (rs.next()) {
				// read the result set
				System.out.println("name = " + rs.getString("Name"));
				System.out.println("Temp = " + rs.getInt("Temp"));
				System.out.println("TempMin = " + rs.getInt("TempMin"));
				System.out.println("TempMax = " + rs.getInt("TempMax"));
				System.out.println("Humidity = " + rs.getInt("Humidity"));
			}
		} catch (SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		IWeatherView view = new SwingWeatherView();
		WeatherModel model = new WeatherModel();
                IWeatherLoader loader = new SqliteWeatherLoader();
                WeatherInfo ll;
                ll=loader.loadWeatherInfo("Paris");
                System.out.println(ll.toString());
		IWeatherController ctrl = new WeatherController(model, view, loader);
		view.run();
	}
}
