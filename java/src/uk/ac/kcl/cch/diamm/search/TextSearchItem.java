package uk.ac.kcl.cch.diamm.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.TextFacet;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.facet.QueryMaker;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 01/03/11
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class TextSearchItem extends SearchItem {
    public SearchItemType getType(){
        return SearchItemType.TEXT;
    }

    @Override
    public void addSearchCriteria(QueryMaker q) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        BooleanQuery query = new BooleanQuery();
        try {
            Directory d = TextFacet.getDir();
            if (d==null){
                d = new TextFacet().initLuceneIndex();
            }
            IndexReader r = IndexReader.open(d, true);
            int num = r.numDocs();
            IndexSearcher searcher = new IndexSearcher(d, true);
            String[] textFields = TextFacet.textFields;
            for (int i = 0; i < textFields.length; i++) {
                String textField = textFields[i];
                Term t = new Term(textField, "*" + getTextValue() + "*");
                WildcardQuery wq = new WildcardQuery(t);
                query.add(wq, BooleanClause.Occur.SHOULD);
                String patternString = getTextValue();
            }
            /*QueryParser q = new QueryParser(Version.LUCENE_29, "textincipit", new StandardAnalyzer(Version.LUCENE_29));
            WildcardQuery w = new WildcardQuery(new Term("textincipit", patternString));
            Query incipitQuery = new TermQuery(new Term("textincipit", patternString));

              q.parse(queryString)
            */
            //query.add(fullTextQuery,BooleanClause.Occur.SHOULD);

            ScoreDoc[] rs = searcher.search(query, null, 10000).scoreDocs;
            for (ScoreDoc r1 : rs) {
                Document hit = searcher.doc(r1.doc);
                keys.add(Integer.parseInt(hit.get("id")));
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //Convert item keys from text facet to source keys for search
        if (keys.size() > 0) {
            /*String hql = "SELECT DISTINCT item.itemkey from Item as item where item.itemkey in ("+ DIAMMFacetManager.serializeKeys(keys)+") ORDER BY item.sourcekey";
            org.hibernate.Query hquery = HibernateUtil.getSession().createQuery(hql);
            ArrayList<Integer> skeys = (ArrayList<Integer>) hquery.list();*/
            //if (skeys != null && skeys.size() > 0) {
                SearchManager.addItemKeyConstraint(keys, q, getOperand());
            //}
        }
    }

    @Override
    public void setFromRequest(HttpServletRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<AutoCompleteResult> getAutoCompleteResults(HttpServletRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addRequestVariables(HttpServletRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
