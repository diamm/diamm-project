<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 16-Nov-2010
  Time: 10:30:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<c:if test="${detailItem!=null}">

<c:if test="${detailItem.composition!=null}">
    <dl>
        <c:if test="${detailItem.item!=null&&fn:length(detailItem.item.piecenumber)>0}">
            <dt class=”x3”>Piece: ${detailItem.item.piecenumber}</dt>
            <dd>&nbsp;</dd>
        </c:if>
        <c:if test="${detailItem.numfolios>0}">
            <dt>Number folios</dt>
            <dd>
                    ${detailItem.numfolios}
                <c:if test="${detailItem.composition!=null&&fn:length(detailItem.composition.compositionName)>0}">
                </c:if>
            </dd>
        </c:if>
        <c:if test="${detailItem.composition.maxnumberofvoices>0}">
            <dt>Number of voices</dt>
            <dd>
                    ${detailItem.composition.maxnumberofvoices}
            </dd>
        </c:if>
        <c:if test="${detailItem.genres!=null}">
            <dt>Genre</dt>
            <dd>
                <ul>
                    <c:forEach var="g" items="${detailItem.genres}">
                        <li>${g.genre}</li>
                    </c:forEach>
                </ul>
            </dd>
        </c:if>
        <!--<dt>Motet parts</dt>
        <dd></dd>-->

    </dl>
</c:if>


<c:if test="${detailItem.composers!=null}">
    <dl>
        <dt class=”x3”>Composer</dt>
        <dd>&nbsp;</dd>
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

        <!--<dt>Attribution</dt>-->
        <!--<dd></dd>-->
        <!--<dt>Original attribution</dt>-->
        <!--<dd></dd>        -->
        <!--<dt>Conflicting attributions</dt>-->

    </dl>
</c:if>

<c:if test="${detailItem.source!=null}">
    <dl>
        <dt class=”x3”>Dating</dt>
        <dd>&nbsp;</dd>
        <c:if test="${detailItem.source.startdate>0}">
            <dt>Start</dt>
            <dd>${detailItem.source.startdate}th century</dd>
        </c:if>
        <c:if test="${detailItem.source.enddate>0}">
            <dt>End</dt>
            <dd>${detailItem.source.enddate}th century</dd>
        </c:if>
        <c:if test="${detailItem.item.datecomposed>0}">
            <dt>Composition date</dt>
            <dd>${detailItem.item.datecomposed}</dd>
        </c:if>
        <c:if test="${detailItem.item.datecopied>0}">
            <dt>Copying date</dt>
            <dd>${detailItem.item.datecopied}</dd>
        </c:if>
    </dl>
</c:if>


<c:if test="${detailItem.item!=null&&detailItem.source!=null&&detailItem.source.alnotationtypes!=null}">
    <dl>
        <dt class=”x3”>NOTATIONAL AND SCRIBAL INFORMATION:</dt>
        <dd>&nbsp;</dd>
        <c:if test="${detailItem.item.copyingstyle!=null&&fn:length(detailItem.item.copyingstyle)>0}">
            <dt>Copying style</dt>
            <dd>${detailItem.item.copyingstyle}</dd>
        </c:if>
        <c:if test="${detailItem.item.corrections!=null&&fn:length(detailItem.item.corrections)>0}">
            <dt>Corrections</dt>
            <dd>${detailItem.item.corrections}</dd>
        </c:if>
        <c:if test="${detailItem.item.restligatureconfig!=null&&fn:length(detailItem.item.restligatureconfig)>0}">
            <dt>Rest & ligature configuration</dt>
            <dd>${detailItem.item.restligatureconfig}</dd>
        </c:if>
            <%--<c:if test="${detailItem.item.restligatureconfig!=null&&fn:length(detailItem.item.restligatureconfig)>0}">--%>
        <!--<dt>Scribe publisher</dt>-->
        <!--<dd></dd>-->
            <%--</c:if>--%>
            <%--<c:if test="${detailItem.item.restligatureconfig!=null&&fn:length(detailItem.item.restligatureconfig)>0}">--%>
        <!--<dt>Comments on hands</dt>-->
        <!--<dd></dd>-->
            <%--</c:if>--%>
    </dl>

</c:if>

<c:if test="${detailItem.source!=null}">
    <dl>
        <dt class=”x3”>DEDICATION</dt>
        <dd>&nbsp;</dd>
        <c:if test="${detailItem.people!=null}">
            <c:forEach var="p" items="${detailItem.people}">
                <dt>Dedicatee</dt>
                <dd>${p.fullname}</dd>
            </c:forEach>
        </c:if>

        <dt>Dedicator</dt>
        <dd>&nbsp;</dd>

        <dt>Establishment patron</dt>
        <dd>&nbsp;</dd>

        <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.dedicationtext)>0}">
            <dt>Dedication text</dt>
            <dd>${detailItem.source.dedicationtext}</dd>
        </c:if>
    </dl>
</c:if>


<c:if test="${detailItem.provs!=null}">
    <dl>

        <dt class=”x3”>PROVENANCE</dt>
        <dd>&nbsp;</dd>
        <c:forEach var="p" items="${detailItem.provs}">
            <c:if test="${p.country!=null&&fn:length(p.country)>0}">
                <dt>Country</dt>
                <dd>${p.country}</dd>
            </c:if>
            <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.dedicationtext)>0}">
                <dt>Region</dt>
                <dd>${p.region}</dd>
            </c:if>
            <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.dedicationtext)>0}">
                <dt>City</dt>
                <dd>${p.city}</dd>
            </c:if>
        </c:forEach>

        <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.provenancecitation)>0}">
            <dt>Citation</dt>
            <dd>${detailItem.source.provenancecitation}</dd>
        </c:if>
        <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.provenancecomment)>0}">
            <dt>Comments</dt>
            <dd>${detailItem.source.provenancecomment}</dd>
        </c:if>

        <c:if test="${detailItem.source!=null&&fn:length(detailItem.source.liminarytext)>0}">
            <dt>Liminary text</dt>
            <dd>${detailItem.source.liminarytext}</dd>
        </c:if>

    </dl>
</c:if>

</c:if>