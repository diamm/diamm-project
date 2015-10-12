<%--
  Created by IntelliJ IDEA.
  User: Elliott Hall
  Date: 13-Dec-2010
  Time: 17:12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<html>
<head>
    <title>DIAMM Image Viewer</title>
    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/omniviewer.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/cch.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s2.css"/>
    <script type="text/javascript" src="../_a/f/agp.font.js"></script>
    <script type="text/javascript" src="../_a/s/c.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-ui-1.8.5.custom.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.jeditable.mini.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.omniviewer-wdgt.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.mousewheel.js"></script>
    <script src="../_a/s/jquery.dimensions.min.js"></script>

    <script type="text/javascript">
        var hideControls = function() {
            $("#closeComparison").hide();
            $("#mirror").hide();
        };

        var showControls = function() {
            $("#closeComparison").show();
            $("#mirror").show();
        };


        // The iipsrv server path (/fcgi-bin/iipsrv.fcgi by default)
        var server = 'http://<%=request.getServerName()%>:<%=request.getServerPort()%>/diamm2/jsp/ImageProxy';
        // The *full* image path on the server. This path does *not* need to be in the web
        // server root directory. On Windows, use Unix style forward slash paths without
        // the "c:" prefix
        var images = ['${image.filename}'];
        //var images = '/Users/56k/phd/code/mooviewer-port/moov-demo/cch_image.tif';
        // Copyright or information message
        var credit = '<c:if test="${image.copyrightstatement!=null}">${image.copyrightstatement}</c:if>';
        var ow1;
        var ow2;
        $(document).ready(function() {
            //Courtesy of Mika Tuupola at http://www.appelsiini.net/projects/jeditable
            $('.editable').editable('AnnotationManager', {
                type: 'textarea',
                tooltip: 'click to edit',
                id   : 'noteid',
                name : 'noteText',
                cancel    : 'Cancel',
                submit    : 'Save'
            });
            $(".editable_select").editable('AnnotationManager', {
                //indicator : '<img src="img/indicator.gif">',
                data   : "{'Public':'1','Private':'2'}",
                type   : "select",
                submit : "OK",
                id   : 'noteid',
                name: 'noteVisibilityId',
                style  : "inherit",
                submitdata : function() {
                    return {id : 2};
                }
            });
            $("#closeComparison").click(function() {
                //Remove second window
                //todo:  change first resolution?
                $("#ow_2").OmniViewer("destroy");
                ow2 = null;
                //$("#ow_1").after("<div id=\"ow_2\"></div>");
                hideControls();
            });
            hideControls();
            $('li a.openAlt').click(function() {
                var fName = $(this).attr('rel');
                if (fName) {
                    if (ow2) {
                        $("#ow_2").OmniViewer("destroy");
                    }
                    ow2 = $("#ow_2").OmniViewer({
                        image: [fName]
                        ,server: server
                        ,credit: credit
                        ,zoom: 1
                        ,render: 'random'
                        ,showNavButtons: true
                        ,showNavigation: true
                        ,debug : false
                        ,fileFormat: "zoomify"
                        ,mode : null
                    }).css("width", "400px").css("height", "500px").css("float", "left").css("margin-right", "50px");
                }
                showControls();
                return false;
            });
            var ow1 = $("#ow_1").OmniViewer({
                image: images
                ,server: server
                ,credit: credit
                ,zoom: 1
                ,render: 'random'
                ,showNavButtons: true
                ,showNavigation: true
                ,debug : false
                ,fileFormat: "zoomify"
                ,mode : null
            }).css("width", "400px").css("height", "500px").css("float", "left").css("margin-right", "50px");


            $('#mirror').bind("click", function() {
                var status = $(this).data('mirror');
                // do stuff

                if (typeof status == 'undefined') {
                    $(this).text('Unsynch');
                    status = true;
                    $("#ow_2").OmniViewer({
                        zoomIn:function(event, data) {
                            ow1.OmniViewer("zoomIn");
                        }
                        ,zoomOut:function(event, data) {
                            ow1.OmniViewer("zoomOut");
                        }
                        ,scrollTo:function(event, data) {
                            ow1.OmniViewer("scrollTo", data.dx, data.dy);
                        }
                    });
                }
                else if (status) {
                    status = false;
                    $(this).text('Synch');
                    $("#ow_2").OmniViewer({
                        zoomIn:null
                        ,zoomOut:null
                        ,scrollTo:null
                    });
                }
                else if (!status) {
                    status = true;
                    $(this).text('Unsynch');
                    $("#ow_2").OmniViewer({
                        zoomIn:function(event, data) {
                            ow1.OmniViewer("zoomIn");
                        }
                        ,zoomOut:function(event, data) {
                            ow1.OmniViewer("zoomOut");
                        }
                        ,scrollTo:function(event, data) {
                            ow1.OmniViewer("scrollTo", data.dx, data.dy);
                        }
                    });
                }
                $(this).data('mirror', status);
            })


        });


    </script>
