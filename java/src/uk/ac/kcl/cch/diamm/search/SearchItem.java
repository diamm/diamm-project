package uk.ac.kcl.cch.diamm.search;

import uk.ac.kcl.cch.facet.QueryMaker;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 14/02/11
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public abstract class SearchItem implements Comparator<SearchItem>{
    private int operand;
    private String textValue;
     private String label;
    private int value;
    private String page;
    private int order;
    private SearchItemType type;
    private int id;
    private ArrayList<Integer> sourceKeys;

    public abstract void addSearchCriteria (QueryMaker q);
    public abstract void setFromRequest (HttpServletRequest request);
    public abstract ArrayList<AutoCompleteResult> getAutoCompleteResults (HttpServletRequest request);
    public abstract void addRequestVariables (HttpServletRequest request);

    public SearchItem(){
        id=this.hashCode();
        order=0;
    }

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int compare(SearchItem o1, SearchItem o2) {
        if (o1.getOrder()>o2.getOrder()){
            return 1;
        }   else if (o2.getOrder()>o1.getOrder()){
            return -1;
        }
        return 0;
    }

    public SearchItemType getType() {
        return type;
    }

    public void setType(SearchItemType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Integer> getSourceKeys() {
        return sourceKeys;
    }

    public void setSourceKeys(ArrayList<Integer> sourceKeys) {
        this.sourceKeys = sourceKeys;
    }
}
