package rs.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import rs.model.Month;
import rs.model.StimaRischio;
import rs.model.MonthDays;

public class MyStimaRischioReader implements StimaRischioReader {

	private Map<String,Collection<StimaRischio>> data;
	
	@Override
	public Map<String, Collection<StimaRischio>> readFrom(Reader reader)
			throws IOException, BadFileFormatException {

		BufferedReader read = new BufferedReader(reader);
		
		data = new HashMap<>();
		
		String line = null;
		
		while( ( line = read.readLine() ) != null ) {
			StringTokenizer tok = new StringTokenizer(line, "|");
			
			String citta,m;
			Month mese;
			int anno, rischio;
			
			if(tok.hasMoreTokens()) {
				citta = tok.nextToken().trim();
			}
			else
				throw new BadFileFormatException("città non specificata");
			
			if(tok.hasMoreTokens()) {
				
				StringTokenizer tok2 = new StringTokenizer(tok.nextToken(), " %");
				
				if(tok2.hasMoreTokens()) {
					m=tok2.nextToken().toUpperCase();
					mese = Month.fromConventionalMonth(MonthDays.getIndex(m));
				}
				else
					throw new BadFileFormatException("mese non specificata");
				
				if(tok2.hasMoreTokens()) {
					anno = Integer.parseInt(tok2.nextToken());
				}
				else
					throw new BadFileFormatException("anno non specificata");
				
				if(tok2.hasMoreTokens()) {
					rischio = Integer.parseInt(tok2.nextToken());
				}
				else
					throw new BadFileFormatException("rischio non specificata");
			}
			else
				throw new BadFileFormatException("parametri non specificata");
			
			Collection<StimaRischio> col = data.get(citta);
			if( col == null ) {
				col = new ArrayList<StimaRischio>();
				data.put(citta, col);
			}
			
			col.add(new StimaRischio(citta, mese, anno, rischio));
			
		}
		
		return data;
	}

}
