package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Alprovenance;
import uk.ac.kcl.cch.diamm.model.Sourceprovenance;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 28/06/11
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class SourceProvenanceDisplay {
    Alprovenance prov;
    Sourceprovenance sp;
    String uncertain;

    public SourceProvenanceDisplay(Alprovenance prov,Sourceprovenance sp){
        this.prov=prov;
        this.sp=sp;
    }

    public SourceProvenanceDisplay(Alprovenance prov,String unc){
        this.prov=prov;
        uncertain=unc;
    }

    public Alprovenance getProv() {
        return prov;
    }

    public void setProv(Alprovenance prov) {
        this.prov = prov;
    }

    public String getUncertain() {
        return uncertain;
    }

    public void setUncertain(String uncertain) {
        this.uncertain = uncertain;
    }

    public Sourceprovenance getSp() {
        return sp;
    }

    public void setSp(Sourceprovenance sp) {
        this.sp = sp;
    }
}
