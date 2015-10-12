package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.facet.DIAMMFacetManager;
import uk.ac.kcl.cch.diamm.facet.GenreFacet;
import uk.ac.kcl.cch.diamm.facet.type.GenreFacetType;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.FacetState;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class GenreGUIFacet extends GUIFacet {

    private String genreLetter;
    private String genreSearchString;
    private static Map<String, List<FacetCriterion>> genreGroups;

    public GenreGUIFacet(Facet f, GUIFacetManager g) {
        super(f, g);

        if (GenreGUIFacet.genreGroups == null) {
            GenreGUIFacet.genreGroups = getGenreGroups(GenreFacet.genreValues);
        }
    }


    /**
     * Takes the inital counts of Genres and applies other
     * facet constraints to get list of Genres with current item count.
     *
     * @param request
     */
    protected void addGenres(HttpServletRequest request, List<FacetCriterion> Genres) {
        //Applies the constraints of other facets to counts
        ArrayList<Integer> constraintKeys = getFacet().getManager().getMasterKeyList(new GenreFacetType());
        if (constraintKeys == null || constraintKeys.size() == 0) {
            //No criteria selected, return initial list
            request.setAttribute(Constants.genreAttrName, Genres);
        } else if (Genres != null && Genres.size() > 0) {
            List<FacetCriterion> comps = new ArrayList<FacetCriterion>();
            for (int i = 0; i < Genres.size(); i++) {
                //Get modified counts based on key list
                //from other facets
                FacetCriterion fc = Genres.get(i);
                ArrayList<Integer> l1 = fc.getKeys();
                ArrayList<Integer> newList = DIAMMFacetManager.mergeKeyLists(l1, constraintKeys);
                FacetCriterion c = new FacetCriterion(newList, getFacet().getType(), fc.getKey(), fc.getLabel());
                if (c.getCount() > 0) {
                    comps.add(c);
                }
            }
            request.setAttribute(Constants.genreAttrName, comps);
        }

        //set letter picker for Genre

    }

    public void addGenresByLetter(HttpServletRequest request) {
        List<FacetCriterion> Genres = GenreGUIFacet.genreGroups.get(genreLetter);
        addGenres(request, Genres);
    }

    public void addGenreAlphabet(HttpServletRequest request) {
        ArrayList<Integer> constraintKeys = getFacet().getManager().getMasterKeyList(getFacet().getType());
        if (constraintKeys == null || constraintKeys.size() == 0) {
            request.setAttribute(Constants.GENRE_PICKER_NAME, new LetterPicker(genreGroups));
        } else {
            List<FacetCriterion> values = GenreFacet.genreValues;
            values = getDynamicCriteria(values);
            Map<String, List<FacetCriterion>> groups = getGenreGroups(values);
            request.setAttribute(Constants.GENRE_PICKER_NAME, new LetterPicker(groups));
        }
    }

    protected void addGenresBySearch(HttpServletRequest request) {
        //narrow down search by finding the correct Genre group based on 1st letter of search string
        List<FacetCriterion> GenresByGroup = GenreGUIFacet.genreGroups.get(genreSearchString.substring(0, 1).toUpperCase());
        List<FacetCriterion> Genres = new ArrayList<FacetCriterion>();

        //if Genre.label begins with the searchstring, then add Genre to list
        for (FacetCriterion Genre : GenresByGroup) {
            //use case-insensitive matching
            if (Genre.getLabel().toLowerCase().indexOf(genreSearchString.toLowerCase()) == 0) {
                Genres.add(Genre);
            }
        }
        addGenres(request, Genres);
    }

    protected void addFacetData(HttpServletRequest request) {
        applyRequestVariables(request);
        //choose method to find Genres depending on search or letter
        if (getGUIstate() == GUIFacetState.OPEN) {
            if (genreSearchString != null) {
                addGenresBySearch(request);
                request.setAttribute(Constants.Genre_SEARCH, genreSearchString);
            } else if (genreLetter != null) {
                addGenresByLetter(request);
                request.setAttribute(Constants.Genre_LETTER_NAME, genreLetter);
            }
            addGenreAlphabet(request);
        }
        //ADD label
        if (getLabel().length() > 0) {
            request.setAttribute("genreLabel", getLabel());
        }
        //If the facet is selected, add the key to request
        if (getFacet().getState() != FacetState.EMPTY) {
            request.setAttribute("GENREFACETSELECTED", getFacet().getValue());
        }
    }

    public boolean updateFacetFromRequest(HttpServletRequest request) {
        boolean changed = getFacet().updateConstraintsFromRequest(request);

        return changed;
    }

    public String getDescription() {
        if (getFacet().getValue() > 0) {
            FacetCriterion fc = getFacet().getCurrentCriterion();
            return " <li> <label> Genre:" + fc.getLabel() + "</label>\n" +
                    "<a class=\"t9 m3\" href=\"FacetManager?op=4&FacetType=GenreFACET\">Remove</a></li>";
        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFacetType() {
        return new GenreFacetType().getTypeName();
    }

    public String getLabel() {
        if (getFacet().getValue() > 0) {
            FacetCriterion fc = getFacet().getCurrentCriterion();
            return fc.getLabel();
        }/*else if (genreSearchString !=  null) {
    	    return genreSearchString;
        } 	else if (genreLetter!=null) {
    		return genreLetter;
        }*/
        return "";
    }

    private Map<String, List<FacetCriterion>> getGenreGroups(List<FacetCriterion> Genres) {

        //sort Genres by label
        Collections.sort(Genres, new FacetCriterion.LabelComparator());

        Map<String, List<FacetCriterion>> GenreGroups = new LinkedHashMap<String, List<FacetCriterion>>();

        //Initialise letter counts to zero
        for (String letter : LetterPicker.alphabet) {
            GenreGroups.put(letter, new ArrayList<FacetCriterion>());
        }

        //Associate each Genre with a letter of the alphabet
        for (FacetCriterion comp : Genres) {
            //only include Genres with items
            if (comp.getLabel()!=null&&comp.getLabel().length()>0&&comp.getCount() > 0) {
                String letter = comp.getLabel().substring(0, 1).toUpperCase();
                GenreGroups.get(letter).add(comp);
            }
        }

        return GenreGroups;
    }

    private void applyRequestVariables(HttpServletRequest request) {
        HttpSession session = request.getSession();

        //a search string was used for Genre
        if (request.getParameter(Constants.Genre_SEARCH) != null && request.getParameter(Constants.Genre_SEARCH).trim().length() > 0) {
            genreSearchString = request.getParameter(Constants.Genre_SEARCH);
        }
        //a search string was not used for Genre
        else {
            genreSearchString = null;

            //check if letter was selected for Genre
            if (request.getParameter(Constants.Genre_LETTER_NAME) != null) {
                session.setAttribute(Constants.Genre_LETTER_NAME, request.getParameter(Constants.Genre_LETTER_NAME));
                genreLetter = request.getParameter(Constants.Genre_LETTER_NAME);
            } else {
                if (session.getAttribute(Constants.Genre_LETTER_NAME) != null) {
                    genreLetter = (String) session.getAttribute(Constants.Genre_LETTER_NAME);
                }
            }
        }
    }
}