package uk.ac.kcl.cch.diamm.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 08/08/11
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(catalog = "diamm_ess", name = "AlPersonRelationship")
public class AlPersonRelationship {


    private int alpersonrelationshipkey;

    @Id
    @Column(name = "alpersonrelationshipkey", nullable = false, length = 10)
    public int getAlpersonrelationshipkey() {
        return alpersonrelationshipkey;
    }

    public void setAlpersonrelationshipkey(int alpersonrelationshipkey) {
        this.alpersonrelationshipkey = alpersonrelationshipkey;
    }


    private String relationshipType;

    @Basic
    @Column(name = "relationshipType", nullable = false, length = 2147483647)
    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}
