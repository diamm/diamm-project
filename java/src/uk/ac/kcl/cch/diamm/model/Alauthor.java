package uk.ac.kcl.cch.diamm.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 10:23:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alAuthor")
public class Alauthor {
    private int alauthorkey;

    @Id
    @Column(name = "alAuthorKey", nullable = false, length = 10)
    public int getAlauthorkey() {
        return alauthorkey;
    }

    public void setAlauthorkey(int alauthorkey) {
        this.alauthorkey = alauthorkey;
    }

    private String authorcomplete;

    @Basic
    @Column(name = "authorComplete", nullable = false, length = 2147483647)
    public String getAuthorcomplete() {
        return authorcomplete;
    }

    public void setAuthorcomplete(String authorcomplete) {
        this.authorcomplete = authorcomplete;
    }

    private String firstname;

    @Basic
    @Column(name = "firstName", length = 2147483647)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    private String lastname;

    @Basic
    @Column(name = "lastName", length = 2147483647)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    private List<Bibliography> bibliographyList = new ArrayList<Bibliography>();
    
    @ManyToMany
    @JoinTable(name = "AuthorBibliography",
    		   joinColumns = {@JoinColumn(name = "alAuthorKey")},
    		   inverseJoinColumns = {@JoinColumn(name = "bibliographyKey")})
    public List<Bibliography> getBibliographyList(){
    	return bibliographyList;
    }
    
    public void setBibliographyList(List<Bibliography> bibliographyList){
    	this.bibliographyList = bibliographyList;
    }
    
    private List<Authorbibliography> authorBibliographyList;
    
    @OneToMany(mappedBy = "author",
    		   targetEntity=Authorbibliography.class)
	public List<Authorbibliography> getAuthorBibliographyList() {
		return authorBibliographyList;
	}
 
	public void setAuthorBibliographyList(List<Authorbibliography> authorBibliographyList) {
		this.authorBibliographyList = authorBibliographyList;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alauthor alauthor = (Alauthor) o;

        if (alauthorkey != alauthor.alauthorkey) return false;
        if (authorcomplete != null ? !authorcomplete.equals(alauthor.authorcomplete) : alauthor.authorcomplete != null)
            return false;
        if (firstname != null ? !firstname.equals(alauthor.firstname) : alauthor.firstname != null) return false;
        if (lastname != null ? !lastname.equals(alauthor.lastname) : alauthor.lastname != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alauthorkey;
        result = 31 * result + (authorcomplete != null ? authorcomplete.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
}
