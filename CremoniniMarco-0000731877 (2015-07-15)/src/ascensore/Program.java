package ascensore;


import java.io.FileReader;
import java.util.List;

import ascensore.model.Edificio;
import ascensore.persistence.EdificiReader;
import ascensore.persistence.MyEdificiReader;
import ascensore.ui.Controller;
import ascensore.ui.MainFrame;
import ascensore.ui.MyController;

public class Program
{
	public static void main(String[] args)
	{
		Controller controller = null;

		try {
			EdificiReader edificiReader = new MyEdificiReader(); 
			List<Edificio> elencoEdifici;
			try (FileReader fileReader = new FileReader("Edifici.txt")) {
				elencoEdifici = edificiReader.readAll(fileReader);
			}
			controller = new MyController(elencoEdifici);
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}