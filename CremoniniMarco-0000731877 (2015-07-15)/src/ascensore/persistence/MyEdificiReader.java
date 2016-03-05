package ascensore.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import ascensore.model.Ascensore;
import ascensore.model.Edificio;
import ascensore.model.Modo;

public class MyEdificiReader implements EdificiReader {

	@Override
	public List<Edificio> readAll(Reader reader) throws BadFileFormatException,	IOException {

		List<Edificio> edifici = new ArrayList<Edificio>();
		
	
		BufferedReader myReader = new BufferedReader(reader);
		String line = null;
		
		try {
			while((line = myReader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, ",\n\r");
				
				//if(tokens.countTokens() != 4)
					//throw new BadFileFormatException("numero inatteso di campi");
				
				// Posso non catchare la NoSuchElementException di nextToken perchè ho già verificato
				// che i tokens devono essere 4
				
				String nome = tokens.nextToken().trim();
				if(nome == null || nome.isEmpty())
					throw new BadFileFormatException("nome edifico non valido");
				
				
				int pianoMin = Integer.parseInt(tokens.nextToken().trim());
				int pianoMax = Integer.parseInt(tokens.nextToken().trim());
				
				
				
				String m = tokens.nextToken().trim();
				if(m == null || m.isEmpty())
					throw new BadFileFormatException("modo di funzionamento non valido");
				Modo modo = Modo.valueOf(m.toUpperCase());
							
				edifici.add(new Edificio(nome, pianoMin, pianoMax, Ascensore.of(modo, pianoMin, pianoMax)));
			}
		}
		/*catch(NumberFormatException e) {
			// se si volesse differenziare l'eccezione delle parseInt
		}*/
		catch(NoSuchElementException | IllegalArgumentException e) {
			throw new BadFileFormatException("formato dati non valido");
		}
		
		
		return edifici;
	}

	public static void main(String[] args) {
		try(FileReader r = new FileReader("Edifici.txt")) {
			
			MyEdificiReader myReader = new MyEdificiReader();
			List<Edificio> res = myReader.readAll(r);
			r.close();
			System.out.println(res);
		}
		catch(IOException | BadFileFormatException e) {
			e.printStackTrace();
		}
	}

}
