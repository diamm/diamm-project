<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 16-Nov-2010
  Time: 10:36:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>

<c:if test="${detailItem!=null&&detailItem.texts!=null}">

    <h3>Incipit and full text - Original Spelling</h3>

    <table>
        <tbody>
            <tr>
                <th>Voice</th>
                <th>clef</th>
                <th>language</th>
                <th>incipit</th>
            </tr>
            <c:forEach var="t" items="${detailItem.texts}">
                <tr>
                    <td>${t.alvoicebyalvoicekey.voice}</td>
                    <td>${t.alclefbyalclefkey.clef}</td>
                    <td>
                        <c:if test="${t.languages!=null}">                                    
                           <c:forEach var="l" items="${t.languages}">
                                ${l.language}
                            </c:forEach>
                        </c:if>
                    </td>
                    <td>${t.textincipit}</td>
                </tr>
                <c:if test="${t.standardspellingfulltext!=null&&fn:length(t.standardspellingfulltext)>0}">
                <tr>
                    ${t.standardspellingfulltext}
                </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>

</c:if>