package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Sep-2010
 * Time: 12:00:22
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class Composer {
    private int composerkey;

    @Id
    @Column(name = "composerKey", nullable = false, length = 10)
    public int getComposerkey() {
        return composerkey;
    }

    public void setComposerkey(int composerkey) {
        this.composerkey = composerkey;
    }

    private String composercomplete;

    @Basic
    @Column(name = "composerComplete", length = 2147483647)
    public String getComposercomplete() {
        return composercomplete;
    }

    public void setComposercomplete(String composercomplete) {
        this.composercomplete = composercomplete;
    }

    private String lastname;

    @Basic
    @Column(name = "lastName", length = 200)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    private String firstname;

    @Basic
    @Column(name = "firstName", length = 200)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }



    private String dateEarliest;

        @Basic
        @Column(name = "date_earliest", length = 2147483647)
        public String getDateEarliest() {
            return dateEarliest;
        }

        public void setDateEarliest(String dateEarliest) {
            this.dateEarliest = dateEarliest;
        }

        private String dateLatest;

        @Basic
        @Column(name = "date_latest", length = 2147483647)
        public String getDateLatest() {
            return dateLatest;
        }

        public void setDateLatest(String dateLatest) {
            this.dateLatest = dateLatest;
        }




    private String dateFloruitEarliest;

    @Basic
    @Column(name = "date_floruit_earliest", length = 2147483647)
    public String getDateFloruitEarliest() {
        return dateFloruitEarliest;
    }

    public void setDateFloruitEarliest(String dateFloruitEarliest) {
        this.dateFloruitEarliest = dateFloruitEarliest;
    }

    private String dateFloruitLatest;

    @Basic
    @Column(name = "date_floruit_latest", length = 2147483647)
    public String getDateFloruitLatest() {
        return dateFloruitLatest;
    }

    public void setDateFloruitLatest(String dateFloruitLatest) {
        this.dateFloruitLatest = dateFloruitLatest;
    }

    private String datesPublic;

    @Basic
    @Column(name = "dates_public", length = 2147483647)
    public String getDatesPublic() {
        return datesPublic;
    }

    public void setDatesPublic(String datesPublic) {
        this.datesPublic = datesPublic;
    }

    private String info;

    @Basic
    @Column(name = "info", length = 2147483647)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String infoSource;

    @Basic
    @Column(name = "info_source", length = 2147483647)
    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    private String variantspellings;

    @Basic
    @Column(name = "variantspellings", length = 2147483647)
    public String getVariantspellings() {
        return variantspellings;
    }

    public void setVariantspellings(String variantspellings) {
        this.variantspellings = variantspellings;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composer composer = (Composer) o;

        if (composerkey != composer.composerkey) return false;
        if (dateEarliest != composer.dateEarliest) return false;
        if (dateLatest != composer.dateLatest) return false;
        if (composercomplete != null ? !composercomplete.equals(composer.composercomplete) : composer.composercomplete != null)
            return false;
        if (dateFloruitEarliest != null ? !dateFloruitEarliest.equals(composer.dateFloruitEarliest) : composer.dateFloruitEarliest != null)
            return false;
        if (dateFloruitLatest != null ? !dateFloruitLatest.equals(composer.dateFloruitLatest) : composer.dateFloruitLatest != null)
            return false;
        if (datesPublic != null ? !datesPublic.equals(composer.datesPublic) : composer.datesPublic != null)
            return false;
        if (firstname != null ? !firstname.equals(composer.firstname) : composer.firstname != null) return false;
        if (info != null ? !info.equals(composer.info) : composer.info != null) return false;
        if (infoSource != null ? !infoSource.equals(composer.infoSource) : composer.infoSource != null) return false;
        if (lastname != null ? !lastname.equals(composer.lastname) : composer.lastname != null) return false;
        if (variantspellings != null ? !variantspellings.equals(composer.variantspellings) : composer.variantspellings != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = composerkey;
        result = 31 * result + (composercomplete != null ? composercomplete.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        
        result = 31 * result + (dateFloruitEarliest != null ? dateFloruitEarliest.hashCode() : 0);
        result = 31 * result + (dateFloruitLatest != null ? dateFloruitLatest.hashCode() : 0);
        result = 31 * result + (datesPublic != null ? datesPublic.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (infoSource != null ? infoSource.hashCode() : 0);
        result = 31 * result + (variantspellings != null ? variantspellings.hashCode() : 0);
        return result;
    }

     private Collection<Compositioncomposer> compositioncomposersByComposerkey;

    @OneToMany(mappedBy = "composerByComposerkey")
    public Collection<Compositioncomposer> getCompositioncomposersByComposerkey() {
        return compositioncomposersByComposerkey;
    }

    public void setCompositioncomposersByComposerkey(Collection<Compositioncomposer> compositioncomposersByComposerkey) {
        this.compositioncomposersByComposerkey = compositioncomposersByComposerkey;
    }

    private Collection<Composition> compositions;

    @ManyToMany
    @JoinTable(catalog = "diamm_ess", name = "CompositionComposer", joinColumns = @JoinColumn(name = "composerKey", referencedColumnName = "composerKey", nullable = false), inverseJoinColumns = @JoinColumn(name = "compositionKey", referencedColumnName = "compositionKey", nullable = false))
    public Collection<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(Collection<Composition> compositions) {
        this.compositions = compositions;
    }

    public String buildComposerLabel(){
        return this.getLastname()+(this.getFirstname()!=null&&this.getFirstname().length()>0?", "+this.getFirstname():"");
    }
}
