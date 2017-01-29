public class Vokabel
{
  private String spracheEins;
  private String spracheZwei;
  private int anzAbfragen;
  private int anzKorrekt;
  private int anzFalsch;
  private boolean abfrageFlag;
  
  public Vokabel(String sprache1, String sprache2)
  {
    this.spracheEins = sprache1;
    this.spracheZwei = sprache2;
    this.anzAbfragen = 0;
    this.anzKorrekt = 0;
    this.anzFalsch = 0;
    this.abfrageFlag = true;
  }
  
  public boolean getFlag()
  {
    return this.abfrageFlag;
  }
  
  public void setFlag(boolean abfrF)
  {
    this.abfrageFlag = abfrF;
  }
  
  public String getSpracheEins()
  {
    return this.spracheEins;
  }
  
  public String getSpracheZwei()
  {
    return this.spracheZwei;
  }
  
  public void erhoeheAbfragen()
  {
    this.anzAbfragen += 1;
  }
  
  public void plusKorrekt()
  {
    this.anzKorrekt += 1;
  }
  
  public void plusFalsch()
  {
    this.anzFalsch += 1;
  }
  
  public int getAnzAbfragen()
  {
    return this.anzAbfragen;
  }
  
  public int getAnzKorrekt()
  {
    return this.anzKorrekt;
  }
  
  public int getAnzFalsch()
  {
    return this.anzFalsch;
  }
  
  public void resetStat()
  {
    this.anzAbfragen = 0;
    this.anzKorrekt = 0;
    this.anzFalsch = 0;
  }
}
