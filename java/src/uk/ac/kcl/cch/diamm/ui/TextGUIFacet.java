package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.facet.TextFacet;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 09-Nov-2010
 * Time: 11:47:32
 * To change this template use File | Settings | File Templates.
 */
public class TextGUIFacet extends GUIFacet {


    public TextGUIFacet(Facet f, GUIFacetManager g) {
        super(f, g);
    }

    protected void addFacetData(HttpServletRequest request) {
        //ADD label
        TextFacet t = (TextFacet) getFacet();
        if (getGUIstate() == GUIFacetState.OPEN) {
            /*if (request.getParameter("resetGuiFacet") != null && request.getParameter("resetGuiFacet").equals("COMPOSERFACET")) {
               t.setConstraintString("");
            }*/

            if (t.getConstraintString() != null && t.getConstraintString().length() > 0) {
                request.setAttribute(Constants.TEXT_SEARCH, t.getConstraintString());
            }
            if (t.getPattern() != null) {
                request.setAttribute(Constants.TEXT_PATTERN, t.getPattern().name());
            }
        }
        if (getLabel().length() > 0) {
            request.setAttribute("textLabel", getLabel());
        }
    }

    public String getDescription() {
        TextFacet t = (TextFacet) getFacet();
        if (t.getConstraintString() != null && t.getConstraintString().length() > 0) {
            return " <li> <label> Text:" + t.getConstraintString() + "</label>\n" +
                    "<a class=\"t9 m3\" href=\"FacetManager?op=4&FacetType=TextFACET\">Remove</a></li>";
        }
        return "";
    }

    public String getFacetType() {
        return getFacet().getType().getTypeName();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLabel() {
        TextFacet t = (TextFacet) getFacet();
        if (t.getConstraintString() != null && t.getConstraintString().length() > 0) {
            return t.getConstraintString();  //To change body of implemented methods use File | Settings | File Templates.
        }
        return "";
    }

    public boolean updateFacetFromRequest(HttpServletRequest request) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
