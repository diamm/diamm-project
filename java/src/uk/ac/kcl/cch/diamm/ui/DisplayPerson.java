package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Alaffiliation;
import uk.ac.kcl.cch.diamm.model.Alperson;
import uk.ac.kcl.cch.diamm.model.Sourcealperson;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 03/06/11
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class DisplayPerson {

    Sourcealperson sourcealperson;
    Alperson p;
    Alaffiliation affiliation;

    public DisplayPerson(){

    }

    public DisplayPerson (Sourcealperson sp){
        sourcealperson=sp;
        p=sourcealperson.getAlpersonByAlpersonkey();
        affiliation= p.getAlaffiliation();
    }

    public DisplayPerson (Alperson person){
        p=person;
        affiliation= p.getAlaffiliation();
    }

    public Sourcealperson getSourcealperson() {
        return sourcealperson;
    }

    public void setSourcealperson(Sourcealperson sourcealperson) {
        this.sourcealperson = sourcealperson;
    }

    public Alperson getP() {
        return p;
    }

    public void setP(Alperson p) {
        this.p = p;
    }

    public Alaffiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Alaffiliation affiliation) {
        this.affiliation = affiliation;
    }
}
