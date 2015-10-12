<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Change Your DIAMM Password</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h3>Change Your DIAMM Password</h3>   
    <c:choose>
      <c:when test="${!changeSuccess}">
        <div>
          <form action="UserAccess?action=changePassword" method="post">
            <div>
              <label>Username</label><br />
              <input type="text" class="text" name="username" value="${userDetails.username}" readonly/>
            </div>
            <div>
              <label>Current Password</label><span class="msg1">*</span><br />
              <input type="password" class="text" name="currentPassword" value="${userDetails.password}" maxlength="20"/>
            <span class="msg2">${errorMessages.password}</span>
            </div>
            <div>
              <label>New Password</label><span class="msg1">*</span><br />
              <input type="password" class="text" name="newPassword1" value="${userDetails.newPassword1}" maxlength="20"/>
              <span class="msg2">${errorMessages.newPassword1}</span>
            </div>
            <div>
              <label>New Password (re-type)</label><span class="msg1">*</span><br />
              <input type="password" class="text" name="newPassword2" value="${userDetails.newPassword2}" maxlength="20"/>
              <span class="msg2">${errorMessages.newPassword2}</span>
            </div>
            <div>
              <input type="submit" name="submit" value="Submit">
              <input type="submit" name="reset" value="Reset">
            </div>
          </form>
        </div>
      </c:when>
      <c:otherwise>
        <div>You have successfully changed your DIAMM password.</div>
      </c:otherwise>
    </c:choose>
    
    <div><a href="myDiamm.jsp">Return to My Diamm Page</a></div>
       
  </body>
</html>