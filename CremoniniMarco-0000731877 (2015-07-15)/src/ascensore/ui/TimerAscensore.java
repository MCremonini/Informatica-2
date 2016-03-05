package ascensore.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ascensore.model.Ascensore;

public class TimerAscensore {
	private Timer timer;
	private Ascensore ascensore;
	
	public TimerAscensore() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ascensore != null) {
					ascensore.tick();
				}
			}
		});
		timer.start();
	}
	
	public void setAscensore(Ascensore ascensore) {
		this.ascensore = ascensore;
	}	
}
