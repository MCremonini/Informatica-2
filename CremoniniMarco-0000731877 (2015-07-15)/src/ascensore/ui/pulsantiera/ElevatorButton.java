package ascensore.ui.pulsantiera;

import java.awt.Color;
import java.awt.Font;


/**
 * MyRoundButton.java - ED specialisation for Elevators of the original
 * RoundButton
 * 
 * @author Enrico Denti
 * @version 01/07/2015
 */
public class ElevatorButton extends RoundButton {

	private static final long serialVersionUID = 1L;

	private Color spento, alPiano, occupato;
	private Color currentColor;
	private String text;

	/**
	 * Construct a new Elevator Button with the specified colors for elevator
	 * status
	 *
	 * @param spento
	 *            the color the button should be filled with when the lift is idle
	 * @param alPiano
	 *            the color the button should be filled with when the lift is present at the floor
	 * @param occupato
	 *            the color the button should be filled with when the lift is busy
	 */
	public ElevatorButton(String label, Color spento, Color alPiano, Color occupato) {
		super(label.length()==1 ? " " + label + " " : label);
		this.setFont(new Font("Verdana", Font.BOLD, 30));
		this.setBorderPainted(true);
		this.setSpento(spento);
		this.setAlPiano(alPiano);
		this.setOccupato(occupato);
		this.setCurrentColor(spento);
	}

	public ElevatorButton() {
		this("", Color.gray, Color.green, Color.red);
	}

	/**
	 * constructor that sets colors to defaults and the button text to the argument
	 */
	public ElevatorButton(String txt) {
		this(txt, Color.gray, Color.green, Color.red);
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public Color getSpento() {
		return spento;
	}

	public void setSpento(Color spento) {
		this.spento = spento;
	}

	public Color getAlPiano() {
		return alPiano;
	}

	public void setAlPiano(Color alPiano) {
		this.alPiano = alPiano;
	}

	public Color getOccupato() {
		return occupato;
	}

	public void setOccupato(Color occupato) {
		this.occupato = occupato;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	protected void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
		setBackground(currentColor);
		repaint(); revalidate();
	}

	public void setAlPiano() {
		setCurrentColor(this.alPiano);
	}

	public void setSpento() {
		setCurrentColor(this.spento);
	}

	public void setOccupato() {
		setCurrentColor(this.occupato);
	}
	
}
