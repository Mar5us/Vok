import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;

public class VokabelListe
{
  private ArrayList<Vokabel> vokListe;
  private Random zufallsGen;
  private Vokabel abfrageVok;
  private float prozRichtig;
  private int anzAbVok;
  private int abfrageRichtung;
  private int flag;
  private int anzCand;
  private int totAnzAb;
  private int totAnzRicht;
  
  public VokabelListe()
  {
    this.vokListe = new ArrayList();
    this.zufallsGen = new Random();
    this.abfrageRichtung = 3;
    
    this.prozRichtig = 0.0F;
    this.anzAbVok = 3;
    this.flag = 0;
  }
  
  public void setAnzRichtige(int anzRich)
  {
    this.anzAbVok = anzRich;
  }
  
  public int totAnzAbfragen()
  {
    this.totAnzAb = 0;
    for (int i = 0; i < this.vokListe.size(); i++) {
      this.totAnzAb += ((Vokabel)this.vokListe.get(i)).getAnzAbfragen();
    }
    return this.totAnzAb;
  }
  
  public int totAnzRichtige()
  {
    this.totAnzRicht = 0;
    for (int i = 0; i < this.vokListe.size(); i++) {
      this.totAnzRicht += ((Vokabel)this.vokListe.get(i)).getAnzKorrekt();
    }
    return this.totAnzRicht;
  }
  
  public float prozRichtige()
  {
    this.prozRichtig = 
      (new Integer(totAnzRichtige()).floatValue() / new Integer(totAnzAbfragen()).floatValue());
    return Math.round(this.prozRichtig * 100.0F * 100.0F) / 100;
  }
  
  public Vokabel getWorst()
  {
    int worst = 0;
    for (int i = 0; i < this.vokListe.size(); i++) {
      if (((Vokabel)this.vokListe.get(i)).getAnzFalsch() > ((Vokabel)this.vokListe.get(worst)).getAnzFalsch()) {
        worst = i;
      }
    }
    return (Vokabel)this.vokListe.get(worst);
  }
  
  public void addVokabel(Vokabel neueVokabel)
  {
    this.vokListe.add(neueVokabel);
  }
  
  public void listeAnzeigen(JTextArea textFeld)
  {
    textFeld.setText("");
    for (int i = 0; i < this.vokListe.size(); i++) {
      textFeld.append("\t" + ((Vokabel)this.vokListe.get(i)).getSpracheEins() + " -- " + 
        ((Vokabel)this.vokListe.get(i)).getSpracheZwei() + "\n");
    }
  }
  
  public void listFertig(JTextArea textFeld)
  {
    for (int i = 0; i < this.vokListe.size(); i++) {
      if (((Vokabel)this.vokListe.get(i)).getAnzKorrekt() >= this.anzAbVok) {
        textFeld.append("\t" + ((Vokabel)this.vokListe.get(i)).getSpracheEins() + 
          " -- " + ((Vokabel)this.vokListe.get(i)).getSpracheZwei() + "\n");
      }
    }
  }
  
  public Vokabel getVokabel()
  {
    do
    {
      this.abfrageVok = ((Vokabel)this.vokListe.get(this.zufallsGen.nextInt(this.vokListe.size())));
    } while (this.abfrageVok.getAnzKorrekt() >= this.anzAbVok);
    this.abfrageVok.erhoeheAbfragen();
    if (this.abfrageRichtung == 1)
    {
      this.abfrageVok.setFlag(true);
    }
    else if (this.abfrageRichtung == 2)
    {
      this.abfrageVok.setFlag(false);
    }
    else if (this.abfrageRichtung == 3)
    {
      this.flag = this.zufallsGen.nextInt(2);
      if (this.flag == 0) {
        this.abfrageVok.setFlag(true);
      } else {
        this.abfrageVok.setFlag(false);
      }
    }
    return this.abfrageVok;
  }
  
  public boolean pruefeEingabe(String eingabe, Vokabel neueVokabel)
  {
    String andereVok;
    if (neueVokabel.getFlag()) {
      andereVok = neueVokabel.getSpracheZwei();
    } else {
      andereVok = neueVokabel.getSpracheEins();
    }
    if (andereVok.equals(eingabe))
    {
      neueVokabel.plusKorrekt();
      return true;
    }
    neueVokabel.plusFalsch();
    return false;
  }
  
  public int checkCand()
  {
    this.anzCand = 0;
    for (int i = 0; i < this.vokListe.size(); i++) {
      if (((Vokabel)this.vokListe.get(i)).getAnzKorrekt() < this.anzAbVok) {
        this.anzCand += 1;
      }
    }
    return this.anzCand;
  }
  
  public void resetStat()
  {
    for (int i = 0; i < this.vokListe.size(); i++) {
      ((Vokabel)this.vokListe.get(i)).resetStat();
    }
  }
  
  public void listeInFile(FileWriter speiDatei)
  {
    for (int i = 0; i < this.vokListe.size(); i++) {
      try
      {
        speiDatei.write(((Vokabel)this.vokListe.get(i)).getSpracheEins() + ", " + 
          ((Vokabel)this.vokListe.get(i)).getSpracheZwei() + "\r\n");
      }
      catch (IOException e1)
      {
        System.out.println("Can't open file");
      }
    }
  }
  
  public int size()
  {
    return this.vokListe.size();
  }
  
  public void setAbfrageRichtung(int abfrRicht)
  {
    this.abfrageRichtung = abfrRicht;
  }
  
  public void initList()
  {
    this.vokListe.clear();
  }
  
  public void delVokabel(Vokabel altesPaar)
  {
    for (int i = 0; i < this.vokListe.size(); i++)
    {
      if (((Vokabel)this.vokListe.get(i)).getSpracheEins().equals(altesPaar
        .getSpracheEins())) {
        if (((Vokabel)this.vokListe.get(i)).getSpracheZwei().equals(altesPaar
          .getSpracheZwei()))
        {
          this.vokListe.remove(i);
          return;
        }
      }
      if (((Vokabel)this.vokListe.get(i)).getSpracheZwei().equals(altesPaar
        .getSpracheEins())) {
        if (((Vokabel)this.vokListe.get(i)).getSpracheEins().equals(altesPaar
          .getSpracheZwei()))
        {
          this.vokListe.remove(i);
          return;
        }
      }
    }
  }
  
  public String getPartnerVok(String vokEingabe)
  {
    for (int i = 0; i < this.vokListe.size(); i++)
    {
      if (((Vokabel)this.vokListe.get(i)).getSpracheEins().equals(vokEingabe)) {
        return ((Vokabel)this.vokListe.get(i)).getSpracheZwei();
      }
      if (((Vokabel)this.vokListe.get(i)).getSpracheZwei().equals(vokEingabe)) {
        return ((Vokabel)this.vokListe.get(i)).getSpracheEins();
      }
    }
    return "";
  }
}
