package ascensore.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;

	public MainFrame(Controller controller) {
		super("Simulatore ascensori");
		setSize(550, 500);
		mainPanel = new MainPanel(controller);
		this.getContentPane().add(mainPanel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
