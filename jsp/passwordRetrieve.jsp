<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Retrive Username and Password</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h3>Retrieve your username and password</h3>
    <c:choose>
      <c:when test="${!retrieveSuccess}">
        <div>To receive a reminder of your login details, enter the e-mail address you registered with, and click 'Submit'. </div>
        <div>
          <form action="UserAccess?action=retrievePassword" method="post">
            <div>
              <label>Email</label>
              <input type="text" class="text" name="email" value="${userDetails.email}" maxlength="80"/>
              <span class="msg2">${errorMessages.email}</span>
            </div>
            <div>
              <input type="submit" name="submit" value="Submit">
            </div>
          </form>
        </div>
      </c:when>
      <c:otherwise>
        <div>Your DIAMM username and a new password will be sent to you by email.</div>
      </c:otherwise>
    </c:choose>        
    
    <div><a href="myDiamm.jsp">Return to Login screen</a></div>
    
  </body>
</html>