<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="DiammTags" prefix="d"%>

<div id="hs">
  <div id="bs">
    <h3 title="DIAMM"><a href="/" title="Return to the DIAMM Home Page">DIAMM</a></h3>
    <h4 title="Digital Image Archive of Medieval Music">Digital Image Archive of Medieval Music</h4>
    <div class="gx"> </div>
    <d:userNotLoggedIn>
      <ul class="utl">
        <li><a href="myDiamm.jsp" class="t10 m4">Login</a></li>
        <li class="ix"><a href="UserAccess?action=register" class="t10 m6">Register</a></li>  
      </ul>
    </d:userNotLoggedIn>
    <d:userLoggedIn>
      <ul class="utl">
        <li><label>Hello, <d:userDisplayName/></label></li>
        <li><a href="myDiamm.jsp">MyDIAMM</a></li>
        <li class="ix"><a href="UserAccess?action=logout" class="t10 m5">Logout</a></li>
      </ul>
    </d:userLoggedIn>
  </div>
  <div id="ns">
    <ul class="nvg">
      <li class="i1"><a href="#">Home</a></li>
      <li><a href="#">About</a></li>
      <li><a href="#">Manuscript Database</a></li>
      <li><a href="#">Services</a></li>
      <li><a href="#">Publications</a></li>
      <li class="ix"><a href="#">Help</a></li>
    </ul>
  </div>
</div>