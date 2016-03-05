package dentinia.persistence;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import dentinia.model.Partito;

public class MySeggiWriter implements SeggiWriter {

	@Override
	public void stampaSeggi(List<Partito> partiti, Writer writer) throws IOException {
		
		PrintWriter myWriter = new PrintWriter(writer, true);
		for(Partito p : partiti) {
			myWriter.println(p);
		}
		
		myWriter.close();
	}

}
