import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI
{
  private String author;
  private String version;
  private String date;
  private VokabelListe vokabelListe;
  private JFrame fenster;
  private JPanel vokPanel;
  private JPanel statPanel;
  private JPanel listPanel;
  private JPanel lernPanel;
  private JPanel messagePanel;
  private Container contentPane;
  private JTextArea listAusgabe;
  private File selDatei;
  private FileWriter speiDatei;
  private JScrollPane listAusScroll;
  private JTextField[] lernFeld;
  private JTextField messageField;
  private JTextField[] statFeld;
  private String wortEingabe;
  private String vokEingabe;
  private Vokabel aktuelleVokabel;
  private JPanel listBearbeitPanel;
  private JRadioButton paramRadioButton1;
  private JRadioButton paramRadioButton2;
  private JRadioButton paramRadioButton3;
  private JRadioButton paramRadioButton4;
  private JRadioButton paramRadioButton5;
  private JRadioButton paramRadioButton6;
  private ButtonGroup paramGroup1;
  private ButtonGroup paramGroup2;
  private JTextField[] listEingabe;
  private JButton listOKbutton;
  private Vokabel altesPaar;
  private Vokabel problemVok;
  
  public GUI()
  {
    this.author = "Marcus Kesper";
    this.version = "Version 1.3";
    this.date = "16.01.2014";
    
    this.wortEingabe = "";
    
    this.vokabelListe = new VokabelListe();
    
    this.lernFeld = new JTextField[12];
    this.statFeld = new JTextField[24];
    this.listEingabe = new JTextField[5];
    
    this.fenster = new JFrame("Vokabeltrainer");
    
    this.contentPane = this.fenster.getContentPane();
    
    this.contentPane.setLayout(new BorderLayout());
    this.vokPanel = new JPanel();
    this.vokPanel.setLayout(new BorderLayout());
    
    this.listPanel = new JPanel();
    this.listPanel.setLayout(new BorderLayout());
    
    this.listBearbeitPanel = new JPanel();
    this.listBearbeitPanel.setLayout(new GridLayout(2, 3));
    for (int k = 0; k < 5; k++)
    {
      this.listEingabe[k] = new JTextField();
      this.listBearbeitPanel.add(this.listEingabe[k]);
    }
    this.listEingabe[0].setText("Vokabel:");
    this.listEingabe[3].setVisible(false);
    this.listEingabe[4].setVisible(false);
    this.listOKbutton = new JButton();
    this.listBearbeitPanel.add(this.listOKbutton);
    this.listOKbutton.setVisible(false);
    
    this.listAusgabe = new JTextArea();
    this.listAusgabe.setTabSize(4);
    this.listAusScroll = new JScrollPane(this.listAusgabe);
    this.listAusScroll.setPreferredSize(new Dimension(300, 200));
    this.listPanel.add(this.listAusScroll, "South");
    setListpanelVisib(false, "");
    
    this.listPanel.add(this.listBearbeitPanel, "North");
    
    this.lernPanel = new JPanel();
    this.lernPanel.setLayout(new GridLayout(4, 3));
    for (int k = 0; k < 12; k++)
    {
      this.lernFeld[k] = new JTextField();
      this.lernPanel.add(this.lernFeld[k]);
    }
    resetLernFelder();
    
    this.paramRadioButton1 = new JRadioButton("1");
    this.paramRadioButton2 = new JRadioButton("2");
    this.paramRadioButton3 = new JRadioButton("3");
    this.paramGroup1 = new ButtonGroup();
    this.paramGroup1.add(this.paramRadioButton1);
    this.paramGroup1.add(this.paramRadioButton2);
    this.paramGroup1.add(this.paramRadioButton3);
    
    this.paramRadioButton4 = new JRadioButton("X/Deutsch");
    this.paramRadioButton5 = new JRadioButton("Deutsch/X");
    this.paramRadioButton6 = new JRadioButton("Zufällig");
    this.paramGroup2 = new ButtonGroup();
    this.paramGroup2.add(this.paramRadioButton4);
    this.paramGroup2.add(this.paramRadioButton5);
    this.paramGroup2.add(this.paramRadioButton6);
    
    this.vokPanel.add(this.listPanel, "South");
    this.vokPanel.add(this.lernPanel, "Center");
    
    this.statPanel = new JPanel();
    this.statPanel.setLayout(new GridLayout(12, 2));
    for (int k = 0; k < 24; k++)
    {
      this.statFeld[k] = new JTextField();
      this.statPanel.add(this.statFeld[k]);
    }
    for (int k = 6; k < 10; k++) {
      this.statFeld[k].setVisible(false);
    }
    for (int k = 16; k < 18; k++) {
      this.statFeld[k].setVisible(false);
    }
    this.messagePanel = new JPanel();
    this.messageField = new JTextField();
    this.messagePanel.add(this.messageField);
    
    this.contentPane.add(this.vokPanel, "Center");
    this.contentPane.add(this.statPanel, "East");
    this.contentPane.add(this.messagePanel, "South");
    
    resetMessageField();
    
    JMenuBar menuZeile = new JMenuBar();
    
    JMenu listeMenu = new JMenu("Liste");
    JMenuItem oeffnen = new JMenuItem("öffnen");
    JMenuItem speichern = new JMenuItem("Speichern");
    JMenu bearbeiten = new JMenu("Bearbeiten");
    JMenuItem hinzufuegen = new JMenuItem("Vokabelpaar hinzufügen");
    JMenuItem loeschen = new JMenuItem("Vokabelpaar löschen");
    JMenuItem aendern = new JMenuItem("Vokabelpaar ändern");
    bearbeiten.add(hinzufuegen);
    bearbeiten.add(loeschen);
    bearbeiten.add(aendern);
    listeMenu.add(oeffnen);
    listeMenu.add(speichern);
    listeMenu.add(bearbeiten);
    
    JMenu sessionsMenu = new JMenu("Lernen");
    JMenuItem startSession = new JMenuItem("Vokabeln abfragen");
    JMenuItem beendeSession = new JMenuItem("Abfragen beenden");
    sessionsMenu.add(startSession);
    sessionsMenu.add(beendeSession);
    
    JMenu statistikMenu = new JMenu("Statistik");
    JMenuItem statReset = new JMenuItem("Alle Werte zurücksetzen");
    statistikMenu.add(statReset);
    
    JMenu einstellungenMenu = new JMenu("Einstellungen");
    JMenu settings1 = new JMenu("Anzahl Richtige");
    ButtonGroup paramAnzR = new ButtonGroup();
    JRadioButtonMenuItem param1 = new JRadioButtonMenuItem("1", false);
    paramAnzR.add(param1);
    settings1.add(param1);
    JRadioButtonMenuItem param2 = new JRadioButtonMenuItem("2", false);
    paramAnzR.add(param2);
    settings1.add(param2);
    JRadioButtonMenuItem param3 = new JRadioButtonMenuItem("3", true);
    paramAnzR.add(param3);
    settings1.add(param3);
    einstellungenMenu.add(settings1);
    
    JMenu settings2 = new JMenu("Abfrage/Eingabe");
    ButtonGroup AbfrEing = new ButtonGroup();
    JRadioButtonMenuItem param4 = new JRadioButtonMenuItem("X/Deutsch", 
      false);
    AbfrEing.add(param4);
    settings2.add(param4);
    JRadioButtonMenuItem param5 = new JRadioButtonMenuItem("Deutsch/X", 
      false);
    AbfrEing.add(param5);
    settings2.add(param5);
    JRadioButtonMenuItem param6 = new JRadioButtonMenuItem("Zufällig", true);
    AbfrEing.add(param6);
    settings2.add(param6);
    einstellungenMenu.add(settings2);
    
    JMenu infoMenu = new JMenu("Info");
    JMenuItem info = new JMenuItem("About...");
    infoMenu.add(info);
    
    JMenu exitMenu = new JMenu("Exit");
    JMenuItem beenden = new JMenuItem("Beenden");
    exitMenu.add(beenden);
    
    menuZeile.add(listeMenu);
    menuZeile.add(sessionsMenu);
    menuZeile.add(statistikMenu);
    menuZeile.add(einstellungenMenu);
    menuZeile.add(infoMenu);
    menuZeile.add(exitMenu);
    
    initStat();
    
    this.fenster.setJMenuBar(menuZeile);
    
    this.listEingabe[1].addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.vokEingabe = GUI.this.listEingabe[1].getText().trim();
        GUI.this.listEingabe[2].setText(GUI.this.vokabelListe.getPartnerVok(GUI.this.vokEingabe));
        GUI.this.listEingabe[4].setVisible(true);
        GUI.this.listEingabe[4].setBackground(new Color(120, 230, 120));
        GUI.this.listEingabe[4].setText(GUI.this.vokabelListe.getPartnerVok(GUI.this.vokEingabe));
        if ((GUI.this.listOKbutton.getText().equals("ändern")) && 
          (!GUI.this.listEingabe[2].getText().equals("")))
        {
          GUI.this.altesPaar = new Vokabel(GUI.this.listEingabe[1].getText().trim(), 
            GUI.this.listEingabe[2].getText().trim());
          GUI.this.listEingabe[2].setBackground(new Color(220, 250, 220));
          GUI.this.listEingabe[2].setEditable(true);
          GUI.this.listEingabe[1].setEditable(false);
        }
        GUI.this.listEingabe[3].setVisible(true);
        GUI.this.listEingabe[3].setBackground(new Color(120, 230, 120));
        GUI.this.listEingabe[3].setText(GUI.this.vokEingabe);
        GUI.this.listEingabe[3].setEditable(false);
        if (!GUI.this.listEingabe[2].getText().equals("")) {
          GUI.this.listOKbutton.setEnabled(true);
        }
      }
    });
    this.listEingabe[2].addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.vokEingabe = GUI.this.listEingabe[2].getText().trim();
        GUI.this.listEingabe[4].setVisible(true);
        GUI.this.listEingabe[4].setBackground(new Color(120, 230, 120));
        GUI.this.listEingabe[4].setText(GUI.this.vokEingabe);
        GUI.this.listEingabe[4].setEditable(false);
        GUI.this.listOKbutton.setEnabled(true);
      }
    });
    this.listOKbutton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (GUI.this.listOKbutton.getText().equals("Neu"))
        {
          Vokabel neuesPaar = new Vokabel(GUI.this.listEingabe[3].getText()
            .trim(), GUI.this.listEingabe[4].getText().trim());
          GUI.this.vokabelListe.addVokabel(neuesPaar);
        }
        else if (GUI.this.listOKbutton.getText().equals("Löschen"))
        {
          Vokabel neuesPaar = new Vokabel(GUI.this.listEingabe[3].getText()
            .trim(), GUI.this.listEingabe[4].getText().trim());
          GUI.this.vokabelListe.delVokabel(neuesPaar);
        }
        else if (GUI.this.listOKbutton.getText().equals("ändern"))
        {
          Vokabel neuesPaar = new Vokabel(GUI.this.listEingabe[3].getText()
            .trim(), GUI.this.listEingabe[4].getText().trim());
          GUI.this.vokabelListe.delVokabel(GUI.this.altesPaar);
          GUI.this.vokabelListe.addVokabel(neuesPaar);
        }
        GUI.this.listOKbutton.setEnabled(false);
        GUI.this.listAusgabe.setText("");
        GUI.this.vokabelListe.listeAnzeigen(GUI.this.listAusgabe);
        GUI.this.fenster.pack();
      }
    });
    param1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAnzRichtige(1);
      }
    });
    param2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAnzRichtige(2);
      }
    });
    param3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAnzRichtige(3);
      }
    });
    param4.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAbfrageRichtung(1);
      }
    });
    param5.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAbfrageRichtung(2);
      }
    });
    param6.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.vokabelListe.setAbfrageRichtung(3);
      }
    });
    oeffnen.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          GUI.this.resetMessageField();
          GUI.this.listeOeffnen();
        }
        catch (IOException e1)
        {
          System.out.println("Can't open file");
        }
      }
    });
    speichern.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          GUI.this.resetMessageField();
          GUI.this.listeSpeichern();
        }
        catch (IOException e1)
        {
          System.out.println("Can't open file");
        }
      }
    });
    hinzufuegen.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        if (GUI.this.vokabelListe.size() == 0)
        {
          GUI.this.messageField.setBackground(new Color(220, 12, 2));
          GUI.this.messageField.setText("Keine Liste geladen");
        }
        else
        {
          GUI.this.setListpanelVisib(true, "Neu");
          GUI.this.vokabelNeu();
        }
        GUI.this.fenster.pack();
      }
    });
    loeschen.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        if (GUI.this.vokabelListe.size() == 0)
        {
          GUI.this.messageField.setBackground(new Color(220, 12, 2));
          GUI.this.messageField.setText("Keine Liste geladen");
        }
        else
        {
          GUI.this.setListpanelVisib(true, "Löschen");
          GUI.this.vokabelLoeschen();
        }
        GUI.this.fenster.pack();
      }
    });
    aendern.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        if (GUI.this.vokabelListe.size() == 0)
        {
          GUI.this.messageField.setBackground(new Color(220, 12, 2));
          GUI.this.messageField.setText("Keine Liste geladen");
        }
        else
        {
          GUI.this.setListpanelVisib(true, "ändern");
          GUI.this.vokabelAendern();
        }
        GUI.this.fenster.pack();
      }
    });
    startSession.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.setListpanelVisib(false, "");
        GUI.this.fenster.pack();
        GUI.this.vokAbfragen();
      }
    });
    beendeSession.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.vokabelListe.resetStat();
        GUI.this.initStat();
        GUI.this.resetLernFelder();
        GUI.this.listAusgabe.setText("");
        GUI.this.vokabelListe.listeAnzeigen(GUI.this.listAusgabe);
        GUI.this.fenster.pack();
      }
    });
    beenden.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.progBeenden();
      }
    });
    this.lernFeld[4].addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.wortEingabe = GUI.this.lernFeld[4].getText().trim();
        GUI.this.lernFeld[4].setEditable(false);
        GUI.this.checkInput(GUI.this.wortEingabe);
        GUI.this.vokAbfragen();
      }
    });
    statReset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.resetMessageField();
        GUI.this.vokabelListe.resetStat();
        GUI.this.initStat();
        GUI.this.fenster.pack();
      }
    });
    this.fenster.pack();
    this.fenster.setVisible(true);
  }
  
  private void setListpanelVisib(boolean listvis, String buttonText)
  {
    for (int k = 0; k < 5; k++)
    {
      this.listEingabe[k].setBackground(new Color(220, 220, 220));
      this.listEingabe[k].setVisible(listvis);
    }
    this.listOKbutton.setText(buttonText);
    this.listOKbutton.setVisible(listvis);
    this.listOKbutton.setEnabled(false);
    this.listEingabe[3].setVisible(false);
    this.listEingabe[4].setVisible(false);
  }
  
  private void vokabelNeu()
  {
    this.listEingabe[1].setText("");
    this.listEingabe[2].setText("");
    this.listEingabe[1].setEditable(true);
    this.listEingabe[2].setEditable(true);
    this.listEingabe[1].setBackground(new Color(220, 250, 220));
    this.listEingabe[2].setBackground(new Color(220, 250, 220));
  }
  
  private void vokabelLoeschen()
  {
    this.listEingabe[1].setText("");
    this.listEingabe[2].setText("");
    this.listEingabe[1].setEditable(true);
    this.listEingabe[2].setEditable(false);
    this.listEingabe[1].setBackground(new Color(220, 250, 220));
  }
  
  private void vokabelAendern()
  {
    this.listEingabe[1].setText("");
    this.listEingabe[2].setText("");
    this.listEingabe[1].setEditable(true);
    this.listEingabe[2].setEditable(false);
    this.listEingabe[1].setBackground(new Color(220, 250, 220));
  }
  
  private void initStat()
  {
    resetMessageField();
    for (int i = 0; i < 6; i++) {
      this.statFeld[i].setBackground(new Color(240, 240, 210));
    }
    for (int i = 10; i < 16; i++) {
      this.statFeld[i].setBackground(new Color(220, 250, 250));
    }
    this.statFeld[0].setText("Anzahl Abfragen: ");
    this.statFeld[1].setText("0");
    this.statFeld[2].setText("davon richtig: ");
    this.statFeld[3].setText("0");
    this.statFeld[4].setText("davon falsch: ");
    this.statFeld[5].setText("0");
    this.statFeld[10].setText("% richtig: ");
    this.fenster.pack();
  }
  
  private void setzeStat()
  {
    resetMessageField();
    this.statFeld[1].setText(new Integer(this.aktuelleVokabel.getAnzAbfragen())
      .toString());
    this.statFeld[3].setText(new Integer(this.aktuelleVokabel.getAnzKorrekt())
      .toString());
    this.statFeld[5].setText(new Integer(this.aktuelleVokabel.getAnzFalsch())
      .toString());
    this.statFeld[11].setText(new Float(this.vokabelListe.prozRichtige()).toString());
    this.fenster.pack();
  }
  
  public static void main(String[] args)
  {
    new GUI();
  }
  
  public void vokAbfragen()
  {
    if (this.vokabelListe.size() == 0)
    {
      this.messageField.setBackground(new Color(220, 12, 2));
      this.messageField.setText("Keine Liste geladen");
      this.fenster.pack();
    }
    else if (this.vokabelListe.checkCand() > 0)
    {
      resetMessageField();
      this.listAusgabe.setText("");
      this.vokabelListe.listFertig(this.listAusgabe);
      this.aktuelleVokabel = this.vokabelListe.getVokabel();
      if (this.aktuelleVokabel.getFlag()) {
        this.lernFeld[1].setText(this.aktuelleVokabel.getSpracheEins());
      } else {
        this.lernFeld[1].setText(this.aktuelleVokabel.getSpracheZwei());
      }
      this.lernFeld[4].setEditable(true);
      this.lernFeld[4].setBackground(new Color(220, 250, 220));
      this.lernFeld[4].setText("");
      this.lernFeld[2].setText("noch " + this.vokabelListe.checkCand() + "/" + 
        this.vokabelListe.size());
      this.fenster.pack();
    }
    else
    {
      resetMessageField();
      this.vokabelListe.resetStat();
      initStat();
      resetLernFelder();
      this.listAusgabe.setText("");
      this.vokabelListe.listeAnzeigen(this.listAusgabe);
      this.messageField.setText("Alle Vokabeln abgefragt");
      zeigeWorst();
      this.fenster.pack();
    }
  }
  
  private void zeigeWorst()
  {
    this.problemVok = this.vokabelListe.getWorst();
    this.statFeld[18].setText(this.problemVok.getSpracheEins());
    this.statFeld[19].setText(this.problemVok.getSpracheZwei());
  }
  
  private void resetMessageField()
  {
    this.messageField.setBackground(new Color(200, 200, 200));
    this.messageField.setText(this.version + " - " + this.author + " - " + this.date);
    this.fenster.pack();
  }
  
  public void checkInput(String eingabe)
  {
    if (this.vokabelListe.pruefeEingabe(eingabe, this.aktuelleVokabel))
    {
      this.lernFeld[10].setBackground(new Color(2, 228, 2));
      this.lernFeld[10].setText("KORREKT");
    }
    else
    {
      this.lernFeld[10].setBackground(new Color(228, 2, 2));
      this.lernFeld[10].setText("FALSCH");
    }
    if (this.aktuelleVokabel.getFlag()) {
      this.lernFeld[11].setText(this.aktuelleVokabel.getSpracheZwei());
    } else {
      this.lernFeld[11].setText(this.aktuelleVokabel.getSpracheEins());
    }
    setzeStat();
  }
  
  public void progBeenden()
  {
    System.exit(0);
  }
  
  public void resetLernFelder()
  {
    for (int k = 0; k < 12; k++)
    {
      this.lernFeld[k].setEditable(false);
      this.lernFeld[k].setBackground(new Color(250, 250, 250));
    }
    this.lernFeld[0].setText("Abfrage: ");
    this.lernFeld[1].setText(".........");
    this.lernFeld[2].setText("");
    this.lernFeld[3].setText("Eingabe: ");
    this.lernFeld[4].setText(".........");
    this.lernFeld[9].setText("Bewertung:");
    this.lernFeld[10].setText(".........");
    this.lernFeld[11].setText("");
  }
  
  public void listeSpeichern()
    throws IOException
  {
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showSaveDialog(this.fenster);
    if (returnVal != 0) {
      return;
    }
    this.selDatei = chooser.getSelectedFile();
    this.speiDatei = new FileWriter(this.selDatei);
    this.vokabelListe.listeInFile(this.speiDatei);
    this.speiDatei.close();
  }
  
  public void listeOeffnen()
    throws IOException
  {
    String[] zeileToVokabel = new String[2];
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(this.fenster);
    if (returnVal != 0) {
      return;
    }
    this.selDatei = chooser.getSelectedFile();
    this.vokabelListe.initList();
    BufferedReader reader = new BufferedReader(new FileReader(this.selDatei));
    String zeile = reader.readLine();
    while (zeile != null)
    {
      zeileToVokabel = zeile.split(",", 2);
      Vokabel vokabelPaar = new Vokabel(zeileToVokabel[0].trim(), 
        zeileToVokabel[1].trim());
      this.vokabelListe.addVokabel(vokabelPaar);
      zeile = reader.readLine();
    }
    reader.close();
    this.vokabelListe.listeAnzeigen(this.listAusgabe);
    this.fenster.pack();
  }
}
