package doc.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import doc.model.DentoStrategia;
import doc.model.DentoStrategiaConfigurabile;

public class TextStrategieRepository extends TextRepository<DentoStrategia> {

	public TextStrategieRepository(String fileName) throws IOException,
			MalformedFileException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DentoStrategia readElement(BufferedReader reader)
			throws MalformedFileException, IOException {

		try {
			String line = reader.readLine();
			if(line.startsWith("STRATEGIA")) {
				StringTokenizer token = new StringTokenizer(line, "\t\n\r");

				// elimino STRATEGIA
				token.nextToken();
				
				String nome = token.nextToken().trim();
				if(nome == null || nome.isEmpty())
					throw new MalformedFileException("");
				
				if(token.nextToken().trim().compareTo("SU") != 0)
					throw new MalformedFileException("");
				
				int ore = Integer.parseInt(token.nextToken().trim());
				
				if(token.nextToken().trim().compareTo("ORE") != 0)
					throw new MalformedFileException("");
				
				
				Map<String, Integer> punteggiBevande = new HashMap<String, Integer>();
				line = reader.readLine();
				token = new StringTokenizer(line, ",\n\r");
				
				while(token.hasMoreTokens()) {
					String campo = token.nextToken().trim();
					
					int equal = campo.indexOf("=");
					if(equal < 0)
						throw new MalformedFileException("");
						
					String bevanda = campo.substring(0, equal).trim();
					int punteggio = Integer.parseInt(campo.substring(equal+1).trim());
					if(bevanda == null || bevanda.isEmpty())
						throw new MalformedFileException("");
					
					punteggiBevande.put(bevanda, punteggio);
				}
				
				line = reader.readLine();
				if(!line.startsWith("FINE STRATEGIA"))
					throw new MalformedFileException("");
				
				return new DentoStrategiaConfigurabile(nome, punteggiBevande, ore);
			}
			else
				throw new MalformedFileException("");
		}
		catch(NoSuchElementException | NumberFormatException | IndexOutOfBoundsException e) {
			throw new MalformedFileException("");
		}
	}

}
