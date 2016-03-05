package rs.model;


public enum MonthDays {
      JANUARY("gennaio",31), 
      FEBRUARY("febbraio",28) {
        public int getDays(boolean bBisestile) {
          return bBisestile ? 29 : 28;
        }
      }, 
      MARCH("marzo",31), 
      APRIL("aprile",30), 
      MAY("maggio",31), 
      JUNE("giugno",30), 
      JULY("luglio",31), 
      AUGUST("agosto",31), 
      SEPTEMBER("settembre",30), 
      OCTOBER("ottobre",31), 
      NOVEMBER("novembre",30), 
      DECEMBER("dicembre",31);

  private int days;
  private String nome;
  
  private MonthDays(String nome, int days) {
    this.days = days;
    this.nome = nome;
  }

  public int getDays(boolean bBisestile) {
    return days;
  }
  
  public static int getIndex(String m) {
	  	int count = 0;
		for (MonthDays month : MonthDays.values()) {
			count++;
			if (month.nome.compareToIgnoreCase(m) ==0) {
				return count;
			}
		}
		return 0;
	}
}
