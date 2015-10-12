<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 29-Jun-2010
  Time: 12:32:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head><title>Item Test</title></head>
<body>
<ul>
    <c:forEach var="item" items="${ItemList}">
        <li>${item.itemkey}: ${item.label}</li>
    </c:forEach>
</ul>
</body>
</html>