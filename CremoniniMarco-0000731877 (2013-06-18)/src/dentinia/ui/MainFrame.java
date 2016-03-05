package dentinia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dentinia.model.CalcolatoreSeggi;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String lastFileSaved;

	private Controller controller;
	private JComboBox<String> cmbMetodo;
	private JButton btnSalva;
	private JElectionPane output;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		lastFileSaved = "";
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Elezioni di Dentinia");
		
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		{
			//mainPanel.setLayout(new GridLayout(1,3));
			
			mainPanel.add(new JLabel("Elezioni Dentinia - Assegnazione Seggi"));
			output = new JElectionPane(controller.getListaPartiti());
			mainPanel.add(output);			
		}
		getContentPane().add(mainPanel, BorderLayout.CENTER);
	
		
		JPanel lowerPanel = new JPanel();
		{
			GridLayout lay = new GridLayout(1,3);
			lay.setHgap(10);
			lay.setVgap(5);
			lowerPanel.setLayout(lay);
			
			lowerPanel.add(new JLabel("Metodo di calcolo:"));
			cmbMetodo = new JComboBox<String>(CalcolatoreSeggi.getCalcolatoriSeggi().toArray(new String[0]));
			lowerPanel.add(cmbMetodo);
			cmbMetodo.addActionListener(this);
			
			btnSalva = new JButton("Salva");
			lowerPanel.add(btnSalva);
			btnSalva.addActionListener(this);
		}
		getContentPane().add(lowerPanel, BorderLayout.SOUTH);
		
		
		setSize(600, 200);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == cmbMetodo) {
			String strMetodo = (String) cmbMetodo.getSelectedItem();
			
			controller.ricalcola(strMetodo);
			output.update(controller.getListaPartiti());
		}
		
		else if(o == btnSalva) {
			salvaSeggi();
		}

	}
	
	private void salvaSeggi() {
		JFileChooser chooser = new JFileChooser(lastFileSaved);
		int res = chooser.showSaveDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			
			lastFileSaved = chooser.getSelectedFile().getAbsolutePath();
			
			
			try {
				controller.salvaSuFile(lastFileSaved);
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(this, "errore in scrittura", "titolo", JOptionPane.WARNING_MESSAGE);
			}
		} 
		// else CANCEL_OPTION, if interested
	}

}
