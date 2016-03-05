package ascensore.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import ascensore.model.Edificio;

public interface EdificiReader {
	public List<Edificio> readAll(Reader reader) throws BadFileFormatException, IOException;
}
