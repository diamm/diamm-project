package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 27-Apr-2010
 * Time: 17:13:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "alPerson")
public class Alperson {
    private int alpersonkey;

    @Id
    @Column(name = "alPersonKey", nullable = false, length = 10)
    public int getAlpersonkey() {
        return alpersonkey;
    }

    public void setAlpersonkey(int alpersonkey) {
        this.alpersonkey = alpersonkey;
    }

    private String fullname;

    @Basic
    @Column(name = "fullName", length = 2147483647)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    private String surname;

    @Basic
    @Column(name = "surName", length = 2147483647)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String aliases;

    @Basic
    @Column(name = "aliases", length = 2147483647)
    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }



    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alperson alperson = (Alperson) o;

        if (alpersonkey != alperson.alpersonkey) return false;


        if (aliases != null ? !aliases.equals(alperson.aliases) : alperson.aliases != null) return false;
        if (firstname != null ? !firstname.equals(alperson.firstname) : alperson.firstname != null) return false;
        if (fullname != null ? !fullname.equals(alperson.fullname) : alperson.fullname != null) return false;
        if (surname != null ? !surname.equals(alperson.surname) : alperson.surname != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = alpersonkey;
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (aliases != null ? aliases.hashCode() : 0);
        
        return result;
    }

    private Collection<Source> sources;
    @ManyToMany(mappedBy = "persons",targetEntity = Source.class)
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }

    /*private Collection<Sourcealperson> sourcealpersonsByAlpersonkey;

    @OneToMany(mappedBy = "alpersonByAlpersonkey")
    public Collection<Sourcealperson> getSourcealpersonsByAlpersonkey() {
        return sourcealpersonsByAlpersonkey;
    }

    public void setSourcealpersonsByAlpersonkey(Collection<Sourcealperson> sourcealpersonsByAlpersonkey) {
        this.sourcealpersonsByAlpersonkey = sourcealpersonsByAlpersonkey;
    }*/
    private int alaffiliationkey;

    @Basic
    @Column(name = "alaffiliationKey", nullable = false, length = 10,insertable = false,updatable = false)
    public int getAlaffiliationkey() {
        return alaffiliationkey;
    }

    public void setAlaffiliationkey(int alaffiliationkey) {
        this.alaffiliationkey = alaffiliationkey;
    }


    private Alaffiliation alaffiliation;

    @OneToOne
    @JoinColumn(name = "alaffiliationKey", referencedColumnName = "alaffiliationKey", nullable = false)
    public Alaffiliation getAlaffiliation() {
        return alaffiliation;
    }

    public void setAlaffiliation(Alaffiliation alaffiliation) {
        this.alaffiliation = alaffiliation;
    }
}
