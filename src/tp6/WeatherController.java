package tp6;

import java.util.Observable;
import java.util.Observer;

public class WeatherController implements Observer, IWeatherController {

	private final WeatherModel _model;
	private final IWeatherView _view;
	private final IWeatherLoader _loader;
	
	public WeatherController(WeatherModel model, IWeatherView view, IWeatherLoader loader) {
		_model = model;
		_view = view;
		_loader = loader;
		
		_model.addObserver(this);
		_view.setController(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		assert o instanceof WeatherModel;
		
		_view.notifyModelChanged((WeatherModel)o);
	}

	@Override
	public void loadWeather(String city) {
		try {
		_model.setWeatherInfo(_loader.loadWeatherInfo(city));
		} catch (Exception ex) {
			_view.notifyError(ex);
		}
	}
}
