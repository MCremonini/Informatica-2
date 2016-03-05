package zannotaxi.model;

import java.util.List;

public class ZannoTassametro extends Tassametro {

	public ZannoTassametro(double velocitaLimite, double scattoInizialeGiorno,
			double scattoInizialeNotte, TariffaATempo tariffaATempo,
			List<TariffaADistanza> tariffeADistanza) {
		super(velocitaLimite, scattoInizialeGiorno, scattoInizialeNotte,
				tariffaATempo, tariffeADistanza);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calcolaCostoVariabile(CorsaTaxi corsa) {
		// TODO Auto-generated method stub
		return 0;
	}

}
