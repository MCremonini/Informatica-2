package zetabank.tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import zetabank.model.*;

public class CalcolatoreRateCostantiTest {
	private TipoFinanziamento tipo = new TipoFinanziamento("rate costanti", SchemaFinanziamento.RATE_COSTANTI, 1.0, 1, 5, new Periodicita[] { Periodicita.ANNUALE });
	
	@Test
	public void testCtor_ShouldSucceed() {
		new CalcolatoreRateCostanti();
	}
	
	@Test
	public void test_CalcShouldSucceed() {
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti(); 
		Finanziamento fin = c.calcola(tipo, 1000, 5, Periodicita.ANNUALE);
		
		for (Rata rata : fin.getRate()) {
			assertEquals(206, rata.getValore(), 0.1);
		}
		
		assertEquals(tipo, fin.getTipoFinanziamento());
		assertEquals(1000, fin.getCapitaleFinanziato(), 0);
		assertEquals(5, fin.getDurata());
		assertEquals(Periodicita.ANNUALE, fin.getPeriodicitaRimborso());		
		assertEquals(30.2, fin.sommaTotaleInteressi(), 0.1);
		
		System.out.println(fin);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNullTipo(){
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti();
		TipoFinanziamento tipo = null; 
		c.calcola(tipo, 1000, 5, Periodicita.ANNUALE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForWrongSchemaFinanziamento(){
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti();
		TipoFinanziamento tipo = new TipoFinanziamento("rate queste e quelle", SchemaFinanziamento.RATE_DECRESCENTI, 1.0, 1, 5, new Periodicita[] { Periodicita.ANNUALE });
		c.calcola(tipo, 1000, 5, Periodicita.ANNUALE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNegativeCapitaleDaFinanziare(){
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti();
		c.calcola(tipo, -1000, 5, Periodicita.ANNUALE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForNullPeriodicita(){
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti();
		c.calcola(tipo, 1000, 5, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CalcShouldFailForWrongPeriodicita(){
		CalcolatoreRateCostanti c = new CalcolatoreRateCostanti();
		c.calcola(tipo, 1000, 5, Periodicita.MENSILE);
	}

}
