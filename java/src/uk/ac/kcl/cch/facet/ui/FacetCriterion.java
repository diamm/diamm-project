package uk.ac.kcl.cch.facet.ui;

import uk.ac.kcl.cch.facet.FacetType;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * A container object for a single choice in a facet.
 *
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 06-Jul-2010
 * Time: 12:09:00
 * To change this template use File | Settings | File Templates.
 */
public class FacetCriterion {
    FacetType facetType;
    int count;
    int key;
    String label;
    ArrayList<Integer> keys;

    public FacetCriterion(){

    }

    public FacetCriterion(int count, FacetType facet, int key, String label) {
        this.count = count;
        this.facetType = facet;
        this.key = key;
        this.label = label;
    }

    public FacetCriterion(ArrayList<Integer> keys, FacetType facet, int key, String label) {
        this.facetType = facet;
        this.key = key;
        this.label = label;
        if (keys!=null){
            this.count = keys.size();
            this.keys=keys;
        }else{
            this.count=0;
        }
    }

    public ArrayList<Integer> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
        this.count=keys.size();
    }

    public FacetType getFacetType() {
        return facetType;
    }

    public void setFacetType(FacetType facetType) {
        this.facetType = facetType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //Label Comparator - implemented as a nested static class for closer association with the outer class
    public static class LabelComparator implements Comparator<FacetCriterion> {
    	public int compare(FacetCriterion fc1, FacetCriterion fc2){
    		//use compareTo method of String class
            if (fc1!=null&&fc2!=null&&fc1.getLabel()!=null&&fc2.getLabel()!=null){
    	        return fc1.getLabel().compareTo(fc2.getLabel());
            }
            return 0;
    	}
    }
}