<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../../css/stardust-spring-example.css">
</head>
<body onload="document.getElementById('editedText').focus()">

<c:forEach var="comment" items="${comments}">
	<c:if test="${comment.commentId != commentIdToEdit}">
	<div class="comment-box">
		<span class="commenter"><c:out value="${comment.commenter.personName.firstName} ${comment.commenter.personName.lastName}"></c:out></span>
		<span class="action-date">-&nbsp;<fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.createdDate}"/></span>
			<c:if test="${not empty comment.updatedDate}">
				<span class="action-date">(edited: <fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.updatedDate}"/>)</span>
			</c:if>
		<br>
		<span class="comment"><c:out value="${comment.text}"></c:out></span>
		<br/>	
			<c:forEach var="tag" items="${comment.tags}">
				<span class="tag-box"><c:out value="${tag.name}"></c:out></span>
			</c:forEach>
	</div>	
	</c:if>
	
	<c:if test="${comment.commentId == commentIdToEdit}">
	<div class="comment-box">		
		<form method="POST" action='<c:url value="/example/comment/${comment.commentId}"></c:url>'>
		<table>
		<tr>
			<td>
				<span class="commenter"><c:out value="${comment.commenter.personName.firstName} ${comment.commenter.personName.lastName}"></c:out></span>
				<span class="action-date">-&nbsp;<fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.createdDate}"/></span>
					<c:if test="${not empty comment.updatedDate}">
						<span class="action-date">(last edited: <fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.updatedDate}"/>)</span>
					</c:if>
				<br>
			</td>
		</tr>
		<tr>
			<td>
				<textarea id="editedText" name="text" rows="10" cols="80"><c:out value="${comment.text}"></c:out></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<label for="tags">Tag</label>
				<input type="text" name="tags" id="tags">
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="Save Comment">
				<input type="button" value="Cancel" onclick="parent.location='../comment'">
			</td>
		</tr>
		</table>
		</form>
	</div>
	</c:if>
</c:forEach>

</body>
</html>