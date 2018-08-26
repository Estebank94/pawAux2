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

<div class="navbar-search">
    <form:form action="processForm" method="GET" modelAttribute="search" accept-charset="ISO-8859-1">
    <div class="input-group container">
        <form:input type="text" aria-label="Buscar por especialista" placeholder="Buscar por nombre del médico" class="form-control" path="name"/>
        <form:input type="text" aria-label="Buscar por especialidad" placeholder="Buscar por especialidad" class="form-control" path="specialty"/>
        <form:select class="custom-select col-sm-3" id="location" path="location">
            <form:option selected="Ubicación" value="">Ubicación</form:option>
            <form:option value="Palermo">Palermo</form:option>
            <form:option value="Belgrano">Belgrano</form:option>
            <form:option value="Recoleta">Recoleta</form:option>
        </form:select>
        <form:select class="custom-select col-sm-3" id="insurance" path="insurance">
            <form:option selected="Prepaga" value="">Prepaga</form:option>
            <form:option value="OSDE">OSDE</form:option>
            <form:option value="Swiss Medical">Swiss Medical</form:option>
            <form:option value="Omint">Omint</form:option>
        </form:select>
        <div class="input-group-append">
            <input type="submit" class="btn btn-primary custom-btn" value="Buscar" path="submit"/>
        </div>
    </div>
    </form:form>
</div>


<div class="main container">
    <c:choose>
        <c:when test="${not empty search.name}">
            <div class="row">
                <div class="col-sm-12">
                    <div class="card card-doctor d-flex flex-row">
                        <img src="http://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg" class="avatar">
                        <div class="card-body">
                            <div class="card-text">
                                <h3>${search.name}</h3>
                                <p class="doctor-specialty">${search.specialty}</p>
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
                                <p class="doctor-text"><i class="far fa-clock"></i>  Lunes a Viernes de 8 a 16hs</p>
                                <p class="doctor-text"><i class="fas fa-map-marker-alt"></i>  Av. Libertador 3000, Buenos Aires</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${(empty search.name) and (empty search.specialty) and (empty search.location) and (empty search.insurance)}"> <br> <p>No hay forma de buscar nada </p> </c:when>
    </c:choose>
</div>

<footer class="footer-grey">
    <div class="container">
        <p class="footer-text">© Copyright 2018. Waldoc</p>
    </div>
</footer>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
