<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 22/02/11
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<html>
<head><title>DIAMM Search Results</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <script type="text/javascript" src="../_a/s/cufon.min.js"></script>
    <script type="text/javascript" src="../_a/f/agp.font.js"></script>
    <script type="text/javascript" src="../_a/s/c.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-ui.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });
    </script>
</head>
<body id="t2">
<div id="gw">
     <jsp:include page="banner.jsp"/>
    <div id="cs">
        <div class="hdp">
            <h1><c:if test="${resultCount>0}"><strong>${resultCount}</strong> Matching Records</c:if><c:if
                    test="${resultCount==0}"> No Results Found</c:if></h1>
        </div>

        <ul class="utl">
            <li>
                <label>Show matching:</label>
                <c:if test="${resultType==1}">
                    <b class="s1">Items</b> - <a href="SearchManager?op=8&resultType=2">Sources</a>
                </c:if>
                <c:if test="${resultType==2}">
                    <a href="SearchManager?op=8&resultType=1">Items</a> - <b class="s1">Sources</b>
                </c:if>
            </li>
            <li><a href="SearchManager?op=7">Refine Search</a>
            </li>
            <li><a href="SearchManager?op=9">New Search</a>
            </li>
        </ul>

        <%--<form action="SearchManager" name="resultForm" id="resultForm">
            <input type="hidden" name="op" value="8"/>
            <select name="resultType" id="resultType">
                <option value="1" <c:if test="${resultType==1}">selected="selected" </c:if>>Item</option>
                <option value="2" <c:if test="${resultType==2}">selected="selected" </c:if>>Source</option>
            </select>
        </form>--%>
        <ul class="m1">
            <c:forEach items="${itemResults}" var="item">
                <li>


                    <c:if test="${item.folioStart!=null}">${item.folioStart}</c:if>
                    <c:if test="${item.folioStartAlt ne 'null'}">${item.folioStartAlt}</c:if>
                    <c:if test="${item.folioStart!=null&&item.folioEnd!=null}">
                        -
                    </c:if>
                    <c:if test="${item.folioEnd!=null}">${item.folioEnd}</c:if>
                    <c:if test="${item.folioEndAlt ne 'null'}">${item.folioEndAlt}</c:if>
                    : ${item.item.compositionByCompositionkey.compositionName} <c:if
                        test="${item.composerString!=null&&fn:length(item.composerString)>0}">- ${item.composerString}</c:if>
                    <a class="t9 m1" href="Descriptions?op=ITEM&itemKey=${item.key}">View</a>
                </li>
            </c:forEach>
            <c:forEach items="${sourceResults}" var="s">
                <li>
                        ${s.source.shelfmark}
                    <a class="t9 m1" href="Descriptions?op=SOURCE&sourceKey=${s.source.sourcekey}">View</a>

                </li>
            </c:forEach>
        </ul>
        <c:if test="${pageNav !=null}">
            <div class="itp">
                <h3>Listing records ${pageNav.pageFirstIndex} - ${pageNav.pageLastIndex}
                    of ${pageNav.recordCount}</h3>
                <ul>
                    <c:choose>
                        <c:when test="${pageNav.selectedPage > 1}">
                            <li><a href="SearchManager?op=8&pageNum=1">&#8249;&#8249;
                                First</a></li>
                            <li>
                                <a href="SearchManager?op=8&pageNum=${pageNav.selectedPage-1}">
                                    &#8249; Previous</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="s5">&#8249;&#8249; First</li>
                            <li class="s5">&#8249; Previous</li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${pageNav.extendLeft}">
                        <li><a href="SearchManager?op=8&pageNum=${pageNav.firstPage-1}">...</a>
                        </li>
                    </c:if>

                    <c:forEach var="pageNo" begin="${pageNav.firstPage}" end="${pageNav.lastPage}">
                        <c:choose>
                            <c:when test="${pageNav.selectedPage == pageNo}">
                                <li class="s1">${pageNo}</li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="SearchManager?op=8&pageNum=${pageNo}">${pageNo}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${pageNav.extendRight}">
                        <li><a href="SearchManager?op=8&pageNum=${pageNav.lastPage+1}">...</a>
                        </li>
                    </c:if>

                    <c:choose>
                        <c:when test="${pageNav.selectedPage < pageNav.pageCount}">
                            <li>
                                <a href="SearchManager?op=8&pageNum=${pageNav.selectedPage+1}">Next
                                    &#8250;</a></li>
                            <li>
                                <a href="SearchManager?op=8&pageNum=${pageNav.pageCount}">Last
                                    &#8250;&#8250;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="s5">Next &#8250;</li>
                            <li class="s5">Last &#8250;&#8250;</li>
                        </c:otherwise>
                    </c:choose>

                </ul>


            </div>
        </c:if>

    </div>
    <div id="fs"></div>
</div>
</body>
</html>
