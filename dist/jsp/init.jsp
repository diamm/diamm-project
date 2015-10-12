<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 29-Jun-2010
  Time: 12:32:04

  op=2&alCountryKey=1&facetType=SOURCEFACET
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>DIAMM</title>

    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="../_a/c/jquery.cluetip.css"/>

    <script type="text/javascript" src="../_a/s/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-ui-1.8.4.custom.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.cluetip.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.hoverIntent.js"></script>
    <script type="text/javascript" src="../_a/s/c.js"></script>

    <style>
        .ui-tabs .ui-tabs-hide {
            display: none;
        }
    </style>
</head>
<body id="f1">
<div id="noteHelp" title="Development Notes">
    <p>
        These notes explain the functionality and purpose of controls on this page not yet activated for the pilot.
    </p>

</div>
<div class="devNote" title="Group By Option" id="n1">
    <p>Groups the search results together. In the prototype, all results are grouped by source.
        The full version will allow grouping by Composer,Genre and Archive.</p>
</div>

<div class="devNote" title="Detail Item View" id="n2">
    <p>A more detailed view of a single composition.</p>

    <p>In the full version, three additional tabs will be available: </p>
    <ul>
        <li>
            <b>Full Item:</b> All available metadata for an item will be displayed here
        </li>
        <li>
            <b>Incipits:</b> thumbnails of all incipits
        </li>
        <li>
            <b>Text:</b> transcribed text from the composition
        </li>
    </ul>
</div>


<div class="devNote" title="Facets" id="n3">
    <p>Other facets available in the full version:</p>
    <ul>
        <li>Text
            <ul>
                <li>Should search across Original Spelling, Modern Spelling,&nbsp; Incipits,&nbsp; Full Text.&nbsp; The
                    user sees a simple text box and cannot choose to limit the scope to one of these fields.
                </li>
            </ul>
        </li>
        <li>Genre
            <ul>
                <li>Composition</li>
            </ul>
        </li>
        <li>Set</li>
        <li>Date of Source
            <ul>
                <li>6 fixed options only, centuries 9th - 15th.&nbsp; Of the two fields in the DB, date of source is to
                    be displayed; start date / end date are the fields to be queried
                </li>
            </ul>
        </li>
        <li>Provenance
            <ul>
                <li>Flat list of 41 geographical areas, JCMcF to tidy up a bit</li>
            </ul>
        </li>
        <li>Notation Type
            <ul>
                <li>Flat list, 79 items or so.&nbsp; No real scope for grouping them</li>
            </ul>
        </li>
        <li>Language
            <ul>
                <li>&#45; Flat list of 10 / 11 items [idea would be to use in combination with text box in the primary
                    facets to distinguish 'amor' in French and Latin]
                </li>
            </ul>
        </li>
        <li>(Person)
            <ul>
                <li>Copyist / Scribe, + flat list of 40 or so types</li>
                <li>Related only to source (unlike composer, which is related at item level)</li>
            </ul>
        </li>

    </ul>
</div>
<div id="gw">
<div id="hs">
    <div id="bs">
        <h3 title="Digital Image Archive of Medieval Music">Digital Image Archive of Medieval
            Music</h3>

        <div class="gx gx1" title="#"/>
    </div>
    <div id="ns"></div>

</div>
<div id="ss"></div>
<div id="cs">

<div class="cg n2">
<div class="m">

<div class="c c1">

<div class="r1" id="facets">

    <div class="hds">
        <h3>Selected Terms</h3>
        <ul class="utl m2">
            <li class="i3">
                <a id="empty_controls" href="FacetManager?op=4" title="Remove all terms">
                    <b>Remove all</b>
                </a>
            </li>


        </ul>
    </div>
    <div class="g n3">
        ${searchDescription}
    </div>
</div>

<%--
<div id="sourceFacet">
<p><a href="FacetManager?op=4&FacetType=SOURCEFACET">Reset</a></p>
--%>
<%--<form action="FacetManager">
    <input type="hidden" name="op" value="1"/>
    <input type="hidden" name="FacetType" value="SOURCEFACET"/>
    <select name="alCountryKey">
        <li><option value="0" <c:if test="${SOURCEFACETSELECTED==null}">selected="selected"</c:if>>All</option></li>
        <c:forEach var="c" items="${Countries}">
            <li><option value="${c.key}" <c:if test="${SOURCEFACETSELECTED==c.key}">selected="selected"</c:if>>${c.label} (${c.count})</option></li>
        </c:forEach>
    </select>
    <input type="submit" value="Search"/>
</form>--%>
<%--
    ${sourceTree}
