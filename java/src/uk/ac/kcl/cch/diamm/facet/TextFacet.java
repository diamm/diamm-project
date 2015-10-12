package uk.ac.kcl.cch.diamm.facet;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import uk.ac.kcl.cch.diamm.facet.type.TextFacetType;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.Text;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetManager;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.TextSearchPattern;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 09-Nov-2010
 * Time: 11:28:10
 * To change this template use File | Settings | File Templates.
 */
public class TextFacet extends Facet {
    /*protected static final String startswithPattern="^?";
    protected static final String containsPattern="?";
    protected static final String wholeWordPattern="[[:<:]]?[[:>:]]";*/
    protected TextSearchPattern pattern;
    protected static Directory dir;
    public static final String[] textFields = {"textincipit", "fulltermtext", "fulltermtextauthority", "standardspellingfulltext"};
    // protected static String LUCENE_PATH = "/usr/local/lucene";

    protected static String LUCENE_PATH = "/vol/diamm/webroot/dev/tomcat/webapps/diamm";
    protected static String INDEX_PATH = "texts";

    public TextFacet() {

    }

    public TextFacet(FacetManager manager) {
        super(new TextFacetType(), manager);
        setPattern(TextSearchPattern.startswithPattern);
        if (getDir() == null) {
            dir = initLuceneIndex();
            /*if (dir==null){
                dir = buildLuceneIndex();
            }*/
        }
    }

