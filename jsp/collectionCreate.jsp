<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin-top:20px; border: 2px black solid; padding:5px;">
  <span class="heading">Create New Collection</span>
  <form action="${param.collectionAction}" method="post">
    <div style="margin-top:10px;">
      <label>Title</label><span class="msg1">*</span><span class="msg2">${messages.title}</span><br />
      <input type="text" class="text" name="createdCollTitle" value="${collDetails.createdCollTitle}" maxlength="30"/>
    </div>
    <div>
      <label>Description</label><span class="msg2">${messages.description}</span><br />
      <input type="text" class="text2" name="createdCollDescription" value="${collDetails.createdCollDescription}" maxlength="100"/>
    </div>
    <div>
      <c:if test="${not empty collDetails.linkType}">
        <input type="hidden" name="linkId" value="${collDetails.linkId}">
        <input type="hidden" name="linkType" value="${collDetails.linkType}">
      </c:if>
      <input type="submit" name="create" value="Create">
    </div>
  </form>
</div>
