package pharmame.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pharmame.model.Farmaco;

public class MyMainView extends JFrame implements ActionListener, ListSelectionListener, MainView {

	private static final long serialVersionUID = 1L;
	
	private Controller controller = null;
	private final String appName = "PharmaMe";
	private JComboBox<String> cmbFiltro;
	private JTextField txtChiave;
	private JButton btnFiltra;
	private FarmacoTable table;
	private JTextArea output;
	private List<Integer> selItems = new ArrayList<>();
	
	public MyMainView() {
		initGUI();
	} 
	
	private void initGUI() {
		setTitle(appName);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		
		
		JPanel upperPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		{
					
			upperPanel.add(new JLabel("Cerca per:"));
			cmbFiltro = new JComboBox<>();
			upperPanel.add(cmbFiltro);
			
			upperPanel.add(new JLabel("Chiave:"));
			txtChiave = new JTextField("");
			txtChiave.addActionListener(this);
			txtChiave.addKeyListener(new KeyAdapter() {
								      public void keyPressed(KeyEvent e) {
								    	  if (e.getKeyCode() == KeyEvent.VK_ENTER)
								    	  {
								    		  ApplyFilter();
								    	  }
								      	}
								    }
									);
			upperPanel.add(txtChiave);
			
			upperPanel.add(new JPanel());
			btnFiltra = new JButton("Filtra");
			btnFiltra.addActionListener(this);
			upperPanel.add(btnFiltra);
			
		}
		getContentPane().add(upperPanel, BorderLayout.PAGE_START);
				
		
		setSize(500,600);
	}
	
	@Override
	public void setController(Controller c) {
		controller = c;
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, appName, JOptionPane.INFORMATION_MESSAGE, null);
	}

	@Override
	public void setFilterNames(Collection<String> names) {
		cmbFiltro.removeAllItems();
		for(String s : names) {
			cmbFiltro.addItem(s);
		}
	}

	@Override
	public void setFarmaci(List<Farmaco> farmaci) {
		table.setFarmaci(farmaci);
	}

	@Override
	public void setOutput(String[] righe) {
		output.setText("");
		for(String s : righe) {
			output.append(s + System.lineSeparator());
		}
	}

	@Override
	public Farmaco getFarmacoAt(int index) {
		return table.getFarmacoAt(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		
		if(e.getValueIsAdjusting())
			return;
		controller.printSelected(table.getSelectedRows());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnFiltra) {
			ApplyFilter();
		}
	}
	
	private void ApplyFilter() {
		String filterName = (String)cmbFiltro.getSelectedItem();
		String searchKey = txtChiave.getText();
		
		selItems.clear();
		controller.filterBy(filterName, searchKey);
	}

}
