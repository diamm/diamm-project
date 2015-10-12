package uk.ac.kcl.cch.diamm.tree;

import uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem;
import uk.ac.kcl.cch.diamm.model.Alcountry;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.ui.SourceGUIFacet;
import uk.ac.kcl.cch.diamm.facet.SourceFacet;
import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.facet.FacetType;
import uk.ac.kcl.cch.diamm.servlet.DIAMMFacetServlet;

import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.criterion.Property;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 28-Jul-2010
 * Time: 18:02:28
 * To change this template use File | Settings | File Templates.
 */
public class TreeRoot implements uk.ac.kcl.cch.XHtmlHierarchy.IXHtmlHierarchyItem{
    private IXHtmlHierarchyItem[] kids;
    private ArrayList<Integer> constraintKeys;

   /* public TreeRoot(HttpServletRequest request){
       constraintKeys= DIAMMFacetServlet.getManager(request).getMasterKeyList(FacetType.SOURCEFACET);
    }
*/
    public TreeRoot(ArrayList<Integer> constraintKeys){
       this.constraintKeys=constraintKeys; 
    }

    //TODO apply other constraints to hierarchy item counts

    public Vector<IXHtmlHierarchyItem> getAncestors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem[] getChildren() {
        //instantiate tree
        List<Alcountry> composers = HibernateUtil.getSession().createCriteria(Alcountry.class).addOrder(Property.forName("country").asc()).list();
        ArrayList<AlCountryItem> items=new ArrayList<AlCountryItem>();
        for (int i = 0; i < composers.size(); i++) {
            Alcountry alcountry =  composers.get(i);           
            //Apply current constraing keys to get correct item count
            int count=0;
            ArrayList<Integer> cKeys = SourceFacet.getInitialvalues(alcountry.getAlcountrykey(),SourceFacet.countryValues);
            if (cKeys != null && cKeys.size() > 0) {
            if (constraintKeys==null||constraintKeys.size()==0){
                count=SourceGUIFacet.getCountryCount(alcountry.getAlcountrykey());
            }  else{
                count= DIAMMFacetManager.mergeKeyLists(cKeys,this.constraintKeys).size();
            }
            AlCountryItem item= new AlCountryItem(alcountry, count);
            if (constraintKeys!=null&&constraintKeys.size()>0){
                item.setConstraintKeys(constraintKeys);
            }
            if (count>0){
                items.add(item);
            }
            }
        }
        kids=items.toArray(new IXHtmlHierarchyItem[0]);
        return kids;
    }

    public String getChildrenContainerClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector<IXHtmlHierarchyItem> getDescendants() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFullID() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getIDNumb() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getItemClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLabel() {
        return "Source";  //To change body of implemented methods use File | Settings | File Templates.
    }

     public int getNumbChildren() {
        if (kids==null){getChildren();}
        return kids.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IXHtmlHierarchyItem getParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasChildren() {
        if (kids==null){getChildren();}
        return kids.length > 0;//To change body of implemented methods use File | Settings | File Templates.
    }

    public void setItemClass(String className) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
