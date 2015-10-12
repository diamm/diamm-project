package uk.ac.kcl.cch.facet.ui;

import uk.ac.kcl.cch.diamm.ui.GroupType;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 04-Nov-2010
 * Time: 13:15:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Constants {
    public static final String groupTypeSessionName="DIAMM.currentGroupType";
    public static final String facetManagerSessionName="DIAMM.facetManager";
    public static final String GUIManagerSessionName="DIAMM.GUIManager";
    public static final GroupType defaultGroupType= GroupType.SOURCE;
    public static final String pageNumberSessionName="DIAMM.currentPage";
    public static final String  itemClauseName="Item.itemKey";
    public static final String  detailItemSessionName="Item.itemKey";
    public static final String  currentDetailTabSessionName="detailTab";
    public static final String  currentResultsPerPage="perPage";
     public static final String  currentResultCount="DIAMM.total";
    public static final String  imageOnlyToggleSessionName="imageonly";
    public static final String  searchDescriptionAttrName="";

    public static final String opParameter="op";
       public static final String redrawParameter="redraw";
       public static final String facetTypeParameter="FacetType";
       public static final String pageNumberParameter="pageNum";
       public static final String rowsPerPageParameter="rowsPerPage";
        public static final String detailItemParameter="detailItemKey";
       public static final String imageOnlyParameter="imageonly";
       public static final String groupTypeParameter="groupType";

       //public static final String
      public static final boolean sync=true;
      public static final int defaultResultsPerPage=20;
       //Number of page links to show on bottom page nav
       public static final int defaulPageRange=3;

      public static String openFacet="openFacet";

    public static enum FacetType{};

}
