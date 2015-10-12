<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 15-Nov-2010
  Time: 12:24:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<h3>Other <c:if test="${secondaryLabel!=null}">: ${secondaryLabel} </c:if> <a href="FacetManager?op=9&FacetType=SECONDARYFACET" class="t9 m0 <c:if test="${openFacet ne 'SECONDARYFACET'}">s6</c:if> <c:if test="${openFacet eq 'SECONDARYFACET'}">s7</c:if>" title="Collapse this Facet">Collapse</a></h3>
  <c:if test="${openFacet eq 'SECONDARYFACET'}">
<form class="fms" method="post" action="FacetManager">
    <fieldset>
        <input type="hidden" name="op" value="1"/>
        <input type="hidden" name="FacetType" value="SECONDARYFACET"/>
        <ul>
            <li>
                <label>Provenance</label>
                <select name="alprovenancekey" id="provenanceSelect">
                    <option value="">Select</option>
                    <c:forEach var="prov" items="${provs}">
                        <option
                                <c:if test="${alprovenancekey==prov.key}">selected="selected"</c:if>
                                value="${prov.key}">${prov.label} (${prov.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label>Person </label>
                <select name="alpersonkey" id="personSelect">
                    <option value="">Select</option>
                    <c:forEach var="person" items="${persons}">
                        <option
                                <c:if test="${alpersonkey==person.key}">selected="selected"</c:if>
                                value="${person.key}">${person.label} (${person.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label> Languages </label>
                <select name="allanguagekey">
                    <option value="">Select</option>
                    <c:forEach var="crit" items="${languages}">
                        <option
                                <c:if test="${allanguagekey==crit.key}">selected="selected"</c:if>
                                value="${crit.key}">${crit.label} (${crit.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label>Century </label>

                <select name="century1">
                    <option value="">Select</option>
                    <c:forEach var="cent" items="${centuries}">
                        <option
                                <c:if test="${century1==cent}">selected="selected"</c:if> value="${cent}">${cent}th
                            Century
                        </option>
                    </c:forEach>
                </select>
                <select name="century2">
                    <option value="">Select</option>
                    <c:forEach var="cent" items="${centuries}">
                        <option
                                <c:if test="${century2==cent}">selected="selected"</c:if> value="${cent}">${cent}th
                            Century
                        </option>
                    </c:forEach>
                </select>
                <button type="submit">Go</button>
            </li>
            <li>
                <label> Set </label>
                <select name="setkey" id="setSelect">
                    <option value="">Select</option>
                    <c:forEach var="crit" items="${sets}">
                        <option
                                <c:if test="${setkey==crit.key}">selected="selected"</c:if>
                                value="${crit.key}">${crit.label} (${crit.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label> Notation </label>
                <select name="alnotationtypekey" id="notationSelect">
                    <option value="">Select</option>
                    <c:forEach var="crit" items="${notations}">
                        <option
                                <c:if test="${alnotationtypekey==crit.key}">selected="selected"</c:if>
                                value="${crit.key}">${crit.label} (${crit.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label> Clef </label>
                <select name="alClefKey" id="clefSelect">
                    <option value="">Select</option>
                    <c:forEach var="crit" items="${clefs}">
                        <option
                                <c:if test="${clefkey==crit.key}">selected="selected"</c:if>
                                value="${crit.key}">${crit.label} (${crit.count})
                        </option>
                    </c:forEach>
                </select>
            </li>
        </ul>


    </fieldset>
</form>
      </c:if>