package compromeglio.ui;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;
import compromeglio.model.Monitoraggio;
import compromeglio.persistence.BeniReader;
import compromeglio.persistence.RilevazioniReader;

public class MyController extends Controller {

	public MyController(BeniReader readerBeni, RilevazioniReader readerRilevazioni, UserInteractor userInteractor) {
		super(userInteractor);

		try {
			beni = readerBeni.caricaBeni();
			rilevazioni = readerRilevazioni.caricaRilevazioni(getMappaBeni());
			
			monitoraggio = new Monitoraggio(beni, rilevazioni);
		}
		catch(IOException e) {
			userInteractor.showMessage("errore nella lettura dei file.applicazione sarà terminata");
			userInteractor.shutDownApplication();
		}
	}

	@Override
	public Set<Bene> getBeniPerCategoria(Categoria c) {
		Set<Bene> result = new TreeSet<>();
		for(Bene b: beni) {
			if(b.getCategoria() == c)
				result.add(b);
		}
		return result;
	}

	@Override
	public Set<Categoria> getCategorie() {
		Set<Categoria> result = new TreeSet<>();
		for(Bene b : beni) {
			result.add(b.getCategoria());
		}
		return result;
	}

}
