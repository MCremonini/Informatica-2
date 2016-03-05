package zetabank.ui;

import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import zetabank.model.*;


public class FinancingPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();
	Object[] titoliColonne = {"N° rata", "Importo", "Quota capitale", "Quota interessi"};
	
	public FinancingPane(List<Rata> listaRate) {
		table = new JTable();
		getViewport().add(table, null);
		setPreferredSize(new Dimension(500,200));
		((JLabel) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
		update(listaRate);
	}
	
	public FinancingPane() {
		this(new ArrayList<>()); // inizialmente vuota
	}
	
	public void reset() {
		update(new ArrayList<>());
	}
	
	public void update(List<Rata> listaRate) {
		Object[][] rowData = new Object[listaRate.size()][4];
		for (int i=0; i<listaRate.size(); i++){
			Rata r = listaRate.get(i);
			rowData[i][0] = i+1;
			rowData[i][1] = formatter.format(r.getValore());
			rowData[i][2] = formatter.format(r.getQuotaCapitale());
			rowData[i][3] = formatter.format(r.getQuotaInteressi());
		}
		DefaultTableModel model = new DefaultTableModel(rowData, titoliColonne);
		table.setModel(model);
	}
}
