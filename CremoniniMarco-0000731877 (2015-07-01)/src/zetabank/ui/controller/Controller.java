package zetabank.ui.controller;

import java.util.List;
import java.util.Optional;

import zetabank.model.Finanziamento;
import zetabank.model.Periodicita;
import zetabank.model.TipoFinanziamento;

public interface Controller {

	Optional<Finanziamento> calcAndCheck(TipoFinanziamento t, double capitale, int durataRichiesta,
			Periodicita p, double rataMax);

	UserInteractor getUserInteractor();

	List<TipoFinanziamento> getElencoTipiFinanziamento();	 
     
}
