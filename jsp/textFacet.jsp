<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 09-Nov-2010
  Time: 14:08:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<h3>Text <c:if test="${textLabel!=null}">: ${textLabel} </c:if> <a href="FacetManager?op=9&FacetType=TEXTFACET" class="t9 m0 <c:if test="${openFacet ne 'TEXTFACET'}">s6</c:if> <c:if test="${openFacet eq 'TEXTFACET'}">s7</c:if>" title="Collapse this Facet">Collapse</a></h3>
 <c:if test="${openFacet eq 'TEXTFACET'}">
<form class="fms" method="get" action="FacetManager" id="textSearchForm">
    <fieldset>
        <fieldset class="m0">
                          <label for="textSearch"></label>
                          <input id="textSearch" type="text" name="textString" value="${textSearch}"/>
                          <button type="submit"><b class="t9 m5">Search</b></button>
        </fieldset>
        <input type="hidden" name="op" value="1"/>
        <input type="hidden" name="FacetType" value="TEXTFACET"/>
        <fieldset class="m1">
        <ul>
            <li><label for="textSearchPattern">Starts with</label><input type="RADIO"
                       <c:if test="${textSearchPattern=='startswithPattern'}">checked=checked</c:if>
                  id="textSearchPattern"     name="textSearchPattern" value="startswithPattern"/></li>
            <li><label for="containsPattern"> Contains </label><input type="RADIO"
                       <c:if test="${textSearchPattern=='containsPattern'}">checked=checked</c:if>
                       id="containsPattern" name="textSearchPattern" value="containsPattern"/></li>
            <li><label for="wholeWordPattern"> Phrase (exact)</label><input type="RADIO"
                       <c:if test="${textSearchPattern=='wholeWordPattern'}">checked=checked</c:if>
                      id="wholeWordPattern" name="textSearchPattern" value="wholeWordPattern"/></li>
        </ul>
            </fieldset>
<%--
        <button type="submit" id="textPicker">Go</button>
        <button type="reset">Clear</button>--%>
    </fieldset>
</form>
     </c:if>
<%-- <ul class="nvz">
<c:forEach var="letter" items="${genrePicker.alphabet}">
<c:if test="${genrePicker.letterSwitch[letter]==true}">
 <li><a href="FacetManager?op=1&FacetType=GEMNREFACET&genreLetter=${letter}">${letter}</a>
 </li>
</c:if>
</c:forEach>
</ul>           --%>