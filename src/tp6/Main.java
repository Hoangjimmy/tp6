package tp6;

public class Main {

	public static void main(String[] args) {
		IWeatherView view = new SwingWeatherView();
		WeatherModel model = new WeatherModel();
		IWeatherLoader loader = new SqliteWeatherLoader();
		IWeatherController ctrl = new WeatherController(model, view, loader);
		view.run();
	}
}
