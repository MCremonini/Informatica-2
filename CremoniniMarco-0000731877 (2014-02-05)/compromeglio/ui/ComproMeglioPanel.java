package compromeglio.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;

public class ComproMeglioPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private MyController controller;
	private JComboBox<Categoria> cmbCategorie;
	private JComboBox<Bene> cmbBeni;
	private JTextField txtPrezzoMedioCat;
	private JTextField txtPrezzoMedioBene;
	private JTextField txtPrezzoMiglioreBene;
	private JTextArea output;
	
	public ComproMeglioPanel(IController controller) {
		this.controller = (MyController)controller;
		
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		
		JPanel upperPanel = new JPanel();
		{
			upperPanel.setLayout(new GridLayout(2, 2));
			
			upperPanel.add(new JLabel("Categorie"));
			cmbCategorie = new JComboBox<Categoria>(controller.getCategorie().toArray(new Categoria[0]));
			cmbCategorie.setSelectedIndex(-1);
			cmbCategorie.addActionListener(this);
			upperPanel.add(cmbCategorie);
			
			upperPanel.add(new JLabel("Beni"));
			cmbBeni = new JComboBox<Bene>();
			cmbBeni.addActionListener(this);
			upperPanel.add(cmbBeni);
		}
		add(upperPanel, BorderLayout.NORTH);
		
		
		JPanel leftPanel = new JPanel();
		{
			leftPanel.setLayout(new GridLayout(3, 2));
			
			leftPanel.add(new JLabel("Prezzo medio categoria"));
			txtPrezzoMedioCat = new JTextField("", 10);
			txtPrezzoMedioCat.setEditable(false);
			leftPanel.add(txtPrezzoMedioCat);
			
			leftPanel.add(new JLabel("Prezzo migliore bene"));
			txtPrezzoMiglioreBene = new JTextField("", 10);
			txtPrezzoMiglioreBene.setEditable(false);
			leftPanel.add(txtPrezzoMiglioreBene);
			
			leftPanel.add(new JLabel("Prezzo medio bene"));
			txtPrezzoMedioBene = new JTextField("", 10);
			txtPrezzoMedioBene.setEditable(false);
			leftPanel.add(txtPrezzoMedioBene);
		}
		add(leftPanel, BorderLayout.WEST);
		
		output = new JTextArea(20, 30);
		JScrollPane scrollpane = new JScrollPane(output);
		add(scrollpane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == cmbCategorie) {
			Categoria cat = (Categoria)cmbCategorie.getSelectedItem();
			
			cmbBeni.removeAllItems();
			for(Bene b : controller.getBeniPerCategoria(cat)) {
				cmbBeni.addItem(b);
			}
			cmbBeni.setSelectedIndex(-1);
			
			
			txtPrezzoMedioCat.setText(controller.getPrezzoMedioPerCategoria(cat));
			txtPrezzoMiglioreBene.setText("");
			txtPrezzoMedioBene.setText("");
			output.setText("");
		}
		else if(obj == cmbBeni) {
			if(cmbBeni.getSelectedIndex() >= 0) {
				Bene bene = (Bene)cmbBeni.getSelectedItem();
				
				txtPrezzoMiglioreBene.setText(controller.getMigliorPrezzoPerBene(bene));
				txtPrezzoMedioBene.setText(controller.getPrezzoMedioPerBene(bene));
				output.setText(controller.getMigliorRilevazionePerBene(bene));
			}
		}
	}

}
