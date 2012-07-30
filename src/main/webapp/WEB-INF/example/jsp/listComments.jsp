<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/stardust-spring-example.css">
</head>
<body>

<jsp:include page="intro.jsp"></jsp:include>
<h2>Comments:</h2>

<c:forEach var="comment" items="${comments}">

<div class="comment-box">
	<a class="commenter" href="${pageContext.request.contextPath}/example/app/user/${comment.commenter.personId}"><c:out value="${comment.commenter.personName.firstName} ${comment.commenter.personName.lastName}"></c:out></a>
	<span class="action-date">-&nbsp;<fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.createdDate}"/></span>
	<c:if test="${not empty comment.updatedDate}">
		<span class="action-date">(edited: <fmt:formatDate pattern="MMM dd, yyyy 'at' h:mm a" value="${comment.updatedDate}"/>)</span>
	</c:if>
	<br>
	<span class="comment"><c:out value="${comment.text}"></c:out></span>
	<br/>	
	<c:forEach var="tag" items="${comment.tags}">
		<a class="tag-box" href="${pageContext.request.contextPath}/example/app/tag/${tag.tagId}"><c:out value="${tag.name}"></c:out></a>
	</c:forEach>

	
	<div class="action-button-box">
	<form id="deleteForm${comment.commentId}" method="post" action="${pageContext.request.contextPath}/example/app/comment/${comment.commentId}">
		<input type="hidden" name="_method" value="delete">
		<input type="hidden" name="personId" value="<c:out value='${comment.commenter.personId}'></c:out>">
		<a class="action-button-text" href="${pageContext.request.contextPath}/example/app/comment/${comment.commentId}">edit</a>
		|
		<a class="action-button-text" href="#"
		   onclick="if (confirm('Are you sure you want to delete this comment?')) {document.getElementById('deleteForm${comment.commentId}').submit();} return false;"
		>delete</a>
	</form>
	</div>
	
</div>	
</c:forEach>

<div class="comment-box">
<form method="POST" action="${pageContext.request.contextPath}/example/app/comment">
<p>Post new comment:</p>
<table>
<tr>
	<td>
		<label for="fn">First Name:</label>
		<input type="text" name="fn" id="fn">
		<label for="sn">Last Name:</label>
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
		<label for="tags">Tag:</label>
		<input type="text" name="tags" id="tags" style="width: 500px;">
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