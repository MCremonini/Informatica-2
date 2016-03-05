package ascensore.ui.pulsantiera;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ascensore.model.Ascensore;
import ascensore.model.AscensoreProperty;

public class Pulsantiera extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private List<ElevatorButton> pulsanti;
	private Ascensore asc;

	private List<ActionListener> actionListeners;
	

	/** Costruisce una pulsantiera vuota, indipendente dall'ascensore su cui opera
	 *  Occorre invocare setAscensore per configurarla su uno specifico ascensore
	 *  E' possibile re-invocare setAscensore più volte per cambiare l'ascensore associato:
	 *  la pulsantiera si adatta di conseguenza al suo ascensore, senza doverla ricreare 
	*/
	public Pulsantiera() {
		pulsanti = new ArrayList<>();
		actionListeners = new ArrayList<>();
	}
	

	/** Fornito solo per completezza ma non usato
	*/
	public Ascensore getAscensore() {
		return asc;
	}
	

	/** Riconfigura la pulsantiera per un nuovo ascensore
	*/
	public void setAscensore(Ascensore asc) {
		if (this.asc != null) {
			this.asc.removePropertyChangedListeners();
		}

		this.asc = asc;

		this.removeAll();
		pulsanti.clear();

		if (this.asc != null) {
			this.asc.addPropertyChangedListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals(AscensoreProperty.PIANO_CORRENTE.toString())) {
						updateStatus();
					}
				}

			});

			for (int p = asc.getPianoMax(); p >= asc.getPianoMin(); p--) {
				ElevatorButton b = new ElevatorButton("" + p);
				b.addActionListener(this);
				this.add(b);
				pulsanti.add(b);
			}
			updateStatus();
		}
	}

	private void updateStatus() {
		if (asc != null) {
			List<Integer> prenotazioni = asc.getPrenotazioni();
			for (int p = asc.getPianoMax(); p >= asc.getPianoMin(); p--) {
				set(p, prenotazioni.contains(p) 
						? StatoPulsante.OCCUPATO
						: StatoPulsante.SPENTO);
			}
			set(asc.getPianoCorrente(), StatoPulsante.ALPIANO);
			repaint(); // ridisegna pannello
			revalidate(); // ordina layout manager di ridisegnare i componenti
							// contenuti
		}
	}

	private void set(int p, StatoPulsante status) {
		switch (status) {
		case OCCUPATO:
			pulsanti.get(asc.getPianoMax() - p).setOccupato();
			break;
		case SPENTO:
			pulsanti.get(asc.getPianoMax() - p).setSpento();
			break;
		case ALPIANO:
			pulsanti.get(asc.getPianoMax() - p).setAlPiano();
			break;
		default:
			throw new IllegalArgumentException("stato pulsante illegale");
		}
	}

	/** Aggancia il listener dato a tutti i pulsanti
	*/
	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}

	/** Fornito solo per completezza ma non usato
	*/
	public void removeActionListener(ActionListener listener) {
		actionListeners.remove(listener);
	}

	/** Non modificare! Implementa il funzionamento interno della pulsantiera
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				e.getActionCommand().trim());
		for (ActionListener listener : actionListeners) {
			listener.actionPerformed(evt);
		}

		updateStatus();
	}

}
