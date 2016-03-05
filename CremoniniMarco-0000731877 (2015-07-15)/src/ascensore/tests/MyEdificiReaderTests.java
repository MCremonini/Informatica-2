package ascensore.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.*;

import ascensore.model.*;
import ascensore.persistence.*;

public class MyEdificiReaderTests {
	@BeforeClass
	public static void setUpClass() {
		Ascensore.setTestMode(true);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		Ascensore.setTestMode(false);
	}

	@Test
	public void MyEdificiReader_ReadAll_ShouldSucceed()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4, Evoluto\n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		List<Edificio> result = reader.readAll(new StringReader(toRead));

		Edificio e;

		assertEquals(2, result.size());

		e = result.get(0);
		assertEquals(-2, e.getPianoMin());
		assertEquals(4, e.getPianoMax());
		assertEquals("Hotel Miralago", e.getDescrizione());
		assertEquals("EVOLUTO", e.getAscensore().getTipo());
		
		e = result.get(1);
		assertEquals(-1, e.getPianoMin());
		assertEquals(6, e.getPianoMax());
		assertEquals("Condominio \"I Girasoli\"", e.getDescrizione());
		assertEquals("BASE", e.getAscensore().getTipo());
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMissingModo()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4, \n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMalformedModo()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4, EvoXXXluto\n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMissingPianoMax()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, , Evoluto\n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMalformedPianoMax()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4a, Evoluto\n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMalformedPianoMin()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-a2, 4, Evoluto\n"
				+ "Condominio \"I Girasoli\", -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMissingPianoMin()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4, Evoluto\n"
				+ "Condominio \"I Girasoli\", , 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}

	@Test(expected=BadFileFormatException.class)
	public void MyEdificiReader_ReadAll_ShouldFailForMissingDescription()
			throws BadFileFormatException, IOException {
		String toRead = "Hotel Miralago,	-2, 4, Evoluto\n"
				+ " , -1, 6, baSE\n";
		
		MyEdificiReader reader = new MyEdificiReader();

		reader.readAll(new StringReader(toRead));
	}
}
