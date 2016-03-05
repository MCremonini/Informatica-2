package dentorestaurant.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;
import dentorestaurant.persistence.MalformedFileException;
import dentorestaurant.persistence.MenuReader;
import dentorestaurant.persistence.PortateReader;

public class MyController implements Controller {

	private Collection<Menu> menu;
	private Map<Categoria, List<Portata>> mappaPortate;
	
	public MyController(PortateReader portateReader, MenuReader menuReader, UserInteractor userInteractor) {
		
		if(portateReader == null || menuReader == null || userInteractor == null )
			throw new IllegalArgumentException();
		
		try {
			mappaPortate = portateReader.caricaPortate();
			menu = menuReader.caricaMenu(mappaPortate);
			
			portateReader.close();
			menuReader.close();
		}
		catch (MalformedFileException | IOException e) {
			userInteractor.showMessage("errore nella lettura dei files di configurazione");
			userInteractor.shutDownApplication();
		}
	}
	
	@Override
	public String sostituisciPortata(Ordine ordine, Portata daMettere) {
		
		Portata daSostituire = ordine.getElencoPortate().get(daMettere.getCategoria());
		if(daSostituire == null)
			ordine.aggiungiPortata(daMettere);
		else {
			try {
				ordine.sostituisciPortata(daSostituire, daMettere);
			}
			catch (IllegalArgumentException e) {
				return e.getMessage();			
			}
		}
		
		return null;
	}

	@Override
	public Collection<Menu> getMenus() {
		return menu;
	}

	@Override
	public Ordine creaOrdine(Menu m, String nomeCliente) {
		return new Ordine(m, nomeCliente);
	}

}
