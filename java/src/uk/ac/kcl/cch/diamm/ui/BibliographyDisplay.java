package uk.ac.kcl.cch.diamm.ui;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 05/07/11
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class BibliographyDisplay {
    private ArrayList<Alauthor> authors;
    private ArrayList<Alauthor> editors;
    private ArrayList<BibliographySourceDisplay> sources;
    private ArrayList<ComposerDisplay> composers;
    private ArrayList<CompositionDisplay> compositions;
    private ArrayList<DisplayItem> items;
    private Bibliography bib;

    public BibliographyDisplay(Bibliography b) {
        //this.authorEditor=authorEditor;
        bib = b;

    }

    public void addVars() {
        addPeople();
        addComposers();
        addCompositions();
        addSources();
        addItems();
    }

    public void addItems() {
        items = new ArrayList<DisplayItem>();
        List<Bibliographyitem> bi = HibernateUtil.getSession().createCriteria(Bibliographyitem.class)
                .add(Restrictions.eq("bibliographykey", bib.getBibliographykey()))
                .createCriteria("itemByItemkey").addOrder(Order.asc("orderno")).list();
        for (int i = 0; i < bi.size(); i++) {
            Bibliographyitem bibliographyitem = bi.get(i);
            DisplayItem di=new DisplayItem(bibliographyitem.getItemByItemkey());
            di.addDetailData();
            items.add(di);

        }
    }

    public void addComposers() {
        List<Bibliographycomposer> bc = (List<Bibliographycomposer>) HibernateUtil.getSession().createCriteria(Bibliographycomposer.class).add(Restrictions.eq("bibliographykey", bib.getBibliographykey()))
                .createCriteria("composer").addOrder(Order.asc("lastname")).list();
        composers = new ArrayList<ComposerDisplay>();
        for (int i = 0; i < bc.size(); i++) {
            Bibliographycomposer bibliographycomposer = bc.get(i);
            composers.add(new ComposerDisplay(bibliographycomposer.getComposer()));
        }
    }

    public void addCompositions() {
        HashSet<Integer> compkeys=new HashSet<Integer>();
        compositions = new ArrayList<CompositionDisplay>();
        List<Bibliographycomposition> bc = HibernateUtil.getSession().createCriteria(Bibliographycomposition.class)
                .add(Restrictions.eq("bibliographykey", bib.getBibliographykey()))
                .createCriteria("composition").addOrder(Order.asc("compositionName"))
                .list();
        for (int i = 0; i < bc.size(); i++) {
            Bibliographycomposition bibliographycomposition = bc.get(i);
            if (!compkeys.contains(bibliographycomposition.getCompositionkey())){
                compositions.add(new CompositionDisplay(bibliographycomposition.getComposition()));
                compkeys.add(bibliographycomposition.getCompositionkey());
            }
        }

    }

    public void addSources() {
        sources = new ArrayList<BibliographySourceDisplay>();
        List<Bibliographysource> bs = HibernateUtil.getSession().createCriteria(Bibliographysource.class)
                .add(Restrictions.eq("bibliographykey", bib.getBibliographykey()))
                .createCriteria("source").addOrder(Order.asc("sortorder")).list();
        for (int i = 0; i < bs.size(); i++) {
            Bibliographysource bibliographysource = bs.get(i);
            sources.add(new BibliographySourceDisplay(bib, bibliographysource.getSource(), bibliographysource.getNotes(), bibliographysource.getPage()));
        }

    }

    public void addPeople() {
        authors = (ArrayList<Alauthor>) getAuthorStrictList();
        editors = (ArrayList<Alauthor>) getEditorStrictList();
    }

    public List<Alauthor> getAuthorStrictList() {
        List<Alauthor> authorStrictList = new ArrayList<Alauthor>();
        for (Authorbibliography ab : bib.getAuthorBibliographyList()) {
            if (ab.getAuthorEditor() != null && ab.getAuthorEditor().equals(Authorbibliography.BibliographyPerson.AUTHOR.toString())) {
                authorStrictList.add(ab.getAuthor());
            }
        }
        /*for (Alauthor authorMixed: authorMixedList){
              List<Authorbibliography> authorBibList = authorMixed.getAuthorBibliographyList();
              for(Authorbibliography authorBib: authorBibList){
                  if (authorBib.getAuthorEditor() != null && authorBib.getAuthorEditor().equals(BibliographyPerson.AUTHOR.toString())){
                      authorStrictList.add(authorMixed);
                      break;
                  }
              }
          }*/
        return authorStrictList;
    }

    //Convenience method to get list of strictly editors

    public List<Alauthor> getEditorStrictList() {
        List<Alauthor> editorStrictList = new ArrayList<Alauthor>();
        for (Authorbibliography ab : bib.getAuthorBibliographyList()) {
            if (ab.getAuthorEditor() != null && ab.getAuthorEditor().equals(Authorbibliography.BibliographyPerson.EDITOR.toString())) {
                editorStrictList.add(ab.getAuthor());
            }
        }
        /*for (Alauthor authorMixed: authorMixedList){
              List<Authorbibliography> authorBibList = authorMixed.getAuthorBibliographyList();
              for(Authorbibliography authorBib: authorBibList){
                  if (authorBib.getAuthorEditor() != null && authorBib.getAuthorEditor().equals(BibliographyPerson.EDITOR.toString())){
                      editorStrictList.add(authorMixed);
                      break;
                  }
              }
          }*/
        return editorStrictList;
    }

    public ArrayList<Alauthor> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Alauthor> authors) {
        this.authors = authors;
    }

    public ArrayList<Alauthor> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<Alauthor> editors) {
        this.editors = editors;
    }

    public ArrayList<BibliographySourceDisplay> getSources() {
        return sources;
    }

    public void setSources(ArrayList<BibliographySourceDisplay> sources) {
        this.sources = sources;
    }

    public ArrayList<ComposerDisplay> getComposers() {
        return composers;
    }

    public void setComposers(ArrayList<ComposerDisplay> composers) {
        this.composers = composers;
    }

    public ArrayList<CompositionDisplay> getCompositions() {
        return compositions;
    }

    public void setCompositions(ArrayList<CompositionDisplay> compositions) {
        this.compositions = compositions;
    }

    public Bibliography getBib() {
        return bib;
    }

    public void setBib(Bibliography bib) {
        this.bib = bib;
    }

    public ArrayList<DisplayItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DisplayItem> items) {
        this.items = items;
    }
}
