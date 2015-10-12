package uk.ac.kcl.cch.diamm.ui;

import com.sun.mail.imap.protocol.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import uk.ac.kcl.cch.diamm.model.Item;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Mar 4, 2011
 * Time: 3:53:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompositionDisplay {
    private Composition composition;
    private ArrayList<SourceDisplay> sources;
    private ArrayList<Item> items;
    private ArrayList<Composer> composers;
    private ArrayList<BibliographyIntersectionDisplay> bibs;
    private ArrayList<Algenre> genres;
    private String uncertain;
    private String cycleString;
    private ArrayList<Compositioncomposer> composerLinks;

     public CompositionDisplay(Composition comp){
         composition =comp;
          if (comp != null) {

            buildVars();
        }
     }

    public CompositionDisplay(int cKey) {
        Composition comp = (Composition) HibernateUtil.getSession().load(Composition.class, cKey);
        if (comp != null) {
            composition = comp;
            buildVars();
        }
    }

    public void buildVars() {
        sources = new ArrayList<SourceDisplay>();
        //if (compositions != null) {
        composers=new ArrayList<Composer>();
        composerLinks=new ArrayList<Compositioncomposer>();
        Iterator<Compositioncomposer> ci= composition.getCompositioncomposersByCompositionkey().iterator();
        while (ci.hasNext()) {
            Compositioncomposer next =  ci.next();
            composerLinks.add(next);
            if (next.getAttributionuncertain()!=null){
                uncertain=next.getAttributionuncertain();
            }
            composers.add(next.getComposerByComposerkey());
        }


        ArrayList<Bibliographycomposition> bibcomps=(ArrayList<Bibliographycomposition>) HibernateUtil.getSession().createCriteria(Bibliographycomposition.class)
                .add(Property.forName("compositionkey").eq(composition.getCompositionkey())) //.addOrder(Order.desc("year"))
                .list();
        bibs=new ArrayList<BibliographyIntersectionDisplay>();
        for (int i = 0; i < bibcomps.size(); i++) {
            Bibliographycomposition bc =  bibcomps.get(i);
            bibs.add(new BibliographyIntersectionDisplay(bc.getBibliographykey(),bc.getNotes(),""));
        }
        Iterator<Algenre> g=composition.getGenres().iterator();
        genres=new ArrayList<Algenre>();
        while (g.hasNext()) {
            Algenre next = g.next();
            genres.add(next);
        }
        ArrayList<Source> sis= (ArrayList<Source>) HibernateUtil.getSession().createCriteria(Source.class)
                .addOrder(Order.asc("sortorder"))
                .createCriteria("itemsBySourcekey")
                .add(Property.forName("compositionkey").eq(composition.getCompositionkey()))
                .list();
        for (int i = 0; i < sis.size(); i++) {
            Source source =  sis.get(i);
            sources.add(new SourceDisplay(source));
        }
        Iterator<Item> ii=composition.getItemsByCompositionkey().iterator();
        items=new ArrayList<Item>();

        while (ii.hasNext()) {
            Item next = ii.next();
            items.add(next);

        }
        addCycles();
    }


    public void addCycles() {
        ArrayList<Compositioncyclecomposition> ccs = (ArrayList<Compositioncyclecomposition>) HibernateUtil.getSession().createCriteria(Compositioncyclecomposition.class)
                .add(Restrictions.eq("compositionkey", composition.getCompositionkey())).addOrder(Order.asc("orderno")).list();
        if (ccs != null && ccs.size() > 0) {
            String cString = "<ul>";
            ArrayList<Compositioncycle> cycles = new ArrayList<Compositioncycle>();
            for (int i = 0; i < ccs.size(); i++) {
                Compositioncyclecomposition ccc = ccs.get(i);
                cycles.add(ccc.getCompositioncycle());
            }
            for (int i = 0; i < cycles.size(); i++) {
                Compositioncycle cc = cycles.get(i);
                cString += "<li>" + cc.getAlcycletype().getCycletype() + ": " + cc.getTitle();
                ArrayList<Compositioncyclecomposition> children = (ArrayList<Compositioncyclecomposition>) HibernateUtil.getSession().createCriteria(Compositioncyclecomposition.class)
                        .add(Restrictions.eq("compositioncyclekey", cc.getCompositioncyclekey())).addOrder(Order.asc("orderno")).list();
                if (children != null && children.size() > 0) {
                    cString += "<ul>";
                    for (int j = 0; j < children.size(); j++) {
                        Compositioncyclecomposition kid = children.get(j);
                        cString += "<li>" + kid.getCompositionByCompositionkey().getCompositionName() + "</li>";
                    }
                    cString += "</ul>";
                }
                cString += "</li>";
            }
            cString += "</ul>";
            cycleString = cString;
        }
    }


    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    public ArrayList<SourceDisplay> getSourceDisplays() {
        return sources;
    }

    public void setSourceDisplays(ArrayList<SourceDisplay> sources) {
        this.sources = sources;
    }

    public ArrayList<Composer> getComposers() {
        return composers;
    }

    public void setComposers(ArrayList<Composer> composers) {
        this.composers = composers;
    }

    public ArrayList<BibliographyIntersectionDisplay> getBibs() {
        return bibs;
    }

    public void setBibs(ArrayList<BibliographyIntersectionDisplay> bibs) {
        this.bibs = bibs;
    }

    public ArrayList<Algenre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Algenre> genres) {
        this.genres = genres;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<SourceDisplay> getSources() {
        return sources;
    }

    public void setSources(ArrayList<SourceDisplay> sources) {
        this.sources = sources;
    }

    public String getUncertain() {
        return uncertain;
    }

    public void setUncertain(String uncertain) {
        this.uncertain = uncertain;
    }

    public ArrayList<Compositioncomposer> getComposerLinks() {
        return composerLinks;
    }

    public void setComposerLinks(ArrayList<Compositioncomposer> composerLinks) {
        this.composerLinks = composerLinks;
    }

    public String getCycleString() {
        return cycleString;
    }

    public void setCycleString(String cycleString) {
        this.cycleString = cycleString;
    }


}
