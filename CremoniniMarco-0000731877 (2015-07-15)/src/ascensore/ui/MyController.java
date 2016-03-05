package ascensore.ui;

import java.util.List;

import ascensore.model.Ascensore;
import ascensore.model.AscensoreProperty;
import ascensore.model.Edificio;

public class MyController extends BaseController {

	public MyController(List<Edificio> elencoEdifici) {
		super(elencoEdifici);
	}

	@Override
	public void vaiAlPiano(int piano) {
		Ascensore a = getAscensore();
		 
		if(a != null && a.vaiAlPiano(piano)) 
			updateView("Prenotazione piano " + piano + " accettata");
		else
			updateView("Prenotazione piano " + piano + " rifiutata");
	}

	@Override
	public void handlePropertyChange(AscensoreProperty prop) {
		if(prop == AscensoreProperty.PIANO_CORRENTE) {
			updateView(getAscensore().getPianoCorrente());
			updateView("Piano " + getAscensore().getPianoCorrente());
		}
		else if(prop == AscensoreProperty.STATO_ASCENSORE) {
			updateView("Stato ascensore: " + getAscensore().getStatoAscensore());
		}
	}

}
