<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 01-Dec-2010
  Time: 13:47:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<html>
<head>
    <title>DIAMM Source</title>

    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="../_a/c/s.css"/>
</head>
<body class="o2">
<jsp:include page="banner.jsp"/>
<div id="wrapper">
    <div>
        <a href="${refererUrl}" title="Return to previous page">Return to previous page</a>
    </div>
    <c:if test="${source!=null}">
        <dl>
            <dt>Shelfmark</dt>
            <dd>${source.s.shelfmark}</dd>

            <c:if test="${source.s.description!=null}">
                <dt>Description</dt>
                <dd>${source.s.description}</dd>
            </c:if>

            <c:if test="${source.s.dateofsource!=null}">
                <dt>Date</dt>
                <dd>${source.s.dateofsource}</dd>
            </c:if>

            <c:if test="${source.s.dedicationtext!=null}">
                <dt>Dedication</dt>
                <dd>${source.s.dedicationtext}</dd>
            </c:if>
        </dl>
    </c:if>

    <c:if test="${set!=null}">
        <h2>${set.set.description}</h2>
        <dl>
            <dt>Shelfmark</dt>
            <dd>${set.set.clustershelfmark}</dd>
            <dt>Set Type</dt>
            <dd>${set.alsettype.description}</dd>
        </dl>
    </c:if>


    <c:if test="${composer!=null}">
        <dl>
            <c:if test="${composer.name!=null}">
                <h2>${composer.name}</h2>
            </c:if>

            <c:if test="${composer.c.datesPublic!=null&&fn:length(compfoser.c.datesPublic)>0}">
                <dt>Date</dt>
                <dd>${composer.c.datesPublic}</dd>
            </c:if>

            <dt>Nationality</dt>
            <dd>?</dd>

            <c:if test="${composer.c.variantspellings!=null&&fn:length(composer.c.variantspellings)>0}">
                <dt>Aliases</dt>
                <dd>${composer.c.variantspellings}</dd>
            </c:if>

            <c:if test="${composer.c.info!=null&&fn:length(composer.c.info)>0}">
                <dt>Biography</dt>
                <dd>${composer.c.info}</dd>
                <c:if test="${composer.c.infoSource!=null&&fn:length(composer.c.infoSource)>0}">
                    <dt>Biography source</dt>
                    <dd>${composer.c.infoSource}</dd>
                </c:if>
            </c:if>
        </dl>
    </c:if>

    <c:if test="${genre!=null}">
        <h2>${genre.g.name}</h2>

    </c:if>

    <c:if test="${archive!=null}">
        <h2>${archive.a.archivename}</h2>
        <c:if test="${archive.a.bannerurl!=null&&fn:length(archive.a.bannerurl)>0}">
            <img src="${archive.a.bannerurl}" alt="">
        </c:if>
        <dl>
            <c:if test="${archive.a.address!=null&&fn:length(archive.a.address)>0}">
                <dt>Address</dt>
                <dd>${archive.a.address}</dd>
            </c:if>
            <c:if test="${archive.a.phone!=null&&fn:length(archive.a.phone)>0}">
                <dt>Telephone</dt>
                <dd>${archive.a.phone}</dd>
            </c:if>
            <c:if test="${archive.a.fax!=null&&fn:length(archive.a.fax)>0}">
                <dt>Fax</dt>
                <dd>${archive.a.fax}</dd>
            </c:if>
            <c:if test="${archive.a.email!=null&&fn:length(archive.a.email)>0}">
                <dt>Email</dt>
                <dd>${archive.a.email}</dd>
            </c:if>
            <c:if test="${archive.a.siglum!=null&&fn:length(archive.a.siglum)>0}">
                <dt>RISM Siglum</dt>
                <dd>${archive.a.siglum}</dd>
            </c:if>
        </dl>
    </c:if>

    <c:if test="${composition!=null}">
        <h2>${composition.composition.compositionName}</h2>
    </c:if>


    <c:if test="${bibs!=null&&fn:length(bibs)>0}">
        <jsp:include page="bibliographyList.jsp"/>
    </c:if>


    <c:if test="${sources!=null}">
        <div id="sources">
            <h3>Sources</h3>
            <ul>
                <c:forEach var="source" items="${sources}">
                    <li>
                        <a href="Descriptions?op=SOURCE&sourceKey=${source.sourcekey}">${source.shelfmark}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <c:if test="${sets!=null}">
        <div id="set">
            <h3>Sets</h3>
            <ul>
                <c:forEach var="s" items="${sets}">
                    <li>
                        <a href="Descriptions?op=SET&setKey=${s.set.setkey}">${s.set.clustershelfmark}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>


    <c:if test="${images!=null}">
    <div id="imageList">
        <ul>
            <c:forEach var="image" items="${images}">
                <li>
                    <c:if test="${image.filename!=null}">
                        <a href="AnnotationManager?imageKey=${image.imagekey}">
                            <img src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/>
                        </a>
                        ${image.folio}
                        <c:if test="${image.copyrightstatement!=null}">${image.copyrightstatement}</c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
        </c:if>
        <c:if test="${source.images==null}}">
            <p>No images available</p>
        </c:if>
    </div>

    <c:if test="${compositions!=null&&fn:length(compositions)>0}">
        <jsp:include page="compositionList.jsp"/>
    </c:if>


    <c:if test="${composers!=null}">
        <div id="sources">
            <h3>Composers</h3>
            <ul>
                <c:forEach var="comp" items="${composers}">
                    <li>
                        <a href="Descriptions?op=COMPOSER&composerKey=${comp.composerkey}">${comp.composercomplete}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <c:if test="${bibs!=null&&fn:length(bibs)>0}">
        <jsp:include page="bibliographyList.jsp"/>
    </c:if>


    <c:if test="${genres!=null&&fn:length(genres)>0}}">
        <div id="sources">
            <h3>Genres</h3>
            <ul>
                <c:forEach var="g" items="${genre.genres}">
                    <li>
                        <a href="Descriptions?op=GENRE&genreKey=${g.algenrekey}">${g.genre}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <c:if test="${items!=null&&fn:length(items)>0}">
        <div id="sources">
            <h3>Items</h3>
            <ul>
                <c:forEach var="i" items="${genre.items}">
                    <li>
                        <a href="Descriptions?op=ITEM&itemKey=${i.itemkey}"></a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>


</div>
</body>
</html>