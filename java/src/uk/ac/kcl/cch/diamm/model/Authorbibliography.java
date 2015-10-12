package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 10:23:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "AuthorBibliography")
public class Authorbibliography {
	
	public static enum BibliographyPerson {
		AUTHOR {
			public String toString() {    
				return "author";    
			}
		},
		EDITOR {   
			public String toString() {
				return "editor";    
			}
		}
	}
	
    private int authorbibliography;

    @Id
    @Column(name = "authorBibliography", nullable = false, length = 10)
    public int getAuthorbibliography() {
        return authorbibliography;
    }

    public void setAuthorbibliography(int authorbibliography) {
        this.authorbibliography = authorbibliography;
    }
    
    private Alauthor author;
    
    @ManyToOne
	@JoinColumn(name = "alAuthorKey", nullable = false)
	public Alauthor getAuthor() {
		return author;
	}
    
    public void setAuthor(Alauthor author) {
      this.author = author;
    }

    private Bibliography bibliography;
    
    @ManyToOne
	@JoinColumn(name = "bibliographyKey", nullable = false)
	public Bibliography getBibliography() {
		return bibliography;
	}
    
    public void setBibliography(Bibliography bibliography) {
      this.bibliography = bibliography;
    }

    private String authorEditor;
    @Basic
    @Column(name = "author_editor",  length = 2147483647)
    public String getAuthorEditor() {
        return authorEditor;
    }

    public void setAuthorEditor(String authorEditor) {
        this.authorEditor = authorEditor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authorbibliography that = (Authorbibliography) o;

        if (authorEditor != that.authorEditor) return false;
        if (authorbibliography != that.authorbibliography) return false;
        if (author.getAlauthorkey() != that.author.getAlauthorkey()) return false;
        if (bibliography.getBibliographykey() != that.bibliography.getBibliographykey()) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = authorbibliography;
        result = 31 * result + author.getAlauthorkey();
        result = 31 * result + bibliography.getBibliographykey();
        
        return result;
    }
}
