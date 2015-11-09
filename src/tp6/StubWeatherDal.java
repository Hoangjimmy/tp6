package tp6;

public class StubWeatherDal implements IWeatherDal {

	@Override
	public WeatherInfo loadWeatherInfo(String city) {
		WeatherInfo info = new WeatherInfo();
		
		info.code = 200;
		info.name = city;
		info.info.temp = 23f;
		info.info.minTemp = 22.8f;
		info.info.maxTemp = 23.4f;
		info.info.humidity = 98;
		
		return info;
	}
}
