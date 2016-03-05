package ascensore.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import ascensore.model.Ascensore;
import ascensore.model.AscensoreProperty;
import ascensore.model.Edificio;

public abstract class BaseController implements Controller, PropertyChangeListener {

	private MainView view;
	private Ascensore asc;
	private List<Edificio> edifici;
	private TimerAscensore timer;
	
	
	public BaseController(List<Edificio> elencoEdifici) {
		this.edifici = elencoEdifici;
		timer = new TimerAscensore();
	}

	@Override
	public List<Edificio> getElencoEdifici() {
		return edifici;
	}
	
	public void setAscensore(Ascensore asc) {
		view.clearStatus();

		if (this.asc != null) {
			this.asc.removePropertyChangedListener(this);
		}

		this.asc = asc;

		if (this.asc != null) {
			this.asc.addPropertyChangedListener(this);
			timer.setAscensore(this.asc);
			view.setPianoCorrente(asc.getPianoCorrente());
		}
	}
	
	public Ascensore getAscensore(){
		return asc;
	}

	@Override
	public void setMainView(MainView view) {
		this.view = view;
	}
	
	public void updateView(String msg){
		view.appendStatus(msg);
	}
	
	public void updateView(int p){
		view.setPianoCorrente(p);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		AscensoreProperty prop = AscensoreProperty.valueOf(evt.getPropertyName());  
		handlePropertyChange(prop);
	}

}
