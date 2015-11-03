package tp6;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SwingWeatherView implements IWeatherView {

	WeatherController _controller;
	JFrame _frame;
	JTextField _cityField;
	JButton _goButton;
	JLabel[] _labels, _values;

	@Override
	public void setController(WeatherController controller) {
		_controller = controller;
	}

	@Override
	public void run() {
		_frame = new JFrame("Météo");
		Container cP = _frame.getContentPane();
		cP.setLayout(new GridLayout(0, 2));

		ActionListener goListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controller.loadWeather(_cityField.getText());
			}
		};
		
		_cityField = new JTextField();
		_cityField.addActionListener(goListener);
		cP.add(_cityField);

		_goButton = new JButton("Go");
		_goButton.addActionListener(goListener);
		cP.add(_goButton);

		_labels = new JLabel[]{
			new JLabel("City : ", SwingConstants.RIGHT),
			new JLabel("T° : ", SwingConstants.RIGHT),
			new JLabel("T° min : ", SwingConstants.RIGHT),
			new JLabel("T° max : ", SwingConstants.RIGHT),
			new JLabel("Humid. : ", SwingConstants.RIGHT)
		};

		_values = new JLabel[_labels.length];
		for (int i = 0; i < _values.length; ++i) {
			_values[i] = new JLabel("-", SwingConstants.CENTER);
			cP.add(_labels[i]);
			cP.add(_values[i]);
		}

		_frame.setSize(200, 200);
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void notifyModelChanged(WeatherModel wm) {
		final WeatherInfo info = wm.getWeatherInfo();
		_values[0].setText(info.name);
		_values[0].setToolTipText(info.name);
		_values[1].setText(String.format("%.2f°C", info.info.temp));
		_values[2].setText(String.format("%.2f°C", info.info.minTemp));
		_values[3].setText(String.format("%.2f°C", info.info.maxTemp));
		_values[4].setText(String.format("%d%%", info.info.humidity));
		
		_cityField.selectAll();
	}

	@Override
	public void notifyError(Exception ex) {
		JOptionPane.showMessageDialog(_frame, ex, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
