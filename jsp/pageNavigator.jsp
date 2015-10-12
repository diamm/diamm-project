<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="itp">
<ul>
<c:choose>
  <c:when test="${pageNav.onFirstPage}">
    <li class="s5">&#8249;&#8249;</li>
    <li class="s5">&#8249;</li>
  </c:when>
  <c:otherwise>
    <li><a href="${param.urlRoot}&nextPage=${pageNav.firstPage}">&#8249;&#8249;</a></li>
    <li><a href="${param.urlRoot}&nextPage=${pageNav.prevPage}">&#8249;</a></li>
  </c:otherwise>
</c:choose>

<c:if test="${pageNav.extendLeft}">
  <li><a href="${param.urlRoot}&nextPage=${pageNav.extendLeftPage}">...</a></li>
</c:if>

<c:forEach var="pageNo" begin="${pageNav.firstPageInRange}" end="${pageNav.lastPageInRange}">
  <c:choose>
    <c:when test="${pageNav.selectedPage == pageNo}">
      <li class="s1">${pageNo}</li>
    </c:when>
    <c:otherwise>
      <li><a href="${param.urlRoot}&nextPage=${pageNo}">${pageNo}</a></li>
    </c:otherwise>
  </c:choose>
</c:forEach>

<c:if test="${pageNav.extendRight}">
  <li><a href="${param.urlRoot}&nextPage=${pageNav.extendRightPage}" >...</a></li>
</c:if>  

<c:choose>
  <c:when test="${pageNav.onLastPage}">
    <li class="s5">&#8250;</li>
    <li class="s5">&#8250;&#8250;</li>
  </c:when>
  <c:otherwise>
    <li><a href="${param.urlRoot}&nextPage=${pageNav.nextPage}">&#8250;</a></li>
    <li><a href="${param.urlRoot}&nextPage=${pageNav.lastPage}">&#8250;&#8250;</a></li>
  </c:otherwise>
</c:choose>
</ul>
</div>