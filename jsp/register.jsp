<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Register with DIAMM</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h3>Register with DIAMM</h3>   
    <c:choose>
      <c:when test="${!registerSuccess}">
        <div>
          <form action="UserAccess?action=register" method="post">
            <div>
              <label>Username</label><span class="msg1">*</span><br />
              <input type="text" class="text" name="username" value="${userDetails.username}" maxlength="20"/>
              <span class="msg2">${errorMessages.username}</span>
            </div>
            <div>
              <label>Name</label><span class="msg1">*</span><br />
              <input type="text" class="text" name="displayName" value="${userDetails.displayName}" maxlength="50"/>
              <span class="msg2">${errorMessages.displayName}</span>
            </div>
            <div>
              <label>Email</label><span class="msg1">*</span><br />
              <input type="text" class="text" name="email" value="${userDetails.email}" maxlength="80"/>
              <span class="msg2">${errorMessages.email}</span>
            </div>
            <div>
              <label>Affiliation</label><span class="sub">(max 200 characters)</span><br />
              <textarea name="affiliation" />${userDetails.affiliation}</textarea>
              <span class="msg2">${errorMessages.affiliation}</span>
            </div>
            <div>
              <input type="submit" name="submit" value="Submit">
              <input type="submit" name="reset" value="Reset">
            </div>
          </form>
        </div>
      </c:when>
      <c:otherwise>
        <div>You have now successfully registered as a DIAMM user and will receive your login details by email.</div>
      </c:otherwise>
    </c:choose>
    
    <div><a href="myDiamm.jsp">Return to Login screen</a></div>
       
  </body>
</html>