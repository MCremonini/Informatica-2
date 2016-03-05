package galliacapocciona.ui;

import galliacapocciona.model.CalcolatoreSeggiMaggioritario;
import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GalliaPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MyController controller;
	private JComboBox<String> cmbMetodo;
	private JTextField txtSeggi;
	private GalliaElectionPane outputPane;
	
	public GalliaPanel(MyController controller) {
		this.controller = controller;
		
		initGUI();
	}
	
	private void initGUI() {
		setBackground(Color.YELLOW);

		JLabel lbl = new JLabel("Consiglio della Gallia Capocciona");
		lbl.setFont(new Font("Verdana", Font.BOLD, 18));
		add(lbl, BorderLayout.NORTH);
		
		add(new JLabel("Metodo:"), BorderLayout.NORTH);
		cmbMetodo = new JComboBox<String>(controller.getCalcolatoriSeggi());
		cmbMetodo.setSelectedIndex(-1);
		cmbMetodo.addActionListener(this);
		add(cmbMetodo, BorderLayout.NORTH);
		
		add(new JLabel("Seggi:"), BorderLayout.NORTH);
		txtSeggi = new JTextField("" + controller.getSeggiMinimi(), 4);
		add(txtSeggi, BorderLayout.NORTH);
		
		Map<String,Integer> mappaSeggi = new HashMap<String, Integer>();
		List<Partito> partiti = Collegio.generaListaPartiti(controller.getListaCollegi());
		for(Partito p : partiti)
			mappaSeggi.put(p.getNome(), 0);
		
		outputPane = new GalliaElectionPane(Collegio.generaListaPartiti(controller.getListaCollegi()), mappaSeggi );
		add(outputPane, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == cmbMetodo) {
			String metodo = (String) cmbMetodo.getSelectedItem();
			
			int numSeggi = 0;
			try {
				numSeggi = Integer.parseInt(txtSeggi.getText());
			}
			catch(NumberFormatException ex) {
				controller.getUserInteractor().showMessage("il parametro \"seggi\" deve essere un intero");
				txtSeggi.setText("" + controller.getSeggiMinimi());
				return;
			}
			
			Map<String, Integer> mapSeggi = controller.calcola(metodo, numSeggi);
			if(mapSeggi != null)
				outputPane.update(mapSeggi);
			else
				txtSeggi.setText("" + controller.getSeggiMinimi());
		}
	}
}
