<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
<link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
<link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
<script type="text/javascript" src="../_a/s/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../_a/s/cufon.min.js"></script>
<script type="text/javascript" src="../_a/f/agp.font.js"></script>
<script type="text/javascript" src="../_a/s/c.js"></script>
<!--
<script type="text/javascript" src="_a/s/jquery-ui-1.8.10.custom.min.js"></script>
-->
<script type="text/javascript" src="../_a/s/jquery.ui.core.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.button.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.position.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.autocomplete.js"></script>
<!--
Typekit stuff
<script type="text/javascript" src="http://use.typekit.com/tae2ufe.js"></script>
<script type="text/javascript">try{Typekit.load();}catch(e){}</script>
-->

</head>
<body id="t2">
  <div id="gw">
    <jsp:include page="banner.jsp"/>
    <div id="cs">
      <div class="hdp">
      <h1>Bibliography Search</h1>
      <form action="Bibliography" method="get">
        <div>
          <label>Title</label><br />
          <input type="text" class="text" name="title" value="${bibSearch.title}"/>
        </div>
        <div>
          <label>Author</label><br />
          <input type="text" class="text" name="author" value="${bibSearch.author}"/>
        </div>
        <div>
          <label>Year</label><br />
          <input type="text" class="text" name="year" value="${bibSearch.year}"/>
        </div>
        <div>
          <label>Publisher</label><br />
          <input type="text" class="text" name="publisher" value="${bibSearch.publisher}"/>
        </div>
        <div>
          <input type="submit" name="search" value="Search"/>
          <input type="submit" name="reset"  value="Reset"/>
        </div>
      </form>
    </div>
  
    <div class="hdp">
      <c:choose>
        <c:when test="${pageNav.hasRecords}">
          <h1>Results <strong>${pageNav.firstRecordInPage} - ${pageNav.lastRecordInPage} of ${pageNav.totalRecordCount}</strong> Matching Records</h1>
        </c:when>
        <c:otherwise>
        </c:otherwise>
      </c:choose> 
    </div>

    <c:forEach items="${bibList}" var="bib" varStatus="rowNum">
	  <c:url value="BibliographyDetail" var="bibDetailUrl">
	    <c:param name="parent"             value="bib"/>
	    <c:param name="title"              value="${bibSearch.title}"/>
	    <c:param name="author"             value="${bibSearch.author}"/>
	    <c:param name="year"               value="${bibSearch.year}"/>
	    <c:param name="publisher"          value="${bibSearch.publisher}"/>
	    <c:param name="totalRecordCount"   value="${pageNav.totalRecordCount}"/>
	    <c:param name="bibOriginatingPage" value="${pageNav.selectedPage}"/>
	    <c:param name="bibPositionInPage"  value="${rowNum.count}"/>
	  </c:url>
      <ul class="m1">
        <li>${bib.fulltextcalculated} <a href="${bibDetailUrl}" class="t9 m1">View Record</a></li>
      </ul>    
    </c:forEach>
    
    <c:if test="${pageNav.hasMultiplePages}">
      <c:url value="Bibliography" var="bibSearchUrl">
	    <c:param name="title"     value="${bibSearch.title}"/>
	    <c:param name="author"    value="${bibSearch.author}"/>
	    <c:param name="year"      value="${bibSearch.year}"/>
	    <c:param name="publisher" value="${bibSearch.publisher}"/>
	    <c:param name="search"    value="Search"/>
	  </c:url>
      <jsp:include page="pageNavigator.jsp">
        <jsp:param value="${bibSearchUrl}" name="urlRoot"/>
      </jsp:include>
    </c:if>

  </div>
  <div id="fs"> </div>
</div>
</body>
</html>
