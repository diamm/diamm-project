package uk.ac.kcl.cch.diamm.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import uk.ac.kcl.cch.diamm.dao.BibliographyDAO;
import uk.ac.kcl.cch.diamm.dao.BibliographyDTO;
import uk.ac.kcl.cch.diamm.model.Alauthor;
import uk.ac.kcl.cch.diamm.model.Authorbibliography;
import uk.ac.kcl.cch.diamm.model.Bibliography;
import uk.ac.kcl.cch.diamm.model.Source;
import uk.ac.kcl.cch.diamm.model.Authorbibliography.BibliographyPerson;

public class HibernateBibliographyDAO extends HibernateDAO <Bibliography, Integer> implements BibliographyDAO
{	
    public HibernateBibliographyDAO() 
    {
	    super(Bibliography.class);
    }
    
    public Bibliography findByPrimaryKey(Integer id)
    {
    	Bibliography bib = super.findByPrimaryKey(id);
    	
    	//pre-load the lazy lists
    	preload(bib);
    	
    	return bib;
    }
    
    public Integer getBibliographyCount(BibliographyDTO bibDTO)
    {    	
    	Integer bibliographyCount = 0;
    	
    	//Perform search only if at least one param supplied
    	if (!bibDTO.isEmpty())
		{
    		Criteria criteria = getSearchCriteria(bibDTO);
			criteria.setProjection(Projections.countDistinct("bibliographykey")); 
			bibliographyCount = (Integer)criteria.uniqueResult();
		}
    	return bibliographyCount;
    }
    
    @SuppressWarnings("unchecked")
	public List<Bibliography> getBibliographyList(BibliographyDTO bibDTO, Integer firstResult, Integer maxResults)
    {    	
    	List<Bibliography> bibliographyList = new ArrayList<Bibliography>();
		
    	//Perform search only if at least one param supplied
    	if (!bibDTO.isEmpty())
		{
    		//1. Retrieve the list of relevant bibliography keys
			Criteria criteria = getSearchCriteria(bibDTO);
			criteria.setProjection(Projections.distinct(Projections.id()));
			criteria.addOrder(Order.asc("bibliographykey"));
			criteria.setFirstResult(firstResult - 1);
			criteria.setMaxResults(maxResults);
			List<Integer> bibKeyList = new ArrayList<Integer>();
			bibKeyList = criteria.list();
			
			//2. Retrieve the bibliography objects based on the keys from step 1 
			if (!bibKeyList.isEmpty())
			{
				for (Integer id: bibKeyList)
				{
                    Bibliography bib=findByPrimaryKey(id);
                    if (bib.getArticletitle()!=null&&bib.getArticletitle().length()>0){
                        bib.setArticletitle(replaceMacChars(bib.getArticletitle()));
                    }
                    if (bib.getFulltextcalculated()!=null&&bib.getFulltextcalculated().length()>0){
                        bib.setFulltextcalculated(replaceMacChars(bib.getFulltextcalculated()));
                    }
					bibliographyList.add(bib);
				}	
			}
		}
    	return bibliographyList;
    }

    public static String replaceMacChars(String s){
        return s.replaceAll("‘","'").replaceAll("’","").replaceAll("\u2013","-");
    }
    
    private Criteria getSearchCriteria(BibliographyDTO bibDTO)
    {
    	Criteria criteria = null;
    	
    	//Set criteria only if at least one search param supplied
    	if (!bibDTO.isEmpty())
		{
    		criteria = HibernateUtil.getSession().createCriteria(Bibliography.class);

			//Add title criteria if supplied
            if (bibDTO.getTitle() != null)
        	{
            	Criterion articleTitle  = Restrictions.like("articletitle",bibDTO.getTitle(), MatchMode.ANYWHERE);
            	Criterion bookTitle     = Restrictions.like("booktitle",   bibDTO.getTitle(), MatchMode.ANYWHERE);
            	Criterion dissertation  = Restrictions.like("dissertation",bibDTO.getTitle(), MatchMode.ANYWHERE);
                Disjunction disjunction = Restrictions.disjunction();
                disjunction.add(articleTitle);
                disjunction.add(bookTitle);
                disjunction.add(dissertation);
                criteria.add(disjunction);
        	}
                  
            //Add year criteria if supplied
            if (bibDTO.getYear() != null)
        	{
            	criteria.add(Expression.like("year", bibDTO.getYear(), MatchMode.ANYWHERE));
        	}

            //Add publisher criteria if supplied
            if (bibDTO.getPublisher() != null)
        	{
            	criteria.add(Expression.like("publisher", bibDTO.getPublisher(), MatchMode.ANYWHERE));
        	}
			
            //Add author criteria if supplied
            if (bibDTO.getAuthor() != null)
        	{
            	criteria.createAlias("authorBibliographyList", "authorBibliography");
            	criteria.createAlias("authorBibliography.author", "author");
                //criteria.createAlias("authorBibliography.authorEditor", "authoreditor");

                Criterion lastName  = Restrictions.like("author.lastname",  bibDTO.getAuthor(), MatchMode.ANYWHERE);
            	Criterion firstName = Restrictions.like("author.firstname", bibDTO.getAuthor(), MatchMode.ANYWHERE);
                Disjunction nameDisjunction = Restrictions.disjunction();
                nameDisjunction.add(lastName);
                nameDisjunction.add(firstName);
                
                criteria.add(nameDisjunction);
                if (bibDTO.getAuthoreditor()!=null && !bibDTO.getAuthoreditor().equalsIgnoreCase("all")){
                    criteria.add(Restrictions.eq("authorBibliography.authorEditor",bibDTO.getAuthoreditor()));
                }
        	}
		}
    	return criteria;
    }
	
	private void preload(Bibliography bib)
	{
    	List<Alauthor> editorStrictList = new ArrayList<Alauthor>();
    	List<Alauthor> authorMixedList  = bib.getAuthorMixedList();
    	for (Alauthor authorMixed: authorMixedList)
    	{
    		List<Authorbibliography> authorBibList = authorMixed.getAuthorBibliographyList();
    		for(Authorbibliography authorBib: authorBibList)
    		{
    			if (authorBib.getAuthorEditor() != null && authorBib.getAuthorEditor().equals(BibliographyPerson.EDITOR.toString()))
    			{
    				editorStrictList.add(authorMixed);
    				break;
    			}
    		}
    	}  	
    	
    	List<Source> sourceOrigList = bib.getSourceList();
    	List<Source> sourceDestList = new ArrayList<Source>();
    	for (Source source: sourceOrigList)
    	{
    		sourceDestList.add(source);
    	}  	
	}
}