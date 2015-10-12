<%--
  Created by IntelliJ IDEA.
  User: Elliot
  Date: 14/02/11
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<html>
<head><title>Search DIAMM</title>
    <link rel="stylesheet" type="text/css" media="all" href="../_a/c/a.css"/>
<link rel="stylesheet" type="text/css" media="screen, projection" href="../_a/c/s.css"/>
    <script type="text/javascript" src="../_a/s/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery-ui-1.8.10.custom.min.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.cluetip.js"></script>
    <script type="text/javascript" src="../_a/s/jquery.hoverIntent.js"></script>
    <!--<script type="text/javascript" src="../_a/s/c.js"></script>-->
    <script type="text/javascript">

        //Courtesy of http://plugins.jquery.com/project/delayer
        function delayTimer(delay) {
            var timer;
            return function(fn) {
                timer = clearTimeout(timer);
                if (fn)
                    timer = setTimeout(function() {
                        fn();
                    }, delay);
                return timer;
            };
        }
        var inputDelayer = delayTimer(500);
        getAutoComplete = function(input) {
            var id = $(input).attr('name').replace('criteriaInput', '');
            var itemType = $('#criteriaType' + id).val();
            var search = $(input).val();
            if (search.length > 1) {
                $.get("SearchManager", {op:2,iid:id,itemType:itemType,search:search}, function(data) {
                    $('#complete' + id).html(data);
                });
            }
        };
        /*
         source: function( request, response ) {
         // delegate back to autocomplete, but extract the last term
         response( $.ui.autocomplete.filter(
         availableTags, extractLast( request.term ) ) );
         },
         */



        /*applyAuto = function(e) {
         if ($(e).attr('class').indexOf('autoChoice') > -1) {
         var ul = $(e).parent();
         var id = $(ul).attr('id').replace('complete', '');
         var key = $(e).attr('rel').replace('key', '');
         $('#value' + id).val(key);
         $('#criteriaInput' + id).val($(e).html());
         //Clear list
         $(ul).html('');
         }
         };
         */
        validate = function() {
            var reject = 1;
            for (var i = 1; i <=${maxCriteria}; i++) {
                var itemType = $('#criteriaType' + i).val();

                if ($('#value' + i).val().length > 0) {
                    reject = 0;
                    break;
                } else if (itemType.indexOf('SOURCEDESCRIPTION') == 0 || itemType.indexOf('TEXT') == 0) {
                    if ($('#criteriaInput' + i).val().length > 0) {
                        reject = 0;
                        break;
                    }
                }
            }
            if (reject == 1) {
                alert('Select Criteria First');
            }
            return reject;
        };

        resetCriteria = function(id) {
            $('#value' + id).val('');
            $('#criteriaInput' + id).val('');
        };

        redrawCriteria = function(id) {
            var itemType = $('#criteriaType' + id).val();
            if (itemType.indexOf('DATE') > -1) {
                $('#criteriaInput' + id).remove();
                $('#c' + id).append('<select name="century1" id="century1' + id + '">');
                $('#c' + id).append('<select name="century2" id="century1' + id + '">');
                for (var x = 10; x < 18; x++) {
                    $('#century1' + id).append('<option value="' + x + '">' + x + 'th century</option>');
                    $('#century2' + id).append('<option value="' + x + '">' + x + 'th century</option>');
                }
            } else if (itemType.indexOf('LAYOUT') > -1) {
                $('#criteriaInput' + id).remove();
                $('#c' + id).append('<select name="layout" id="layout' + id + '>');
                $('#layout' + id).append('<option value="score">score</option>').append('<option value="parts">parts</option>');
            } else {

                if ($('#criteriaInput' + id).length == 0) {
                    $('#layout' + id).remove();
                    $('#century1' + id).remove();
                    $('#century2' + id).remove();
                    $('#c' + id).append('<input class="autoInput" id="criteriaInput' + id + '" name="criteriaInput' + id + '" value="">');
                }
            }
        };

        $(document).ready(function () {
            $('#resetForm').click(function() {
                for (var x = 1; x < 6; x++) {
                    resetCriteria(x);
                    if (x > 1) {
                        $('#c' + x).hide();
                    }
                }
            });

            $(".autoInput").autocomplete({
            source:  function(request, response) {
                var id = $(this.element).attr('name').replace('criteriaInput', '');
                var itemType = $('#criteriaType' + id).val();
                var search = request.term;
                $.getJSON("SearchManager", {search:search,op:2,iid:id,itemType:itemType}, response);
                },
                    minLength: 2,
            select
                    :
            function(event, ui) {
                $(this).val(ui.item.label);
                var id = $(this).attr('name').replace('criteriaInput', '');
                $('#value'+id).val(ui.item.id);
                /*
                 var id = $(this.element).attr('name').replace('criteriaInput', '');
                $('#value'+id).val(ui.item.value);*/
            }
        })
                ;
            /*$('.autoInput').keyup(function() {
                //inputDelayer(function() {
                var id = $(this).attr('id').replace('criteriaInput', '');
                var itemType = $('#criteriaType' + id).val();
                if (itemType.indexOf('SOURCEDESCRIPTION') < 0 && itemType.indexOf('TEXT') < 0) {
                    getAutoComplete(this);
                }
                //});
            });
            $('#searchManagerForm').click(function(e) {
                if ($(e.target).attr('class')) {
                    applyAuto(e.target);
                }
            });*/
            $('select.criteriaSelector').change(function() {
                var id = $(this).attr('id').replace('criteriaType', '');
                resetCriteria(id);
                redrawCriteria(id);
            });
            $('.hideButton').click(function() {
                var div = $(this).parents('div');
                $(div).attr('class', 'hideClass');
                var id = $(div).attr('id').replace('c', '');
                $('#value' + id).val('');
                $(div).slideUp();
            });
            $('.addButton').click(function() {
                for (var i = 2; i <=${maxCriteria}; i++) {
                    if ($('#c' + i).attr('class').indexOf('hideClass') > -1) {
                        $('#c' + i).hide();
                        var c = $('#c' + i).attr('class').replace('hideClass', '');
                        $('#c' + i).attr('class', c);
                        $('#c' + i).slideDown();
                        break;
                    }
                }
            });
            $('#searchManagerForm').submit(function() {
                var reject = validate();
                if (reject == 1) {
                    return false;
                }
            });
        });
    </script>
    <style type="text/css">
        .hideClass {
            display: none;
        }
    </style>
