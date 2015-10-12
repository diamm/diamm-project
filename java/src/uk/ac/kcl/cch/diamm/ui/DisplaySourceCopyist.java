package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Alcopyist;
import uk.ac.kcl.cch.diamm.model.Alcopyisttype;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.model.Sourcecopyist;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 03/06/11
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class DisplaySourceCopyist {

    private Sourcecopyist sourcecopyist;
    private Source s;
    private Alcopyist copyist;
    private Alcopyisttype type;


    public DisplaySourceCopyist(Sourcecopyist sc,Source source){
        sourcecopyist =sc;
        s=source;
        copyist=sc.getAlcopyist();
        type=sc.getAlcopyisttype();
    }

    public void addVars(){

    }

    public DisplaySourceCopyist(){

    }

    public Sourcecopyist getSourcecopyist() {
        return sourcecopyist;
    }

    public void setSourcecopyist(Sourcecopyist sourcecopyist) {
        this.sourcecopyist = sourcecopyist;
    }

    public Source getS() {
        return s;
    }

    public void setS(Source s) {
        this.s = s;
    }

    public Alcopyist getCopyist() {
        return copyist;
    }

    public void setCopyist(Alcopyist copyist) {
        this.copyist = copyist;
    }

    public Alcopyisttype getType() {
        return type;
    }

    public void setType(Alcopyisttype type) {
        this.type = type;
    }
}
