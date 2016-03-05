package ascensore.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public abstract class Ascensore {
	
	private static boolean testMode;
	
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private int pianoCorrente;
	public final int pianoMin, pianoMax;
	private StatoAscensore statoCorrente;

	private int pianoRichiesto;
	
	public Ascensore(int pianoMin, int pianoMax, int pianoIniziale) {
		if (pianoMin >= pianoMax)
			throw new IllegalArgumentException("pianoMin >= pianoMax");
		if (pianoIniziale < pianoMin || pianoMax < pianoIniziale)
			throw new IllegalArgumentException("pianoCorrente < pianoMin || pianoMax < pianoCorrente");
		
		this.pianoMax = pianoMax;
		this.pianoMin = pianoMin;
		this.pianoCorrente = pianoIniziale;
		this.pianoRichiesto = pianoIniziale;
		this.statoCorrente = StatoAscensore.Fermo;
	}
	
	public abstract String getTipo();
	
	public int getPianoCorrente() {
		return pianoCorrente;
	}

	private void setPianoCorrente(int pianoCorrente) {
		if (pianoCorrente != this.pianoCorrente) {
			int oldValue = this.pianoCorrente;
			this.pianoCorrente = pianoCorrente;
			changes.firePropertyChange(AscensoreProperty.PIANO_CORRENTE.toString(), oldValue, pianoCorrente);
		}
	}

	public int getPianoMin() {
		return pianoMin;
	}

	public int getPianoMax() {
		return pianoMax;
	}

	public StatoAscensore getStatoAscensore() {
		return statoCorrente;
	}

	private void setStatoAscensore(StatoAscensore stato) {
		if (this.statoCorrente != stato) {
			StatoAscensore oldValue = statoCorrente;
			this.statoCorrente = stato;
			segnalaCambiamentoStato(oldValue, stato);
			changes.firePropertyChange(AscensoreProperty.STATO_ASCENSORE.toString(), oldValue, stato);
		}
	}

	public abstract List<Integer> getPrenotazioni();
	
	public void addPropertyChangedListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangedListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

	public void removePropertyChangedListeners() {
		for (PropertyChangeListener pcl : changes.getPropertyChangeListeners()) {
			changes.removePropertyChangeListener(pcl);
		}
	}

	public abstract boolean vaiAlPiano(int pianoArrivo);

	public abstract void arrivatoAlPianoGestisciPrenotazioni();

	
	protected void setPianoRichiesto(int pianoRichiesto) {
		this.pianoRichiesto = pianoRichiesto;
	}
	
	protected int getPianoRichiesto() {
		return this.pianoRichiesto;
	}

	protected void segnalaCambiamentoStato(StatoAscensore vecchioStato, StatoAscensore nuovoStato){
		if (nuovoStato == StatoAscensore.Fermo) {
			arrivatoAlPianoGestisciPrenotazioni();
		}
	}

		
	/** Il metodo tick() incorpora la logica di avanzamento della simulazione:
	 *  è invocato automaticamente dal MainFrame della GUI a intervalli regolari.
	 *  Non toccare!
	 */
	public void tick() {
		if (getStatoAscensore() != StatoAscensore.Fermo) {
			if (pianoCorrente < pianoRichiesto) {
				if (getStatoAscensore() != StatoAscensore.InSalita)
					throw new IllegalStateException("Dovrebbe essere in salita, invece no.");

				setPianoCorrente(getPianoCorrente() + 1);
			} else if (pianoCorrente > pianoRichiesto) {
				if (getStatoAscensore() != StatoAscensore.InDiscesa)
					throw new IllegalStateException("Dovrebbe essere in discesa, invece no.");

				setPianoCorrente(getPianoCorrente() - 1);
			} else {
				setStatoAscensore(StatoAscensore.Fermo);
			}
		} else {
			if (pianoCorrente < pianoRichiesto) {
				setStatoAscensore(StatoAscensore.InSalita);
			} else if (pianoRichiesto < pianoCorrente) {
				setStatoAscensore(StatoAscensore.InDiscesa);
			}
		}
	}

	public static Ascensore of(Modo modo, int pianoMin, int pianoMax) {
		if (testMode) {
			return new AscensoreMock(modo, pianoMin, pianoMax);
		}
		
		switch (modo) {
		case BASE:
			return new AscensoreBase(pianoMin, pianoMax, 0);
		case EVOLUTO:
			return new AscensoreEvoluto(pianoMin, pianoMax, 0);
		default:
			break;
		}
		throw new IndexOutOfBoundsException(); // will never happen
	}
	
	public static void setTestMode(boolean isTest) {
		testMode = isTest;
	}
}
