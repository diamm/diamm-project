<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Bibliography Search</title>
  <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
  <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
  <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
</head>
<body>
<jsp:include page="banner.jsp"/>
<h3>Record View:</h3>${bib.fulltextcalculated}
<hr />
<div>
  <table>
    <c:if test="${not empty bib.booktitle}">
      <tr>
        <th>Book Title:</th>
        <td>${bib.booktitle}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.chapter}">
      <tr>
        <th>Chapter:</th>
        <td>${bib.chapter}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.novolumes}">
      <tr>
        <th>No. of Volumes:</th>
        <td>${bib.novolumes}</td>    
      </tr>
    </c:if>
    <c:if test="${not empty bib.festschrift}">
      <tr>
        <th>Festschrift:</th>
        <td>${bib.festschrift}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.journal}">
      <tr>
        <th>Journal:</th>
        <td>${bib.journal}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.articletitle}">
      <tr>
        <th>Article Title:</th>
        <td>${bib.articletitle}</td>    
      </tr>
    </c:if>
    <c:if test="${not empty bib.vol}">
      <tr>
        <th>Volume:</th>
        <td>${bib.vol}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.dissertation}">
      <tr>
        <th>Dissertation:</th>
        <td>${bib.dissertation}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.degree}">
      <tr>
        <th>Degree:</th>
        <td>${bib.degree}</td>    
      </tr>
    </c:if>
    <c:if test="${not empty bib.university}">
      <tr>
        <th>University:</th>
        <td>${bib.university}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.page}">
      <tr>
        <th>Page:</th>
        <td>${bib.page}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.year}">
      <tr>
        <th>Year:</th>
        <td>${bib.year}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.publisher}">
      <tr>
        <th>Publisher:</th>
        <td>${bib.publisher}</td>    
      </tr>
    </c:if>
    <c:if test="${not empty bib.authorStrictListAsString}">
      <tr>
        <th>Author:</th>
        <td>${bib.authorStrictListAsString}</td>
      </tr>
    </c:if>
    <c:if test="${not empty bib.editorStrictListAsString}">
      <tr>
        <th>Editor:</th>
        <td>${bib.editorStrictListAsString}</td>
      </tr>
    </c:if>
  </table>
</div>  
<hr />

<c:choose>
<c:when test="${parent=='bib'}">

<div>
  <h4>Sources: <c:if test="${not bib.hasSource}">&nbsp;None</c:if></h4>
  <ul>
    <c:forEach items="${bib.archiveToSourceList}" var="archiveToSource">
      <li>${archiveToSource.archiveDetails}
        <ul>
          <c:forEach items="${archiveToSource.sourceList}" var="source">
            <li><a href="FacetManager?op=8&sourceKey=${source.sourcekey}" title="${source.shelfmark}">${source.shelfmark}</a></li>
          </c:forEach>
        </ul>
      </li>
    </c:forEach>
  </ul>
</div>

<hr />

<c:url value="BibliographyDetail" var="bibNavigateUrl">
  <c:param name="parent"             value="bib"/>
  <c:param name="title"              value="${bibSearch.title}"/>
  <c:param name="author"             value="${bibSearch.author}"/>
  <c:param name="year"               value="${bibSearch.year}"/>
  <c:param name="publisher"          value="${bibSearch.publisher}"/>
  <c:param name="totalRecordCount"   value="${recordNav.totalRecordCount}"/>
  <c:param name="bibOriginatingPage" value="${recordNav.selectedPage}"/>
</c:url>

<c:url value="Bibliography" var="bibSearchUrl">
  <c:param name="title"        value="${bibSearch.title}"/>
  <c:param name="author"       value="${bibSearch.author}"/>
  <c:param name="year"         value="${bibSearch.year}"/>
  <c:param name="publisher"    value="${bibSearch.publisher}"/>
  <c:param name="search"       value="Search"/>
  <c:param name="nextPage"     value="${recordNav.selectedPage}"/>
</c:url>

<div>
  <h3>Record ${recordNav.selectedRecord} of ${recordNav.totalRecordCount}</h3> 
  <c:if test="${recordNav.hasMultipleRecords}">
    <jsp:include page="recordNavigator.jsp">
      <jsp:param value="${bibNavigateUrl}" name="urlRoot"/>
    </jsp:include>
  </c:if>
</div>

<div>
  <a href="${bibSearchUrl}" title="Return to bibliography list">Return to bibliography list</a>
</div>

</c:when>

<c:otherwise>
<div>
  <a href="${refererUrl}" title="Return to previous page">Return to previous page</a>
</div>
</c:otherwise>

</c:choose>

</body>
</html>