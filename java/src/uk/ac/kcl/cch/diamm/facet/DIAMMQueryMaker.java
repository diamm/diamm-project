package uk.ac.kcl.cch.diamm.facet;

import uk.ac.kcl.cch.facet.QueryMaker;
import uk.ac.kcl.cch.diamm.ui.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 05-May-2010
 * Time: 16:49:04
 * To change this template use File | Settings | File Templates.
 */
public class DIAMMQueryMaker extends QueryMaker {

    private HashMap<String, String> _aliases;

    public DIAMMQueryMaker() {

		super();
		/*_aliases = new HashMap<String, String>();
		_aliases.put("FactoidLocation", "fl");
		_aliases.put("FactoidPossession", "fp");
		_aliases.put("FactoidPersonWLocation", "fpl");
		_aliases.put("FactoidPersonWPossession", "fpp");*/

	}

    /*public static ArrayList<Integer> getQueryItemKeys(QueryMaker q) {
        HashMap<String, ArrayList<Integer>> clauses = q.getInClauses();
        if (clauses != null) {
            ArrayList<Integer> queryItemKeys = clauses.get(Constants.itemClauseName);
            return queryItemKeys;
        }
        return null;
    }*/

    public void addCoupledClauses(String fromString, String whereString) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addTextSearchConstraint(ArrayList<String> textFields, String textToSearchFor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
