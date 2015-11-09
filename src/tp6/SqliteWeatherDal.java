package tp6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SqliteWeatherDal implements IWeatherDal {

    private Connection connection;
    private Statement statement;

    public SqliteWeatherDal() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
    }

    @Override
    public WeatherInfo loadWeatherInfo(String city) {
        WeatherInfo res = new WeatherInfo();
        try {
            ResultSet rs = this.statement.executeQuery("SELECT Name FROM WeatherInfo WHERE Name = \"" + city + "\";");
            if(!rs.next())throw new RuntimeException() ;
                   
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

    @Override
    public void storeWeatherInfo(WeatherInfo info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
