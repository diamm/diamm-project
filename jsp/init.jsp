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
<%--<script type="text/javascript" src="../_a/s/jquery-ui.min.js"></script>--%>
<%--<script type="text/javascript" src="../_a/s/jquery-ui-1.8.10.custom.min.js"></script>--%>
<script type="text/javascript" src="../_a/s/jquery.ui.core.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.button.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.tabs.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.position.js"></script>
<script type="text/javascript" src="../_a/s/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="../_a/s/jquery.cluetip.js"></script>
<script type="text/javascript" src="../_a/s/jquery.hoverIntent.js"></script>
<!--<script type="text/javascript" src="../_a/s/c.js"></script>-->
<script type="text/javascript">
    (function($) {
        $.widget("ui.combobox", {
            _create: function() {
                var self = this;
                var select = this.element.hide();
                var input = $("<input>")
                        .insertAfter(select)
                        .autocomplete({
                                          source: function(request, response) {
                                              var matcher = new RegExp(request.term, "i");
                                              response(select.children("option").map(function() {
                                                  var text = $(this).text();
                                                  if (!request.term || matcher.test(text))
                                                      return {
                                                          id: $(this).val(),
                                                          label: text.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + request.term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>"),
                                                          value: text
                                                      };
                                              }));
                                          },
                                          delay: 0,
                                          select: function(e, ui) {
                                              if (!ui.item) {
                                                  // remove invalid value, as it didn't match anything
                                                  $(this).val("");
                                                  return false;
                                              }
                                              $(this).focus();
                                              select.val(ui.item.id);
                                              self._trigger("selected", null, {
                                                  item: select.find("[value='" + ui.item.id + "']")
                                              });

                                          },
                                          minLength: 0
                                      })
                        .addClass("ui-widget ui-widget-content ui-corner-left");
                $("<button><b>View List</b></button>")
                        .insertAfter(input)
                        .button({
                                    icons: {
                                        primary: "ui-icon-triangle-1-s"
                                    },
                                    text: false
                                }).removeClass("ui-corner-all")
                        .addClass("ui-corner-right ui-button-icon")
                        .position({
                                      my: "left center",
                                      at: "right center",
                                      of: input,
                                      offset: "-1 0"
                                  }).css("top", "")
                        .click(function() {
                    // close if already visible
                    if (input.autocomplete("widget").is(":visible")) {
                        input.autocomplete("close");
                        return;
                    }
                    // pass empty string as value to search for, displaying all results
                    input.autocomplete("search", "");
                    input.focus();
                });
            }
        });

    })(jQuery);

    $(function() {
        $("#personSelect,#setSelect,#notationSelect,#provenanceSelect,#clefSelect").combobox();
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        // $("#f1").hide();

        $("#detailTabs").tabs();
        ${detailItem.disablestring}
        <c:if test="${detailItem!=null&&detailItem.disablestring!=null}">
        $("#detailTabs").tabs("option", "disabled", [${detailItem.disablestring}]);
        </c:if>


        $("#imageonlyToggle").change(function() {
            if ($("#imageonlyToggle").attr('checked')) {
                $("#imageonly").val(1);
            } else {
                $("#imageonly").val(0);
            }
            $('#optionForm').submit();
        });

        $("#groupSelect").change(function() {

            $('#optionForm').submit();
        });

        //Notation
       /* $('.devNote').dialog({
            autoOpen:false,
            draggable:true,
            dragStop:function(event, ui) {
                var pos = $(this).position();
                alert(pos.left + ':' + pos.top);
            },
            hide:'explode',
            zIndex: 2000
        });
        $('#n1').dialog("option", "position", ['top','center']);
        $('#n2').dialog("option", "position", ['right','center']);
        $('#n3').dialog("option", "position", ['left','center']);

        $("#noteHelp").dialog({
            autoOpen:false,
            zIndex: 2000,
            buttons: {
                Return: function() {
                    $('.devNote').dialog('close');
                    $(this).dialog('close');
                }
            }
        });
        $('.devNoteTrigger').each(function() {
            var pos = $(this).position();
            var id = $(this).attr('rel');
            //
            // $('#'+id).dialog("option","position",[pos.left,pos.top]);

        });
        $('.devNoteTrigger').click(function() {
            var id = $(this).attr('rel');
            $('#' + id).dialog('open');
            return false;
        });
        $('#allNoteTrigger').click(function() {
            $('.devNote').dialog('open');
            $('#noteHelp').dialog('open');
            return false;
        });

        $('#allNoteClose').click(function() {
            $('.devNote').dialog('close');
            return false;
        });   */




        /*$('#facetAccordion').accordion({
         header:'h4',
         fillSpace: false,
         collapsible:true,
         active:index
         });
         */


    });
