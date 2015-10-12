package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Archive;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.diamm.model.Source;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 05/07/11
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class BibliographySourceDisplay {
    Bibliography bib;
    Source s;
    String sourceString;
    Archive a;

    public BibliographySourceDisplay(Bibliography b,Source source,String notes,String page){
        s=source;
        bib=b;
        a=s.getArchiveByArchivekey();
        sourceString=DisplayItem.makeSourceString(a, a.getAlcityByAlcitykey(), a.getAlcityByAlcitykey().getAlcountryByAlcountrykey());
    }

    public Bibliography getBib() {
        return bib;
    }

    public void setBib(Bibliography bib) {
        this.bib = bib;
    }

    public Source getS() {
        return s;
    }

    public void setS(Source s) {
        this.s = s;
    }

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }

    public Archive getA() {
        return a;
    }

    public void setA(Archive a) {
        this.a = a;
    }
}
