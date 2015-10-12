package uk.ac.kcl.cch.diamm.ui;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 12-May-2010
 * Time: 16:24:28
 * To change this template use File | Settings | File Templates.
 */
public class Constants extends uk.ac.kcl.cch.facet.ui.Constants{
    /*Session variables*/
    public static String groupTypeSessionName="DIAMM.currentGroupType";
    public static String facetManagerSessionName="DIAMM.facetManager";
    public static String GUIManagerSessionName="DIAMM.GUIManager";
    public static GroupType defaultGroupType= GroupType.SOURCE;
    public static String pageNumberSessionName="DIAMM.currentPage";
    public static String  itemClauseName="Item.itemKey";
    public static String  detailItemSessionName="Item.itemKey";
    public static String  currentDetailTabSessionName="detailTab";
    public static String  currentResultsPerPage="perPage";
     public static String  currentResultCount="DIAMM.total";
    public static String  imageOnlyToggleSessionName="imageonly";
    public static String groupItemsSessionName="DIAMM.groupedItems";
    public static String ImageSessionName="DIAMM.imageviewed";
    /*
    Attribute names
     */
    public static String  searchDescriptionAttrName="searchDescription";
   public static String  itemAttrName="ItemGroupList";
    public static String  groupAttrName="groupType";
    public static String  itemCountAttrName="ItemCount";
   public static String sourceCountryAttrName ="Countries";
   public static String  composerAttrName="Composers";
    public static String  genreAttrName="Genres";
    public static String  sourceTreeAttrName="sourceTree";
    public static String  paginationAttrName="pageNav";
     public static String  detailItemName="detailItem";
    public static String  currentDetailTabName="detailTab";
    public static String COMPOSER_LETTER_NAME = "composerLetter";
    public static String COMPOSER_SEARCH = "composerSearch";
     public static String TEXT_SEARCH = "textSearch";
    public static String TEXT_PATTERN = "textSearchPattern";
    public static String COMPOSER_PICKER_NAME = "composerPicker";
    public static String GENRE_PICKER_NAME = "genrePicker";
    public static String provenanceAttrName="provs";
    public static String provenanceAttrSelected="alprovenancekey";
    public static String languageAttrName="languages";
    public static String languageAttrSelected="allanguagekey";

    public static String notationAttrName="notations";
    public static String notationAttrSelected="alnotationtypekey";
    public static String setAttrName="sets";
    public static String setAttrSelected="setkey";
    public static String clefAttrSelected="clefkey";
    public static String clefAttrName="clefs";

    public static String century1AttrSelected="century1";
    public static String century2AttrSelected="century2";

    public static String personAttrName="persons";
    public static String personAttrSelected="alpersonkey";
    public static String imageOnlyAttrName="imageonly";
    public static String openFacet="openFacet";

    /*
        JSP Pages
     */
    public static final String windowPage="init.jsp";
    public static final String sourceFacetPage="sourcefacet.jsp";

    public static final String itemFacetPage="itemfacet.jsp";

    /*
        Names of Init Parameters
     */
    public static final String jspPRoot="jsp_protected_root";


    /*Request Parameters*/
    public static String opParameter="op";
    public static String redrawParameter="redraw";
    public static String facetTypeParameter="FacetType";
    public static String pageNumberParameter="pageNum";
    public static String rowsPerPageParameter="rowsPerPage";
     public static String detailItemParameter="detailItemKey";
    public static String imageOnlyParameter="imageonly";
    public static String groupTypeParameter="groupType";
    public static String Genre_LETTER_NAME="genreLetter";
    public static String Genre_SEARCH="genreSearch";
    //public static String
   public static boolean sync=true;
   public static int defaultResultsPerPage=20;
    //Number of page links to show on bottom page nav
    public static int defaulPageRange=3; 

    

}
