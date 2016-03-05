package zetabank.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import zetabank.model.Finanziamento;
import zetabank.model.Periodicita;
import zetabank.model.TipoFinanziamento;
import zetabank.ui.controller.Controller;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JComboBox<TipoFinanziamento> cmbFinanziamento; 
	private JTextField txtImporto;
	private JTextField txtAnni;
	private JTextField txtRataMax;
	private JButton btnCalcola;
	private JComboBox<Periodicita> cmbPeriodicita;
	private FinancingPane pane;
	
	public MainFrame(Controller controller) {
		
		this.controller = controller;
		
		setTitle("ZetaBank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
		
		setSize(550, 500);
	}
	
	private void initGUI() {
		
		JPanel cmdPanel = new JPanel();
		
		cmdPanel.add(new JLabel("Simulazione Finanziamento"), BorderLayout.NORTH);
		cmbFinanziamento = new JComboBox<TipoFinanziamento>(controller.getElencoTipiFinanziamento().toArray(new TipoFinanziamento[0]));
		cmdPanel.add(cmbFinanziamento, BorderLayout.NORTH);


		cmdPanel.add(new JLabel("Importo"), BorderLayout.NORTH);
		txtImporto = new JTextField(8);
		cmdPanel.add(txtImporto, BorderLayout.NORTH);
		
		cmdPanel.add(new JLabel("Durata anni"), BorderLayout.NORTH);
		txtAnni = new JTextField(3);
		cmdPanel.add(txtAnni, BorderLayout.NORTH);
		
		cmdPanel.add(new JLabel("Rata max"), BorderLayout.NORTH);
		txtRataMax = new JTextField(8);
		cmdPanel.add(txtRataMax, BorderLayout.NORTH);
		
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("Periodicità"));
		box.add(Box.createHorizontalStrut(8));
		cmbPeriodicita = new JComboBox<Periodicita>(Periodicita.values());
		box.add(cmbPeriodicita);
		
		btnCalcola = new JButton("Calcola");
		btnCalcola.addActionListener(this);
		box.add(Box.createHorizontalStrut(50));
		box.add(btnCalcola);
		cmdPanel.add(box, BorderLayout.NORTH);
		
		
		pane = new FinancingPane();
		cmdPanel.add(pane, BorderLayout.CENTER);
		
		getContentPane().add(cmdPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		/*
		// sarebbe stato più usabile
		if(obj == cmbFinanziamento) {
			cmbPeriodicita.removeAllItems();
			
			TipoFinanziamento tipo = (TipoFinanziamento) cmbFinanziamento.getSelectedItem();
			
			for(Periodicita p : tipo.getPeriodicitaAmmesse())
				cmbPeriodicita.addItem(p);
			
			pane.reset();
		}
		else*/ if(obj == btnCalcola) {
			
			pane.reset();
			
			TipoFinanziamento tipo = (TipoFinanziamento) cmbFinanziamento.getSelectedItem();			
			Periodicita p = (Periodicita) cmbPeriodicita.getSelectedItem();
			
			if(tipo.getPeriodicitaAmmesse().contains(p)) {
				
				double importo = 0;
				double rataMax = Double.MAX_VALUE;
				int durata = 0;
				try {
					importo = Double.parseDouble(txtImporto.getText());
					durata = Integer.parseInt(txtAnni.getText());
					String tmp = txtRataMax.getText();
					if(!tmp.isEmpty())
						rataMax = Double.parseDouble(tmp);
					
					if(importo > 0 && durata > 0) {
						Optional<Finanziamento> val = controller.calcAndCheck(tipo, importo, durata, p, rataMax);
						if(val.isPresent()) {
							pane.update(val.get().getRate());
						}
					}
					else
						controller.getUserInteractor().showMessage("Capitale, rata max e anni devono essere numeri");
				}
				catch(NumberFormatException ex) {
					controller.getUserInteractor().showMessage("Capitale, rata max e anni devono essere numeri");
				}
			}
			else
				controller.getUserInteractor().showMessage("Periodicità non ammessa per questo tipo di finanziamento");
		}
	}

}
