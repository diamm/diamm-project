package uk.ac.kcl.cch.diamm.dao;

import uk.ac.kcl.cch.util.StringUtil;

public class BibliographyDTO 
{
    private String title;
    private String author;
    private String year;
    private String publisher;
    private String authoreditor;

	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = StringUtil.squeeze(title);
	}
	
	public String getAuthor() 
	{
		return author;
	}
	
	public void setAuthor(String author) 
	{
		this.author = StringUtil.squeeze(author);
	}
	
	public String getYear() 
	{
		return year;
	}
	
	public void setYear(String year)
	{
		this.year = StringUtil.squeeze(year);
	}
	
	public String getPublisher() 
	{
		return publisher;
	}
	
	public void setPublisher(String publisher) 
	{
		this.publisher = StringUtil.squeeze(publisher);
	}
	
	public Boolean isEmpty()
	{
		return ((authoreditor     == null || authoreditor.length()     == 0) &&
                (title     == null || title.length()     == 0) &&
				(author    == null || author.length()    == 0) && 
				(year      == null || year.length()      == 0) &&
				(publisher == null || publisher.length() == 0));
	}

    public String getAuthoreditor() {
        return authoreditor;
    }

    public void setAuthoreditor(String authoreditor) {
        this.authoreditor = authoreditor;
    }
}
