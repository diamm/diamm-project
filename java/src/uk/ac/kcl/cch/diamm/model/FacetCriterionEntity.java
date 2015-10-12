package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 26/05/11
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess")
public class FacetCriterionEntity {
    private int id;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, length = 10)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int ckey;

    @Basic
    @Column(name = "ckey", nullable = false, length = 10)
    public int getCkey() {
        return ckey;
    }

    public void setCkey(int ckey) {
        this.ckey = ckey;
    }




    private String label;

    @Basic
    @Column(name = "label", length = 2147483647)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private int facettype;

    @Basic
    @Column(name = "facetType", nullable = false, length = 10)
    public int getFacettype() {
        return facettype;
    }

    public void setFacettype(int facettype) {
        this.facettype = facettype;
    }






    private int count;

    @Column(name = "count")
    @Basic
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String keyString;

    @Basic
    @Column(name = "keyString", length = 2147483647)
    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    private int orderno;

    @Column(name = "orderNo")
    @Basic
    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    /*


        private String keys;

        @javax.persistence.Column(name = "keys", length = 2147483647)
        @Basic
        public String getKeys() {
            return keys;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }


        }
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacetCriterionEntity that = (FacetCriterionEntity) o;

        //if (count != that.count) return false;
        if (id != that.id) return false;
        /*if (key != that.key) return false;
if (orderno != that.orderno) return false;
if (facettype != null ? !facettype.equals(that.facettype) : that.facettype != null) return false;
if (keys != null ? !keys.equals(that.keys) : that.keys != null) return false;
if (label != null ? !label.equals(that.label) : that.label != null) return false;*/

        return true;
    }

    @Override
    public int hashCode
            () {
        int result = id;
        /*result = 31 * result + (facettype != null ? facettype.hashCode() : 0);
  result = 31 * result + count;
  result = 31 * result + key;
  result = 31 * result + (label != null ? label.hashCode() : 0);
  result = 31 * result + (keys != null ? keys.hashCode() : 0);
  result = 31 * result + orderno;*/
        return result;
    }
}
