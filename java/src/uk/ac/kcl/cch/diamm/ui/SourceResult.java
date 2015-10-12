package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 22/02/11
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class SourceResult {
    private Source source;
    private String sourceString;
    private Archive a;

    public SourceResult(int sKey){
       Source s=(Source) HibernateUtil.getSession().load(Source.class, sKey);
        if (s!=null){
            source=s;
            //Make sure it's initialized
             s.getShelfmark();
             a=s.getArchiveByArchivekey();
            sourceString= DisplayItem.makeSourceString(a, a.getAlcityByAlcitykey(), a.getAlcityByAlcitykey().getAlcountryByAlcountrykey());
        }
    }


    public SourceResult(Source s){
        this.source=s;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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
