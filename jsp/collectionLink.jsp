<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Add Object to Collection</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
	<div>
	  <form action="CollectionLink" method="post">
	    <h4>Select a collection to add the ${collDetails.linkType} to:</h4>
        <input type="hidden" name="linkId"   value="${collDetails.linkId}">
	    <input type="hidden" name="linkType" value="${collDetails.linkType}">
        <input type="submit" name="link"     value="Add To">
        <select name="collIdToLink"/>
          <c:forEach var="coll" items="${collList}" >
            <option value="${coll.id}" <c:if test="${coll.id==collDetails.defaultCollId}">selected</c:if>>${coll.title}</option>
          </c:forEach>
        </select>
        <span class="msg1">${messages.link}</span>
      </form>
    </div>
	
	<div id="createCollLink">
	  <jsp:include page="collectionCreate.jsp">
	    <jsp:param value="CollectionLink" name="collectionAction" />
	  </jsp:include>
	</div>
	
  </body>
</html>