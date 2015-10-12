package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Archive;
import uk.ac.kcl.cch.diamm.model.Source;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Dec 3, 2010
 * Time: 10:30:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveDisplay {
    Archive a;
    private ArrayList<Source> sources;

    public ArchiveDisplay(int aKey){
        if (aKey>0){
            a=(Archive) HibernateUtil.getSession().load(Archive.class,aKey);
            if (a.getSourcesByArchivekey()!=null){
                sources=new ArrayList<Source>();
                Iterator<Source> arcs=a.getSourcesByArchivekey().iterator();
                while (arcs.hasNext()) {
                    Source source =  arcs.next();
                    sources.add(source);
                }
            }
        }
    }

    public Archive getA() {
        return a;
    }

    public void setA(Archive a) {
        this.a = a;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }
}
