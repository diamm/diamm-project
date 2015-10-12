package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 29/06/11
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public class DisplayText {
    Alvoice voice;
    Alclef clef;
    Almensuration mensuration;
    ArrayList<Allanguage> langs;
    Text t;
    String fullterm;
    String fulltermstandard;

    public DisplayText(Text t){
        this.t=t;
        if (t.getFulltermtext()!=null){
            fullterm=t.getFulltermtext().replaceAll("\\r","<br/>");
        }
        if (t.getStandardspellingfulltext()!=null){
            fulltermstandard =t.getStandardspellingfulltext().replaceAll("\\r","<br/>");
        }
        clef=t.getAlclefbyalclefkey();
        if (clef!=null){clef.getClef();}
        mensuration=t.getAlmensurationByAlmensurationkey();
        if (mensuration!=null){mensuration.getMensurationsign();}
        langs=new ArrayList<Allanguage>();
        Iterator<Allanguage> li=t.getLanguages().iterator();
        while (li.hasNext()) {
            Allanguage next =  li.next();
            next.getLanguage(); //force load
            langs.add(next);
        }
    }

    public Alvoice getVoice() {
        return voice;
    }

    public void setVoice(Alvoice voice) {
        this.voice = voice;
    }

    public Alclef getClef() {
        return clef;
    }

    public void setClef(Alclef clef) {
        this.clef = clef;
    }

    public Almensuration getMensuration() {
        return mensuration;
    }

    public void setMensuration(Almensuration mensuration) {
        this.mensuration = mensuration;
    }

    public ArrayList<Allanguage> getLangs() {
        return langs;
    }

    public void setLangs(ArrayList<Allanguage> langs) {
        this.langs = langs;
    }

    public Text getT() {
        return t;
    }

    public void setT(Text t) {
        this.t = t;
    }

    public String getFullterm() {
        return fullterm;
    }

    public void setFullterm(String fullterm) {
        this.fullterm = fullterm;
    }

    public String getFulltermstandard() {
        return fulltermstandard;
    }

    public void setFulltermstandard(String fulltermstandard) {
        this.fulltermstandard = fulltermstandard;
    }
}
