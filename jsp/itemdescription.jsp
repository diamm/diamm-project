<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 22/02/11
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<html>
<head><title>Item Description</title>
    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="../_a/c/s.css"/>
    <script type="text/javascript" src="../_a/s/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-ui.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            // $("#f1").hide();
            $("#detailTabs").tabs();
        });
    </script>
</head>
<body>
 <jsp:include page="itemdetail.jsp"/>
</body>
</html>