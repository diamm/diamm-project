package uk.ac.kcl.cch.diamm.ui;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Another convenience class.
 * Gathers all relevant data to display an item in jsp
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 01-Sep-2010
 * Time: 13:29:47
 * To change this template use File | Settings | File Templates.
 */
public class DisplayItem implements Serializable{
    private Item item;
    private int key;
    private String folioStart;
    private String folioEnd;
    private String folioStartAlt;
    private String folioEndAlt;
    private String composerString;
    private String genreString;
    private String layout;
    private String compositionString;
    private List<Composer> composers;
    private List<Algenre> genres;
    private List<Image> images;
    private List<Image> sourceImages;
    private Composition composition;
    private Source source;
    private int numfolios;
    private List<Alperson> people;
    private List<Alprovenance> provs;
    private List<DisplayText> texts;
    // private Altincipit altincipit;
    private String disablestring;
    private String folioString;
    private String sourceString;
    private Archive archive;
    private String cycleString;
    private List<DisplayItem> cognates;
    private ArrayList<BibliographyIntersectionDisplay> bibs;
    private ArrayList<BibliographyIntersectionDisplay> compbibs;


    public DisplayItem(Item item) {
        this.item = item;
        if (this.item != null) {
            /*addComposerString();
            addGenreString();*/
            key = item.getItemkey();

            makeFolioString();

            /*folioStart = item.getFolioStart() + "";
            folioEnd = item.getFolioEnd() + "";
            folioEndAlt = item.getFolioEndAlt() + "";
            folioStartAlt = item.getFolioStartAlt() + "";*/
            composition = item.getCompositionByCompositionkey();
            source = item.getSourceBySourcekey();
            if (source != null) {
                archive = source.getArchiveByArchivekey();
            }
            if (item.getFolioEnd() != null && item.getFolioEnd().length() > 0 ) {
                numfolios = 0;// item.getFolioEnd() - item.getFolioStart();
            }
            makeSourceString();
        }
    }

    private void makeSourceString() {
        Archive a = archive;
        Alcity city = a.getAlcityByAlcitykey();
        Alcountry c = city.getAlcountryByAlcountrykey();
        sourceString = makeSourceString(a, city, c);
    }


    public static String makeSourceString(Archive a, Alcity city, Alcountry country) {
        String ss = "";
        if (country != null) {
            ss += country.getCountry() + ", ";
        }
        if (city != null) {
            ss += city.getCity() + ", ";
        }
        if (a != null) {
            ss += a.getArchivename();
        }
        return ss;
        //Archive::siglum {popup: alCountry::country, alCity::city Archive::archiveName} “ “ Source::shelfmark
    }

    public void makeFolioString(){
      folioString = makeFolioString(item);
    }

    public static String makeFolioString(Item item) {
        String fs = "";
        if (item.getFolioStart() != null && item.getFolioStart().length() > 0 && !item.getFolioStart().equalsIgnoreCase("null")) {
            fs = item.getFolioStart();
        }
        if (item.getFolioStartAlt() != null) {
            fs += item.getFolioStartAlt();
        }
        if (item.getFolioStart() != null && item.getFolioEnd() != null && !item.getFolioStart().equalsIgnoreCase(item.getFolioEnd())) {
            fs += "-";
        }
        if (item.getFolioEnd() != null && item.getFolioEnd().length() > 0 && !item.getFolioEnd().equalsIgnoreCase("null") && !item.getFolioStart().equalsIgnoreCase(item.getFolioEnd())) {
            fs += item.getFolioEnd();
        }
        if (item.getFolioEndAlt() != null &&  !item.getFolioStart().equalsIgnoreCase(item.getFolioEnd())) {
            fs += item.getFolioEndAlt();
        }
        return fs;
        /*
        <c:if test="${item.folioStart!=null}">${item.folioStart}</c:if>
                    <c:if test="${item.folioStartAlt ne 'null'}">${item.folioStartAlt}</c:if>
                    <c:if test="${item.folioStart!=null&&item.folioEnd!=null&&item.folioStart!=item.folioEnd}">
                        -
                    </c:if>
                    <c:if test="${item.folioEnd!=null&&item.folioStart!=item.folioEnd}">${item.folioEnd}</c:if>
                    <c:if test="${item.folioEndAlt ne 'null'}">${item.folioEndAlt}</c:if>
         */
    }

