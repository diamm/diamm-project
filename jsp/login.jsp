<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Login Form</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h3>Please enter a username and password to login.</h3>
    <div>
      <form action="j_security_check" method="post">
        <div>
          <label>Username</label><br />
          <input type="text" class="text" name="j_username" maxlength="20"/>
        </div>
        <div>
          <label>Password</label><br />
          <input type="password" class="text" name="j_password" maxlength="20"/>
        </div>
        <div>
          <input type="submit" name="login" value="Login">
          <c:if test='${not empty param["retry"]}'>
            <span class="msg2">It was not possible to log in with the supplied username and password</span>
          </c:if>
        </div>
      </form>
    </div>
    
    <div><a href="UserAccess?action=retrievePassword">Forgotten your login details?</a></div>
        
  </body>
</html>