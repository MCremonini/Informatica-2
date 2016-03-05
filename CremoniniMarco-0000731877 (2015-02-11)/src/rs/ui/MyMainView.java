package rs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import rs.model.StimaRischio;
import rs.model.StimaSintetica;


public class MyMainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	private JButton stimaButton;
	private JComboBox<String> cmbCitta;
	private JComboBox<Integer> cmbAnno;
	private Controller cnt;
	private JTextArea output;
	
	public MyMainView( Controller controller ) {
	
		cnt = controller;
		
		JPanel panel = new JPanel();
		{
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

			JLabel lbl = new JLabel("Città");
			panel.add(lbl);
			
			String [] citta = new String [cnt.getCitta().size()];
			cnt.getCitta().toArray(citta);
			cmbCitta = new JComboBox<String>(citta);
			cmbCitta.addActionListener(this);
			panel.add(cmbCitta);
			
			JLabel lbl2 = new JLabel("Anni");
			panel.add(lbl2);
			
			cmbAnno = new JComboBox<Integer>();
			cmbAnno.addActionListener(this);
			panel.add(cmbAnno);
			
			stimaButton = new JButton("stime");
			stimaButton.addActionListener(this);
			panel.add(stimaButton);

		}
		getContentPane().add(panel, BorderLayout.LINE_START);
		
		
		output = new JTextArea();
		getContentPane().add(output, BorderLayout.CENTER);
		
		
		
		
		
		setSize(400, 200);
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == stimaButton) {
			String obj = cmbCitta.getItemAt(cmbCitta.getSelectedIndex());
			
			if( obj instanceof String ) {
				
				int obj2 = cmbAnno.getItemAt(cmbAnno.getSelectedIndex());
				
				StimaSintetica stima = cnt.getStimaSintetica(obj,obj2);
				String strOut = "";
				
				Iterator<StimaRischio> sr= cnt.getStimeRischio(obj, obj2).iterator();
				while(sr.hasNext()) {
					strOut += sr.next().toString() + "\n";
				}
				
				
				strOut += ("\n" + stima.toString() );
				output.setText(strOut); 
			}
		}
		else if( source == cmbCitta ) {
			
			//valido anche con combo editabili
			Object obj = cmbCitta.getSelectedItem();
			
			if( obj instanceof String ) {
				Integer [] aInts = new Integer[cnt.getAnniPerCitta((String)obj).size()];
				cnt.getAnniPerCitta((String) obj).toArray(aInts);
				
				cmbAnno.removeAllItems();
				for(Integer i:aInts) {
					cmbAnno.addItem(i.intValue());
				}
			}
				
		}
		
	}


}
