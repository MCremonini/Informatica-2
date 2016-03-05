package dentorestaurant.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Portata;

public class MyMenuReader implements MenuReader {

	private BufferedReader reader;
	private final String startToken = "MENU";
	private final String endToken = "END MENU";
	
	public MyMenuReader(Reader reader) {
		if(reader == null)
			throw new IllegalArgumentException("invalid reader");
		
		this.reader = new BufferedReader(reader);
	}
	
	@Override
	public List<Menu> caricaMenu(Map<Categoria, List<Portata>> mappaPortate) throws IOException, MalformedFileException {

		List<Menu> lstMenu = new ArrayList<Menu>();
		
		String line = null;
		while((line = reader.readLine()) != null) {
			
			if(line.startsWith(startToken)) {
								
				String nome = line.substring(startToken.length()).trim();
				if(nome == null || nome.isEmpty())
					throw new MalformedFileException("nome non valido");
				
				Menu menu = new Menu(nome);
				readMenu(menu, mappaPortate);
				
				lstMenu.add(menu);
			}
			else
				throw new MalformedFileException("start token not found");
		}
		
		
		return lstMenu;
	}

	private void readMenu(Menu menu, Map<Categoria, List<Portata>> mappaPortate) throws IOException, MalformedFileException {
			
		String line = null;
		while((line = reader.readLine()) != null) {
			
			///@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			
			if(line.equalsIgnoreCase(endToken)) {
				break;
			}
			else {
				
				try {
									
					// controllo un token per volta in modo da avere la situazione più sotto controllo per il formato
					StringTokenizer tok = new StringTokenizer(line, ":");
					
					if(tok.countTokens() != 2)
						throw new MalformedFileException("categoria non corretta");
						
					// il nome della categoria DEVE essere presente
					String tmp = tok.nextToken().trim().toUpperCase();
					if(tmp == null || tmp.isEmpty())
						throw new MalformedFileException("categoria vuota");
					Categoria cat = Categoria.valueOf(tmp);
					


					List<Portata> elenco = mappaPortate.get(cat);
					String [] tokCodici = tok.nextToken().split(",");
					for(String s : tokCodici) {
						
						String codice = s.trim();
						if(codice.isEmpty())
							throw new MalformedFileException("codice portata vuoto");
						
						///@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
						boolean found = false;
						for(Portata p : elenco) {
							if(p.getCodice().compareTo(codice) == 0) {
								menu.getPortate(cat).add(p);
								found = true;
								break;
							}
						}					
						
						if(!found)
							throw new MalformedFileException("portata non trovata");
					}
				
					
				}
				catch(NoSuchElementException | IllegalArgumentException e) {
					throw new MalformedFileException("formato menu non valido");
				}
				
			}
		}
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

	public static void main(String[] a) {
		Map<Categoria, List<Portata>> portate = null;
		
		try(Reader myReader = new FileReader("Menu.txt")) {
			
			MyPortateReader read = new MyPortateReader(myReader);
			portate = read.caricaPortate();
			read.close();
		} 
		catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}

		try(Reader myReader2 = new FileReader("Portate.txt")) {
			
			MyMenuReader read = new MyMenuReader(myReader2);
			List<Menu> menu = read.caricaMenu(portate);
			read.close();
			
			for(Menu m: menu) {
				System.out.println("********************************" + System.lineSeparator());
				System.out.println(m.toFullString() + System.lineSeparator());
			}
		} 
		catch (IOException | MalformedFileException e) {
			e.printStackTrace();
		}

	}

}