</div>
<div id="composerFacet">
    <p><a href="FacetManager?op=4&FacetType=COMPOSERFACET">Reset</a></p>
    <form action="FacetManager">
        <input type="hidden" name="op" value="1"/>
        <input type="hidden" name="FacetType" value="COMPOSERFACET"/>
        <select name="composerKey">
            <li><option value="0" <c:if test="${COMPOSERFACETSELECTED==null}">selected="selected"</c:if>>All</option></li>
            <c:forEach var="c" items="${Composers}">
                <li><option value="${c.key}" <c:if test="${COMPOSERFACETSELECTED==c.key}">selected="selected"</c:if>>${c.label} (${c.count})</option></li>
            </c:forEach>
        </select>
        <input type="submit" value="Search"/>
    </form>
</div>
--%>


<div class="r2" id="results">


<!-- hidden fields with useful data -->

<input type="hidden" id="active_ordering" value="default"/>
<!-- end of hidden fields -->


<div class="hds">
<h3>Group by:</h3>

<div class="s03">
    <form id="optionForm" action="FacetManager">
        <select name="groupType" id="groupSelect">
            <option
                    <c:if test="${groupType eq 'SOURCE'}">selected="selected"</c:if> value="SOURCE">Source
            </option>
            <option
                    <c:if test="${groupType eq 'COMPOSER'}">selected="selected"</c:if> value="COMPOSER">Composer
            </option>
            <!--<option value="COMPOSER">Composer</option>
            <option value="">Archive</option>
           <option value="">Country</option>-->
        </select>
        <!--<a href="#" class="devNoteTrigger" rel="n1"><img border="0" src="../_a/i/question_mark.png"/></a>
        -->
        <input type="hidden" name="op" value="1"/>
        <input type="hidden" name="FacetType" value="ITEMFACET"/>
        <input type="hidden" id="imageonly" name="imageonly" value="0"/>
        <input type="checkbox" id="imageonlyToggle" name="imageonlytoggle" value=1
               <c:if test="${imageonly!=null&&imageonly==1}">checked="checked"</c:if>>
        <span>Only show items with images</span>
    </form>


</div>
<div class="rs">

    <dl>
        <c:forEach var="group" items="${ItemGroupList}">
            <dt><a ref="#">${group.label}</a></dt>

            <c:forEach var="item" items="${group.itemList}">
                 <dd>
                        <a class="m2" href="FacetManager?op=6&detailItemKey=${item.key}"></a>

                        <c:if test="${item.folioStart!=null}">${item.folioStart}</c:if>
                        <c:if test="${item.folioStartAlt ne 'null'}">${item.folioStartAlt}</c:if>
                        <c:if test="${item.folioStart!=null&&item.folioEnd!=null}">
                            -
                        </c:if>
                        <c:if test="${item.folioEnd!=null}">${item.folioEnd}</c:if>
                        <c:if test="${item.folioEndAlt ne 'null'}">${item.folioEndAlt}</c:if>
                <c:choose>
                        <c:when test="${groupType eq 'SOURCE'}">
                            :  ${item.item.compositionByCompositionkey.compositionName} <c:if
                                test="${item.composerString!=null&&fn:length(item.composerString)>0}">- ${item.composerString}</c:if>

                        </c:when>
                        <c:when test="${groupType eq 'COMPOSER'}">
                            : ${item.item.compositionByCompositionkey.compositionName}
                            <c:if test="${item.source.archiveByArchivekey!=null&&fn:length(item.source.archiveByArchivekey.archivename)>0}">
                            - ${item.source.archiveByArchivekey.archivename}: ${item.source.shelfmark}
                            </c:if>
                        </c:when>

                </c:choose>
                    </dd>
            </c:forEach>

        </c:forEach>
    </dl>

