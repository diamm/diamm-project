<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 14/03/11
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<div class="t03" id="detailTabs">
    <ul>
        <li>
            <a class="s1" href="#summary">
                <b>Summary</b>
            </a>
        </li>
        <li>
            <a href="#fullItem" title="All available metadata for an item will be displayed here">
                <b>Full Item</b>
            </a>
        </li>
        <li>
            <a href="#incipits" title="thumbnails of all incipits">
                <b>Incipits</b>
            </a>
        </li>
        <li>
            <a href="#texts" title="transcribed text from the composition">
                <b>Text</b>
            </a>
        </li>
        <li>
            <a href="#imageList">
                <b>Image List</b>
            </a>
        </li>
        <li>
            <!--<a href="#" class="devNoteTrigger" rel="n2">
                <img border="0" src="../_a/i/question_mark.png"/>
            </a>-->
        </li>

    </ul>

    <div id="summary" class="s7">
        <c:if test="${detailItem!=null}">
            <dl>
                <c:if test="${detailItem.composition!=null&&fn:length(detailItem.composition.compositionName)>0}">
                    <dt>Composition</dt>
                    <dd>
                        ${detailItem.composition.compositionName}
                </c:if>
                </dd>
                <c:if test="${detailItem.source!=null}">
                    <dt>Archive</dt>
                    <dd>
                        ${detailItem.source.archiveByArchivekey.archivename}: ${detailItem.source.shelfmark}
                    </dd>
                    <c:if test="${detailItem.source.olim!=null&&fn:length(detailItem.source.olim)>0}">
                        <dt>Olim</dt>
                        <dd>
                            ${detailItem.source.olim}
                        </dd>

                    </c:if>
                    <c:if test="${detailItem.source.description!=null&&fn:length(detailItem.source.description)>0}">
                        <dt>Description</dt>
                        <dd>
                            ${detailItem.source.description}
                        </dd>
                    </c:if>
                    <c:if test="${detailItem.source.pagemeasurements!=null&&fn:length(detailItem.source.pagemeasurements)>0}">
                        <dt>Page Measurements</dt>
                        <dd>
                            ${detailItem.source.pagemeasurements}
                        </dd>
                    </c:if>

                    <c:if test="${detailItem.source.notation!=null&&fn:length(detailItem.source.notation)>0}">
                        <dt>Notation</dt>
                        <dd>
                            ${detailItem.source.notation}
                        </dd>
                    </c:if>
                    <c:if test="${detailItem.source.notes!=null&&fn:length(detailItem.source.notes)>0}">
                        <dt>Notes</dt>
                        <dd>
                            ${detailItem.source.notes}
                        </dd>
                    </c:if>
                </c:if>

                <dt>Foliation</dt>
                <dd><c:if test="${detailItem.folioStart!=null}">${detailItem.folioStart}</c:if>
                    <c:if test="${detailItem.folioStartAlt ne 'null'}">${detailItem.folioStartAlt}</c:if>
                    <c:if test="${detailItem.folioStart!=null&&detailItem.folioEnd!=null}">
                        -
                    </c:if>
                    <c:if test="${detailItem.folioEnd!=null}">${detailItem.folioEnd}</c:if>
                    <c:if test="${detailItem.folioEndAlt ne 'null'}">${detailItem.folioEndAlt}</c:if>
                </dd>

                <c:if test="${detailItem!=null&&detailItem.composers!=null}">
                    <dt>Composers</dt>
                    <dd>
                        <ul>
                            <c:forEach var="c" items="${detailItem.composers}">
                                <li>${c.composercomplete}</li>
                            </c:forEach>
                        </ul>
                    </dd>
                </c:if>


                <c:if test="${detailItem!=null&&detailItem.genres!=null}">
                    <dt>Genres</dt>
                    <dd>

                        <ul>
                            <c:forEach var="g" items="${detailItem.genres}">
                                <li>${g.genre}</li>
                            </c:forEach>
                        </ul>

                    </dd>
                </c:if>


            </dl>

        </c:if>
    </div>
    <div id="fullItem" class="s7">
        <jsp:include page="fullitem.jsp"/>
    </div>

    <div id="incipits" class="s7">
        <jsp:include page="incipits.jsp"/>
    </div>

    <div id="texts" class="s7">
        <jsp:include page="texts.jsp"/>
    </div>


    <div id="imageList" class="s7">
        <c:if test="${detailItem!=null&&detailItem.images!=null}">
            <ul>
                <c:forEach var="image" items="${detailItem.images}">
                    <li>
                        <c:if test="${image.filename!=null}">
                            <a href="AnnotationManager?imageKey=${image.imagekey}"><img
                                    src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/></a>
                            <c:if test="${image.copyrightstatement!=null}">${image.copyrightstatement}</c:if>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <h3>Source</h3>
        <c:if test="${detailItem!=null&&detailItem.sourceImages!=null}">
            <ul>
                <c:forEach var="image" items="${detailItem.sourceImages}">
                    <li>
                        <c:if test="${image.filename!=null}">
                            <a href="AnnotationManager?imageKey=${image.imagekey}"><img
                                    src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/></a>
                            <c:if test="${image.copyrightstatement!=null}">${image.copyrightstatement}</c:if>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${detailItem!=null&&detailItem.images==null}}">
            <p>No images available</p>
        </c:if>
    </div>

</div>