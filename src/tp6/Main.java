package tp6;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException {
		final IWeatherView view = new SwingWeatherView();
		final WeatherModel model = new WeatherModel();
		final IWeatherLoadingDal lDal = new OpenWeatherMapWeatherDal();
		final IWeatherStoringDal sDal = new SqliteWeatherDal("jdbc:sqlite:sample.db");
		
		IWeatherController ctrl = new WeatherController(model, view, new ArrayList<IWeatherLoadingDal>(){{add(lDal);}}, sDal);		
		view.run();
	}
}
