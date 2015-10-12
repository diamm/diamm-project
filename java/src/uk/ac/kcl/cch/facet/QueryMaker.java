package uk.ac.kcl.cch.facet;


import java.util.*;
import java.util.regex.Pattern;

/**
 * Stores and manipulates SQL clauses handed to it by <code>Facet</codes>s.
 *
 * @author thill
 * @version 1.1 2010.4.26
 *
 */

abstract public class QueryMaker {

    private String fromSeparator=" ";

    protected Vector<String> _froms;
	protected Vector<String> _wheres;
	protected Vector<String> _orderByFieldNames;
	protected Vector<String> _groupBys;
	/** Stores the results of queries that are to be  used as the values of an IN-clause. */
	//private TargetEntity _targetEntity;
	/** Exigencies of responsiveness mean that it is often necessary for facetted searches
	 * to proceed as a series of small queries generating values that are subsequently used
	 * in an IN-clause to limit the search space for the final target query. These values are
	 * stored in the <code>_inClauses HashMap</code>, the keys being of the form {table_name.
	 * field_name} and the values being the db keys of that field. */
	private HashMap<String, ArrayList<Integer>> _inClauses;


	/** Constructor */
	public QueryMaker(){

		_froms = new Vector<String>();
		_wheres = new Vector<String>();
		_orderByFieldNames = new Vector<String>();
		_inClauses = new HashMap<String, ArrayList<Integer>>();
		_groupBys = new Vector<String>();
		

	}


	public void addFromString(String newFrom){

		if(!_froms.contains(newFrom)){

			_froms.add(newFrom);

		}

	}

	public void addWhereString(String newWhere){

		if(!_wheres.contains(newWhere) && newWhere != "" && newWhere != null){

			_wheres.add(newWhere);

		}

	}

	public void addOrderByFieldName(String newOrderBy){

		if(!_orderByFieldNames.contains(newOrderBy)){

			_orderByFieldNames.add(newOrderBy);

		}
	}

	public String generateFroms(){

		String fromString = "";
		Iterator<String> it = _froms.iterator();

		while(it.hasNext()){

			String storedTable = it.next();
			fromString += storedTable + fromSeparator+" ";

		}

		fromString = this.trimClause(fromString, ", ");
		return fromString;

	}

	public String generateFroms(String currentTable){

		String fromString = "";
		Iterator<String> it = _froms.iterator();

		while(it.hasNext()){

			String storedTable = it.next();

			if(!storedTable.equals(currentTable)){

				fromString += storedTable + ", ";

			}

		}

		fromString = this.trimClause(fromString, ", ");
		return fromString;

	}

	public String generateWheres(){

		buildInClauses();
		String whereString = "";

		Iterator<String> it = _wheres.iterator();

		while(it.hasNext()){

			whereString += it.next() + " AND ";

		}

		whereString = this.trimClause(whereString, " AND ");
		String groupBys = this.buildGroupByClause();
		if(!groupBys.equals("")){ whereString += " " + groupBys; }
		return whereString;

	}

	public String generateOrderByFieldNames(){

		String orderString = "";

		Iterator<String> it = _orderByFieldNames.iterator();

		while(it.hasNext()){

			orderString += it.next() + ", ";

		}

		orderString = this.trimClause(orderString, ", ");
		return orderString;

	}

	public void removeFromClause(String badFrom){

		Vector<String> filteredFroms = new Vector<String>();

		for(int i = 0; i < _froms.size(); i++){

			String testFrom = _froms.get(i);
			if(!testFrom.equals(badFrom)){

				filteredFroms.add(testFrom);

			}

		}

		_froms.clear();
		_froms = filteredFroms;

	}

	public void removeWhereClause(String badWhere){

		for(int i = 0; i < _wheres.size(); i++){

			String testFrom = _wheres.get(i);
			if(testFrom.equals(badWhere)){ _wheres.remove(i); break; }

		}


	}

	public void addGroupByField(String groupBy){

		if(groupBy.length() > 1 && !_groupBys.contains(groupBy)){

			_groupBys.add(groupBy);


		}

	}

	private String buildGroupByClause(){

		if(_groupBys.size() == 0){ return ""; }
		String gbBase = "GROUP BY ";
		Iterator<String> gbit = _groupBys.iterator();
		while(gbit.hasNext()){

			gbBase += gbit.next();
			if(gbit.hasNext()){ gbBase += ", "; }

		}

		return gbBase;

	}

