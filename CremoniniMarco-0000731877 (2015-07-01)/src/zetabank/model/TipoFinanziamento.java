package zetabank.model;

import java.util.Arrays;
import java.util.List;

public class TipoFinanziamento {
	private String nome;
	private SchemaFinanziamento categoria;
	private double tassoAnnuoNominale;
	private int durataMinima, durataMassima;
	private List<Periodicita> periodicitaAmmesse;
	
	public TipoFinanziamento(String nome, SchemaFinanziamento categoria,
			double tassoAnnuoNominale, int durataMinima, int durataMassima,
			List<Periodicita> periodicitaAmmesse) {
		this.nome = nome;
		this.categoria = categoria;
		this.tassoAnnuoNominale = tassoAnnuoNominale;
		this.durataMinima = durataMinima;
		this.durataMassima = durataMassima;
		this.periodicitaAmmesse = periodicitaAmmesse;
	}
	
	public TipoFinanziamento(String nome, SchemaFinanziamento categoria,
			double tassoAnnuoNominale, int durataMinima, int durataMassima,
			Periodicita[] periodicitaAmmesse) {
		this(nome, categoria, tassoAnnuoNominale, durataMinima, durataMassima, Arrays.asList(periodicitaAmmesse));
	}

	public String getNome() {
		return nome;
	}

	public SchemaFinanziamento getCategoria() {
		return categoria;
	}

	public double getTassoAnnuoNominale() {
		return tassoAnnuoNominale;
	}

	public int getDurataMinima() {
		return durataMinima;
	}

	public int getDurataMassima() {
		return durataMassima;
	}

	public List<Periodicita> getPeriodicitaAmmesse() {
		return periodicitaAmmesse;
	}

	public String toFullString() {
		return "TipoFinanziamento [nome=" + nome + ", categoria=" + categoria
				+ ", tassoAnnuoNominale=" + tassoAnnuoNominale
				+ ", durataMinima=" + durataMinima + ", durataMassima="
				+ durataMassima + ", periodicitaAmmesse=" + periodicitaAmmesse
				+ "]";
	}
	
	@Override
	public String toString() {
		return nome + ", al " + tassoAnnuoNominale + "%, " + categoria + ", "
				+ durataMinima + "-" + durataMassima + " anni";
	}
	
}
