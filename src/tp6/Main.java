package tp6;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		final IWeatherView view = new SwingWeatherView();
		final WeatherModel model = new WeatherModel();
		final IWeatherLoadingDal lDal = new OpenWeatherMapWeatherDal();
		final IWeatherLoadingStoringDal sDal = new SqliteWeatherDal("jdbc:sqlite:sample.db");

		IWeatherController ctrl = new WeatherController(model, view, lDal, sDal);
		view.run();
	}
}