    /**
     * Loads extra data needed for detailed display
     */
    public void addDetailData() {
        addImages();
        addSourceImages();
        addComposers();
        if (composition!=null){
            //Populate objects
            Iterator<Composer> ci=composition.getComposers().iterator();
            while (ci.hasNext()) {
                Composer next = ci.next();
            }
        }
    }

    public void addFullItemData() {
        addGenres();


        addTexts();
        addPeople();
        addProvs();
        addCognates();
        addCycles();
        //addIncipits();
        addDisableString();
        addBibs();
    }


    public void addBibs() {
        ArrayList<Bibliographyitem> bibitems = (ArrayList<Bibliographyitem>) HibernateUtil.getSession().createCriteria(Bibliographyitem.class)
                .add(Restrictions.eq("itemkey", item.getItemkey()))
                .createCriteria("bibliography").addOrder(Order.asc("orderno"))
                .list();

        bibs=new ArrayList<BibliographyIntersectionDisplay>();
        if (bibitems != null && bibitems.size() > 0) {
            for (int i = 0; i < bibitems.size(); i++) {
                Bibliographyitem bitem = bibitems.get(i);
                BibliographyIntersectionDisplay bib = new BibliographyIntersectionDisplay(bitem.getBibliography(), bitem.getNotes(),"");
                bibs.add(bib);
            }
        }
        compbibs=new ArrayList<BibliographyIntersectionDisplay>();
        Iterator bc=item.getCompositionByCompositionkey().getBibliographycompositions().iterator();
        while (bc.hasNext()) {
            Bibliographycomposition next = (Bibliographycomposition) bc.next();
            BibliographyIntersectionDisplay bib = new BibliographyIntersectionDisplay(next.getBibliographykey(),next.getNotes(), "");
            compbibs.add(bib);
        }

    }

    public void addCognates() {
        String fullHql = "SELECT distinct item from Item as item" +
                //addJoin("join photodata.intermedium as intermedium ");
                " join item.sourceBySourcekey as source" +
                " WHERE item.compositionkey=" + item.getCompositionkey() +
                " order by source.sortorder";
        Query query = HibernateUtil.getSession().createQuery(fullHql);
        ArrayList<Item> items = (ArrayList<Item>) query.list();
        if (items != null && items.size() > 0) {
            cognates = new ArrayList<DisplayItem>();
            for (int i = 0; i < items.size(); i++) {
                Item item1 = items.get(i);
                cognates.add(new DisplayItem(item1));
            }
        }

    }

