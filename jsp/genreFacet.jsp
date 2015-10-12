<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 09-Nov-2010
  Time: 14:08:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<h3>Genre <c:if test="${genreLabel!=null}">: ${genreLabel} </c:if>
    <a href="FacetManager?op=9&FacetType=GENREFACET" class="t9 m0 <c:if test="${openFacet ne 'GENREFACET'}">s6</c:if> <c:if test="${openFacet eq 'GENREFACET'}">s7</c:if>" title="Collapse this Facet">Collapse</a>
</h3>
 <c:if test="${openFacet eq 'GENREFACET'}">
            <ul class="nvz">
                <c:forEach var="letter" items="${genrePicker.alphabet}">
                    <c:choose>
                        <c:when test="${genreLetter eq letter}">
                            <li class="s1">${letter}</li>
                        </c:when>
                        <c:when test="${genrePicker.letterSwitch[letter]==true}">
                            <li <c:if test="${genreLetter eq letter}">class="s1"</c:if>><a href="FacetManager?op=1&FacetType=GENREFACET&genreLetter=${letter}">${letter}</a>
                        </li>
                        </c:when>
                        <c:otherwise>
                            <li class="s5">${letter}</li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
             <form class="fms" method="get" action="FacetManager">
                           <fieldset>
                              <input type="hidden" name="op" value="1"/>
                               <input type="hidden" name="FacetType" value="GENREFACET"/>
                              <input type="text" name="genreSearch" value="${genreSearch}"/>
                              <button type="submit" id="genrePicker">Go</button>
                           </fieldset>
                        </form>
            <ul>
                <c:forEach var="c" items="${Genres}">
                    <li>
                        <a href="FacetManager?op=1&FacetType=GENREFACET&alGenreKey=${c.key}">${c.label}</a>&nbsp;(${c.count})
                        &nbsp;<a class="g5" href="Descriptions?op=GENRE&alGenreKey=${c.key}"></a>
                    </li>
                </c:forEach>
            </ul>
     </c:if>