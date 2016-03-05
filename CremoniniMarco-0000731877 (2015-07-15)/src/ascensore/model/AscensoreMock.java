package ascensore.model;

import java.util.List;

public class AscensoreMock extends Ascensore {
	private Modo modo;
	
	public AscensoreMock(Modo modo, int pianoMin, int pianoMax) {
		super(pianoMin, pianoMax, 0);
		
		this.modo = modo;
	}

	@Override
	public String getTipo() {
		return modo.toString();
	}

	@Override
	public List<Integer> getPrenotazioni() {
		return null;
	}

	@Override
	public boolean vaiAlPiano(int pianoArrivo) {
		return false;
	}

	@Override
	public void arrivatoAlPianoGestisciPrenotazioni() {
	}

}
