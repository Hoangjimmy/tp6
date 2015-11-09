package tp6;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WeatherController implements Observer, IWeatherController {

	private final WeatherModel _model;
	private final IWeatherView _view;
	private final List<IWeatherLoadingDal> _loadingDals;
	private final IWeatherStoringDal _storingDal;

	public WeatherController(WeatherModel model, IWeatherView view, List<IWeatherLoadingDal> loadingDals, IWeatherStoringDal storingDal) {
		_model = model;
		_view = view;
		_loadingDals = loadingDals;
		_storingDal = storingDal;

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
		WeatherInfo info = null;
		Exception topExc = null, botExc = null;

		for (Iterator<IWeatherLoadingDal> ite = _loadingDals.iterator(); ite.hasNext() && info == null;)
			try {
				info = ite.next().loadWeatherInfo(city);
			} catch (Exception ex) {
				if (topExc == null)
					topExc = botExc = ex;
				else {
					botExc.initCause(ex);
					botExc = ex;
				}
			}

		if (info == null) {
			String msg = "No data source could answer the request for weather in " + city + ".";
			RuntimeException exc;
			if (topExc != null) {
				msg += " Exceptions were thrown by one or more data sources.";
				exc = new RuntimeException(msg);
				exc.initCause(topExc);
			} else
				exc = new RuntimeException(msg);
			_view.notifyError(exc);
			return;
		}

		_storingDal.storeWeatherInfo(info);
		_model.setWeatherInfo(info);
	}
	    public void storeWeatherInfo(WeatherInfo w) {
            _storingDal.storeWeatherInfo(w);
    }
}
