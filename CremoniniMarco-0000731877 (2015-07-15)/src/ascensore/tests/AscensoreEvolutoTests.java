package ascensore.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import ascensore.model.AscensoreEvoluto;
import ascensore.model.Ascensore;
import ascensore.model.StatoAscensore;

public class AscensoreEvolutoTests {
	
	@Test
	public void testGetTipo() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		assertEquals("Evoluto", asc.getTipo());
	}

	@Test
	public void testGetPrenotazioni_dopoCostruzione() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		// Ascensore è fermo e al piano richiesto: 
		// l'elenco prenotazioni dovrebbe essere vuoto
		assertEquals(0, asc.getPrenotazioni().size());
	}

	@Test
	public void testGetPrenotazioni_dopoVaiAlPiano_Fermo() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		asc.vaiAlPiano(7);
		
		// Ascensore è fermo ma non al piano richiesto
		assertEquals(1, asc.getPrenotazioni().size());
		assertEquals(7, (int) asc.getPrenotazioni().get(0));
	}

	@Test
	public void testGetPrenotazioni_dopoVaiAlPiano_InMovimentoPoiFermo() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
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
	public void testVaiAlPiano_ComandoAccettato_Fermo() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
	}

	@Test
	public void testVaiAlPiano_ComandoAccettato_InMovimentoEPianoRichiestoDiverso() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
		asc.tick();
		asc.tick();
		
		assertTrue(asc.vaiAlPiano(8));
		assertEquals(2, asc.getPrenotazioni().size());
	}

	@Test
	public void testVaiAlPiano_ComandoRifiutato_PrenotazioneReplicata() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(7));
		asc.tick();
		asc.tick();
		
		assertFalse(asc.vaiAlPiano(7));
		assertEquals(1, asc.getPrenotazioni().size());
	}
	
	@Test 
	public void testVaiAlPiano_SequenzaPrenotazioni() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		assertTrue(asc.vaiAlPiano(8));
		asc.tick(); // Fermo -> InSalita; 5
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(Arrays.asList(8), asc.getPrenotazioni());
		
		assertTrue(asc.vaiAlPiano(6));
		asc.tick();	// InSalita; 5 -> 6
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(6, asc.getPianoCorrente());
		assertEquals(Arrays.asList(8, 6), asc.getPrenotazioni());
		
		asc.tick();	// InSalita; 6 -> 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(Arrays.asList(8, 6), asc.getPrenotazioni());
		
		assertTrue(asc.vaiAlPiano(7));
		asc.tick(); // InSalita; 7 -> 8
		assertEquals(8, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(Arrays.asList(8, 6, 7), asc.getPrenotazioni());
		
		asc.tick(); // InSalita -> Fermo; 8
		assertEquals(8, asc.getPianoCorrente());
		assertEquals(StatoAscensore.Fermo, asc.getStatoAscensore());
		assertEquals(Arrays.asList(6, 7), asc.getPrenotazioni());
		
		asc.tick();	// Fermo -> InDiscesa; 8
		assertEquals(8, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InDiscesa, asc.getStatoAscensore());
		assertEquals(Arrays.asList(6, 7), asc.getPrenotazioni());

		asc.tick();	// InDiscesa; 8 -> 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InDiscesa, asc.getStatoAscensore());
		assertEquals(Arrays.asList(6, 7), asc.getPrenotazioni());
		
		asc.tick();	// InDiscesa; 7 -> 6
		assertEquals(6, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InDiscesa, asc.getStatoAscensore());
		assertEquals(Arrays.asList(6, 7), asc.getPrenotazioni());
		
		asc.tick(); // InDiscesa -> Fermo; 6
		assertEquals(6, asc.getPianoCorrente());
		assertEquals(StatoAscensore.Fermo, asc.getStatoAscensore());
		assertEquals(Arrays.asList(7), asc.getPrenotazioni());
		
		asc.tick();	// Fermo -> InSalita; 6
		assertEquals(6, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(Arrays.asList(7), asc.getPrenotazioni());
		
		asc.tick();	// InSalita; 6 -> 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.InSalita, asc.getStatoAscensore());
		assertEquals(Arrays.asList(7), asc.getPrenotazioni());
		
		asc.tick();	// InSalita -> Fermo; 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.Fermo, asc.getStatoAscensore());
		assertTrue(asc.getPrenotazioni().isEmpty());
		
		asc.tick();	// Fermo; 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.Fermo, asc.getStatoAscensore());
		assertTrue(asc.getPrenotazioni().isEmpty());
		
		asc.tick();	// Fermo; 7
		assertEquals(7, asc.getPianoCorrente());
		assertEquals(StatoAscensore.Fermo, asc.getStatoAscensore());
		assertTrue(asc.getPrenotazioni().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVaiAlPiano_IllegalArgument_Troppo() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		asc.vaiAlPiano(11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVaiAlPiano_IllegalArgument_Poco() {
		Ascensore asc = new AscensoreEvoluto(2, 10, 5);
		
		asc.vaiAlPiano(1);
	}
}
