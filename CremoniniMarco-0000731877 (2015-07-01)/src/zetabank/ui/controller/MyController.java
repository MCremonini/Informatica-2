package zetabank.ui.controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

import zetabank.model.CalcolatoreRate;
import zetabank.model.Finanziamento;
import zetabank.model.Periodicita;
import zetabank.model.Rata;
import zetabank.model.TipoFinanziamento;

public class MyController implements Controller {

	private UserInteractor userInteractor;
	private List<TipoFinanziamento> elencoFinanziamenti = null;

	public MyController(UserInteractor userInteractor,
			List<TipoFinanziamento> elencoFinanziamenti) {
		this.userInteractor = userInteractor;
		this.elencoFinanziamenti = elencoFinanziamenti;
	}

	protected Finanziamento calculate(TipoFinanziamento t, double capitale,
			int durataRichiesta, Periodicita p) {
		return CalcolatoreRate.calcolaFinanziamento(t, capitale,
				durataRichiesta, p);
	}

	@Override
	public List<TipoFinanziamento> getElencoTipiFinanziamento() {
		return elencoFinanziamenti;
	}

	@Override
	public UserInteractor getUserInteractor() {
		return this.userInteractor;
	}

	@Override
	public Optional<Finanziamento> calcAndCheck(TipoFinanziamento t,
			double capitale, int durataRichiesta, Periodicita p, double rataMax) {
		if (durataRichiesta < t.getDurataMinima()
				|| durataRichiesta > t.getDurataMassima()) {
			userInteractor.showMessage("Durata richiesta illegale: anni "
					+ durataRichiesta + " (possibile [" + t.getDurataMinima()
					+ "-" + t.getDurataMassima() + "])");
			return Optional.empty();
		} else {
			Finanziamento fin = calculate(t, capitale, durataRichiesta, p);
			return checkRate(fin, rataMax) ? Optional.of(fin) : Optional
					.empty();
		}
	}

	private boolean checkRate(Finanziamento fin, double rataMax) {
		for (Rata rata : fin.getRate()) {
			if (rata.getValore() > rataMax) {
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				userInteractor.showMessage("Rata eccessiva: "
						+ formatter.format(rata.getValore()) + " (max "
						+ formatter.format(rataMax) + ")");
				return false;
			}
		}
		return true;
	}

}
