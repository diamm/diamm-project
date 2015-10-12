package uk.ac.kcl.cch.diamm.ui;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.SetType;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 01-Dec-2010
 * Time: 14:26:58
 * To change this template use File | Settings | File Templates.
 */
public class SourceDisplay {
    Source s;
    Archive a;
    ArrayList<Image> images;
    private String sourceString;
    private ArrayList<Composition> compositions;
    private ArrayList<BibliographyIntersectionDisplay> bibs;
    private ArrayList<DisplayItem> items;
    private ArrayList<SetDisplay> sets;
    private ArrayList<DisplaySourceCopyist> copyists;
    private ArrayList<DisplayPerson> persons;
     private ArrayList<SourceProvenanceDisplay> sourceprovs;

    int sourcekey = 0;

    public SourceDisplay(Source source) {
        s = source;
        init();
    }

    public SourceDisplay(int sKey) {
        s = (Source) HibernateUtil.getSession().load(Source.class, sKey);
        init();
    }

    private void init() {

        if (s != null) {
            a = s.getArchiveByArchivekey();


        }
        this.sourcekey = s.getSourcekey();
        sourceString = DisplayItem.makeSourceString(a, a.getAlcityByAlcitykey(), a.getAlcityByAlcitykey().getAlcountryByAlcountrykey());
    }

    public void addPersons() {
        Iterator<Sourcealperson> i = (Iterator<Sourcealperson>) s.getSourcealpersonsBySourcekey().iterator();
        if (i.hasNext()) {
            persons = new ArrayList<DisplayPerson>();
            while (i.hasNext()) {
                Sourcealperson next = i.next();
                persons.add(new DisplayPerson(next));
            }
        }
    }

    public void addCopyists() {
        Iterator<Sourcecopyist> i = (Iterator<Sourcecopyist>) s.getSourcecopyists().iterator();
        if (i.hasNext()) {
            copyists = new ArrayList<DisplaySourceCopyist>();
            while (i.hasNext()) {
                Sourcecopyist next = i.next();
                DisplaySourceCopyist ds = new DisplaySourceCopyist(next, s);
                copyists.add(ds);
            }
        }
    }

    public void addLinkedSets() {
        //TODO optimize getting settype
        String fullHql = "SELECT DISTINCT S from Source as source"
                + " join source.sourcesetsBySourcekey as sourceset"
                + " join sourceset.setBySetkey as S"
               // + " join S.alsettype as settype"
                + " WHERE source.sourcekey=" + s.getSourcekey();
        org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
        List<Set> sds=query.list();
        //ArrayList<SetDisplay> filteredList=new ArrayList<SetDisplay>();
        sets=new ArrayList<SetDisplay>();
        //TODO fix multiple returns on set query
        int skey=0;
        for (int i = 0; i < sds.size(); i++) {
            Set set =  sds.get(i);
            if (set.getSetkey()!=skey){
                skey=set.getSetkey();
                sets.add(new SetDisplay(set));
            }
        }
        /*Iterator<Set> is = s.getSets().iterator();
        sets = new ArrayList<SetDisplay>();
        while (is.hasNext()) {
            Set next = is.next();
            SetDisplay sd = new SetDisplay(next);
            sets.add(sd);
        }*/
    }

    private void addSourceProvs(){
        String fullHql = "SELECT prov,sp from Source as source"
                + " join source.sourceprovenancesBySourcekey as sp"
                + " join sp.alprovenanceByAlprovenancekey as prov"
                + " WHERE source.sourcekey=" + s.getSourcekey();
        org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
        List objs = query.list();
        sourceprovs=new ArrayList<SourceProvenanceDisplay>();
        for (int i = 0; i < objs.size(); i++) {
            Object[] o =(Object[])  objs.get(i);
            Alprovenance p=(Alprovenance) o[0];
            Sourceprovenance sp=(Sourceprovenance) o[1];
            sourceprovs.add(new SourceProvenanceDisplay(p,sp));
        }
    }

    public void buildVars() {
        images = ImageSearch.getImagesBySourceKey(s.getSourcekey());
        compositions = new ArrayList<Composition>();//(ArrayList<Composition>) c.getCompositions();
        addSourceProvs();

        //if (compositions != null) {
        String fullHql = "SELECT DISTINCT composition from Source as source"
                + " join source.itemsBySourcekey as item"
                + " join item.compositionByCompositionkey as composition"
                + " WHERE source.sourcekey=" + s.getSourcekey();
        org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
        //sources=(ArrayList<Source>) query.list();
        List<Composition> objs = query.list();
        for (int i = 0; i < objs.size(); i++) {
            Composition c = objs.get(i);
            if (!compositions.contains(c)) {
                if (c.getCompositioncomposersByCompositionkey()!=null){
                    Iterator<Compositioncomposer> links=c.getCompositioncomposersByCompositionkey().iterator();
                    while (links.hasNext()) {
                        Compositioncomposer next = links.next();
                        next.getComposerByComposerkey();
                    }
                }
                compositions.add(c);
            }
        }
        if (s.getItemsBySourcekey() != null) {
            Iterator<Item> sitems = (Iterator<Item>) s.getItemsBySourcekey().iterator();
            this.items = new ArrayList<DisplayItem>();
            while (sitems.hasNext()) {
                Item next = sitems.next();
                DisplayItem di = new DisplayItem(next);
                di.addDetailData();
                //di.addFullItemData();
                items.add(di);
            }

        }
        if (s.getBibliographysources() != null) {
            bibs = new ArrayList<BibliographyIntersectionDisplay>();
            Iterator<Bibliographysource> bibit = HibernateUtil.getSession().createCriteria(Bibliographysource.class)
                    .add(Restrictions.eq("sourcekey",s.getSourcekey()))
                    .createCriteria("bibliography").addOrder(Order.asc("orderno")).list().iterator();
            while (bibit.hasNext()) {
                Bibliographysource bibliography = bibit.next();
                BibliographyIntersectionDisplay bib = new BibliographyIntersectionDisplay(bibliography.getBibliographykey(), bibliography.getNotes(), bibliography.getPage());
                bibs.add(bib);
            }
        }
        addCopyists();
        addPersons();
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Source getS() {
        return s;
    }

    public void setS(Source s) {
        this.s = s;
    }

    public int getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(int sourcekey) {
        this.sourcekey = sourcekey;
    }

    public ArrayList<BibliographyIntersectionDisplay> getBibs() {
        return bibs;
    }

    public void setBibs(ArrayList<BibliographyIntersectionDisplay> bibs) {
        this.bibs = bibs;
    }

    public ArrayList<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(ArrayList<Composition> compositions) {
        this.compositions = compositions;
    }

    public ArrayList<DisplayItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DisplayItem> items) {
        this.items = items;
    }

    public Archive getA() {
        return a;
    }

    public void setA(Archive a) {
        this.a = a;
    }

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }

    public ArrayList<SetDisplay> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SetDisplay> sets) {
        this.sets = sets;
    }

    public ArrayList<DisplaySourceCopyist> getCopyists() {
        return copyists;
    }

    public void setCopyists(ArrayList<DisplaySourceCopyist> copyists) {
        this.copyists = copyists;
    }

    public ArrayList<DisplayPerson> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<DisplayPerson> persons) {
        this.persons = persons;
    }

    public ArrayList<SourceProvenanceDisplay> getsourceprovs() {
        return sourceprovs;
    }

    public void setsourceprovs(ArrayList<SourceProvenanceDisplay> sourceprovs) {
        this.sourceprovs = sourceprovs;
    }
}
