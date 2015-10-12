package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.facet.ComposerFacet;
import uk.ac.kcl.cch.diamm.facet.type.ComposerFacetType;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static uk.ac.kcl.cch.diamm.ui.Constants.*;

public class ComposerGUIFacet extends GUIFacet {

    private String composerLetter;
    private String composerSearchString;
    private static Map<String, List<FacetCriterion>> composerGroups;

    public ComposerGUIFacet(Facet f, GUIFacetManager g) {
        super(f, g);

        if (ComposerGUIFacet.composerGroups == null) {
            ComposerGUIFacet.composerGroups = getComposerGroups(ComposerFacet.composerValues);
        }
    }


    /**
     * Takes the inital counts of composers and applies other
     * facet constraints to get list of composers with current item count.
     *
     * @param request
     */
    protected void addComposers(HttpServletRequest request, List<FacetCriterion> composers) {
        List<FacetCriterion> comps = getDynamicCriteria(composers);
        request.setAttribute(Constants.composerAttrName, comps);
        /*if (constraintKeys == null || constraintKeys.size() == 0) {
            //No criteria selected, return initial list
            request.setAttribute(Constants.composerAttrName, composers);
        } else if (composers != null && composers.size() > 0) {
            List<FacetCriterion> comps = new ArrayList<FacetCriterion>();
            for (int i = 0; i < composers.size(); i++) {
                //Get modified counts based on key list
                //from other facets
                FacetCriterion fc = composers.get(i);
                ArrayList<Integer> l1 = fc.getKeys();
                ArrayList<Integer> newList = DIAMMFacetManager.mergeKeyLists(l1, constraintKeys);
                FacetCriterion c = new uk.ac.kcl.cch.facet.ui.FacetCriterion(newList, getFacet().getNotetype(), fc.getKey(), fc.getLabel());
                if (c.getCount() > 0) {
                    comps.add(c);
                }
            }
            request.setAttribute(Constants.composerAttrName, comps);
        }*/


    }


    public void addComposersByLetter(HttpServletRequest request) {
        List<FacetCriterion> composers = null;

        composers = ComposerGUIFacet.composerGroups.get(composerLetter);

        addComposers(request, composers);
    }

    protected void addComposersBySearch(HttpServletRequest request) {
        //narrow down search by finding the correct composer group based on 1st letter of search string
        List<FacetCriterion> composersByGroup = ComposerGUIFacet.composerGroups.get(composerSearchString.substring(0, 1).toUpperCase());
        List<FacetCriterion> composers = new ArrayList<FacetCriterion>();

        //if composer.label begins with the searchstring, then add composer to list
        for (FacetCriterion composer : composersByGroup) {
            //use case-insensitive matching
            if (composer.getLabel().toLowerCase().indexOf(composerSearchString.toLowerCase()) == 0) {
                composers.add(composer);
            }
        }
        addComposers(request, composers);
    }

    public void addComposerAlphabet(HttpServletRequest request) {
        ArrayList<Integer> constraintKeys = getFacet().getManager().getMasterKeyList(new ComposerFacetType());
        if (constraintKeys == null || constraintKeys.size() == 0) {
            request.setAttribute(COMPOSER_PICKER_NAME, new LetterPicker(composerGroups));
        } else {
            List<FacetCriterion> values = ComposerFacet.composerValues;
            values = getDynamicCriteria(values);
            Map<String, List<FacetCriterion>> groups = getComposerGroups(values);
            request.setAttribute(COMPOSER_PICKER_NAME, new LetterPicker(groups));
        }
    }

    protected void addFacetData(HttpServletRequest request) {

        applyRequestVariables(request);
        //choose method to find composers depending on search or letter
        if (getGUIstate() == GUIFacetState.OPEN) {
            if (composerSearchString != null) {
                addComposersBySearch(request);
                request.setAttribute(Constants.COMPOSER_SEARCH, composerSearchString);
            } else if (composerLetter != null) {
                addComposersByLetter(request);
            }
            //Add general alphabet            
            addComposerAlphabet(request);
        }
        //ADD label
        if (getLabel().length() > 0) {
            request.setAttribute("composerLabel", getLabel());
        }
        //If the facet is selected, add the key to request
        if (getFacet().getState() != FacetState.EMPTY) {
            request.setAttribute("COMPOSERFACETSELECTED", getFacet().getValue());
        }
    }

    public boolean updateFacetFromRequest(HttpServletRequest request) {
        boolean changed = getFacet().updateConstraintsFromRequest(request);

        return changed;
    }

    public String getDescription() {
        if (getFacet().getValue() > 0) {
            uk.ac.kcl.cch.facet.ui.FacetCriterion fc = getFacet().getCurrentCriterion();
            return " <li> <label> Composer:" + fc.getLabel() + "</label>\n" +
                    "<a class=\"t9 m3\" href=\"FacetManager?op=4&FacetType=COMPOSERFACET\">Remove</a></li>";

        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFacetType() {
        return new ComposerFacetType().getTypeName();
    }

    public String getLabel() {
        if (getFacet().getValue() > 0) {
            FacetCriterion fc = getFacet().getCurrentCriterion();
            return fc.getLabel();
        } /*else if (composerSearchString != null) {
            return composerSearchString;
        } else if (composerLetter != null) {
            return composerLetter;
        }   */
        return "";
    }

    private Map<String, List<FacetCriterion>> getComposerGroups(List<FacetCriterion> composers) {

        //sort composers by label
        Collections.sort(composers, new FacetCriterion.LabelComparator());

        Map<String, List<FacetCriterion>> composerGroups = new LinkedHashMap<String, List<FacetCriterion>>();

        //Initialise letter counts to zero
        for (String letter : LetterPicker.alphabet) {
            composerGroups.put(letter, new ArrayList<FacetCriterion>());
        }

        //Associate each composer with a letter of the alphabet
        for (FacetCriterion comp : composers) {
            //only include composers with items
            if (comp.getCount() > 0) {
                String letter = comp.getLabel().substring(0, 1).toUpperCase();
                if (letter != null && composerGroups.get(letter) != null) {
                    composerGroups.get(letter).add(comp);
                }
            }
        }

        return composerGroups;
    }

    private void applyRequestVariables(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (request.getParameter("resetGuiFacet") != null && request.getParameter("resetGuiFacet").equals("COMPOSERFACET")) {
            //Reset
            composerSearchString = null;
            composerLetter = null;
            //a search string was used for composer
        } else if (request.getParameter(COMPOSER_SEARCH) != null && request.getParameter(COMPOSER_SEARCH).trim().length() > 0) {
            composerSearchString = request.getParameter(COMPOSER_SEARCH);
        }
        //a search string was not used for composer
        else {
            composerSearchString = null;

            //check if letter was selected for composer
            if (request.getParameter(COMPOSER_LETTER_NAME) != null) {
                session.setAttribute(COMPOSER_LETTER_NAME, request.getParameter(COMPOSER_LETTER_NAME));
                composerLetter = request.getParameter(COMPOSER_LETTER_NAME);
            } else {
                if (session.getAttribute(COMPOSER_LETTER_NAME) != null) {
                    composerLetter = (String) session.getAttribute(COMPOSER_LETTER_NAME);
                }
            }
        }
    }
}
