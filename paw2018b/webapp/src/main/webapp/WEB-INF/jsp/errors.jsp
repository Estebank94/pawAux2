<%--
  Created by IntelliJ IDEA.
  User: martinascomazzon
  Date: 26/9/18
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Errors</title>
</head>
<body>
<h1><c:out value="${errorMsg}"/></h1>
<a href="<c:url value="/"/>>Back to HomePage</a>
</body>
</html>
