package tp6;

import java.util.Observable;

public class WeatherModel extends Observable {

	private WeatherInfo _weatherInfo;
	
	public WeatherInfo getWeatherInfo() {
		return _weatherInfo;
	}

	public void setWeatherInfo(WeatherInfo info) {
		_weatherInfo = info;
		setChanged();
		notifyObservers();
	}
}
