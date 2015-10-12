package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;
import uk.ac.kcl.cch.diamm.model.Alcity;
import uk.ac.kcl.cch.diamm.model.Alcountry;
import uk.ac.kcl.cch.diamm.ui.Constants;
import uk.ac.kcl.cch.diamm.ui.SourceGUIFacet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 28-Jul-2010
 * Time: 10:46:44
 * To change this template use File | Settings | File Templates.
 */
public class AlCountryItem implements uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem {

    private Alcountry alcountry;
    private String cssclass = "s7";
    private int count;
    private IXHtmlHierarchyItem[] kids;

    private ArrayList<Integer> constraintKeys;

    public ArrayList<Integer> getConstraintKeys() {
        return constraintKeys;
    }

    public void setConstraintKeys(ArrayList<Integer> constraintKeys) {
        this.constraintKeys = constraintKeys;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AlCountryItem(Alcountry alcountry) {
        this.alcountry = alcountry;
    }

    public AlCountryItem(Alcountry alcountry, int count) {
        this.alcountry = alcountry;
        this.count = count;
    }

    public Vector<IXHtmlHierarchyItem> getAncestors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem[] getChildren() {
        if (kids != null) {
            return kids;
        }
        List<Alcity> cities = (List<Alcity>) alcountry.getAlcitiesByAlcountrykey();
        ArrayList<AlCityItem> items = new ArrayList<AlCityItem>();
        for (int i = 0; i < cities.size(); i++) {
            Alcity alcity = cities.get(i);
            //
            int count = 0;
            ArrayList<Integer> cKeys = SourceFacet.getInitialvalues(alcity.getAlcitykey(), SourceFacet.cityValues);
            if (cKeys != null && cKeys.size() > 0) {
                if (constraintKeys == null || constraintKeys.size() == 0) {
                    count = cKeys.size(); //SourceGUIFacet.getCityCount(alcity.getAlcitykey());
                } else {
                    count = DIAMMFacetManager.mergeKeyLists(cKeys, this.constraintKeys).size();
                }
                AlCityItem item = new AlCityItem(alcity, count);
                if (constraintKeys != null && constraintKeys.size() > 0) {
                    item.setConstraintKeys(constraintKeys);
                }
                if (count > 0) {
                    items.add(item);
                }
            }
        }
        kids = items.toArray(new IXHtmlHierarchyItem[0]);
        return kids;
    }

    //http://localhost:8080/diamess/jsp/FacetManager?opened=&incld=Alcountry_17#Alcountry_17
    public String getChildrenContainerClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector<IXHtmlHierarchyItem> getDescendants() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFullID() {
        return "Alcountry_" + getIDNumb();
    }

    public int getIDNumb() {
        return alcountry.getAlcountrykey();
    }

    public String getItemClass() {
        return cssclass;
    }

    public String getLabel() {
        /*
        <a href="#" class="t9 m0 s6" title="Expand this item">Expand</a>
        <label><a href="#">Selectable Facet Value 1</a></label>
       <dfn title="123 items">123</dfn> <a href="#" class="t9 m1">View Record</a> <a href="#" class="t9 m2">Add to a Collection</a>
         */
        return "<label><a href=\"FacetManager?op=" + (Constants.sync ? "1" : "2") + "&FacetType=SOURCEFACET&alCountryKey=" +
                alcountry.getAlcountrykey() + "\" title=\"Set as criteria\"\n" +
                "  >" + alcountry.getCountry() + "</a></label> <dfn title=\""+getCount()+" items\">" + getCount() +
                "</dfn>";


    }

    public int getNumbChildren() {
        if (kids == null) {
            getChildren();
        }
        return kids.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem getParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasChildren() {
        if (kids == null) {
            getChildren();
        }
        return kids.length > 0;//To change body of implemented methods use File | Settings | File Templates.
    }

    public void setItemClass(String className) {
        cssclass = className;
    }
}
