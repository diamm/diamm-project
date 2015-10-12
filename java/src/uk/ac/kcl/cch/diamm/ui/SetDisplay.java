package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alsettype;
import uk.ac.kcl.cch.diamm.model.Set;
import uk.ac.kcl.cch.diamm.model.Source;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Dec 3, 2010
 * Time: 10:29:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class SetDisplay {
    private Set set;
    private Alsettype alsettype;
    private ArrayList<SourceDisplay> linkedsources;

    public SetDisplay(Set s,Alsettype type){
        set=s;
        alsettype=type;
    }
    public SetDisplay(Set s){
        set=s;

        if (s!=null){
            alsettype=s.getAlsettype();
            buildVars();
        }
    }

    public void buildVars(){
        Iterator s=set.getSources().iterator();
        if (s.hasNext()){
            linkedsources=new ArrayList<SourceDisplay>();
            while (s.hasNext()) {
                Source next = (Source) s.next();
                linkedsources.add(new SourceDisplay(next));
            }
        }
    }

    public SetDisplay(int sKey){
        set=(Set)  HibernateUtil.getSession().load(Set.class,sKey);
        setAlsettype(set.getAlsettype());
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public Alsettype getAlsettype() {
        return alsettype;
    }

    public void setAlsettype(Alsettype alsettype) {
        this.alsettype = alsettype;
    }

    public ArrayList<SourceDisplay> getLinkedsources() {
        return linkedsources;
    }

    public void setLinkedsources(ArrayList<SourceDisplay> linkedsources) {
        this.linkedsources = linkedsources;
    }
}
