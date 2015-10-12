<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>

<h3>Bibliography</h3>

<div id="bibliography">
    <ul>
        <c:forEach var="bib" items="${bibs}">
            <li>
                <a href="BibliographyDetail?bibId=${bib.bib.bibliographykey}">${bib.bib.fulltextcalculated}</a> ${bib.notes}
            </li>
        </c:forEach>
    </ul>
</div>