</head>
<body id="t3">
<div id="gw">
    <jsp:include page="banner.jsp"/>
    <div id="cs">
        <div class="hdp">
            <h1>${source.shelfmark}: ${image.folio}</h1>
            <ul class="itp">
                <c:if test="${prev==null}">
                    <li class="s5">&#8249; ${prev.folio}</li>
                </c:if>
                <c:if test="${prev!=null}">
                    <li><a href="AnnotationManager?imageKey=${prev.imagekey}">&#8249; ${prev.folio}</a></li>
                </c:if>
                <c:if test="${sourceImages!=null}">
                    <c:choose>
                        <c:when test="${fn:length(sourceImages)>20}">
                            <li class="s4"><a href="4" target="_self">...</a>
                                <ul>
                                    <c:forEach var="i" items="${sourceImages}" varStatus="c">
                                        <c:choose>
                                            <c:when test="${i.orderno==image.orderno}">
                                                <li class="s1">${c.count} </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="AnnotationManager?imageKey=${i.imagekey}">${c.count}</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="i" items="${sourceImages}" varStatus="c">
                                <c:choose>
                                    <c:when test="${i.orderno==image.orderno}">
                                        <li class="s1">${c.count} </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="AnnotationManager?imageKey=${i.imagekey}">${c.count}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>

                    </c:choose>
                </c:if>

                <c:if test="${next!=null}">
                    <li><a href="AnnotationManager?imageKey=${next.imagekey}">${next.folio} &#8250;</a></li>
                </c:if>
                <c:if test="${next==null}">
                    <li class="s5">${next.folio} &#8250;</li>
                </c:if>
            </ul>
        </div>
        <div class="imb">
            <div class="utl" id="windowControls">
                <h3>Show a Second Image</h3>
                <ul>
                    <li>
                        <label>Open for comparison:</label>

                        <c:if test="${prev!=null&&prev.folio!=null}">
                            <a class="openAlt" rel="${prev.filename}"
                               href="AnnotationManager?imageKey=${prev.imagekey}">previous
                                page ${prev.folio}</a>
                        </c:if>
                        <c:if test="${next!=null&&next.folio!=null}">
                            - <a class="openAlt" rel="${next.filename}"
                                 href="AnnotationManager?imageKey=${next.imagekey}">next
                            page ${next.folio}</a>
                        </c:if>
                        <c:if test="${seconds!=null}">
                            <c:forEach var="s" items="${seconds}">
                                - <a class="openAlt" rel="${s.filename}"
                                     href="AnnotationManager?imageKey=${s.imagekey}">${s.caption}</a>

                            </c:forEach>
                        </c:if>
                    </li>
                    <li><a href="#" id="mirror" class="t10 m0 s5">Synchronise Pan and Scroll</a></li>
                    <li><a href="#" id="closeComparison" class="t10 m1">Close second image</a></li>
                </ul>
            </div>
            <div id="ow_1" class="u u1"></div>
            <div id="ow_2" class="u u2"></div>
        </div>
        <h3>Image Metadata</h3>
        <img src="hdr-logo.gif" alt="" width="317" height="46" class="m0"/>
        <dl class="m0">
            <c:if test="${image.copyrightstatement!=null}">
                <dt>Image copyright:</dt>
                <dd> ${image.copyrightstatement}</dd>
            </c:if>
            <dt>Source Path:</dt>
            <dd><a href="#">Oxford</a> &#8250; <a href="#">All Souls College</a> &#8250; <a href="#">MS 330</a> &#8250;
                <strong>19r</strong></dd>
        </dl>
        <h3>Annotations</h3>
        <ul class="itl m0">
            <c:forEach var="pbComm" items="${pbCommList}">

                <li><i class="t9 m0">Comment</i> <i class="t9 m1 editable_select">Public</i>
                    <label>${pbComm.user.displayName} (${pbComm.dateDisplay})</label>
                        <%--<a href="#" class="t10 m2">Edit</a> <a href="#" class="t10 m3">Delete</a>--%>
                    <p>${pbComm.notetext}</p>
                </li>
            </c:forEach>
            <c:if test="${user!=null}">
            <c:forEach var="pvComm" items="${pvCommList}">
            <li><i class="t9 m0">Comment</i> <i class="t9 m1">Private</i>
                <label>Your private comment (${pvComm.dateDisplay})</label>

                <p class="editable" id="note_${pvComm.noteKey}">${pvComm.notetext}</p>
                </c:forEach>
                </c:if>

                <c:forEach var="pbTran" items="${pbTranList}">
            <li><i class="t9 m0">Transcription</i> <i class="t9 m1">Public</i>
                <label>${pbTran.user.displayName} (${pbTran.dateDisplay})</label>
                    <%--<a href="#" class="t10 m2">Edit</a> <a href="#" class="t10 m3">Delete</a>--%>
                <p class="editable" id="note_${pbTran.noteKey}">${pbTran.notetext} </p>
            </li>
            </c:forEach>
            <c:if test="${user!=null}">
                <c:forEach var="pvTran" items="${pvTranList}">
                    <li><i class="t9 m0">Transcription</i> <i class="t9 m1">Private</i>
                        <label>${pvTran.user.displayName} (${pvTran.dateDisplay})</label>
                            <%--<a href="#" class="t10 m2">Edit</a> <a href="#" class="t10 m3">Delete</a>--%>
                        <p class="editable" id="note_${pvTran.noteKey}">${pvTran.notetext} </p>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${user!=null}">
                <h3>Add new note</h3>

                <form action="AnnotationManager" method="POST">
                    <input type="hidden" name="op" value="1"/>
                    <input type="hidden" name="imageKey" value="${image.imagekey}"/>

                    <div>
                        <select name="noteType">
                            <option value="COM" selected="selected">Comment</option>
                            <option value="TRA">Transcription</option>
                        </select>
                        <input type="radio" name="noteVis" value="PV" checked/> Private
                        <input type="radio" name="noteVis" value="PB"/> Public
                    </div>
                    <div>
                        <textarea rows="4" cols="20" name="noteText"></textarea>
                    </div>
                    <div>
                        <input type="submit" name="create" value="Add New Note">
                    </div>
                </form>
            </c:if>
        </ul>

    </div>