</script>

<style>
    .ui-tabs .ui-tabs-hide {
        display: none;
    }

</style>
</head>
<body>
<div id="gw">
<jsp:include page="banner.jsp"/>
<div id="cs">
<div class="c c1" id="searchPane">
<div class="g n2">
<div class="u u1">
    <h2>Search</h2>

    <div class="itl">
        <h3>Search Criteria <a id="empty_controls" href="FacetManager?op=4" class="t9 m4" title="Reset">Reset</a></h3>
        <ul>
            ${searchDescription}
        </ul>
    </div>
    <ul id="facetAccordion" class="nvh">
        <li id="sourceFacet">
            <h3>Source <c:if test="${sourceLabel!=null}">: ${sourceLabel} </c:if> <a
                    href="FacetManager?op=9&FacetType=SOURCEFACET" class="t9 m0 <c:if test="${openFacet ne 'SOURCEFACET'}">s6</c:if> <c:if test="${openFacet eq 'SOURCEFACET'}">s7</c:if>"
                    title="Collapse this Facet">Collapse</a>
            </h3>
            <c:if test="${openFacet eq 'SOURCEFACET'}">
            ${sourceTree}
            </c:if>
        </li>

        <li id="composerFacet">
            <h3>Composer <c:if test="${composerLabel!=null}">: ${composerLabel} </c:if> <a
                    href="FacetManager?op=9&FacetType=COMPOSERFACET" class="t9 m0 <c:if test="${openFacet ne 'COMPOSERFACET'}">s6</c:if> <c:if test="${openFacet eq 'COMPOSERFACET'}">s7</c:if>"
                    title="Collapse this Facet">Collapse</a></h3>
            <c:if test="${openFacet eq 'COMPOSERFACET'}">
            <ul class="nvz">
                <c:forEach var="letter" items="${composerPicker.alphabet}">
                    <c:choose>
                        <c:when test="${composerLetter eq letter}">
                            <li class="s1">${letter}</li>
                        </c:when>
                        <c:when test="${composerPicker.letterSwitch[letter]==true}">
                            <li>
                                <a href="FacetManager?op=1&FacetType=COMPOSERFACET&composerLetter=${letter}">${letter}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="s5">${letter}</li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
            <form class="fms" method="post" action="FacetManager">
                <fieldset class="m0">
                    <input type="hidden" name="op" value="1"/>
                    <input type="hidden" name="FacetType" value="COMPOSERFACET"/>
                    <input type="text" name="composerSearch" value="${composerSearch}"/>
                    <button type="submit" id="composerPicker">Go</button>
                </fieldset>
            </form>
            <ul>
                <c:forEach var="c" items="${Composers}">
                    <li>
                        <label><a
                                href="FacetManager?op=1&FacetType=COMPOSERFACET&composerKey=${c.key}">${c.label}</a></label>
                        <dfn>${c.count}</dfn> <a href="Descriptions?op=COMPOSER&composerKey=${c.key}" class="t9 m1">View
                        Record</a>
                    </li>
                </c:forEach>
            </ul>
            </c:if>
        </li>

        <li id="genreFacet">
            <jsp:include page="genreFacet.jsp"/>
        </li>
        <li id="textFacet">
            <jsp:include page="textFacet.jsp"/>
        </li>
        <li id="secondaryFacet">
            <jsp:include page="secondaryFacet.jsp"/>

        </li>
    </ul>