</head>
<body>
<jsp:include page="banner.jsp"/>
<a href="SearchManager?op=9">Reset Search</a>

<h3>Add Criteria</h3>

<form id="searchManagerForm" action="SearchManager" method="POST">
    <a href="#" class="addButton">Add new search criterion</a>
    <c:forEach var="i" begin="1" end="${maxCriteria}" varStatus="status">
        <div id="c${i}"
             <c:if test="${i>1 and values[i]==null}">class="hideClass"</c:if> >
            <c:choose>
                <c:when test="${i>1}">
                    <a href="#" class="hideButton">X</a>
                    <select id="op${i}" name="op${i}">
                        <option value="0">AND</option>
                        <option value="1">OR</option>
                    </select>
                </c:when>
                <c:otherwise> </c:otherwise>
            </c:choose>
            <select id="criteriaType${i}" name="criteriaType${i}" class="criteriaSelector">
                <c:forEach items="${searchTypes}" var="type">
                    <option value="${type}"
                            <c:if test="${criteriaTypes[i]==type}">selected="selected" </c:if>>${type}</option>
                </c:forEach>
            </select>
            <input class="autoInput" id="criteriaInput${i}" name="criteriaInput${i}"
                   value="<c:if test="${labels[i]!=null}">${labels[i]}</c:if>">
            <input type="hidden" value="<c:if test="${values[i]!=null}">${values[i]}</c:if>" id="value${i}"
                   name="value${i}">
            <ul id="complete${i}"></ul>
        </div>
    </c:forEach>
    <select name="resultType">
        <option value="1" <c:if test="${resultType==1}">selected="selected" </c:if>>Item</option>
        <option value="2" <c:if test="${resultType==2}">selected="selected" </c:if>>Source</option>
    </select>
    <input type="hidden" name="op" value="4"/>
    <input value="Reset" id="resetForm" type="reset">
    <input value="Search" type="submit">
</form>

</body>
</html>