package tp6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SqliteWeatherLoader implements IWeatherLoader {

    private Statement statement;

    public SqliteWeatherLoader() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    @Override
    public WeatherInfo loadWeatherInfo(String city) {
        WeatherInfo res = new WeatherInfo();
        try {
            ResultSet rs = this.statement.executeQuery("SELECT Name FROM WeatherInfo Where Name = \" " + city + "\"");
            res.name = city;
            res.info.temp = rs.getDouble(2);
            res.info.minTemp = rs.getDouble(3);
            res.info.maxTemp = rs.getDouble(4);
            res.info.humidity = rs.getInt(5);
            return res;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return res;
    }
}
