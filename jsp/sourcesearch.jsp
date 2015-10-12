<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 16/02/11
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
    <select name="sourceType">
        <option value="1">Country</option>
        <option value="2">City</option>
        <option value="3">Archive</option>
        <option value="4">Source</option>
    </select>
    <select name="sourceSelect">
        <c:choose>
            <c:when test="${sourceOptions!=null}">
                  <c:forEach items="${sourceOptions}" var="item">
                      <option value="${item.id}" <c:if test="${item.id==sourceSelected}">selected="selected"</c:if>>${item.label}</option>
                  </c:forEach>
            </c:when>
            <c:otherwise>
                  <option value="0" selected="selected">Select source type</option>
            </c:otherwise>
        </c:choose>
    </select>
