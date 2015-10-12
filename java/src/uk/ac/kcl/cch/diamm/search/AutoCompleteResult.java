package uk.ac.kcl.cch.diamm.search;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 14/02/11
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class AutoCompleteResult {
    private String label;
    private int id;

    public AutoCompleteResult(int key, String l){
        id=key;
        label=l;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
