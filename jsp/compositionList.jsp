<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>

<h3>Compositions</h3>

<div id="compositions">
    <ul>
        <c:forEach var="comp" items="${compositions}">
            <li>
                <a href="Descriptions?op=COMPOSITION&compositionKey=${comp.compositionkey}">${comp.compositionName}</a>
            </li>
        </c:forEach>
    </ul>
</div>