</div>
<div class="hdp">
    <c:if test="${pageNav !=null}">
        <div class="grid">
            <ul class="utl">
                <li>Show
                    <c:choose>
                        <c:when test="${pageNav.rowsPerPage == 10}">
                            <b class="s1">10</b>
                            -
                            <a href="FacetManager?op=1&rowsPerPage=20&prevRowsPerPage=10">20</a>
                            -
                            <a href="FacetManager?op=1&rowsPerPage=40&prevRowsPerPage=10">40</a>
                        </c:when>
                        <c:when test="${pageNav.rowsPerPage == 20}">
                            <a href="FacetManager?op=1&rowsPerPage=10&prevRowsPerPage=20">10</a>
                            -
                            <b class="s1">20</b>
                            -
                            <a href="FacetManager?op=1&rowsPerPage=40&prevRowsPerPage=20">40</a>
                        </c:when>
                        <c:otherwise>
                            <a href="FacetManager?op=1&rowsPerPage=10&prevRowsPerPage=40">10</a>
                            -
                            <a href="FacetManager?op=1&rowsPerPage=20&prevRowsPerPage=40">20</a>
                            -
                            <b class="s1">40</b>
                        </c:otherwise>
                    </c:choose>
                    records per page
                </li>


            </ul>

        </div>
        <div class="itp m0">
            <h3>Listing records ${pageNav.pageFirstIndex} - ${pageNav.pageLastIndex} of ${pageNav.recordCount}</h3>
            <ul>
                <c:choose>
                    <c:when test="${pageNav.selectedPage > 1}">
                        <li><a href="FacetManager?op=1&pageNum=1">&#8249;&#8249;
                            First</a></li>
                        <li>
                            <a href="FacetManager?op=1&pageNum=${pageNav.selectedPage-1}">
                                &#8249; Previous</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="s5">&#8249;&#8249; First</li>
                        <li class="s5">&#8249; Previous</li>
                    </c:otherwise>
                </c:choose>

                <c:if test="${pageNav.extendLeft}">
                    <li><a href="FacetManager?op=1&pageNum=${pageNav.firstPage-1}">...</a>
                    </li>
                </c:if>

                <c:forEach var="pageNo" begin="${pageNav.firstPage}" end="${pageNav.lastPage}">
                    <c:choose>
                        <c:when test="${pageNav.selectedPage == pageNo}">
                            <li class="s1">${pageNo}</li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="FacetManager?op=1&pageNum=${pageNo}">${pageNo}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${pageNav.extendRight}">
                    <li><a href="FacetManager?op=1&pageNum=${pageNav.lastPage+1}">...</a>
                    </li>
                </c:if>

                <c:choose>
                    <c:when test="${pageNav.selectedPage < pageNav.pageCount}">
                        <li>
                            <a href="FacetManager?op=1&pageNum=${pageNav.selectedPage+1}">Next
                                &#8250;</a></li>
                        <li>
                            <a href="FacetManager?op=1&pageNum=${pageNav.pageCount}">Last
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
</div>
</div>
</div>
</div>

<div class="c c2">
    <h3>Refine Search </h3><a href="#" class="devNoteTrigger" rel="n3"><img border="0" src="../_a/i/question_mark.png"/></a>
    <ul id="facetAccordion" class="accordion nvh m1">
        <li id="sourceFacet" class="s7">
            <h4 class="to_collapse">
                <a class="t9 m1" title="" href="#">Collapse Facet: Source</a>
                <strong>Source</strong>
            </h4>
            ${sourceTree}
        </li>

        <li id="composerFacet" class="s7">
            <h4 class="to_collapse">
                <a class="t9 m1" title="" href="#">Collapse Facet: Composer</a>
                <strong>Composer</strong>
            </h4>

            <ul class="nvz">
                <c:forEach var="letter" items="${composerPicker.alphabet}">
                    <c:if test="${composerPicker.letterSwitch[letter]==true}">
                        <li><a href="FacetManager?op=1&FacetType=COMPOSERFACET&composerLetter=${letter}">${letter}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>

            <ul>
                <c:forEach var="c" items="${Composers}">
                    <li>
                        <a href="FacetManager?op=1&FacetType=COMPOSERFACET&composerKey=${c.key}">${c.label}</a>&nbsp;(${c.count})
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>

</div>

<div class="c c3">
<div class="options">
<div class="t03" id="detailTabs">
<ul>
    <li>
        <a class="s1" href="#summary">
            <b>Summary</b>
        </a>
    </li>
    <li>
        <a href="#" title="All available metadata for an item will be displayed here">
            <b>Full Item</b>
        </a>
    </li>
    <li>
        <a href="#" title="thumbnails of all incipits">
            <b>Incipits</b>
        </a>
    </li>
    <li>
        <a href="#" title="transcribed text from the composition">
            <b>Text</b>
        </a>
    </li>
    <li>
        <a href="#imageList">
            <b>Image List</b>
        </a>
    </li>
    <li>
        <a href="#" class="devNoteTrigger" rel="n2">
            <img border="0" src="../_a/i/question_mark.png"/>
        </a>
    </li>

</ul>

<div id="summary">
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
                <c:if test="${detailItem.source.provenance!=null&&fn:length(detailItem.source.provenance)>0}">
                    <dt>Provenance</dt>
                    <dd>
                            ${detailItem.source.provenance}
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
<div id="imageList">
    <c:if test="${detailItem!=null&&detailItem.images!=null}">
        <ul>
            <c:forEach var="image" items="${detailItem.images}">
                <li>
                    <c:if test="${image.filename!=null}">
                        <img src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/>
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
</div>
</div>
</div>
</div>

</div>
<div id="fs">
    <ul>
        <li class="i1">
            <a href="#">Contacts</a>
        </li>
        <li>
            <a id="allNoteTrigger" href="#" title="View Notes">Help</a>
        </li>


    </ul>
</div>

</body>
</html>
<%--<div id="itemFacet">
    <p>Total item count: ${ItemCount}</p>
    <c:if test="${not empty COMPOSERFACETSELECTED}">
    <p>${searchDescription}</p>
    </c:if>
    <p><a href="FacetManager?op=4">Start New Search</a></p>
    <ul>
        <c:forEach var="item" items="${ItemList}">
            <li>${item.itemkey}: ${item.label}</li>
        </c:forEach>
    </ul>
</div>--%>