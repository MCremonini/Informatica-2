package dentinia.ui;

import java.io.FileWriter;
import java.io.IOException;

import dentinia.model.CalcolatoreSeggi;
import dentinia.persistence.BadFileFormatException;
import dentinia.persistence.SeggiWriter;
import dentinia.persistence.VotiReader;

public class MyController extends AbstractController {

	public MyController(VotiReader myReader, SeggiWriter myWriter) throws IOException, BadFileFormatException {
		super(myReader, myWriter);
	}

	@Override
	public void ricalcola(String meccanismoSelezionato) {

		CalcolatoreSeggi calc = CalcolatoreSeggi.getInstance(meccanismoSelezionato);
		calc.assegnaSeggi(myReader.getSeggi(), myReader.getListaPartiti());

	}

	@Override
	public void salvaSuFile(String nomeFile) throws IOException {
		try(FileWriter f = new FileWriter(nomeFile)) {
			myWriter.stampaSeggi(listaPartiti, f);
		}
	}

}
