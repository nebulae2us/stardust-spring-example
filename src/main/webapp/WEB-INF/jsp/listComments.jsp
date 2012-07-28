<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="../css/app.css">
</head>
<body>

<c:forEach var="comment" items="${comments}">

<div class="comment-box">
	<a class="commenter" href='<c:url value="/rest/user/${comment.commenter.personId}"></c:url>'><c:out value="${comment.commenter.personName.firstName} ${comment.commenter.personName.lastName}"></c:out></a>
	<span class="action-date">-&nbsp;<fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.createdDate}"/></span>
		<c:if test="${not empty comment.updatedDate}">
			<span class="action-date">(edited: <fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.updatedDate}"/>)</span>
		</c:if>
	<br>
	<span class="comment"><c:out value="${comment.text}"></c:out></span>
	<br/>	
		<c:forEach var="tag" items="${comment.tags}">
			<a class="tag-box" href='<c:url value="/rest/tag/${tag.tagId}"></c:url>'><c:out value="${tag.name}"></c:out></a>
		</c:forEach>

	
	<div class="action-button-box">
	<a class="action-button-text" href='<c:url value="/rest/comment/${comment.commentId}"></c:url>'>edit</a>
	|
	<a class="action-button-text" href='<c:url value="/rest/comment/${comment.commentId}"></c:url>'>delete</a>
	</div>
	
</div>	
</c:forEach>

<div class="comment-box">
<form method="POST" action='<c:url value="/rest/comment"></c:url>'>
<table>
<tr>
	<td>
		<label for="fn">First Name</label>
		<input type="text" name="fn" id="fn">
		<label for="sn">Last Name</label>
		<input type="text" name="sn" id="sn">
	</td>
</tr>
<tr>
	<td>
		<textarea name="text" rows="10" cols="80"></textarea>
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
		<input type="submit" value="Post Comment">
	</td>
</tr>
</table>
</form>
</div>
</body>
</html>