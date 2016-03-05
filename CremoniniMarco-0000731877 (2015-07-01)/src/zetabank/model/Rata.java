package zetabank.model;

public class Rata implements Comparable<Rata> {

	private String denominazione;
	private double quotaCapitale; 
	private double quotaInteressi;
	
	public Rata(int num, double quotaCapitale, double quotaInteressi) {
		this("Rata "+num, quotaCapitale, quotaInteressi);
	}

	public Rata(String denominazione, double quotaCapitale, double quotaInteressi) {
		this.denominazione = denominazione;
		this.quotaCapitale = quotaCapitale;
		this.quotaInteressi = quotaInteressi;
	}
	
	public double getQuotaInteressi() {
		return quotaInteressi;
	}
	
	public double getQuotaCapitale() {
		return quotaCapitale;
	}
	
	public double getValore() {
		return quotaCapitale+quotaInteressi;
	}
	
	public String toString(){
		return denominazione + ": " + getValore();
	}

	public int compareTo(Rata that) {
		return Double.compare(that.getValore(), this.getValore());
	}

}
