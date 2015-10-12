package uk.ac.kcl.cch.facet;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 09-Nov-2010
 * Time: 13:13:44
 * To change this template use File | Settings | File Templates.
 */
public enum TextSearchPattern {
    startswithPattern("^?"),
    containsPattern("?"),
    wholeWordPattern("[[:<:]]?[[:>:]]");
    private final String pattern;

    public String pattern() {
        return pattern;
    }

    TextSearchPattern(String pattern) {
        this.pattern = pattern;
    }

}
