<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
</head>
<body>

<h2><c:out value="${tag.name}"></c:out></h2>

<c:forEach var="comment" items="${comments}">
	<p>...................</p>
	<span><c:out value="${comment.text}"></c:out></span>

	<br/>	
	<c:forEach var="tag" items="${comment.tags}">
		<a href='<c:url value="/rest/tag/${tag.tagId}"></c:url>'><c:out value="${tag.name}"></c:out></a>
	</c:forEach>
	
	<p>posted date: <fmt:formatDate pattern="MM-dd-yyyy HH:mm" value="${comment.createdDate}"/><p>
	<c:if test="${not empty comment.updatedDate}">
		<p>edited date: <fmt:formatDate pattern="MM-dd-yyyy HH:mm" value="${comment.updatedDate}"/><p>
	</c:if>
	<p><a href='<c:url value="/rest/user/${comment.commenter.personId}"></c:url>'><c:out value="${comment.commenter.personName.firstName} ${comment.commenter.personName.lastName}"></c:out></a></p>
	
</c:forEach>

</body>
</html>