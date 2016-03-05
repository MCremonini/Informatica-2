package ascensore.ui;

import java.util.List;

import ascensore.model.Ascensore;
import ascensore.model.AscensoreProperty;
import ascensore.model.Edificio;

public interface Controller {
	
	List<Edificio> getElencoEdifici();

	void setMainView(MainView view);

	void setAscensore(Ascensore newAsc);

	void vaiAlPiano(int piano);
	
	void handlePropertyChange(AscensoreProperty prop);
}
