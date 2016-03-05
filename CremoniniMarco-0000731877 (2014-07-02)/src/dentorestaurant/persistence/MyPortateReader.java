package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Portata;

public class MyPortateReader implements PortateReader {

	private BufferedReader reader;
	
	public MyPortateReader(Reader reader) {
		if(reader == null)
			throw new IllegalArgumentException("invalid reader");
		
		this.reader = new BufferedReader(reader);
	}
	
	@Override
	public Map<Categoria, List<Portata>> caricaPortate() throws MalformedFileException, IOException {
		
		Map<Categoria, List<Portata>> portate = new HashMap<Categoria, List<Portata>>();
		for (Categoria c : Categoria.values())
			portate.put(c, new ArrayList<Portata>());
		
		String line = null;
		while((line = reader.readLine()) != null) {
			
			String [] tokens = line.split(",");
			
			if(tokens.length != 4)
				throw new MalformedFileException("numero di campi per portata");
			
			String tmp = tokens[2].trim().toUpperCase();
			// facendo il controllo sul numero dei tokens posso non eseguire il controllo sul riferimento alla stringa
			// perchè al massimo sarà empty
			if(tmp.isEmpty())
				throw new MalformedFileException("categoria vuota");
			Categoria cat = null;
			try {
				cat = Categoria.valueOf(tmp);
			}
			catch(IllegalArgumentException e) {
				throw new MalformedFileException("categoria non trovata");
			}
			
			
			
			String codice = tokens[0].trim();
			if(codice.isEmpty())
				throw new MalformedFileException("codice vuota");
			
			String nome = tokens[1].trim();
			if(nome.isEmpty())
				throw new MalformedFileException("nome vuoto");
			
			tmp = tokens[3].trim();
			if(tmp.isEmpty())
				throw new MalformedFileException("prezzo vuoto");
			double prezzo = 0.0;
			try {
				prezzo = Double.parseDouble(tmp);
			}
			catch(NumberFormatException e) {
				throw new MalformedFileException("prezzo non valido");
			}
			
			
			portate.get(cat).add(new Portata(codice, nome, cat, prezzo));
		}
		
		return portate;
	}

	@Override
	public void close() {
		try {
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void main(String a[]) {
		
		try(Reader myReader = new FileReader("Menu.txt")) {
			
			MyPortateReader read = new MyPortateReader(myReader);
			Map<Categoria, List<Portata>> portate = read.caricaPortate();
			read.close();
			
			for(Categoria c : portate.keySet()) {
				System.out.println("********************************" + System.lineSeparator());
				System.out.println(c.toString() + System.lineSeparator());
				
				for(Portata p : portate.get(c)) {
					System.out.println(p.toString() + System.lineSeparator());
				}
			}
		} 
		catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}
	}

}
