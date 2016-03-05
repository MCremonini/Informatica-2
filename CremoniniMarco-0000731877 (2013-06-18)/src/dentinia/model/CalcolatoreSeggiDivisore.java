package dentinia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalcolatoreSeggiDivisore extends CalcolatoreSeggi {

	public CalcolatoreSeggiDivisore() {
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
		
		
		List<ValorePartito> graduatoria = new ArrayList<>();

		for(Partito p : listaPartiti) {
			
			for(int i = 0; i < seggi; i++) {
				graduatoria.add(new ValorePartito(p.getVoti() / (i + 1), p));
			}
		}
		
	/*	
		boolean bBreak = false;
		int div = 1;
		while(div <= seggi && !bBreak) {
			
			for(Partito p : listaPartiti) {
				resti.add(new ValorePartito(p.getVoti() / div, p));
			}
			
			div++;
		}*/
		
		Collections.sort(graduatoria);
		
		
		for(int i = 0; i < seggi; i++) {
			ValorePartito vp = graduatoria.get(i);
			vp.getPartito().setSeggi(vp.getPartito().getSeggi() + 1);
		}
		
	}
}
