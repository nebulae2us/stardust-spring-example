<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../../css/stardust-spring-example.css">
</head>
<body>

<h2>Tag: <c:out value="${tag.name}"></c:out></h2>

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
</div>
</c:forEach>

</body>
</html>