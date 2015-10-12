package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Alauthor;
import uk.ac.kcl.cch.diamm.model.Authorbibliography;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.diamm.model.Composer;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom object to display data in bibliography intersection sets
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 15/03/11
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public class BibliographyIntersectionDisplay {
    private String page;
    private String notes;
    private String authorEditor;


    private Bibliography bib;



    public BibliographyIntersectionDisplay(int bKey,String notes,String page){
        if (bKey!=0){
            bib=(Bibliography) HibernateUtil.getSession().load(Bibliography.class, bKey);
            bib.getBiblabbrev();
            initVars(notes,page);
        }
    }
    public BibliographyIntersectionDisplay(Bibliography b,String notes,String page){
        bib=b;
        initVars(notes,page);
    }

    private void initVars(String notes,String page){
       if (notes!=null){
                this.notes=notes;
            }
            if (page!=null){
                this.page=page;
            }
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Bibliography getBib() {
        return bib;
    }

    public void setBib(Bibliography bib) {
        this.bib = bib;
    }



    public String getAuthorEditor() {
        return authorEditor;
    }

    public void setAuthorEditor(String authorEditor) {
        this.authorEditor = authorEditor;
    }


}
