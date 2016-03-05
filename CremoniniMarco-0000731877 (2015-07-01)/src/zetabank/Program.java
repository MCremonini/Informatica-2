package zetabank;

import java.io.FileReader;
import java.util.List;

import zetabank.model.TipoFinanziamento;
import zetabank.persistence.FinReader;
import zetabank.persistence.MyFinReader;
import zetabank.ui.MainFrame;
import zetabank.ui.controller.Controller;
import zetabank.ui.controller.MyController;
import zetabank.ui.controller.SwingUserInteractor;
import zetabank.ui.controller.UserInteractor;

public class Program
{
	public static void main(String[] args)
	{
		Controller controller = null;
		UserInteractor userInteractor = new SwingUserInteractor();

		try {
			FinReader reader = new MyFinReader(new FileReader("Finanziamenti.txt"));
			List<TipoFinanziamento> elencoFinanziamenti = reader.readElements();
			controller = new MyController(userInteractor, elencoFinanziamenti);
			
			MainFrame mainFrame = new MainFrame(controller);
			mainFrame.setVisible(true);
			
//			//------------------------------------------------------------------------------------------------
//			Finanziamento fin = controller.calcAndCheck(
//					new TipoFinanziamento("rate decrescenti", SchemaFinanziamento.RATE_DECRESCENTI,
//							4.0, 10, 20, new Periodicita[]{Periodicita.MENSILE, Periodicita.BIMESTRALE}),
//					60000, 10, Periodicita.MENSILE, 699);
//			if (fin==null) System.exit(1);
//			
//			System.out.println(fin);
//			NumberFormat formatter = NumberFormat.getCurrencyInstance();
//			System.out.println("Interessi complessivi: " + formatter.format(fin.sommaTotaleInteressi()));
//			//----------dettaglio rate-----------
//			int k=0;
//			for(Rata r: fin.getRate()){
//				k++;
//				System.out.println("Rata " + k + ", quota capitale = " + formatter.format(r.getQuotaCapitale()) 
//						+ ", quota interessi = " + formatter.format(r.getQuotaInteressi())); 
//			}
			//------------------------------------------------------------------------------------------------
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}