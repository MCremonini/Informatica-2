package doc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import doc.model.Bevanda;
import doc.model.Cliente;

public class MainFrame extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	
	private MacchinaController controller;
	private JComboBox<Cliente> cmbClients;
	private JComboBox<Bevanda> cmbBevande;
	private JTextField txtPrezzo;
	private JButton btnAcquista;
	
	public MainFrame(MacchinaController controller) {
		this.controller = controller;
		
		createGUI();
	}
		
	
	private void createGUI() {
		setTitle("Dent-o-Coffee");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new GridLayout(4,2));
		{
					
			mainPanel.add(new JLabel("Cliente: "));
			cmbClients = new JComboBox<Cliente>();
			mainPanel.add(cmbClients);
			
			mainPanel.add(new JLabel("Bevanda:"));
			cmbBevande = new JComboBox<Bevanda>();
			mainPanel.add(cmbBevande);

			mainPanel.add(new JLabel("Prezzo:"));
			txtPrezzo = new JTextField();
			txtPrezzo.setEditable(false);
			mainPanel.add(txtPrezzo);
			
			btnAcquista = new JButton("Acquista");
			mainPanel.add(btnAcquista);
			
		}
		getContentPane().add(mainPanel, BorderLayout.PAGE_START);
			
		
		for(Cliente c : controller.getClienti())
			cmbClients.addItem(c);
		
		for(Bevanda b : controller.getBevande())
			cmbBevande.addItem(b);
		
		
		cmbClients.addActionListener(this);
		cmbBevande.addActionListener(this);
		btnAcquista.addActionListener(this);		
		
		pack();
		//setSize(300, 140);
	}
						
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAcquista)
			mostraPrezzo(true);
		else if(e.getSource() == cmbBevande)
			mostraPrezzo(false);
		else if(e.getSource() == cmbClients)
			mostraPrezzo(false);
	}
	
	private void mostraPrezzo(boolean b) {
		Bevanda selBevanda = (Bevanda)cmbBevande.getSelectedItem();
		Cliente selCliente = (Cliente)cmbClients.getSelectedItem();
		
		if(b)
			controller.acquistaBevanda(selBevanda, selCliente);
		
		txtPrezzo.setText(String.format("$ %.2f", controller.getPrezzoBevanda(selBevanda, selCliente)));
	}

}
