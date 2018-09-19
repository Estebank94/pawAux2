<%--
  Created by IntelliJ IDEA.
  User: martinascomazzon
  Date: 25/8/18
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="/">
            <h1><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="navbar-search sticky-top">
    <%--action="${postPath}--%>
    <form:form action="processForm" method="GET" modelAttribute="search" accept-charset="ISO-8859-1">
    <div class="input-group container">
        <form:input type="text" aria-label="Buscar por especialista" placeholder="Buscar por nombre del médico" class="form-control" path="name"/>
        <form:input type="text" aria-label="Buscar por especialidad" placeholder="Buscar por especialidad" class="form-control" path="specialty"/>
        <form:select class="custom-select" id="insurance" path="insurance">
            <form:option value="no" label="Prepaga" selected="Prepaga"/>
            <form:options items="${insuranceList}" itemValue="name" itemLabel="name" />
        </form:select>
        <div class="input-group-append">
            <input type="submit" class="btn btn-outline-light" value="Buscar" path="submit"/>
        </div>
    </div>
</div>


<div class="main container">
    <div class="row">
        <div class="col-md-9">
            <c:forEach items="${doctorList}" var="doctorListItem">
                <div class="card card-doctor d-flex flex-row"  onclick='window.location="<c:url value='/specialist/${doctorListItem.id}'/>"'>
                    <img src=${doctorListItem.avatar} class="avatar">
                    <div class="card-body">
                        <div class="card-text">
                            <h3>${doctorListItem.lastName}, ${doctorListItem.firstName}</h3>
                            <div class="row container">
                                <c:forEach items="${doctorListItem.specialty}" var="doctorSpecialty">
                                    <p class="doctor-specialty" style="padding-right: 2em">${doctorSpecialty}</p>
                                </c:forEach>
                            </div>
                            <br>
                            <div class="row container">
                                <i class="fas fa-star star-yellow"></i>
                                <i class="fas fa-star star-yellow"></i>
                                <i class="fas fa-star star-yellow"></i>
                                <i class="fas fa-star star-yellow"></i>
                                <i class="fas fa-star star-grey"></i>
                            </div>
                            <p class="doctor-text">"Muy buena atención, muy puntual"</p>
                            <br>
                            <p class="doctor-text"><i class="far fa-clock"></i> ${doctorListItem.workingHours} </p>
                            <p class="doctor-text"><i class="fas fa-map-marker-alt"></i> ${doctorListItem.address}, CABA</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-3">
            <div class="sidebar-nav-fixed pull-right affix">
                <h3 class="sidebar-title">Filtrar Resultados</h3>
                <%--<c:choose>--%>
                    <%--<c:when test="${insuranceNameList.size() == 1 && sexList.size() != 1}">--%>
                        <hr class="hr-header-sidebar">
                        <div>
                            <c:if test="${insuranceNameList.size() == 1 }">
                            <h4 class="sidebar-title">Plan Prepaga</h4>
                            <div class="form-check">
                                <br>
                                <c:forEach items="${insuranceNameList}" var="insuranceNameList">
                                    <b> ${insuranceNameList.key} <br> </b>
                                    <c:forEach items="${insuranceNameList.key}">
                                        <form:checkboxes path="insurancePlan" items="${insuranceNameList.value}" delimiter="<br>" />
                                        <%--<c:forEach items="${insuranceNameList.value}" var="info">--%>
                                        <%----%>
                                        <%--${info}<br>--%>
                                        <%--</c:forEach>--%>
                                    </c:forEach>
                                    <br>
                                </c:forEach>
                                </c:if>
                            </div>
                                <%--<hr class="hr-sidebar">--%>
                            <div>
                                <c:if test="${sexList.size() != 1}">
                                    <h4 class="sidebar-title">Sexo</h4>
                                    <div class="form-check">
                                        <form:radiobutton path="sex" value="ALL"/> Todos <br>
                                        <c:forEach items="${sexList}" var="sex">
                                            <form:radiobutton path="sex" value="${sex}"/>
                                            <c:if test="${sex.equals('M')}">Masculino<br></c:if>
                                            <c:if test="${sex.equals('F')}">Femenino<br></c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                                <c:if test="${insuranceNameList.size() != 1 && sexList.size() == 1}">
                                    <div class="center-horizontal">
                                        <i class="fas fa-exclamation-triangle center-horizontal" style="color:#CECECE; font-size: 36px; margin-bottom: 16px; margin-top: 16px "></i>
                                        <p>No hay filtros aplicables</p>
                                    </div>
                                </c:if>
                        </div>
                    <%--</c:when>--%>

                <%--</c:choose>--%>

                    <%--<hr class="hr-sidebar">--%>
                    <%--<div>--%>
                    <%--<h4 class="sidebar-title">Estrellas</h4>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1" checked>--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--Todas--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--<div class="row star-container">--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--<div class="row star-container">--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--<div class="row star-container">--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--<div class="row star-container">--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--<div class="form-check">--%>
                    <%--<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">--%>
                    <%--<label class="form-check-label" for="defaultCheck1">--%>
                    <%--<div class="row star-container">--%>
                    <%--<i class="fas fa-star star-yellow star-small"></i>--%>
                    <%--</div>--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </form:form>
            </div>
    </div>
</div>

<%--<footer class="footer-grey">--%>
    <%--<div class="container">--%>
        <%--<p class="footer-text">© Copyright 2018. Waldoc</p>--%>
    <%--</div>--%>
<%--</footer>--%>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
