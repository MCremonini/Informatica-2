package ascensore.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AscensoreEvoluto extends Ascensore {

	Queue<Integer> prenotazioni;
	
	public AscensoreEvoluto(int pianoMin, int pianoMax, int pianoIniziale) {
		super(pianoMin, pianoMax, pianoIniziale);

		prenotazioni = new LinkedList<Integer>();
	}

	@Override
	public String getTipo() {
		return "Evoluto";
	}

	@Override
	public List<Integer> getPrenotazioni() {
		return new ArrayList<Integer>(prenotazioni);
	}

	@Override
	public boolean vaiAlPiano(int pianoArrivo) {
		if(pianoArrivo > getPianoMax() || pianoArrivo < getPianoMin())
			throw new IllegalArgumentException("piano di arrivo fuori range");
		
		if(prenotazioni.contains(pianoArrivo) || 
			getStatoAscensore().equals(StatoAscensore.Fermo) && getPianoCorrente() == getPianoRichiesto() && getPianoRichiesto() == pianoArrivo)
			return false;
		
		if(prenotazioni.isEmpty())
			setPianoRichiesto(pianoArrivo);
		prenotazioni.add(pianoArrivo);
		return true;
	}

	@Override
	public void arrivatoAlPianoGestisciPrenotazioni() {
		if(getPianoCorrente() == getPianoRichiesto())
			prenotazioni.poll();
		
		Integer piano = prenotazioni.peek();
		if(piano != null)
			setPianoRichiesto(piano);
	}

}
