package dentinia.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import dentinia.model.Partito;

public class MyVotiReader implements VotiReader {

	private BufferedReader reader;
	private List<Partito> listaPartiti;
	private int seggiDaAssegnare;
	
	public MyVotiReader(Reader reader) {
		if(reader == null)
			throw new IllegalArgumentException("reader non valido");
		
		this.reader = new BufferedReader(reader);
		//this.reader = (BufferedReader) reader;
		listaPartiti = new ArrayList<Partito>();
	}

	@Override
	public List<Partito> caricaElementi() throws IOException, BadFileFormatException {
		
		listaPartiti.clear();
		
		
		try {
			
			String line = reader.readLine();
			if(line == null || !line.startsWith("SEGGI:"))
				throw new BadFileFormatException("parola chiave iniziale non trovata");
			
			StringTokenizer tokens = new StringTokenizer(line, ":\n\r");
			
			// scarto la parte iniziale già verificata in precedenza			
			tokens.nextToken();
			seggiDaAssegnare = Integer.parseInt(tokens.nextToken().trim());	
			
			
			// lettura dei partiti
			while((line = reader.readLine()) != null) {
				
				tokens = null;
				tokens = new StringTokenizer(line, ":\n\r");
				
				String nome = tokens.nextToken().trim();
				if(nome == null || nome.isEmpty())
					throw new BadFileFormatException("nome partito non valido");
				
				String tmp = tokens.nextToken().trim();
				if(tmp == null || tmp.isEmpty())
					throw new BadFileFormatException("parametro voti non valido");
				int voti = Integer.parseInt(tmp);
				
				listaPartiti.add(new Partito(nome, voti));
			}
			
		}
		catch(NoSuchElementException | NumberFormatException e) {
			throw new BadFileFormatException("formato sbagliato");
		}
		
		
		return listaPartiti;
	}

	@Override
	public int getSeggi() {
		return seggiDaAssegnare;
	}

	@Override
	public List<Partito> getListaPartiti() {
		return listaPartiti;
	}

}
