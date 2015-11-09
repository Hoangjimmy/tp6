package tp6;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WeatherController implements Observer, IWeatherController {

	private final WeatherModel _model;
	private final IWeatherView _view;
	private final IWeatherLoadingDal _liveDal;
	private final IWeatherLoadingStoringDal _cacheDal;

	public WeatherController(WeatherModel model, IWeatherView view, IWeatherLoadingDal loadingDals, IWeatherLoadingStoringDal storingDal) {
		_model = model;
		_view = view;
		_liveDal = loadingDals;
		_cacheDal = storingDal;

		_model.addObserver(this);
		_view.setController(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		assert o instanceof WeatherModel;

		_view.notifyModelChanged((WeatherModel) o);
	}

	@Override
	public void loadWeather(String city) {
		WeatherInfo info;
		try {
			info = _cacheDal.loadWeatherInfo(city);

			if (info == null) {

				info = _liveDal.loadWeatherInfo(city);

				if (info == null)
					throw new RuntimeException("Couldn't load weather.");
			}
		} catch (Exception ex) {
			_view.notifyError(new RuntimeException("Couldn't load weather.", ex));
			return;
		}
		
		_model.setWeatherInfo(info);

		try {
		_cacheDal.storeWeatherInfo(info);
		} catch (Exception ex) {
			_view.notifyError(new RuntimeException("Couldn't cache weather.", ex));
		}
	}
}
