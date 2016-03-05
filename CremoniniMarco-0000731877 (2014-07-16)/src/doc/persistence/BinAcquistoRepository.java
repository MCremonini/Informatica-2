package doc.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import doc.model.Acquisto;

public class BinAcquistoRepository extends BinRepository<Acquisto> implements
		AcquistoRepository {

	public BinAcquistoRepository(String fileName) throws IOException,
			MalformedFileException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void add(Acquisto acquisto) throws IOException {
		getStorage().add(acquisto);
		try {
			writeAll();
		} catch (MalformedFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeAll() throws IOException, MalformedFileException {
		try (OutputStream out = getOutputStream()) {
			ObjectOutputStream ous = new ObjectOutputStream(out);
			ous.writeObject(getStorage());
		} catch (FileNotFoundException e) {
			
		}
	}
}
