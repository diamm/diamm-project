package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Algenre;
import uk.ac.kcl.cch.diamm.model.Composer;
import uk.ac.kcl.cch.diamm.model.Composition;
import uk.ac.kcl.cch.diamm.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Dec 3, 2010
 * Time: 10:30:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenreDisplay {
    Algenre g;
    private ArrayList<Source> sources;
    private ArrayList<Composition> compositions;
    private ArrayList<Composer> composers;

    public GenreDisplay(int gKey) {
        if (gKey > 0) {
            g = (Algenre) HibernateUtil.getSession().load(Algenre.class, gKey);
            buildVars();
        }
    }

    public void buildVars() {
        compositions = new ArrayList<Composition>();//(ArrayList<Composition>) c.getCompositions();
        sources = new ArrayList<Source>();
        composers=new ArrayList<Composer>();
        //if (compositions != null) {
        String fullHql = "SELECT DISTINCT source,composition,composer from Source as source";
        fullHql += " join source.itemsBySourcekey as item";
        fullHql += " join item.compositionByCompositionkey as composition";
        fullHql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
        fullHql += " join compositioncomposer.composerByComposerkey as composer";
        fullHql += " join composition.compositiongenresByCompositionkey as compositiongenre";
        fullHql += " WHERE compositiongenre.algenrekey="+g.getAlgenrekey();

        org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
        //sources=(ArrayList<Source>) query.list();
        List<Object[]> objs = query.list();
        for (int i = 0; i < objs.size(); i++) {
            Object[] objects = objs.get(i);
            Source s = (Source) objects[0];
            Composition c = (Composition) objects[1];
            Composer comp=(Composer)objects[2];
            if (!sources.contains(s)) {
                sources.add(s);
            }
            if (!compositions.contains(c)) {
                compositions.add(c);
            }
            if (!composers.contains(comp)){
                composers.add(comp);
            }
        }
    }

    public ArrayList<Composer> getComposers() {
        return composers;
    }

    public void setComposers(ArrayList<Composer> composers) {
        this.composers = composers;
    }

    public ArrayList<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(ArrayList<Composition> compositions) {
        this.compositions = compositions;
    }

    public Algenre getG() {
        return g;
    }

    public void setG(Algenre g) {
        this.g = g;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }
}