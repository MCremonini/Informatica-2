package zetabank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import zetabank.model.Periodicita;
import zetabank.model.SchemaFinanziamento;
import zetabank.model.TipoFinanziamento;

public class MyFinReader implements FinReader {

	private BufferedReader myReader;
	
	public MyFinReader(Reader reader) {
		if(reader == null)
			throw new IllegalArgumentException("reader non valido");
		myReader = new BufferedReader(reader);
	}
	
	@Override
	public List<TipoFinanziamento> readElements() throws BadFileFormatException {

		String line = null;
		List<TipoFinanziamento> tipoFinanziamenti = new ArrayList<>();
		
		try {
			
			while((line = myReader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, "\t\r\n");
				
				String nome = tokens.nextToken().trim();
				if(nome == null || nome.isEmpty())
					throw new BadFileFormatException("nome non corretto");
				
				String cat = tokens.nextToken().trim();
				if(cat == null || cat.isEmpty())
					throw new BadFileFormatException("categoria non corretta");
				SchemaFinanziamento categoria = SchemaFinanziamento.valueOf(cat.replace(" ", "_"));
				
				String tasso = tokens.nextToken().trim();
				if(tasso == null || tasso.isEmpty() || !tasso.endsWith("%"))
					throw new BadFileFormatException("tasso non valido");
				double tassoAnnuo = Double.parseDouble(tasso.substring(0, tasso.length() - 1));
				
				int durataMin = Integer.parseInt(tokens.nextToken().trim());
				int durataMax = Integer.parseInt(tokens.nextToken().trim());
				
				String [] periodicita = tokens.nextToken().trim().split(",");
				if(periodicita == null || periodicita.length == 0)
					throw new BadFileFormatException("periodicità non sono corretto");
				List<Periodicita> periodicitaAmmesse = new ArrayList<>();
				for(String s : periodicita)
					periodicitaAmmesse.add(Periodicita.valueOf(s.trim().toUpperCase()));
				
				tipoFinanziamenti.add(new TipoFinanziamento(nome, categoria, tassoAnnuo, durataMin, durataMax, periodicitaAmmesse));
			}
		}
		catch(IOException | NoSuchElementException | IllegalArgumentException e) {
			throw new BadFileFormatException("formato file dati non corretto");
		}
		
		return tipoFinanziamenti;
	}

}
