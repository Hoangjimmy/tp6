package tp6;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleWeatherView implements IWeatherView {

	/**
	 * The input stream
	 */
	private final Scanner in;
	/**
	 * The output stream
	 */
	public final PrintStream out;
	/**
	 * The error stream
	 */
	public final PrintStream err;

	private WeatherController _controller;
	
	public ConsoleWeatherView(InputStream in, PrintStream out, PrintStream err) {
		this.in = new Scanner(in);
		this.out = out;
		this.err = err;
	}

	@Override
	public void notifyModelChanged(WeatherModel wm) {
		WeatherInfo info = wm.getWeatherInfo();
		out.printf("Weather in %s\n", info.name);
		out.printf("----------\n");
		out.printf("T°     : %.3f°C\n", info.info.temp);
		out.printf("T° min : %.3f°C\n", info.info.minTemp);
		out.printf("T° max : %.3f°C\n", info.info.maxTemp);
		out.printf("Humid. : %d%%\n", info.info.humidity);
	}

	@Override
	public void run() {
		out.print("Entrez des noms de ville pour obtenir la météo, EOF pour quitter.\n> ");
		
		while(in.hasNextLine()) {
			_controller.loadWeather(in.nextLine());
			out.println("\n> ");
		}
	}

	@Override
	public void setController(WeatherController controller) {
		_controller = controller;
	}

	@Override
	public void notifyError(Exception ex) {
		err.print(ex.getMessage());
	}
}
