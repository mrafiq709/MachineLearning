import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class SignGraph extends JFrame {

	private static final long serialVersionUID = 1L;

	public SignGraph() throws FileNotFoundException, IOException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 500);

		DataTable data = new DataTable(Double.class, Double.class);

		for (double x = -5.0; x <= 5.0; x += 0.25) {
			double y = 5.0 * Math.sin(x);
			data.add(x, y);
		}

		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(data, lines);

		Color color = new Color(0.0f, 1.0f, 0.0f);
		Color color2 = new Color(1.0f, 0.0f, 0.0f);
		plot.getPointRenderer(data).setColor(color2);
		plot.getLineRenderer(data).setColor(color);

	}

	public static void main(String[] args) {
		SignGraph frame = null;

		try {
			frame = new SignGraph();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.setVisible(true);

	}

}
