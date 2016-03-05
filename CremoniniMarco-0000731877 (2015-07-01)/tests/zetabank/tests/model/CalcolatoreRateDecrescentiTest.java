package zetabank.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import zetabank.model.*;

public class CalcolatoreRateDecrescentiTest {

	private TipoFinanziamento tipo = new TipoFinanziamento("rate decrescenti", SchemaFinanziamento.RATE_DECRESCENTI, 1.0, 1, 5, new Periodicita[] { Periodicita.ANNUALE });
	@Test
	public void testCtor_ShouldSucceed() {
		new CalcolatoreRateDecrescenti();
	}
	
	@Test
	public void test_CalcShouldSucceed() {
		CalcolatoreRateDecrescenti c = new CalcolatoreRateDecrescenti(); 
		Finanziamento fin = c.calcola(tipo, 1000, 5, Periodicita.ANNUALE);

		System.out.println(fin);
		
		for (int i = 0; i < fin.getRate().size(); i++) {
			Rata rata = fin.getRate().get(i);
			assertEquals(200, rata.getQuotaCapitale(), 0);
			assertEquals(2 * (fin.getRate().size() - i), rata.getQuotaInteressi(), 0);
		}
		
		assertEquals(tipo, fin.getTipoFinanziamento());
		assertEquals(1000, fin.getCapitaleFinanziato(), 0);
		assertEquals(5, fin.getDurata());
		assertEquals(Periodicita.ANNUALE, fin.getPeriodicitaRimborso());		
		assertEquals(30, fin.sommaTotaleInteressi(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNullTipo(){
		CalcolatoreRateDecrescenti c = new CalcolatoreRateDecrescenti();
		TipoFinanziamento tipo = null; 
		c.calcola(tipo, 1000, 5, Periodicita.ANNUALE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNegativeCapitaleDaFinanziare(){
		CalcolatoreRateDecrescenti c = new CalcolatoreRateDecrescenti();
		c.calcola(tipo, -1000, 5, Periodicita.ANNUALE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNullPeriodicita(){
		CalcolatoreRateDecrescenti c = new CalcolatoreRateDecrescenti();
		c.calcola(tipo, 1000, 5, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForWrongPeriodicita(){
		CalcolatoreRateDecrescenti c = new CalcolatoreRateDecrescenti();
		c.calcola(tipo, 1000, 5, Periodicita.MENSILE);
	}

}
