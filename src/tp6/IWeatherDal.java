package tp6;

public interface IWeatherDal {
	WeatherInfo loadWeatherInfo(String city);
	void storeWeatherInfo(WeatherInfo info);
}
