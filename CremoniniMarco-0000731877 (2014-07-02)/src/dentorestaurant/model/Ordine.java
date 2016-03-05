package dentorestaurant.model;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ordine {

	private Menu menu;
	private String nomeCliente;
	private Map<Categoria, Portata> ordine;
	
	public Ordine(Menu menu, String nomeCliente) {
		if(menu == null || nomeCliente == null || nomeCliente.trim().isEmpty())
			throw new IllegalArgumentException("invalid parameters");
		
		this.menu = menu;
		this.nomeCliente = nomeCliente.trim();
		this.ordine = new HashMap<Categoria, Portata>();
	}
	
	public void aggiungiPortata(Portata portata) {
		if(ordine.get(portata.getCategoria()) != null)
			throw new IllegalArgumentException("tipo di portata già inserita");
		
		ordine.put(portata.getCategoria(), portata);
	}
	
	public Map<Categoria, Portata> getElencoPortate() {
		return ordine;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	public double getPrezzoTotale() {
		double tot = 0.0;
		
		for(Categoria c : ordine.keySet()) {
			tot += ordine.get(c).getPrezzo();
		}
		
		return tot;
	}
	
	public boolean isValid() {
		for (Categoria c : Categoria.values()) {
			if(ordine.get(c) == null)
				return false;
		}
		
		return true;
	}
	
	public void sostituisciPortata(Portata corrente, Portata futuro) {
		
		if(corrente == null)
			throw new IllegalArgumentException("portata non corretta");
		
		if(corrente.getCategoria().compareTo(futuro.getCategoria()) != 0)
			throw new IllegalArgumentException("tentativo di sostituire portate di categoria diversa");
			
		Portata oldPortata = ordine.get(corrente.getCategoria());
		if(oldPortata == null || oldPortata.compareTo(corrente) != 0)
			throw new IllegalArgumentException("portata non presente");
		
		
		///@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		List<Portata> portate = menu.getPortate(futuro.getCategoria());
		boolean b = false;
		for(Portata p : portate) {
			if(p.compareTo(futuro) == 0) {
				b = true;
				break;
			}
		}

		if(!b)
			throw new IllegalArgumentException("portata non presente in menu");
		
		ordine.put(futuro.getCategoria(), futuro);
	}
	
	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(toString() + System.lineSeparator());
		for(Categoria c : ordine.keySet()) {
			sb.append(ordine.get(c).toString() + System.lineSeparator());
		}		
		
		return sb.toString();
	}
	
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		return (nomeCliente + " " + formatter.format(getPrezzoTotale()));
	}
}
