package ascensore.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ascensore.model.Ascensore;
import ascensore.model.Edificio;
import ascensore.ui.pulsantiera.Pulsantiera;

public class MainPanel extends JPanel implements ActionListener, MainView {

	private static final long serialVersionUID = 1L;
	private MyController controller;
	private JComboBox<Edificio> cmbEdifici;
	private Pulsantiera pulsantiera;
	private JTextArea output;
	private JTextField txtPiano;

	public MainPanel(Controller controller) {
		this.controller = (MyController)controller;
		
		initGUI();
		controller.setMainView(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		
		JPanel upperPanel = new JPanel();
		{
			upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
			cmbEdifici = new JComboBox<Edificio>(controller.getElencoEdifici().toArray(new Edificio[0]));
			cmbEdifici.setSelectedIndex(-1);
			cmbEdifici.addActionListener(this);
			upperPanel.add(cmbEdifici, BorderLayout.NORTH);
			
			
			txtPiano = new JTextField(2);
			txtPiano.setBackground(Color.BLACK);
			txtPiano.setFont(new Font("Verdana", Font.BOLD, 40));
			txtPiano.setForeground(Color.GREEN);
			txtPiano.setAlignmentX(CENTER_ALIGNMENT);
			upperPanel.add(txtPiano, BorderLayout.NORTH);
		}
		add(upperPanel, BorderLayout.NORTH);
		
		pulsantiera = new Pulsantiera();
		pulsantiera.addActionListener(this);
		pulsantiera.setPreferredSize(new Dimension(90,60));
		add(pulsantiera, BorderLayout.WEST);
		
		
		output = new JTextArea(20, 35);
		JScrollPane scrollpane = new JScrollPane(output);
		add(scrollpane, BorderLayout.CENTER);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == cmbEdifici) {
			Edificio edificio = (Edificio)cmbEdifici.getSelectedItem();
			
			Ascensore a = edificio.getAscensore();
			pulsantiera.setAscensore(a);
			controller.setAscensore(a);
			
			output.setText("");
			txtPiano.setText("" + controller.getAscensore().getPianoCorrente());
		}
		else if(obj == pulsantiera) {
			controller.vaiAlPiano(Integer.parseInt(e.getActionCommand()));
		}
	}

	@Override
	public void appendStatus(String status) {
		output.append(status + System.lineSeparator());		
	}

	@Override
	public void clearStatus() {
		output.setText("");		
	}

	@Override
	public void setPianoCorrente(int pianoCorrente) {
		txtPiano.setText("" + pianoCorrente);		
	}

}
