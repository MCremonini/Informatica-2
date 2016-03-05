package galliacapocciona.ui;

import galliacapocciona.model.CalcolatoreSeggi;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MyController extends Controller {

	public MyController(UserInteractor userInteractor) {
		super(userInteractor);
	}

	@Override
	public String[] getCalcolatoriSeggi() {
		return CalcolatoreSeggi.getCalcolatoriSeggi();
	}

	@Override
	public Map<String, Integer> calcola(String nomeCalcolatoreSeggi, int numeroSeggi) {
		
		if(numeroSeggi < getSeggiMinimi()) {
			getUserInteractor().showMessage("numero di seggi inferiore al minimo richiesto");
			return null;
		}
		
		CalcolatoreSeggi calc = null;
		try {
			calc = CalcolatoreSeggi.getInstance(nomeCalcolatoreSeggi);
		}
		catch(NoSuchAlgorithmException e) {
			getUserInteractor().showMessage("algoritmo non valido");
			return null;
		}
		
		try {
			return calc.assegnaSeggi(numeroSeggi, getListaCollegi());
		}
		catch(Exception e) {
			getUserInteractor().showMessage("errore nel calcolo seggi");
		}
		return null;
	}

}
