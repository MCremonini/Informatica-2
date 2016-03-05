package zetabank.tests.persistence;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

import zetabank.model.*;
import zetabank.persistence.*;

public class ReaderTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testCtor_FailForNullReader() throws BadFileFormatException {
		new MyFinReader( null );
	}

	@Test
	public void testCaricaElementi_ShouldSucceed() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		List<TipoFinanziamento> fins = myReader.readElements();
		
		assertEquals(2, fins.size());
		
		TipoFinanziamento tipo;
		
		tipo = fins.get(0);
		assertEquals("Fantastik Mutuo", tipo.getNome());
		assertEquals(SchemaFinanziamento.RATE_DECRESCENTI, tipo.getCategoria());
		assertEquals(6, tipo.getTassoAnnuoNominale(), 0);
		assertEquals(8, tipo.getDurataMinima());
		assertEquals(16, tipo.getDurataMassima());
		assertEquals(3, tipo.getPeriodicitaAmmesse().size());
		assertTrue(tipo.getPeriodicitaAmmesse()
				.containsAll(Arrays.asList(Periodicita.MENSILE, Periodicita.BIMESTRALE, Periodicita.TRIMESTRALE)));
		
		tipo = fins.get(1);
		assertEquals("ZMutuoForYou", tipo.getNome());
		assertEquals(SchemaFinanziamento.RATE_COSTANTI, tipo.getCategoria());
		assertEquals(4.8, tipo.getTassoAnnuoNominale(), 0);
		assertEquals(10, tipo.getDurataMinima());
		assertEquals(25, tipo.getDurataMassima());
		assertEquals(2, tipo.getPeriodicitaAmmesse().size());
		assertTrue(tipo.getPeriodicitaAmmesse()
				.containsAll(Arrays.asList(Periodicita.MENSILE, Periodicita.BIMESTRALE)));

	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingPeriodicita() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8	16\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForWrongPeriodicita() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8	16	MENSILE, xBIMESTRALEx, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingDurataMax() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8		MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForWrongDurataMax() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8	x16x	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingDurataMin() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForWrongDurataMin() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		x8x	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingTasso() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI			8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForWrongTasso() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6a%		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingPercentageSign() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingSchema() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo			6%		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForWrongSchema() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE PAZZE	6%		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "ZMutuoForYou		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}
	
	@Test(expected=BadFileFormatException.class)
	public void testCaricaElementi_ShouldFailForMissingName() throws IOException, BadFileFormatException {
		String toRead = "Fantastik Mutuo		RATE DECRESCENTI	6%		8	16	MENSILE, BIMESTRALE, TRIMESTRALE\n" +
						  "		RATE COSTANTI		4.8%	10	25	MENSILE, BIMESTRALE\n";


		FinReader myReader = new MyFinReader( new StringReader(toRead) );
		myReader.readElements();
	}

	

}
