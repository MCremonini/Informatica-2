package ascensore.model;

public enum AscensoreProperty {
	PIANO_CORRENTE("pianoCorrente"),
	STATO_ASCENSORE("stato");
	
	private AscensoreProperty(String name){
		this.name=name;
	}
	
	@SuppressWarnings("unused")
	private String name;
}