    /*
    *alCycleType::cycleType “: “ CompositionCycle::title list on new lines all members of linked cycle(s)
    * using CompositionCycle::compositionCycleKey
    * Composition::composition_name order using CompositionCycleComposition::orderNo
     */
    public void addCycles() {
        ArrayList<Compositioncyclecomposition> ccs = (ArrayList<Compositioncyclecomposition>) HibernateUtil.getSession().createCriteria(Compositioncyclecomposition.class)
                .add(Restrictions.eq("compositionkey", item.getCompositionkey())).addOrder(Order.asc("orderno")).list();
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

    public void addDisableString() {
        String dString = "";
        if ((item.getIncipitfilename() == null || item.getIncipitfilename().length() == 0) && (item.getMotetincipitfilename() == null || item.getMotetincipitfilename().length() == 0)) {
            dString = "1";
        }
        if (texts == null || texts.size() == 0) {
            dString += (dString.length() > 0 ? "," : "") + "2";
        }
        if ( (images == null || images.size() == 0 ) &&(sourceImages == null || sourceImages.size() == 0 )) {
            dString += (dString.length() > 0 ? "," : "") + "3";
        }
        disablestring = dString;
    }

    public void addTexts() {
        ArrayList<DisplayText> t = new ArrayList<DisplayText>();
        Iterator<Text> it = item.getTextsByItemkey().iterator();
        while (it.hasNext()) {
            //Make sure the authority lists are loaded
            Text text = it.next();
            t.add(new DisplayText(text));
        }
        if (t != null) {
            texts = t;
        }
        /*for (int i = 0; i < t.size(); i++) {
            Text text =  t.get(i);

        }*/
    }

    public void addPeople() {
        Iterator<Sourcealperson> sp = source.getSourcealpersonsBySourcekey().iterator();
        if (sp != null && sp.hasNext()) {
            people = new ArrayList<Alperson>();
            while (sp.hasNext()) {
                Sourcealperson sourcealperson = sp.next();
                people.add(sourcealperson.getAlpersonByAlpersonkey());
            }
        }


    }

    public void addProvs() {
        Iterator<Alprovenance> itprov = source.getAlprovenances().iterator();
        if (itprov != null & itprov.hasNext()) {
            provs = new Vector<Alprovenance>();
            while (itprov.hasNext()) {
                Alprovenance alprovenance = itprov.next();
                provs.add(alprovenance);
            }
        }
    }


    /**
     * Serialize all composers
     */
    private void addComposers() {
        //TODO:  Use this when attribution fixed
        /*
        if (ccs != null && ccs.size() > 0) {
            composers = new ArrayList<Composer>();
            String cString="";
            boolean uncertain=false;
            for (int i = 0; i < ccs.size(); i++) {
                Compositioncomposer cc = ccs.get(i);
                Composer c=cc.getComposerByComposerkey();
                composers.add(c);
                if (i>0){
                    cString+=" / ";
                }
                cString+="<a class=\"t9 m1\" href=\"Descriptions?op=COMPOSER&composerKey="+c.getComposerkey()+"\">"+c.getComposercomplete()+"</a>";
                if (!uncertain&&(cc.getAttributionuncertain()!=null&&cc.getAttributionuncertain().length()>0 )||ccs.size()>1){
                    uncertain=true;
                }
            }
             if (uncertain){
                 cString="?"+cString;
             }
            composerString=cString;
        }      */

        Iterator<Composer> comps = item.getCompositionByCompositionkey().getComposers().iterator();
        if (comps != null && comps.hasNext()) {
            composers = new ArrayList<Composer>();
            String cString = "";
            boolean uncertain = false;
            while (comps.hasNext()) {
                Composer c = comps.next();
                if (c.getComposerkey() > 0) {
                    composers.add(c);
                    if (cString.length() > 0) {
                        cString += " / ";
                        uncertain = true;
                    }
                    cString +=c.getComposercomplete()+ "<a class=\"t9 m1\" href=\"Descriptions?op=COMPOSER&composerKey=" + c.getComposerkey() + "\">view</a>";
                }
            }
            if (composers.size() == 0) {
                composers = null;
            } else {
                if (uncertain) {
                    cString = "?" + cString;
                }
                composerString = cString;
            }
        }
    }

    private void addImages() {
        this.images = ImageSearch.getImagesByItemKey(item.getItemkey());
        /*List<Itemimage> ii = HibernateUtil.getSession().createCriteria(Itemimage.class).add(Property.forName("itemKey").eq(item.getItemkey())).list();
        this.images = new ArrayList<Image>();
        for (int i = 0; i < ii.size(); i++) {
            Itemimage itemimage = ii.get(i);
            Image I = itemimage.getImageByImagekey();
            images.add(I);
        }*/

    }

    public List<Image> getSourceImages() {
        return sourceImages;
    }

    public void setSourceImages(List<Image> sourceImages) {
        this.sourceImages = sourceImages;
    }

    private void addSourceImages() {
        sourceImages = ImageSearch.getImagesBySourceKey(getSource().getSourcekey());
        //sourceImages = HibernateUtil.getSession().createCriteria(Image.class).add(Property.forName("sourcekey").eq(getSource().getSourcekey())).list();

    }

    /**
     * Serialize genres
     */
    private void addGenres() {
        Iterator<Compositiongenre> gi = composition.getCompositiongenresByCompositionkey().iterator();
        if (gi != null && gi.hasNext()) {
            genres = new ArrayList<Algenre>();
            while (gi.hasNext()) {
                Compositiongenre compositiongenre = gi.next();
                genres.add(compositiongenre.getAlgenreByAlgenrekey());
            }
        }
    }

    public String getComposerString() {
        if (composerString == null) {
            addComposers();
            /*composerString = "";
            if (composers == null) {
                addComposers();
            }
            if (composers != null) {
                for (int i = 0; i < composers.size(); i++) {
                    Composer composer = composers.get(i);
                    if (composerString.length() > 0) {
                        composerString += ",";
                    }
                    composerString += composer.getComposercomplete();
                }
            }*/
        }
        return composerString;
    }

    public void setComposerString(String composerString) {
        this.composerString = composerString;
    }

    public List<Alperson> getPeople() {
        return people;
    }

    public void setPeople(List<Alperson> people) {
        this.people = people;
    }

    public List<Alprovenance> getProvs() {
        return provs;
    }

    public void setProvs(List<Alprovenance> provs) {
        this.provs = provs;
    }

    public String getFolioEnd() {
        return folioEnd;
    }

    public void setFolioEnd(String folioEnd) {
        this.folioEnd = folioEnd;
    }

    public String getFolioStart() {
        return folioStart;
    }

    public void setFolioStart(String folioStart) {
        this.folioStart = folioStart;
    }

    public String getGenreString() {
        if (genreString == null) {
            genreString = "";
            if (composers == null) {
                addGenres();
            }
            if (genres != null) {
                for (int i = 0; i < genres.size(); i++) {
                    Algenre g = genres.get(i);
                    if (genreString.length() > 0) {
                        genreString += ",";
                    }
                    genreString += g.getGenre();
                }
            }
        }
        return genreString;
    }

    public void setGenreString(String genreString) {
        this.genreString = genreString;
    }

    public List<DisplayText> getTexts() {
        return texts;
    }

    public void setTexts(List<DisplayText> texts) {
        this.texts = texts;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getCompositionString() {
        return compositionString;
    }

    public void setCompositionString(String compositionString) {
        this.compositionString = compositionString;
    }

    public String getFolioEndAlt() {
        return folioEndAlt;
    }

    public void setFolioEndAlt(String folioEndAlt) {
        this.folioEndAlt = folioEndAlt;
    }

    public String getFolioStartAlt() {
        return folioStartAlt;
    }

    public void setFolioStartAlt(String folioStartAlt) {
        this.folioStartAlt = folioStartAlt;
    }


    public List<Composer> getComposers() {
        if (composers == null) {
            addComposers();
        }
        return composers;
    }

    public void setComposers(List<Composer> composers) {
        this.composers = composers;
    }

    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    public List<Algenre> getGenres() {
        return genres;
    }

    public void setGenres(List<Algenre> genres) {
        this.genres = genres;
    }

    public List<Image> getImages() {
        if (images == null) {
            addImages();
        }
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }


    public int getNumfolios() {
        return numfolios;
    }

    public void setNumfolios(int numfolios) {
        this.numfolios = numfolios;
    }

    public String getDisablestring() {
        return disablestring;
    }

    public void setDisablestring(String disablestring) {
        this.disablestring = disablestring;
    }

    public String getFolioString() {
        return folioString;
    }

    public void setFolioString(String folioString) {
        this.folioString = folioString;
    }

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public String getCycleString() {
        return cycleString;
    }

    public void setCycleString(String cycleString) {
        this.cycleString = cycleString;
    }

    public List<DisplayItem> getCognates() {
        return cognates;
    }

    public void setCognates(List<DisplayItem> cognates) {
        this.cognates = cognates;
    }

    public ArrayList<BibliographyIntersectionDisplay> getBibs() {
        return bibs;
    }

    public void setBibs(ArrayList<BibliographyIntersectionDisplay> bibs) {
        this.bibs = bibs;
    }

    public ArrayList<BibliographyIntersectionDisplay> getCompbibs() {
        return compbibs;
    }

    public void setCompbibs(ArrayList<BibliographyIntersectionDisplay> compbibs) {
        this.compbibs = compbibs;
    }
}