</div>
<div class="u u2" id="results">
    <h2><strong>${pageNav.recordCount}</strong> Matching Records</h2>
    <ul class="utl">
        <li><label>Display:</label>
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
            records at a time
        </li>
        <li><label>Include:</label>
            <c:choose>
            <c:when test="${imageonly==null||imageonly==0}">
                <b class="s1">All items</b> - <a href="FacetManager?op=1&FacetType=ITEM&imageonly=1">Items with
                Images</a>
            </c:when>
            <c:when test="${imageonly!=null&&imageonly==1}">
            <a href="FacetManager?op=1&FacetType=ITEM&imageonly=0">All items</a> - <b class="s1">Items with Images<b>
                </c:when>
                </c:choose>
        </li>
        <li><label>Group by:</label>

            <form id="optionForm" action="FacetManager">
                <select name="groupType" id="groupSelect">
                    <option
                            <c:if test="${groupType eq 'SOURCE'}">selected="selected"</c:if> value="SOURCE">Source
                    </option>
                    <option
                            <c:if test="${groupType eq 'COMPOSER'}">selected="selected"</c:if> value="COMPOSER">Composer
                    </option>
                    <option
                            <c:if test="${groupType eq 'PROVENANCE'}">selected="selected"</c:if> value="PROVENANCE">
                        Provenance
                    </option>
                    <option
                            <c:if test="${groupType eq 'GENRE'}">selected="selected"</c:if> value="GENRE">Genre
                    </option>
                    <option
                            <c:if test="${groupType eq 'DATE'}">selected="selected"</c:if> value="DATE">Century
                    </option>
                </select>
                <!--<a href="#" class="devNoteTrigger" rel="n1"><img border="0" src="../_a/i/question_mark.png"/></a>
                -->
                <input type="hidden" name="op" value="1"/>
                <input type="hidden" name="FacetType" value="ITEM"/>
                <%--<input type="hidden" id="imageonly" name="imageonly" value="0"/>
                <input type="checkbox" id="imageonlyToggle" name="imageonlytoggle" value=1
                       <c:if test="${imageonly!=null&&imageonly==1}">checked="checked"</c:if>>
                <span>Only show items with images</span>--%>
            </form>
        </li>
    </ul>

    <dl>
        <c:forEach var="group" items="${ItemGroupList}">
            <dt><a ref="#">${group.label}</a></dt>

            <c:forEach var="item" items="${group.itemList}" varStatus="index">
                <dd <c:if test="${index.first}">class="s9"</c:if>>

                    <c:if test="${item.folioStart!=null}">${item.folioStart}</c:if>
                    <c:if test="${item.folioStartAlt ne 'null'}">${item.folioStartAlt}</c:if>
                    <c:if test="${item.folioStart!=null&&item.folioEnd!=null&&item.folioStart!=item.folioEnd}">
                        -
                    </c:if>
                    <c:if test="${item.folioEnd!=null&&item.folioStart!=item.folioEnd}">${item.folioEnd}</c:if>
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
                    <c:if test="${item.images!=null&&fn:length(item.images)>0}">++</c:if>
                    <a class="t9 m1" href="FacetManager?op=6&detailItemKey=${item.key}">View Record</a>
                </dd>
            </c:forEach>

        </c:forEach>
    </dl>

    <div class="itp">
        <c:if test="${pageNav !=null}">
        <h3>Page <strong>${pageNav.selectedPage}</strong> of <strong>${pageNav.pageCount}</strong></h3>

        <ul>
            <c:choose>
                <c:when test="${pageNav.selectedPage > 1}">
                    <li><a href="FacetManager?op=1&pageNum=1">&#8249;&#8249;
                    </a></li>
                    <li>
                        <a href="FacetManager?op=1&pageNum=${pageNav.selectedPage-1}">
                            &#8249;</a></li>
                </c:when>
                <c:otherwise>
                    <li class="s5">&#8249;&#8249;</li>
                    <li class="s5">&#8249;</li>
                </c:otherwise>
            </c:choose>

               <c:if test="${pageNav.extendLeft}">
                                            <li><a href="FacetManager?op=1&pageNum=${pageNav.firstPage-1}">...</a>
                                            </li>
                                        </c:if>

            <li class="s4"><a href="4" target="_self">...</a>
                <ul>
                    <c:choose>
                        <c:when test="${pageNav.pageCount>100}">
                            <c:forEach var="pageNo" step="50" begin="${pageNav.firstPage}" end="${pageNav.pageCount}">
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
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="pageNo" begin="${pageNav.firstPage}" end="${pageNav.pageCount}">
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
                        </c:otherwise>
                    </c:choose>

                </ul>
            </li>

            <c:choose>
                <c:when test="${pageNav.selectedPage < pageNav.pageCount}">
                    <li>
                        <a href="FacetManager?op=1&pageNum=${pageNav.selectedPage+1}">&#8250;</a></li>
                    <li>
                        <a href="FacetManager?op=1&pageNum=${pageNav.pageCount}">&#8250;&#8250;</a></li>
                </c:when>
                <c:otherwise>
                    <li class="s5">&#8250;</li>
                    <li class="s5">&#8250;&#8250;</li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    </c:if>
</div>
</div>

<div class="c c2">
    <jsp:include page="itemdetail.jsp"/>
</div>
</div>
</div>
<div id="f2">

</div>
</div>
</body>
</html>

<%--<div class="c c3">
    <div class="options">
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
                            <dt>ARCHIVE</dt>
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
</div>--%>

