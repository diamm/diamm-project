package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.facet.ui.FacetCriterion;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LetterPicker {

	public static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
                                       "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	Map<String, Boolean> letterSwitch = new TreeMap<String, Boolean>();
	
	public LetterPicker(Map<String, List<FacetCriterion>> map) {
		for (String letter: alphabet) {
            letterSwitch.put(letter, map.get(letter).isEmpty() ? false : true);
        }
	}
	
	public String[] getAlphabet() {
		return LetterPicker.alphabet;
	}
	
	public Map<String, Boolean> getLetterSwitch() {
		return letterSwitch;
	}
}