	public void removeGroupByClause(String badGroup){

		for(int i = 0; i < _groupBys.size(); i++){

			String testGroup = _groupBys.get(i);
			if(testGroup.equals(badGroup)){ _groupBys.remove(i); break; }

		}


	}

    public void resetAllStringFields() {
        reset(_froms);
        reset(_wheres);
        reset(_orderByFieldNames);
        reset(_groupBys);
        _inClauses = new HashMap<String, ArrayList<Integer>>();

    }

    private void reset(Vector<String> v){
        if(v == null){
            v = new Vector<String>();
        }else{
            v.clear();
        }
    }

    /**
     * Used to eliminate trailing commas and/or brackets in FROM
     * and WHERE clauses
     *
     * @param clause The clause to be trimmed.
     * @param trailingChars The characters of which the clause is to be trimmed.
     * @return The trimmed clause.
     */
	private String trimClause(String clause, String trailingChars){

		if(clause.length() > trailingChars.length()){

			clause = clause.substring(0, clause.length() - trailingChars.length());

		}

		return clause;

	}

	public int getNumFroms(){ return _froms.size(); }

	public int getNumWheres(){

		return _wheres.size() + _inClauses.keySet().size();

	}



	public Vector<String> getFroms(){ return _froms; }

	public Vector<String> getWheres(){ return _wheres; }

	protected void setWheres(Vector<String> newWheres){ _wheres.clear(); _wheres = newWheres; }

	public void addStringConstraint(String ts, String tableAndField){

		if(ts != "" && ts != null){

			addWhereString(tableAndField + " LIKE '" + ts + "'");

		}

	}

	public void removeStringConstraints(){

		Vector<String> copyWheres = new Vector<String>();

		Iterator<String> wit = _wheres.iterator();
		while(wit.hasNext()){

			String where = wit.next();
			if(!where.matches(".+\\s+LIKE\\s+.+")){ copyWheres.add(where); }

		}

		_wheres.clear();
		_wheres = (Vector<String>)copyWheres.clone();

	}

	public void removeLetterPickerConstraint(){

		Vector<String> copyWheres = new Vector<String>();

		Iterator<String> wit = _wheres.iterator();
		while(wit.hasNext()){

			String where = wit.next();
			if(!where.matches(".+\\s+LIKE\\s+[\\\"'].+%['\\\"]")){

				copyWheres.add(where);

			}

		}

		_wheres.clear();
		_wheres = (Vector<String>)copyWheres.clone();

	}




	protected void buildInClauses(){

		for(Map.Entry<String, ArrayList<Integer>> entry : _inClauses.entrySet()){

			String base = entry.getKey() + " IN ";
			ArrayList<Integer> dbKeys = entry.getValue();
			String startString = dbKeys.toString();
			String openBracketedKeys = startString.replace("[", "(");
			String closeBracketedKeys = openBracketedKeys.replace("]", ")");
			base = base + closeBracketedKeys;
			addWhereString(base);

		}

	}

	public void setInClause(String tableAndField, ArrayList<Integer> values){

		if(values.size() < 1){ values.add(-1); }
		if(!_inClauses.keySet().contains(tableAndField)){ _inClauses.put(tableAndField, values); }
		else{

			ArrayList<Integer> storedValues = (ArrayList<Integer>) _inClauses.get(tableAndField).clone();
			if(storedValues.size() == 0){ storedValues = (ArrayList<Integer>) values.clone();  }
			else{ storedValues.retainAll(values); }
			_inClauses.remove(tableAndField);
			_inClauses.put(tableAndField, storedValues);

		}

	}

	public ArrayList<Integer> getInClauseValues(String field){

		if(!_inClauses.keySet().contains(field)){ return new ArrayList<Integer>(); }
		else{ return _inClauses.get(field); }

	}

	public Vector<String> getGroupBys(){ return _groupBys; }

	public HashMap<String, ArrayList<Integer>> getInClauses(){ return _inClauses; }

	public void resetInClause(String tableAndField){

		if(!_inClauses.containsKey(tableAndField)){ return; }
        //Changed to remove by eh 6/7/2010
        else{ _inClauses.remove(tableAndField); }

	}

	public void removeInsFromWheres(){

		Vector<String> newWheres = new Vector<String>();
		Iterator<String> wheres = _wheres.iterator();
		Pattern pattern = Pattern.compile("\\w+.\\w+\\s+IN\\s*\\((-)?[\\d,\\s]+\\)");

		while(wheres.hasNext()){

			String where = wheres.next();
			if(!pattern.matcher(where).matches()){

				newWheres.add(where);

			}

		}

		_wheres =  newWheres;

	}


}