package teethcollege.esami.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import teethcollege.model.Carriera;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;

public class TeethCollegeExamPanel extends JPanel implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JComboBox<PianoDiStudi> cmbStudenti;
	private JRadioButton radioCarriera, radioEsami;
	private ButtonGroup grpView;
	private JTextArea output;
	private JComboBox<Insegnamento> cmbEsami;
	private JComboBox<String> cmbVoto;
	private JTextField txtData;
	private JButton btnInserisci;
//	private DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
	private Carriera carriera;
	
	public TeethCollegeExamPanel(Controller controller) {
		if(controller == null)
			throw new IllegalArgumentException();
		
		this.controller = controller;
		
		initGUI();
	}
	
	
	private void initGUI() {
		//setLayout(new BorderLayout());
		
		
		JPanel upperPanel = new JPanel();
		{			
			JLabel lbl = new JLabel("Studenti");
			upperPanel.add(lbl);
			cmbStudenti = new JComboBox<PianoDiStudi>(controller.getPianiDiStudi().toArray(new PianoDiStudi[0]));
			cmbStudenti.setPreferredSize(new Dimension(250, 20));
			cmbStudenti.addActionListener(this);
			upperPanel.add(cmbStudenti);
			
			lbl = new JLabel("Mostra:");
			upperPanel.add(lbl);
			
			radioCarriera = new JRadioButton("carriera");
			radioEsami = new JRadioButton("esami");
			radioEsami.addActionListener(this);
			radioCarriera.addActionListener(this);
			upperPanel.add(radioCarriera);
			upperPanel.add(radioEsami);
			grpView = new ButtonGroup();
			grpView.add(radioCarriera);
			grpView.add(radioEsami);
		}
		add(upperPanel, BorderLayout.NORTH);
		
		
		output = new JTextArea(30,65);
		output.setEditable(false);
		add(output, BorderLayout.CENTER);
		
		
		JPanel lowerPanel = new JPanel();
		{
			JLabel lbl = new JLabel("Nuovo esame");
			lowerPanel.add(lbl);
			cmbEsami = new JComboBox<Insegnamento>();
			PianoDiStudi p = (PianoDiStudi) cmbStudenti.getSelectedItem();
			
			carriera = new Carriera(p, controller.getEsami(p.getMatricola()));
			
			for(Insegnamento ins : p.getInsegnamenti()) {
				cmbEsami.addItem(ins);
			}
			cmbEsami.setPreferredSize(new Dimension(300, 20));
			lowerPanel.add(cmbEsami);
			
			lbl = new JLabel("voto");
			lowerPanel.add(lbl);
			cmbVoto = new JComboBox<String>();
			cmbVoto.setPreferredSize(new Dimension(60, 20));
			for(int i = 1; i <= 30; i++)
				cmbVoto.addItem(Integer.toString(i));
			cmbVoto.addItem("30L");
			lowerPanel.add(cmbVoto);
			
			lbl = new JLabel("data");
			lowerPanel.add(lbl);
			txtData = new JTextField("", 9);
			lowerPanel.add(txtData);
			
			btnInserisci = new JButton("Inserisci");
			btnInserisci.addActionListener(this);
			lowerPanel.add(btnInserisci);
		}
		add(lowerPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == cmbStudenti) {
			cmbEsami.removeAllItems();
			
			PianoDiStudi p = (PianoDiStudi) cmbStudenti.getSelectedItem();
			for(Insegnamento ins : p.getInsegnamenti()) {
				cmbEsami.addItem(ins);
			}
			
			carriera = null;
			carriera = new Carriera(p, controller.getEsami(p.getMatricola()));
			
			aggiornaOutput();
			
			
		}
		else if(o == radioCarriera || o == radioEsami) {
			aggiornaOutput();
		}
		else if(o == btnInserisci) {
			
			carriera = null;
			carriera = controller.aggiungiEsame((Insegnamento)cmbEsami.getSelectedItem(), (String)cmbVoto.getSelectedItem(),
									(String)txtData.getText(),(PianoDiStudi) cmbStudenti.getSelectedItem());
			
			aggiornaOutput();
		}
	}

	
	private void aggiornaOutput() {
		output.setText("");
		if(radioCarriera.isSelected()) {
			output.append(carriera.getPianoDiStudi().toFullString());
		}
		else if(radioEsami.isSelected()) {		
			output.append(carriera.toFullString(true));
		}
	}
}