</div>
<div id="fs"></div>

</body>
</html>


<%--<h3>Comments</h3>
            <dl>
                <c:forEach var="pbComm" items="${pbCommList}">
                    <dt>${pbComm.user.displayName} (${pbComm.dateDisplay})</dt>
                    <dd>${pbComm.notetext}</dd>
                </c:forEach>
                <c:if test="${user!=null}">
                    <c:forEach var="pvComm" items="${pvCommList}">
                        <dt>Your private comment (${pvComm.dateDisplay})</dt>
                        <dd class="editable" id="${pvComm.noteKey}">${pvComm.notetext}</dd>
                    </c:forEach>
                </c:if>

                <dt>Test comment</dt>
                <dd class="editable" id="note_455">This is a test</dd>
            </dl>

            <h3>Transcriptions</h3>
            <dl>
                <c:forEach var="pbTran" items="${pbTranList}">
                    <dt>${pbTran.user.displayName} (${pbTran.dateDisplay})</dt>
                    <dd>${pbTran.notetext}</dd>
                </c:forEach>
                <c:if test="${user!=null}">
                    <c:forEach var="pvTran" items="${pvTranList}">
                        <dt>Your private transcription (${pvTran.dateDisplay})</dt>
                        <dd>${pvTran.notetext}</dd>
                    </c:forEach>
                </c:if>
            </dl>



</body>
</html>--%>