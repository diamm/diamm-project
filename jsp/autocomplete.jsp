<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 17/02/11
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="application/JSON;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
[
<c:forEach var="auto" items="${autoCompleteResults}" varStatus="c">
      {"id":"${auto.id}","label":"${auto.label}","value":"${auto.label}"} <c:if test="${!c.last}">,</c:if>
    </c:forEach>
]