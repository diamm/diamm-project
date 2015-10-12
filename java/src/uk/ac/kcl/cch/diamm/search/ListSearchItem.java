package uk.ac.kcl.cch.diamm.search;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.FacetTools;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.type.*;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.facet.QueryMaker;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Feb 18, 2011
 * Time: 3:40:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListSearchItem extends SearchItem {

    //private SearchItemType listType;
    private Hashtable listValues;


    public ListSearchItem(SearchItemType type) {
        setType(type);
        setId(this.hashCode());
        setOrder(0);
        listValues = new Hashtable();
        //setPage("sourcesearch.jsp");

    }

    public void addSearchCriteria(QueryMaker q) {

        String hql = "  ";
        switch (getType()) {
            case LANGUAGE:

                hql += " join item.textsByItemkey as text";
                hql += " join text.languages as lang";
                hql += " WHERE lang.language like '%" + getTextValue() + "%'";
                break;
            case COPYIST:
                hql = " join item.sourceBySourcekey as source " +
                        "join source.sourcecopyists as sc " +
                        " WHERE sc.alcopyist.copyistname like '%" + getTextValue() + "%'";
                break;
            case NOTATION:
                hql = " join item.sourceBySourcekey as source "
                        + " join source.notationsources as ns"
                        + " WHERE ns.alnotationtype.notationType like '%" + getValue()+"%'"
                        + " OR lower(source.notation) like '%" + getLabel() + "%'";
                break;
            case SET:
                hql = " join item.sourceBySourcekey as source "
                        + " join source.sets as sets"
                        + " WHERE sets.clustershelfmark like '" + getTextValue() + "'";
                break;
            /*case CLEF:

                hql += " join item.textsByItemkey as texts";
                hql += " join texts.alclefbyalclefkey as clef";
                hql += " WHERE clef.clef like '%" + getTextValue() + "%'";
                break;
            case MENSURATION:

                hql += " join item.textsByItemkey as texts";
                hql += " join texts.almensurationByAlmensurationkey as mens";
                hql += " WHERE mens.mensuration like '%" + getTextValue() + "%'";
                break;*/
            case VOICE:

                hql += " join item.textsByItemkey as texts";
                hql += " join texts.alvoicebyalvoicekey as voice";
                hql += " WHERE voice.voice like '%" + getTextValue() + "%'";
                break;
            case GENRE:

                hql += " join item.compositionByCompositionkey as composition";
                hql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
                hql += " join compositioncomposer.composerByComposerkey as composer";
                hql += " join composition.compositiongenresByCompositionkey as compositiongenre";
                hql += " join compositiongenre.algenreByAlgenrekey as genre";
                hql += " WHERE genre.genre like '" + getTextValue() + "'";
                break;
            case TITLE:

                hql += " join item.compositionByCompositionkey as composition";
                hql += " WHERE composition.compositionname like '" + getTextValue() + "'";
                break;
            case COMPOSER:

                hql += " join item.compositionByCompositionkey as composition";
                hql += " join composition.compositioncomposersByCompositionkey as compositioncomposer";
                hql += " join compositioncomposer.composerByComposerkey as composer";
                hql += " WHERE composer.composercomplete like '" + getTextValue() + "'";
                break;
            case PERSON:
                hql += " join item.sourceBySourcekey as source ";
                hql += " join source.sourcealpersonsBySourcekey as sourceperson";
                hql += " join sourceperson.alpersonByAlpersonkey as person";
                hql += " WHERE person.fullname like '" + getTextValue() + "'";
                break;
            case PROVENANCE:
                hql += " join item.sourceBySourcekey as source ";
                hql += " join source.sourceprovenancesBySourcekey as sourceprovenance";
                hql += " join sourceprovenance.alprovenanceByAlprovenancekey as prov";
                hql += " WHERE prov.country like '%" + getTextValue() + "%'";
                break;

        }
        if (hql.length() > 0) {
            //Workaround to fix granularity problem.
            //if (hql.indexOf("join source.itemsBySourcekey as item") > -1) {
            hql = "SELECT DISTINCT item.itemkey from Item as item " + hql + " ORDER BY item.itemkey";
            Query query = HibernateUtil.getSession().createQuery(hql);
            ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
            if (keys != null && keys.size() > 0) {
                SearchManager.addItemKeyConstraint(keys, q, getOperand());
            }
            /*} else {
                hql = "SELECT DISTINCT source.sourcekey from Source as source " + hql + " ORDER BY source.sourcekey";
                Query query = HibernateUtil.getSession().createQuery(hql);
                ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
                if (keys != null && keys.size() > 0) {
                    SearchManager.addSourceKeyConstraint(keys, q, getOperand());
                }
            } */
        }
    }

    public void setFromRequest(HttpServletRequest request) {
        /* String p = request.getParameter("copistykey");
        if (p != null) {
            listValues.put("copyist", p);
        }
        p = request.getParameter("notationtypekey");
        if (p != null) {
            listValues.put("notationtype", p);
        }
        p = request.getParameter("clefkey");
        if (p != null) {
            listValues.put("clef", p);
        }
        p = request.getParameter("mensurationkey");
        if (p != null) {
            listValues.put("mensuration", p);
        }
        p = request.getParameter("voicekey");
        if (p != null) {
            listValues.put("voice", p);
        }
        p = request.getParameter("genrekey");
        if (p != null) {
            listValues.put("genre", p);
        }
        p = request.getParameter("compositionkey");
        if (p != null) {
            listValues.put("composition", p);
        }
        p = request.getParameter("composerkey");
        if (p != null) {
            listValues.put("composer", p);
        }
        p = request.getParameter("languagekey");
        if (p != null) {
            listValues.put("languagekey", p);
        }

        p = request.getParameter("personkey");
        if (p != null) {
            listValues.put("personkey", p);
        }
        p = request.getParameter("provenancekey");
        if (p != null) {
            listValues.put("provenancekey", p);
        }*/
    }

    public ArrayList<AutoCompleteResult> getAutoCompleteResults(HttpServletRequest request) {
        String t = request.getParameter("itemType");
        ArrayList<AutoCompleteResult> results = new ArrayList<AutoCompleteResult>();
        String search = request.getParameter("search");
        if (t != null) {
            SearchItemType type = SearchItemType.valueOf(t);
            switch (type) {
                case PERSON:
                    //List<FacetCriterion> persons = PersonFacet.personValues;
                    List<Alperson> persons = HibernateUtil.getSession().createCriteria(Alperson.class)
                            .add(Restrictions.ilike("fullname", "%" + search + "%"))
                            .addOrder(Property.forName("fullname").asc()).list();
                    for (int i = 0; i < persons.size(); i++) {
                        Alperson alperson = persons.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(alperson.getAlpersonkey(), alperson.getFullname());
                        results.add(r);
                    }
                    /*for (int i = 0; i < persons.size(); i++) {
                        FacetCriterion fc = persons.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*/
                    results = filterAutoResults(request, new PersonFacetType(), results);
                    break;
                case LANGUAGE:
                    //List<FacetCriterion> langs = LanguageFacet.languageValues;
                    List<Allanguage> langs = HibernateUtil.getSession().createCriteria(Allanguage.class)
                            .add(Restrictions.ilike("language", "%" + search + "%"))
                            .addOrder(Property.forName("language").asc()).list();
                    for (int i = 0; i < langs.size(); i++) {
                        Allanguage al = langs.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(al.getAllangaugekey(), al.getLanguage());
                        results.add(r);
                    }
                    /*for (int i = 0; i < langs.size(); i++) {
                        FacetCriterion fc = langs.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*/
                    results = filterAutoResults(request, new LanguageFacetType(), results);
                    break;
                case COPYIST:
                    List<Alcopyist> copies = (List<Alcopyist>) HibernateUtil.getSession().createCriteria(Alcopyist.class).add(Restrictions.ilike("copyistname", "%" + search + "%")).addOrder(Property.forName("copyistname").asc()).list();
                    for (int i = 0; i < copies.size(); i++) {
                        Alcopyist alcopyist = copies.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(alcopyist.getAlcopyistkey(), alcopyist.getCopyistname());//+" ("+alcopyist.g +")");
                        results.add(r);
                    }
                    break;
                case NOTATION:
                    List<Alnotationtype> notes = (List<Alnotationtype>) HibernateUtil.getSession().createCriteria(Alnotationtype.class)
                            .add(Restrictions.ilike("notationType", "%" + search + "%")).addOrder(Order.asc("notationType")).list();
                    for (int i = 0; i < notes.size(); i++) {
                        Alnotationtype aln = notes.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(aln.getAlnotationtypekey(), aln.getNotationType());
                        results.add(r);
                    }
                    /*langs = NotationFacet.notationValues;
                    for (int i = 0; i < langs.size(); i++) {
                        FacetCriterion fc = langs.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*/
                    break;
                case SET:
                    List<Set> sets = (List<Set>) HibernateUtil.getSession().createCriteria(Set.class)
                            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                            .add(Restrictions.ilike("clustershelfmark", "%" + search + "%")).addOrder(Order.asc("clustershelfmark")).list();
                    for (int i = 0; i < sets.size(); i++) {
                        Set s = sets.get(i);
                        Alsettype setType = s.getAlsettype();
                        String label = "";
                        if (setType != null) {
                            label = setType.getDescription() + " > ";
                        }
                        label += s.getClustershelfmark();
                        AutoCompleteResult r = new AutoCompleteResult(s.getSetkey(), label);
                        results.add(r);
                    }
                    break;
                /*case CLEF:
                    //List<FacetCriterion> crits = ClefFacet.clefValues;
                    List<Alclef> clefs = HibernateUtil.getSession().createCriteria(Alclef.class)
                            .add(Restrictions.ilike("clef", "%" + search + "%"))
                            .addOrder(Property.forName("clef").asc()).list();
                    for (int i = 0; i < clefs.size(); i++) {
                        Alclef alc = clefs.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(alc.getAlclefkey(), alc.getClef());//+" ("+alcopyist.g +")");
                        results.add(r);
                    }
                    *//*for (int i = 0; i < crits.size(); i++) {
                        FacetCriterion fc = crits.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*//*
                    results = filterAutoResults(request, new ClefFacetType(), results);
                    break;
                case MENSURATION:
                    List<Almensuration> mens = (List<Almensuration>) HibernateUtil.getSession().createCriteria(Almensuration.class).add(Restrictions.ilike("mensurationtext", "%" + search + "%")).addOrder(Property.forName("mensurationtext").asc()).list();
                    for (int i = 0; i < mens.size(); i++) {
                        Almensuration m = mens.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(m.getAlmensurationkey(), m.getMensurationtext());//+" ("+alcopyist.g +")");
                        results.add(r);
                    }
                    break;*/
                case VOICE:
                    List<Alvoice> voices = (List<Alvoice>) HibernateUtil.getSession().createCriteria(Alvoice.class).add(Restrictions.ilike("voice", "%" + search + "%")).addOrder(Property.forName("voice").asc()).list();
                    for (int i = 0; i < voices.size(); i++) {
                        Alvoice v = voices.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(v.getAlvoicekey(), v.getVoice());//+" ("+alcopyist.g +")");
                        results.add(r);
                    }
                    break;
                case GENRE:
                    //crits = GenreFacet.genreValues;
                    List<Algenre> genres = HibernateUtil.getSession().createCriteria(Algenre.class)
                            .add(Restrictions.ilike("genre", "%" + search + "%"))
                            .addOrder(Property.forName("genre").asc()).list();
                    for (int i = 0; i < genres.size(); i++) {
                        Algenre algenre = genres.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(algenre.getAlgenrekey(), algenre.getGenre());
                        results.add(r);
                    }
                    /*for (int i = 0; i < crits.size(); i++) {
                        FacetCriterion fc = crits.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*/
                    break;
                case TITLE:
                    List<Composition> comps = (List<Composition>) HibernateUtil.getSession().createCriteria(Composition.class).add(Restrictions.ilike("compositionName", "%" + search + "%")).addOrder(Property.forName("compositionName").asc()).list();
                    for (int i = 0; i < comps.size(); i++) {
                        Composition c = comps.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(c.getCompositionkey(), c.getCompositionName());//+" ("+alcopyist.g +")");
                        results.add(r);
                    }
                    break;
                case COMPOSER:
                    List<Composer> composers = HibernateUtil.getSession().createCriteria(Composer.class)
                            .add(Restrictions.ilike("composercomplete", "%" + search + "%"))
                            .addOrder(Property.forName("lastname").asc()).list();
                    for (int i = 0; i < composers.size(); i++) {
                        Composer composer = composers.get(i);
                        AutoCompleteResult r = new AutoCompleteResult(composer.getComposerkey(), composer.getComposercomplete());
                        results.add(r);
                    }
                    /*crits = ComposerFacet.composerValues;
                    for (int i = 0; i < crits.size(); i++) {
                        FacetCriterion fc = crits.get(i);
                        if (fc.getLabel().toLowerCase().contains(search)) {
                            AutoCompleteResult r = new AutoCompleteResult(fc.getKey(), fc.getLabel());
                            results.add(r);
                        }
                    }*/
                    results = filterAutoResults(request, new ComposerFacetType(), results);
                    break;
                case PROVENANCE:
                    //List<FacetCriterion> provs = ProvenanceFacet.provenanceValues;
                    List<Alprovenance> provs = (List<Alprovenance>) HibernateUtil.getSession().createCriteria(Alprovenance.class).add(Restrictions.ilike("country", "%" + search + "%")).addOrder(Property.forName("country").asc()).list();
                    for (int i = 0; i < provs.size(); i++) {
                        Alprovenance prov = provs.get(i);
                        //if (fc.getLabel().toLowerCase().contains(search)) {
                        AutoCompleteResult r = new AutoCompleteResult(prov.getAlprovenancekey(), prov.getCountry());
                        results.add(r);
                        //}
                    }
                    results = filterAutoResults(request, new ProvenanceFacetType(), results);
                    break;
            }
        }
        return results;
    }

    public ArrayList<AutoCompleteResult> filterAutoResults(HttpServletRequest request, FacetType ftype, ArrayList<AutoCompleteResult> unfiltered) {
        DIAMMFacetManager facetM = (DIAMMFacetManager) request.getSession().getAttribute(Constants.facetManagerSessionName);
        ArrayList<AutoCompleteResult> filtered = new ArrayList<AutoCompleteResult>();
        if (facetM != null) {
            ArrayList<Integer> cKeys = facetM.getMasterKeyList(ftype);
            if (cKeys != null && cKeys.size() > 0) {
                for (int i = 0; i < unfiltered.size(); i++) {
                    AutoCompleteResult aR = unfiltered.get(i);
                    FacetCriterion fc = FacetTools.getCachedFacetCriteria(ftype, aR.getId());
                    if (fc != null) {
                        ArrayList<Integer> newKeys = FacetManager.mergeKeyLists(cKeys, fc.getKeys());
                        if (newKeys.size() > 0) {
                            filtered.add(aR);
                        }
                    }
                }
                return filtered;
            } /*else{
                return unfiltered;
            }*/
        }
        return unfiltered;
    }

    public void addRequestVariables(HttpServletRequest request) {
        Enumeration values = listValues.keys();
        while (values.hasMoreElements()) {
            String v = (String) values.nextElement();
            request.setAttribute(v, listValues.get(v) + "");

        }
    }
}
