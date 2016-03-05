package ascensore.model;

import java.util.ArrayList;
import java.util.List;

public class AscensoreBase extends Ascensore {

	public AscensoreBase(int pianoMin, int pianoMax, int pianoIniziale) {
		super(pianoMin, pianoMax, pianoIniziale);
	}
	
	@Override
	public String getTipo() {
		return "Base";
	}

	@Override
	public List<Integer> getPrenotazioni() {
		List<Integer> ret = new ArrayList<Integer>();
		
		if(getStatoAscensore().equals(StatoAscensore.Fermo) && getPianoCorrente() == getPianoRichiesto()) {
			// do nothing
		}
		else {
			ret.add(getPianoRichiesto());
		}
			
		
		return ret;
	}

	@Override
	public boolean vaiAlPiano(int pianoArrivo) {
		if(pianoArrivo > getPianoMax() || pianoArrivo < getPianoMin())
			throw new IllegalArgumentException("piano di arrivo fuori range");
		
		if(getStatoAscensore().equals(StatoAscensore.Fermo) && getPianoCorrente() == getPianoRichiesto() && 
		   getPianoCorrente() != pianoArrivo) {
			setPianoRichiesto(pianoArrivo);
			return true;
		}
		
		
		return false;
	}

	@Override
	public void arrivatoAlPianoGestisciPrenotazioni() {
		// do nothing

	}

}
