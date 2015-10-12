<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul>
  <c:choose>
    <c:when test="${recordNav.onFirstRecord}">
      <li>&#8249; Previous</li>
      <li><a href="${param.urlRoot}&nextRecord=${recordNav.nextRecord}" title="Next">Next &#8250;</a></li>
    </c:when>
    <c:when test="${recordNav.onLastRecord}">
      <li><a href="${param.urlRoot}&prevRecord=${recordNav.prevRecord}" title="Previous">&#8249; Previous</a></li>
      <li>Next &#8250;</li>
    </c:when>
    <c:otherwise>
      <li><a href="${param.urlRoot}&prevRecord=${recordNav.prevRecord}" title="Previous">&#8249; Previous</a></li>
      <li><a href="${param.urlRoot}&nextRecord=${recordNav.nextRecord}" title="Next">Next &#8250;</a></li>
    </c:otherwise>
  </c:choose>
</ul>