    public Directory initLuceneIndex() {

        try {
            Directory index = new SimpleFSDirectory(new File(LUCENE_PATH + "/" + INDEX_PATH));
            return index;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public FacetCriterion getCriterionByKey(int key, int level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public ArrayList<Integer> getResultSetKeys() {
        //ArrayList<Integer> itemKeys = doTextSearch();
        if (this.getState() == FacetState.UPDATED) {
            itemKeys = doTextSearch();
            setItemKeys(itemKeys);
            setState(FacetState.SELECTED);
        }
        return getItemKeys();
    }

    protected List<Text> getTexts(int offset, int limit) {
        System.out.println("offset:" + offset);
        Criteria tSearch = HibernateUtil.getSession().createCriteria(Text.class).addOrder(Order.asc("textkey")).setMaxResults(limit).setFirstResult(offset);
        List<Text> textchunk = tSearch.list();
        return textchunk;
    }

    public void addTexts(List<Text> texts, IndexWriter w) throws IOException {
        for (int i = 0; i < texts.size(); i++) {
            Text text = texts.get(i);
            Document doc = new Document();
            doc.add(new Field("id", text.getItemkey() + "", Field.Store.YES, Field.Index.NOT_ANALYZED));
            boolean add = false;
            if (text.getStandardspellingfulltext() != null && text.getStandardspellingfulltext().length() > 0) {
                doc.add(new Field("standardspellingfulltext", text.getStandardspellingfulltext(), Field.Store.YES, Field.Index.ANALYZED));
                add = true;
            }
            if (text.getTextincipit() != null && text.getTextincipit().length() > 0) {
                doc.add(new Field("textincipit", text.getTextincipit(), Field.Store.YES, Field.Index.ANALYZED));
                add = true;
            }
            if (text.getFulltermtext() != null && text.getFulltermtext().length() > 0) {
                doc.add(new Field("fulltermtext", text.getFulltermtext(), Field.Store.YES, Field.Index.ANALYZED));
                add = true;
            }
            if (text.getFulltermtextAuthority() != null && text.getFulltermtextAuthority().length() > 0) {
                doc.add(new Field("fulltermtextauthroity", text.getFulltermtextAuthority(), Field.Store.YES, Field.Index.ANALYZED));
                add = true;
            }
            if (text.getStandardspellingincipit() != null && text.getStandardspellingincipit().length() > 0) {
                doc.add(new Field("standardspellingincipit", text.getStandardspellingincipit(), Field.Store.YES, Field.Index.ANALYZED));
                add = true;
            }
            if (add) {
                w.addDocument(doc);
            }
        }
    }

    public Directory buildLuceneIndex() {
        IndexWriter w = null;
        Directory index = null;
        try {
            index = new SimpleFSDirectory(new File(LUCENE_PATH + "/" + INDEX_PATH));
            w = new IndexWriter(index, new StandardAnalyzer(Version.LUCENE_29), true,
                    IndexWriter.MaxFieldLength.UNLIMITED);
            int offset = 0;
            int limit = 5000;
            int total = limit;
            while (limit == total) {
                List<Text> texts = getTexts(offset, limit);
                total = texts.size();
                addTexts(texts, w);
                offset += limit;
            }

            /*String fullHql = "SELECT DISTINCT text.itemkey,text.textincipit, text.fulltermtext,text.fulltermtextAuthority,text.standardspellingfulltext from Text as text";
            fullHql += " order by text.textkey";
            org.hibernate.Query query = HibernateUtil.getSession().createQuery(fullHql);
            List<Object[]> results = (List<Object[]>) query.list();*/
            w.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return index;
    }

    public ArrayList<Integer> getItemKeys(TopDocs results) {
        ArrayList<Integer> keys = new ArrayList<Integer>();

        return keys;
    }

    /*
   if (constraintString != null && constraintString.length() > 0) {
        String constraint = "";
        switch (pattern) {
            case startswithPattern:
                constraint = constraintString + "*";
                break;
            case containsPattern:
                constraint = "*" + constraintString + "*";
                break;
            case wholeWordPattern:
                constraint = constraintString;
        }
        queryString="textincipit:"+constraint+""+
                   " fulltermtext:"+constraint+""+
                " fulltermtextauthority:"+constraint+""+
                " standardspellingfulltext:"+constraint+"";
    }
    */
    public BooleanQuery buildQuery() {
        BooleanQuery query = new BooleanQuery();
        if (constraintString != null && constraintString.length() > 0) {
            for (int i = 0; i < textFields.length; i++) {
                String textField = textFields[i];
                Term t = new Term(textField, constraintString);
                switch (pattern) {
                    case startswithPattern:
                        PrefixQuery pq = new PrefixQuery(t);
                        query.add(pq, BooleanClause.Occur.SHOULD);
                        break;
                    case containsPattern:
                        /*t = new Term(textField, "" + constraintString + "");
                        //WildcardQuery wq = new WildcardQuery(t);
                        PhraseQuery wq=new PhraseQuery();
                        wq.add(t);
                        query.add(wq, BooleanClause.Occur.SHOULD);
                        */
                        if (constraintString.indexOf(" ") > -1&&constraintString.indexOf("\"") == -1) {
                            //TermQuery phq = new TermQuery();
                            String[] terms = constraintString.split(" ");
                            for (int j = 0; j < terms.length; j++) {
                                String token = terms[j];
                                //phq.add(new Term(textField,token));
                                query.add(new TermQuery(new Term(textField, token)), BooleanClause.Occur.SHOULD);
                            }
                            //query.add(phq, BooleanClause.Occur.SHOULD);
                        } else if (constraintString.indexOf("\"") >= -1 ){
                            String p=constraintString.replaceAll("\"","");
                            PhraseQuery phq=new PhraseQuery();
                            phq.add(new Term(textField, p));
                            query.add(phq, BooleanClause.Occur.SHOULD);
                        }else{
                            TermQuery tq = new TermQuery(t);
                            query.add(tq, BooleanClause.Occur.SHOULD);
                        }
                        break;
                    case wholeWordPattern:
                        if (constraintString.indexOf(" ") > -1) {
                            PhraseQuery phq = new PhraseQuery();
                            String[] terms = constraintString.split(" ");
                            for (int j = 0; j < terms.length; j++) {
                                String token = terms[j];
                                phq.add(new Term(token));
                            }
                            query.add(phq, BooleanClause.Occur.SHOULD);
                        } else {
                            TermQuery tq = new TermQuery(t);
                            query.add(tq, BooleanClause.Occur.SHOULD);
                        }
                        break;
                }
            }
        }
        return query;
    }

    protected ArrayList<Integer> doTextSearch() {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        if (constraintString != null && constraintString.length() > 0) {

            try {
                Directory d = getDir();
                if (d == null) {
                    d = initLuceneIndex();
                }
                IndexReader r = null;
                try {
                    r = IndexReader.open(d, true);
                    int num = r.numDocs();
                    if (num == 0) {
                        d = buildLuceneIndex();
                    }
                } catch (IOException e) {
                    //Empty reindex
                    d = buildLuceneIndex();
                }
                IndexSearcher searcher = new IndexSearcher(d, true);
                //todo: modify pattern based on type
                String patternString = constraintString;
                //Query fullTextQuery = new TermQuery(new Term("standardspellingfulltext", patternString));
                BooleanQuery bq = buildQuery();
                BooleanQuery bq2 = buildQuery();
                Query fullTextQuery = new TermQuery(new Term("standardspellingfulltext","de bien amer"));
                ScoreDoc[] test = searcher.search(new TermQuery(new Term("standardspellingfulltext","de bien amer")), null, 10000).scoreDocs;
                ScoreDoc[] rs = searcher.search(bq, null, 10000).scoreDocs;
                for (ScoreDoc r1 : rs) {
                    Document hit = searcher.doc(r1.doc);
                    keys.add(Integer.parseInt(hit.get("id")));
                }
                searcher.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return keys;
    }


    protected void updateConstraints(int value, String constraintString, int level) {
        if (level != this.level) {
            setLevel(level);
            setUpdated();
        }
        if (value != getValue()) {
            setValue(value);
            setUpdated();
        }
        if (constraintString != null && !constraintString.equalsIgnoreCase(getConstraintString())) {
            setConstraintString(constraintString);
            setUpdated();
        }
    }

    public void resetFacet() {
        setLevel(0);
        setValue(0);
        setItemKeys(null);
        setConstraintString("");
    }

    public String[] valueParameters = {"textString", "textSearchPattern"};

    public String[] getValueParameters() {
        return valueParameters;
    }

    public void setValueParameters(String[] valueParameters) {
        this.valueParameters = valueParameters;
    }


    protected int level;
    protected String constraintString;
    protected int value;
    protected ArrayList<Integer> itemKeys;

    public ArrayList<Integer> getItemKeys() {
        /*if (getState() != FacetState.EMPTY && itemKeys == null) {

        }*/
        return itemKeys;
    }

    public void setItemKeys(ArrayList<Integer> itemKeys) {
        this.itemKeys = itemKeys;
    }


    public String getConstraintString() {
        return constraintString;
    }

    public void setConstraintString(String constraintString) {
        this.constraintString = constraintString;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getValue() {
        if (getConstraintString() != null && getConstraintString().length() > 0) {
            return 1;
        } else {
            return 0;
        }
        //return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TextSearchPattern getPattern() {
        return pattern;
    }

    public void setPattern(TextSearchPattern pattern) {
        this.pattern = pattern;
    }

    public static Directory getDir() {
        return dir;
    }

    public static void setDir(Directory dir) {
        TextFacet.dir = dir;
    }

    public boolean updateConstraintsFromRequest(HttpServletRequest request) {
        boolean changed = false;
        String[] params = getValueParameters();
        for (int i = 0; i < params.length; i++) {
            String valueParameter = params[i];
            if (request.getParameter(valueParameter) != null && request.getParameter(valueParameter).length() > 0) {
                String param = request.getParameter(valueParameter);
                if (param != null && valueParameter.equalsIgnoreCase("textSearchPattern")) {
                    TextSearchPattern tp = TextSearchPattern.valueOf(param);
                    if (tp != null) {
                        setPattern(tp);
                    }
                } else if (param != null && param.length() > 0) {
                    updateConstraints(0, param, (i + 1));
                    //Set to Empty
                    //setState(FacetState.EMPTY);
                }
                changed = true;
            }
        }
        return changed;
    }
}
