package pharmame.ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import pharmame.model.Farmaco;
import pharmame.model.FarmacoFilterFactory;
import pharmame.model.Filter;
import pharmame.persistence.BadFileFormatException;
import pharmame.persistence.FarmacoReader;

public class MyController implements Controller {

	private FarmacoReader reader;
	private MainView view;
	private FarmacoFilterFactory filterFactory;
	Collection<Farmaco> farmaci;
	
	public MyController(FarmacoReader reader, MainView view, FarmacoFilterFactory filterFactory) {
		this.reader = reader;
		this.view = view;
		this.filterFactory = filterFactory;
	}

	@Override
	public void start() {
		
		try {
			Reader file = new FileReader("Classe_A_per_Principio_Attivo_15.01.2014.csv");
			farmaci = reader.readFrom(file);
		}
		catch(FileNotFoundException e) {
			view.showMessage("impossibile accedere al file");
		}
		catch(IOException e) {
			view.showMessage("impossibile leggere da file");
		}
		catch(BadFileFormatException e) {
			view.showMessage("formato non corretto");
		}
		view.setController(this);
		view.setFilterNames(filterFactory.getNames());
		view.setVisible(true);
	}

	@Override
	public void filterBy(String filterName, String searchKey) {
		
		List<Farmaco> fltFarmaci = new ArrayList<>();
		Filter<Farmaco> filter = null;
		
		try {
			filter = filterFactory.get(filterName, searchKey);
		}
		catch(IllegalArgumentException e) {
			
		}
		
		if(filter != null) {
			for(Farmaco f : farmaci) {
				if (filter.filter(f))
					fltFarmaci.add(f);
			}
		}
		
		view.setFarmaci(fltFarmaci);
	}

	@Override
	public void printSelected(int[] selectedRows) {
		
		// recupero tutti i farmaci selezionati
		List<Farmaco> selFarmaci = new ArrayList<>();

		for(int i = 0; i < selectedRows.length; i ++) {
			int sel = selectedRows[i];
			if(view.getFarmacoAt(sel) != null)
				selFarmaci.add(view.getFarmacoAt(sel));
		}
		
		Collections.sort(selFarmaci, new Comparator<Farmaco>() {
										public int compare(Farmaco f1, Farmaco f2) {
											if(f1.getPrezzo() > f2.getPrezzo())
												return 1;
											else if(f1.getPrezzo() < f2.getPrezzo())
												return -1;
											return 0;
										}
									}
						);
		
		String [] strFarmaci = new String[selFarmaci.size()];
		int i = 0;
		for( Farmaco f : selFarmaci) {
			strFarmaci[i++] = f.toString();
		}
		view.setOutput(strFarmaci);
	}

	

}
