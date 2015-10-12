<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Collection Details</title>
    <link rel="stylesheet" type="text/css" href="../_a/c/a.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/s.css"/>
    <link rel="stylesheet" type="text/css" href="../_a/c/user.css"/>
  </head>
  <body>
    <jsp:include page="banner.jsp"/>
    <h2>${origColl.title}</h2>
    <a href="${refererUrl}" title="Return to previous page">Return to previous page</a>
    <hr />
	
    <div>
	  <c:choose>
	    <c:when test="${empty origColl.sourceList and empty origColl.itemList and empty origColl.imageList}">
	      <div>This collection is empty</div>
	    </c:when>
	    <c:otherwise>
	      
	      <c:if test="${not empty origColl.sourceList}">
		    <div>
		      <h2>Sources</h2>
		        <c:forEach items="${origColl.sourceList}" var="source">
		          <dl>
		            <dt>${source.shelfmark}</dt>
		            <dd>
		              <c:forEach items="${source.imageList}" var="image">
		                <ul>
		                  <c:if test="${not empty image.filename}">
                            <li>
                              <a href="AnnotationManager?imageKey=${image.imagekey}">
                                <img src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/>
                              </a>
                              <c:if test="${not empty image.copyrightstatement}">${image.copyrightstatement}</c:if>
                            </li>
                          </c:if>
		                </ul>
		              </c:forEach>
		            </dd>
		          </dl>
		        </c:forEach>
		    </div>   
		    <hr /> 
		  </c:if>  
		  
		  <c:if test="${not empty origColl.itemList}">
		    <div>
		      <h2>Items</h2>
		        <c:forEach items="${origColl.itemList}" var="item">
		          <dl>
		            <dt>
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
		            </dt>
		            <dd>
		              <c:forEach items="${item.imageList}" var="image">
		                <ul>
		                  <c:if test="${not empty image.filename}">
                            <li>
                              <a href="AnnotationManager?imageKey=${image.imagekey}">
                                <img src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/>
                              </a>
                              <c:if test="${not empty image.copyrightstatement}">${image.copyrightstatement}</c:if>
                            </li>
                          </c:if>
		                </ul>
		              </c:forEach>
		            </dd>
		          </dl>
		        </c:forEach>
		    </div> 
		    <hr />   
		  </c:if>
		  
		  <c:if test="${not empty origColl.imageList}">
		    <div>
		      <h2>Images</h2>
		        <c:forEach items="${origColl.imageList}" var="image">
		          <dl>
		            <dt>
                      Filename: ${image.filename} <br />
			          <c:if test="${not empty image.folio}">
					    Folio: ${image.folio}
					  </c:if>
		            </dt>
		            <dd>
		              <ul>
                        <c:if test="${not empty image.filename}">
                          <li>
                            <a href="AnnotationManager?imageKey=${image.imagekey}">
                              <img src="http://images.cch.kcl.ac.uk/diamm/liv/thumbnails/${image.filename}"/>
                            </a>
                            <c:if test="${not empty image.copyrightstatement}">${image.copyrightstatement}</c:if>
                          </li>
                        </c:if>
		              </ul>
		            </dd>
		          </dl>
		        </c:forEach>
		    </div>    
		  </c:if>  
	    </c:otherwise>
	  </c:choose>
	</div>
	
  </body>
</html>