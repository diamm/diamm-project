package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.model.Composer;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.model.Composition;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Dec 3, 2010
 * Time: 10:00:22 AM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * From Greg's file:
 * The Composer's name (Composers::lastname + Composers::firstname)
 * 2. A list of Sources (ARCHIVE::siglum + Source::sourceDisplayName) which load
 * Source Description Pages on click
 * 3. A list of Compositions (Composition::composition_name) which load Composition
 * Description Pages on click
 * 4. A statement of Date (as defined above) which is not a link
 * 5. A statement of Nationality (as defined above) which is not a link
 * 6. A list of aliases (Composers::variantspellings)
 * 7. A biography (Composers::info)
 * 8. A list of Bibliography Items (Bibliography::FullText_calculated) which are not
 * links
 */
public class ComposerDisplay {
    private Composer c;
    private ArrayList<SourceDisplay> sources;
    private ArrayList<CompositionDisplay> compositions;

    private ArrayList<Bibliography> bibs;
    private String name;

    public ComposerDisplay(Composer comp){
        if (comp != null) {
            c = comp;
            buildVars();
        }
    }

    public ComposerDisplay(int cKey) {
        Composer comp = (Composer) HibernateUtil.getSession().load(Composer.class, cKey);
        if (comp != null) {
            c = comp;
            name = c.buildComposerLabel();

        }
    }

    public void buildVars() {

        compositions = new ArrayList<CompositionDisplay>();//(ArrayList<Composition>) c.getCompositions();
        sources = new ArrayList<SourceDisplay>();
        //if (compositions != null) {
        String fullHql = "SELECT DISTINCT source,composition from Source as source"
         + " join source.itemsBySourcekey as item"
         + " join item.compositionByCompositionkey as composition"
         + " join composition.compositioncomposersByCompositionkey as compositioncomposer"
         + " WHERE composition.compositionName!='works by' AND compositioncomposer.composerkey=" + c.getComposerkey();
        org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
        //sources=(ArrayList<Source>) query.list();
        List<Object[]> objs = query.list();
        HashSet<Integer> compKeys=new HashSet<Integer>();
        HashSet<Integer> sKeys=new HashSet<Integer>();
        for (int i = 0; i < objs.size(); i++) {
            Object[] objects = objs.get(i);
            Source s = (Source) objects[0];
            Composition c = (Composition) objects[1];
            if (!sKeys.contains(new Integer(s.getSourcekey()))) {
                sKeys.add(new Integer(s.getSourcekey()));
                sources.add(new SourceDisplay(s));
            }
            if (!compKeys.contains(new Integer(c.getCompositionkey()))) {
                compKeys.add(new Integer(c.getCompositionkey()));
                compositions.add(new CompositionDisplay(c));
            }
        }
    }


    public Composer getC() {
        return c;
    }

    public void setC(Composer c) {
        this.c = c;
    }

    public ArrayList<SourceDisplay> getSources() {
        return sources;
    }

    public void setSources(ArrayList<SourceDisplay> sources) {
        this.sources = sources;
    }

    public ArrayList<CompositionDisplay> getCompositions() {
        return compositions;
    }

    public void setCompositions(ArrayList<CompositionDisplay> compositions) {
        this.compositions = compositions;
    }

    /*public ArrayList<Bibliography> getBibs() {
        return bibs;
    }

    public void setBibs(ArrayList<Bibliography> bibs) {
        this.bibs = bibs;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
