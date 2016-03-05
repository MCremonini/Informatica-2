package zetabank.model;

public enum Periodicita {
	MENSILE(12), BIMESTRALE(6), TRIMESTRALE(4), QUADRIMESTRALE(3), SEMESTRALE(2), ANNUALE(1);
	
	private int numeroRatePerAnno;
	
	Periodicita(int numeroRatePerAnno){
		this.numeroRatePerAnno = numeroRatePerAnno;
	}
	
	public int numeroRatePerAnno() { return numeroRatePerAnno; }
}
