package tp6;

public interface IWeatherView {

	void setController(WeatherController controller);
	
	void notifyModelChanged(WeatherModel wm);

	void run();

	void notifyError(Exception ex);
}
