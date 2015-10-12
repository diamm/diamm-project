<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Collection Management</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h2>Collection Management</h2>
    <hr />

	<div style="border: 2px black solid; padding:5px;">
	  <span class="heading">Collection List</span>
	  <c:if test="${mode eq 'modeEdit'}"><a class="small" href="CollectionManage">Create New Collection</a></c:if>
	  <table class="coll">
	    <tr>
	      <th>Title</th>
	      <th>Description</th>
	      <th></th>
	      <th></th>
	    </tr>
	    <c:forEach items="${collList}" var="coll">
	      <c:url value="CollectionManage" var="collectionEditUrl">
	        <c:param name="edit" value="edit"/>
	        <c:param name="origCollId" value="${coll.id}"/>
	      </c:url>
	      <c:url value="CollectionManage" var="collectionFullDetailsUrl">
	        <c:param name="fullDetails" value="fullDetails"/>
	        <c:param name="origCollId" value="${coll.id}"/>
	      </c:url>
	      <tr>
	        <td class="title">${coll.title}</td>
	        <td class="desc">${coll.description}</td>
	        <td class="action"><a href="${collectionEditUrl}">edit</a></td>
	        <td class="action"><a href="${collectionFullDetailsUrl}">view</a></td>
	      </tr>
	    </c:forEach>
	  </table>
	</div>
	
	<c:choose>
      <c:when test="${empty mode or mode eq 'modeCreate'}">
	    <div>
	      <jsp:include page="collectionCreate.jsp">
	        <jsp:param value="CollectionManage" name="collectionAction" />
	      </jsp:include>
	    </div>	
      </c:when>
      <c:otherwise>
        <div style="margin-top:20px; border: 2px black solid; padding:5px;">
          <span class="heading">Edit Collection</span>
		  <c:choose>
	        <c:when test="${empty origColl}">
			  <div>None Selected</div>
			</c:when>
            <c:otherwise>
			  <form action="CollectionManage" method="post">
			    <div style="position: relative; height:50px; width: 800px; margin-top:10px;">
                  <div style="position: absolute; left: 0px; top: 0px;">
	                <label>Original Title</label><br />
	                <input type="text" class="text" name="origCollTitle" value="${origColl.title}" disabled="disabled" />
	              </div>
	              <div style="position: absolute; left: 210px; top: 0px">
	                <label>Original Description</label><br />
	                <input type="text" class="text2" name="origCollDescription" value="${origColl.description}" disabled="disabled" />
	              </div>
	            </div>
	            <div style="position: relative; height:50px; width: 800px;">
                  <div style="position: absolute; left: 0px; top: 0px">
	                <label>New Title</label><span class="msg2">${messages.title}</span><br />
	   		        <input type="text" class="text" name="editedCollTitle" value="${collDetails.editedCollTitle}"/>
	              </div>
	              <div style="position: absolute; left: 210px; top: 0px">
	                <label>New Description</label><br />
			        <input type="text" class="text2" name="editedCollDescription" value="${collDetails.editedCollDescription}"/>
	              </div>
	            </div>
			    <div>
			      <input type="hidden" name="origCollId" value="${origColl.id}">
			      <input type="submit" name="save"       value="Update">
			      <input type="submit" name="delete"     value="Delete Collection">
			      <span class="msg1">${messages.edit}</span>
			    </div>
			  </form>
		    </c:otherwise>
	      </c:choose>
		</div>
	    <div style="margin-top:20px; border: 2px black solid; padding:5px;">
	      <span class="heading">Objects in the Collection</span>
		  <c:choose>
		    <c:when test="${empty origColl.sourceList and empty origColl.itemList and empty origColl.imageList}">
		      <div>This collection is empty</div>
		    </c:when>
		    <c:otherwise>
		    
		      <c:if test="${not empty origColl.sourceList}">
			    <table class="coll">
			      <tr><th colspan="2">Sources</th></tr>
			        <c:forEach items="${origColl.sourceList}" var="source">
			          <c:url value="CollectionManage" var="unlinkUrl">
		                <c:param name="unlink"     value="unlink"/>
		                <c:param name="origCollId" value="${origColl.id}"/>
		                <c:param name="unlinkId"   value="${source.sourcekey}"/>
		                <c:param name="unlinkType" value="source"/>
		              </c:url>
			          <tr>
			            <td class="action"><a href="${unlinkUrl}">remove</a></td>
			            <td class="default">${source.shelfmark}</td>
			          </tr>
			        </c:forEach>
			    </table>  
			  </c:if>
			  
			  <c:if test="${not empty origColl.itemList}">
			    <table class="coll">
			      <tr><th colspan="2">Items</th></tr>
			        <c:forEach items="${origColl.itemList}" var="item">
			          <c:url value="CollectionManage" var="unlinkUrl">
		                <c:param name="unlink"     value="unlink"/>
		                <c:param name="origCollId" value="${origColl.id}"/>
		                <c:param name="unlinkId"   value="${item.itemkey}"/>
		                <c:param name="unlinkType" value="item"/>
		              </c:url>
			          <tr>
			            <td class="action"><a href="${unlinkUrl}">remove</a></td>
			            <td class="default">
			              <c:if test="${not empty item.compositionByCompositionkey.compositionName}">
							Composition: ${item.compositionByCompositionkey.compositionName}<br />
						  </c:if>
					   	  <c:if test="${not empty item.sourceBySourcekey}">
						    Archive: ${item.sourceBySourcekey.archiveByArchivekey.archivename}; ${item.sourceBySourcekey.shelfmark}<br />
						  </c:if>				
						  <c:if test="${not empty item.sourceBySourcekey.olim}">
						    Olim: ${item.sourceBySourcekey.olim}<br />
						  </c:if>				
						  <c:if test="${not empty item.sourceBySourcekey.description}">
						    Description: ${item.sourceBySourcekey.description}<br />
						  </c:if>					         
						  <c:if test="${not empty item.sourceBySourcekey.pagemeasurements}">
						    Page Measurements: ${item.sourceBySourcekey.pagemeasurements}<br />
						  </c:if>						
						  <c:if test="${not empty item.sourceBySourcekey.notation}">
						    Notation: ${item.sourceBySourcekey.notation}<br />
						  </c:if>					
						  <c:if test="${not empty item.sourceBySourcekey.notes}">
						    Notes: ${item.sourceBySourcekey.notes}<br />
						  </c:if>				
						  Foliation:
						  <c:if test="${item.folioStart!=null}">${item.folioStart}</c:if>
						  <c:if test="${item.folioStartAlt ne 'null'}">${item.folioStartAlt}</c:if>
						  <c:if test="${item.folioStart!=null&&item.folioEnd!=null}">-</c:if>
						  <c:if test="${item.folioEnd!=null}">${item.folioEnd}</c:if>
						  <c:if test="${item.folioEndAlt ne 'null'}">${item.folioEndAlt}</c:if>
			            </td>
			          </tr>
			        </c:forEach>
			    </table>  
			  </c:if>
			  
			  <c:if test="${not empty origColl.imageList}">
			    <table class="coll">
			      <tr><th colspan="2">Images</th></tr>
			        <c:forEach items="${origColl.imageList}" var="image">
			          <c:url value="CollectionManage" var="unlinkUrl">
		                <c:param name="unlink"     value="unlink"/>
		                <c:param name="origCollId" value="${origColl.id}"/>
		                <c:param name="unlinkId"   value="${image.imagekey}"/>
		                <c:param name="unlinkType" value="image"/>
		              </c:url>
			          <tr>
			            <td class="action"><a href="${unlinkUrl}">remove</a></td>
			            <td class="default">
			              Filename: ${image.filename} <br />
			              <c:if test="${not empty image.folio}">
						    Folio: ${image.folio}
						  </c:if>	
			            </td>
			          </tr>
			        </c:forEach>
			    </table>  
			  </c:if>  
			  
		    </c:otherwise>
		  </c:choose>
		</div>
	  </c:otherwise>
	</c:choose>
	
  </body>
</html>