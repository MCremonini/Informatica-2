package teethcollege.esami.ui;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import teethcollege.model.Carriera;
import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.EsamiManager;
import teethcollege.persistence.MalformedFileException;

public class MyController extends Controller {

	private EsamiManager esamiManager;
	DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
	
	public MyController(String nomeFileInsegnamenti, String nomeFileCarriere, 
						UserInteractor userInteractor, EsamiManager esamiManager) {
		super(nomeFileInsegnamenti, nomeFileCarriere, userInteractor);
		
		if(esamiManager == null)
			throw new IllegalArgumentException("parametro EsamiManager non valido");
		
		this.esamiManager = esamiManager;
	}
	
	@Override
	public Carriera aggiungiEsame(Insegnamento corso, String voto, String data,	PianoDiStudi prescelto) {
		
		
		List<Esame> esami = getEsami(prescelto.getMatricola());
		Carriera carriera = new Carriera(prescelto, esami);
		
		try {
			Esame esame = getNuovoEsame(corso, voto, data);
			carriera.addEsame(esame);
			salvaEsami(prescelto.getMatricola(), carriera.getEsamiSostenuti());
		}
		catch(IllegalArgumentException e) {
			getUserInteractor().showMessage("non è possibile aggiungere l'esame");
		}
		
		return carriera;
	}

	@Override
	public List<Esame> getEsami(String matricola) {
		
		FileReader reader = null;
		List<Esame> esami = null;
		
		try {
			reader = new FileReader(matricola + ".txt");
			esami = esamiManager.caricaEsami(reader, getMappaInsegnamenti());
		}
		catch(MalformedFileException e) {
			getUserInteractor().showMessage("file matricola " + matricola + " non corretto");
		}
		catch(IOException e1) {
			getUserInteractor().showMessage("file matricola " + matricola + " non valido");
		}
		
		return esami;
	}

	@Override
	protected void salvaEsami(String matricola, List<Esame> esami) {
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(matricola + ".txt");
			esamiManager.salvaEsami(writer, esami);
		}
		catch(IOException e1) {
			getUserInteractor().showMessage("file matricola " + matricola + " non valido");
		}
	}
	
	public Esame getNuovoEsame(Insegnamento corso, String votoScelto, String dataScelta) {
		
		/*if (!getPianiDiStudi().contains(corso)) {
			getUserInteractor().showMessage("il corso non è previsto dal piano di studi");
			return null;
		}*/
		
		int voto = 0;
		try {
			voto = votoScelto.equalsIgnoreCase("30L") ? 31 : Integer.parseInt(votoScelto);
		}
		catch(NumberFormatException e) {
			getUserInteractor().showMessage("voto non valido");
			return null;
		}
			
		Date date = null;
		try {
			date = formatter.parse(dataScelta);
		}
		catch(ParseException e1) {
			getUserInteractor().showMessage("data non valida");
			return null;
		}
		
		return new Esame(corso, voto, date);
				
	}
}
