package ascensore.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ascensore.model.*;

public class AscensoreBaseTests {

	@Test
	public void testGetTipo() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertEquals("Base", asc.getTipo());
	}

	@Test
	public void testGetPrenotazioni_dopoCostruzione() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		// Ascensore è fermo e al piano richiesto: 
		// l'elenco prenotazioni dovrebbe essere vuoto
		assertEquals(0, asc.getPrenotazioni().size());
	}

	@Test
	public void testGetPrenotazioni_dopoVaiAlPiano_Fermo() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
		
		// Ascensore è fermo ma non al piano richiesto
		assertEquals(1, asc.getPrenotazioni().size());
		assertEquals(7, (int) asc.getPrenotazioni().get(0));
	}

	@Test
	public void testGetPrenotazioni_dopoVaiAlPiano_InMovimentoPoiFermo() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(6));
		assertNotEquals(6, asc.getPianoCorrente());
		
		asc.tick();
		// Ascensore in movimento e non al piano richiesto
		assertEquals(1, asc.getPrenotazioni().size());
		assertEquals(6, (int) asc.getPrenotazioni().get(0));
		assertNotEquals(6, asc.getPianoCorrente());
		
		asc.tick();
		// Ascensore in movimento anche se al piano richiesto
		assertEquals(1, asc.getPrenotazioni().size());
		assertEquals(6, (int) asc.getPrenotazioni().get(0));
		assertEquals(6, asc.getPianoCorrente());
		
		asc.tick();
		// Ascensore fermo e al piano richiesto
		assertEquals(0, asc.getPrenotazioni().size());
		assertEquals(6, asc.getPianoCorrente());
	}

	@Test
	public void testVaiAlPiano_ComandoAccettato() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
	}

	@Test
	public void testVaiAlPiano_ComandoRifiutato_InMovimentoEPianoRichiestoDiverso() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
		asc.tick();
		asc.tick();
		
		assertFalse(asc.vaiAlPiano(8));		
	}

	@Test
	public void testVaiAlPiano_ComandoRifiutato_FermoEPianoRichiestoDiversoDaCorrente() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
		
		assertFalse(asc.vaiAlPiano(8));		
	}

	@Test
	public void testVaiAlPiano_ComandoRifiutato_InMovimentoEPianoRichiestoUgualeACorrente() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(6));
		asc.tick();
		asc.tick();
		
		assertFalse(asc.vaiAlPiano(8));		
	}

	@Test
	public void testAscensoreBase() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
	
		assertEquals(2, asc.getPianoMin());
		assertEquals(10, asc.getPianoMax());
		assertEquals(5, asc.getPianoCorrente());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVaiAlPiano_IllegalArgument_Troppo() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		asc.vaiAlPiano(11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVaiAlPiano_IllegalArgument_Poco() {
		Ascensore asc = new AscensoreBase(2, 10, 5);
		
		asc.vaiAlPiano(1);
	}

}
