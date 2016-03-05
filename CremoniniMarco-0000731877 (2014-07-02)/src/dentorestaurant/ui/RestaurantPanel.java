package dentorestaurant.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;

public class RestaurantPanel extends JPanel implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private String nomeCliente;
	private JComboBox<Menu> cmbMenu;
	private JComboBox<Portata> cmbAntipasti;
	private JComboBox<Portata> cmbPrimi;
	private JComboBox<Portata> cmbSecondi;
	private JComboBox<Portata> cmbDessert;
	private JTextField txtPrezzo;
	private Ordine ordine;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lbl_1;
	private JLabel lbl_2;
	private JLabel lbl_3;
	private JLabel lbl_4;
	
	public RestaurantPanel(Controller controller, String nomeCliente) {
		
		this.controller = controller;
		this.nomeCliente = nomeCliente;
		
		
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	
		JLabel lbl = new JLabel("Menu disponibili");
		panel.add(lbl);
		cmbMenu = new JComboBox<>();//controller.getMenus().toArray(new Menu[0])
		panel.add(cmbMenu);
		cmbMenu.setSelectedIndex(-1);
		cmbMenu.addActionListener(this);
		
		panel_1 = new JPanel();
		add(panel_1,BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		lbl_1 = new JLabel(Categoria.ANTIPASTO.toString());
		lbl_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbl_1);
		cmbAntipasti = new JComboBox<>();
		panel_1.add(cmbAntipasti);
		cmbAntipasti.setSelectedIndex(-1);
		
		lbl_2 = new JLabel(Categoria.PRIMO.toString());
		lbl_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbl_2);
		cmbPrimi = new JComboBox<>();
		panel_1.add(cmbPrimi);
		cmbPrimi.setSelectedIndex(-1);
		
		lbl_3 = new JLabel(Categoria.SECONDO.toString());
		lbl_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbl_3);
		cmbSecondi = new JComboBox<>();
		panel_1.add(cmbSecondi);
		cmbSecondi.setSelectedIndex(-1);
		
		lbl_4 = new JLabel(Categoria.DESSERT.toString());
		lbl_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbl_4);
		cmbDessert = new JComboBox<>();
		panel_1.add(cmbDessert);
		cmbDessert.setSelectedIndex(-1);
		cmbDessert.addActionListener(this);
		cmbSecondi.addActionListener(this);
		cmbPrimi.addActionListener(this);
		cmbAntipasti.addActionListener(this);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		add(panel2,BorderLayout.EAST);
		lbl = new JLabel("Prezzo totale");
		panel2.add(lbl, BorderLayout.CENTER);
		txtPrezzo = new JTextField(10);
		txtPrezzo.setEditable(false);
		panel2.add(txtPrezzo, BorderLayout.EAST);
		
		//fillComboPortate();
		//calcolaPrezzo();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == cmbMenu) {
			
			ordine = null;
			ordine = controller.creaOrdine(cmbMenu.getItemAt(cmbMenu.getSelectedIndex()), nomeCliente);
			
			fillComboPortate();
			
			
		}
		else if(obj == cmbAntipasti) {
			if(cmbAntipasti.getSelectedIndex() >= 0)
				controller.sostituisciPortata(ordine, cmbAntipasti.getItemAt(cmbAntipasti.getSelectedIndex()));
		}
		else if(obj == cmbPrimi) {
			if(cmbPrimi.getSelectedIndex() >= 0)
				controller.sostituisciPortata(ordine, cmbPrimi.getItemAt(cmbPrimi.getSelectedIndex()));
		}
		else if(obj == cmbSecondi) {
			if(cmbSecondi.getSelectedIndex() >= 0)
				controller.sostituisciPortata(ordine, cmbSecondi.getItemAt(cmbSecondi.getSelectedIndex()));
		}
		else if(obj == cmbDessert) {
			if(cmbDessert.getSelectedIndex() >= 0)
				controller.sostituisciPortata(ordine, cmbDessert.getItemAt(cmbDessert.getSelectedIndex()));
		}
		
		calcolaPrezzo();
	}
	
	
	private void fillComboPortate() {
		cmbAntipasti.removeAllItems();
		cmbPrimi.removeAllItems();
		cmbSecondi.removeAllItems();
		cmbDessert.removeAllItems();
		
		Menu m = cmbMenu.getItemAt(cmbMenu.getSelectedIndex());
		
		if(m != null) {
			for(Portata p : m.getPortate(Categoria.ANTIPASTO))
				cmbAntipasti.addItem(p);
			
			for(Portata p : m.getPortate(Categoria.PRIMO))
				cmbPrimi.addItem(p);
			
			for(Portata p : m.getPortate(Categoria.SECONDO))
				cmbSecondi.addItem(p);
			
			for(Portata p : m.getPortate(Categoria.DESSERT))
				cmbDessert.addItem(p);
		}
		
		/*cmbAntipasti.setSelectedIndex(0);
		cmbPrimi.setSelectedIndex(0);
		cmbSecondi.setSelectedIndex(0);
		cmbDessert.setSelectedIndex(0);*/
	}

	private void calcolaPrezzo() {
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		txtPrezzo.setText(formatter.format(ordine.getPrezzoTotale()));
	}
}
