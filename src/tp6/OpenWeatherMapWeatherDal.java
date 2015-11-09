package tp6;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class OpenWeatherMapWeatherDal implements IWeatherDal {

	@Override
	public WeatherInfo loadWeatherInfo(String city) {

		try {
			
			String urlRoot = "http://api.openweathermap.org/data/2.5/weather";
			String appId = "bd82977b86bf27fb59a04b61b657fb6f";
			
			String urlStr = String.format("%s?q=%s&appid=%s&units=metric", urlRoot, URLEncoder.encode(city, "UTF-8"), appId);
			
			try {
				URL url = new URL(urlStr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				try {
					InputStream istream = new BufferedInputStream(conn.getInputStream());
					
					Reader reader = new InputStreamReader(istream);
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					WeatherInfo weatherInfo = gson.fromJson(reader, WeatherInfo.class);
					
					if (weatherInfo.code != 200)
						throw new RuntimeException("The server returned code " + weatherInfo.code + " : " + weatherInfo.message);
					
					return weatherInfo;
					
				} finally {
					conn.disconnect();
				}
			} catch (MalformedURLException ex) {
				throw new RuntimeException("Malformed URL : " + urlStr, ex);
			} catch (IOException ex) {
				throw new RuntimeException("I/O exception while contacting " + urlStr + " : " + ex.getMessage(), ex);
			}
		} catch (UnsupportedEncodingException ex) {
				throw new RuntimeException("Unsupported encoding UTF-8.", ex);
		}
	}

	@Override
	public void storeWeatherInfo(WeatherInfo info) {
		/* Nothing, one can't push to OpenWeatherMap */
	}
}
