package dentinia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalcolatoreSeggiQuoziente extends CalcolatoreSeggi {

	
	public CalcolatoreSeggiQuoziente() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void assegnaSeggi(int seggi, List<Partito> listaPartiti) {
		
		if(seggi <= 0)
			throw new IllegalArgumentException("numero di seggi non valido");
		
		// per pulizia
		for(Partito p : listaPartiti) {
			p.setSeggi(0);
		}
		
				
		List<ValorePartito> resti = new ArrayList<>();
		int totVoti = totaleVoti(listaPartiti);
		int quozNaturale = totVoti / seggi;
		int seggiAssegnati = 0;
		
		for(Partito p : listaPartiti) {
			int seggiPartito = p.getVoti() / quozNaturale;
			p.setSeggi(seggiPartito);
			int votiResidui = p.getVoti() % quozNaturale;
			resti.add(new ValorePartito(votiResidui, p));
			
			seggiAssegnati += seggiPartito;
		}
		
		int seggiRimasti = seggi - seggiAssegnati;
		
		Collections.sort(resti);
		
		// con questo metodo di calcolo che si basa sui voti effettivi, è impossibile che rimangano più seggi
		// dei partiti
		while(--seggiRimasti >= 0) {
			ValorePartito vp = resti.get(seggiRimasti);
			vp.getPartito().setSeggi(vp.getPartito().getSeggi() + 1);
		}
	}

	private int totaleVoti(List<Partito> listaPartiti) {
		int tot = 0;
		
		for(Partito p : listaPartiti) {
			tot += p.getVoti();
		}
		
		return tot;
	}
}
