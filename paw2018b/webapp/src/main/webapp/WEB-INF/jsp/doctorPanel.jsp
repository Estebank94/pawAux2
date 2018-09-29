<%--
  Created by IntelliJ IDEA.
  User: estebankramer
  Date: 26/09/2018
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <!-- Pongo mi stylesheet despues de la de Bootstrap para poder hacer override los estilos -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2-bootstrap4.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="/">
            <h1><strong>Waldoc</strong></h1>
        </a>
        <a>
            <security:authorize access="isAuthenticated()">
                <form:form action="${pageContext.request.contextPath}/logout" method="post">
                    <security:authentication property="principal.username" var="userName"/>
                    <div class="dropdown">
                        <button class="btn btn-light dropdown-toggle" style="margin-right: 15px; background-color:transparent; border-color:white; color:white !important;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><b>${userName}</b></button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                            <button class="dropdown-item" type="submit">Cerrar Sesion</button>
                        <%--<button class="dropdown-item" type="button">Registrate como especialista</button>--%>
                        </div>
                    </div>
                </form:form>
            </security:authorize>
        </a>
    </div>
</nav>

<div class="container">
    <div class="col-sm-12">
        <div class="card card-doctor flex-row">
            <div class="card-body">
                <div class="card-text">
                    <div class="row">
                        <img class="avatar big" src=${doctor.avatar}>
                        <div class="doctor-info-container">
                            <div>
                                <p class="doctor-specialty">Bienvenido</p>
                                <h3 class="doctor-name">Dr. ${doctor.lastName}, ${doctor.firstName}</h3>
                                <br>
                                <button type="button" class="btn btn-outline-secondary"><i class="fas fa-cog"></i> Editar Perfil</button>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div>
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Mis Turnos Como Especialista</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Mis Turnos Como Paciente</a>
                        </li>
                    </ul>
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <br>
                            <c:forEach items="${appointments}" var="appointment">
                                    <div style="margin-left: 16px; margin-right: 16px;">
                                        <h3>
                                            ${appointment.key.dayOfMonth} de ${appointment.key.month} de ${appointment.key.year}
                                        </h3>
                                        <br>
                                        <c:forEach items="${appointment.value}" var="listItems">
                                            <div>
                                                <div class="row">
                                                    <img src="http://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg" class="avatar medium">
                                                    <div class="center-vertical">
                                                        <div>
                                                                <p style="margin-bottom: 0px">${listItems}</p>
                                                            <h5>Palito</h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr class="hr-header-sidebar">
                                            </div>
                                            <br>
                                        </c:forEach>
                                    </div>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
                        <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>

