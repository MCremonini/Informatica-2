package zetabank.tests.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import zetabank.model.*;

public class FinanziamentoTest {

	private List<Rata> rate;
	private TipoFinanziamento tipoFinanziamento;
	private double totaleInteressi;
	
	@Before
	public void setUp() throws Exception {
		int rateCount = 10;
		rate = new ArrayList<>();
		totaleInteressi = 0;
		for (int i = 1; i <= rateCount; i++) {
			rate.add(new Rata(i, i, rateCount + 1 - i));
			totaleInteressi += rateCount + 1 - i;
		}
		
		tipoFinanziamento = new TipoFinanziamento("Uno", SchemaFinanziamento.RATE_DECRESCENTI, 5, 1, 10, Arrays.asList(Periodicita.values()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForNullTipo() {
		new Finanziamento(null, 10, Periodicita.ANNUALE, this.rate, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForNegativeDurata() {
		new Finanziamento(tipoFinanziamento, -1, Periodicita.ANNUALE, this.rate, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForNullPeriodicita() {
		new Finanziamento(tipoFinanziamento, 10, null, this.rate, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForWrongPeriodicita() {
		TipoFinanziamento tipo = new TipoFinanziamento("Uno", SchemaFinanziamento.RATE_DECRESCENTI, 5, 1, 10, Arrays.asList(Periodicita.ANNUALE));
		new Finanziamento(tipo, 10, Periodicita.MENSILE, this.rate, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForNullRate() {
		new Finanziamento(tipoFinanziamento, 10, Periodicita.ANNUALE, null, 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForEmptyRate() {
		new Finanziamento(tipoFinanziamento, 10, Periodicita.ANNUALE, new ArrayList<>(), 100);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtor_shouldFailForNegativeCapitaleFinanziato() {
		new Finanziamento(tipoFinanziamento, 10, Periodicita.ANNUALE, this.rate, -10);
	}
	
	@Test
	public void testCtor_ShouldSucceedAndGettersAreOk() {
		Finanziamento f = new Finanziamento(tipoFinanziamento, 10, Periodicita.ANNUALE, this.rate, 100);
		
		assertEquals(tipoFinanziamento, f.getTipoFinanziamento());
		assertEquals(10, f.getDurata());
		assertEquals(Periodicita.ANNUALE, f.getPeriodicitaRimborso());
		assertEquals(this.rate, f.getRate());
		assertEquals(100, f.getCapitaleFinanziato(), 0.001);
	}
	
	@Test
	public void testSommaTotaleInteressi() {
		Finanziamento f = new Finanziamento(tipoFinanziamento, 10, Periodicita.ANNUALE, this.rate, 100);
		
		assertEquals(totaleInteressi, f.sommaTotaleInteressi(), 0.001);	
	}
}
