package uk.ac.kcl.cch.diamm.search;

import org.hibernate.Query;
import uk.ac.kcl.cch.facet.QueryMaker;
import uk.ac.kcl.cch.diamm.model.Alauthor;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Property;

/**
 * Created by IntelliJ IDEA.
 * User: elliotthall
 * Date: Feb 18, 2011
 * Time: 3:30:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BibliographySearchItem extends SearchItem {

    public SearchItemType getType(){
        return SearchItemType.AUTHOR;
    }

    public void addSearchCriteria(QueryMaker q) {
        String hql = " join item.sourceBySourcekey as source ";
        hql+=" join source.bibliographysources as bibsource";
        hql+=" join bibsource.bibliography as bib";
        hql+=" join bib.authorMixedList as author";
        hql+=" WHERE author.authorcomplete=:aString";
        hql="SELECT DISTINCT item.itemkey from Item as item " + hql+" ORDER BY item.itemkey";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setString("aString",getTextValue());
        ArrayList<Integer> keys = (ArrayList<Integer>) query.list();
        if (keys != null && keys.size() > 0) {
            SearchManager.addItemKeyConstraint(keys,q,getOperand());
        }
    }

    public void setFromRequest(HttpServletRequest request) {
        String p = request.getParameter("authorkey");
        if (p != null) {
            setValue(Integer.parseInt(p));
        }
    }

    public ArrayList<AutoCompleteResult> getAutoCompleteResults(HttpServletRequest request) {
        String search = request.getParameter("search");
        ArrayList<AutoCompleteResult> results=new ArrayList<AutoCompleteResult>();
        List<Alauthor> copies = (List<Alauthor>) HibernateUtil.getSession().createCriteria(Alauthor.class).add(Restrictions.ilike("authorcomplete", "%" + search + "%")).addOrder(Property.forName("authorcomplete").asc()).list();
        for (int i = 0; i < copies.size(); i++) {
            Alauthor ala= copies.get(i);
            AutoCompleteResult r = new AutoCompleteResult(ala.getAlauthorkey(), ala.getAuthorcomplete());//+" ("+alcopyist.g +")");
            results.add(r);
        }
        return results;
    }

    public void addRequestVariables(HttpServletRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
