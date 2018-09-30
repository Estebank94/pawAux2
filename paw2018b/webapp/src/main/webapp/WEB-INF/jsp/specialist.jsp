<%--
  Created by IntelliJ IDEA.
  User: estebankramer
  Date: 12/09/2018
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2-bootstrap4.css" />">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="/">
            <h1><strong>Waldoc</strong></h1>
        </a>
        <div class="row">
            <security:authorize access="!isAuthenticated()">
            <div class="dropdown" style="z-index: 1000000 !important;">
                <button class="btn btn-light dropdown-toggle" style="margin-right: 8px; background-color:transparent; border-color:white; color:white !important;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Registrate
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <button class="dropdown-item" type="button">Registrate como paciente</button>
                    <button class="dropdown-item" type="button">Registrate como especialista</button>
                </div>
            </div>
            </security:authorize>
            <div>
                <security:authorize access="!isAuthenticated()">
                    <button class="btn btn-secondary" style="background-color:transparent; border-color:transparent;" type="button" onclick="window.location='/showLogIn'">
                        Iniciá Sesión
                    </button>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <form:form action="${pageContext.request.contextPath}/logout" method="post">
                        <security:authentication property="principal.username" var="userName"/>
                        <button class="btn btn-secondary" style="background-color:transparent; border-color:transparent;" type="submit">Logout <b>${userName}</b></button>
                    </form:form>
                </security:authorize>
            </div>
            <%--<button class="btn btn-secondary" style="background-color:transparent; border-color:transparent;" type="button">--%>
            <%--Iniciá Sesión--%>
            <%--</button>--%>
        </div>
        </a>
    </div>
</nav>

<div class="navbar-search sticky-top">
<%--action="${postPath}--%>
<form:form action="/processForm" method="GET" modelAttribute="search" accept-charset="ISO-8859-1">
    <div id="search-bar" class="input-group container">
        <form:input type="text" aria-label="Buscar por especialista" placeholder="Buscar por nombre del médico" class="form-control" path="name"/>
        <form:select class="custom-select" id="specialty" path="specialty" cssStyle="cursor: pointer;">
            <form:option value="noSpecialty" label="Especialidad" selected="Especialidad"/>
            <form:options items="${specialtyList}" itemValue="name" itemLabel="name" />
        </form:select>
            <%--<form:input type="text" aria-label="Buscar por especialidad" placeholder="Buscar por especialidad" class="form-control" path="specialty"/>--%>
        <form:select class="custom-select" id="insurance" path="insurance" cssStyle="cursor: pointer;">
            <form:option value="no" label="Prepaga" selected="Prepaga"/>
            <form:options items="${insuranceList}" itemValue="name" itemLabel="name" />
        </form:select>
        <div class="input-group-append">
            <input type="submit" class="btn btn-outline-light" value="Buscar" path="submit"/>
        </div>
    </div>
</form:form>
    </div>

<div class="container">
    <div class="col-sm-12">
        <div class="card card-doctor flex-row">
            <div class="card-body">
                <div class="card-text">
                    <div class="row">
                        <img class="avatar big" src=${doctor.avatar}>
                        <div class="doctor-info-container">
                            <div>
                                <h3 class="doctor-name">${doctor.lastName}, ${doctor.firstName}</h3>
                                <div class="row container">
                                    <c:forEach items="${doctor.specialty}" var="doctorSpecialty">
                                        <p class="doctor-specialty" style="padding-right: 2em">${doctorSpecialty}</p>
                                    </c:forEach>
                                </div>
                                <p class="doctor-text"><i class="fas fa-phone" style="padding-right: 0.5em"></i>${doctor.phoneNumber}</p>
                                <%--<p class="doctor-text"><i class="far fa-clock" style="padding-right: 0.5em"></i>${doctor.workingHours}</p>--%>
                                <p class="doctor-text"><i class="fas fa-map-marker-alt" style="padding-right: 0.5em"></i>${doctor.address}, CABA</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div style="background-color: #F3F3F4; border-radius: 5px; padding: 16px; padding-bottom: 0px; margin-top: 32px; margin-bottom:32px">
                        <h3 class="doctor-name">Reservá un Turno</h3>
                        <form:form modelAttribute="appointment" method="post" action="/specialist/${doctor.id}" id="appointment">
                            <div class="row">
                                    <div class="col-sm-5">
                                        <label for="day">Día</label>
                                        <form:select class="custom-select" id="day" path="day">
                                            <option value="no" selected>Elegí el Día</option>
                                            <c:forEach items="${appointmentsAvailable}" var="date">
                                                <form:option value="${date.key}" label="${date.key}">${date.key}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                <div class="col-sm-5">
                                    <label for="time">Horario</label>
                                    <form:select class="custom-select" disabled="false" id="time" path="time">
                                        <option value="no" selected>Elegí el Horario</option>
                                    <c:forEach items="${appointmentsAvailable}" var="date">
                                        <c:forEach items="${date.value}" var="listItem">
                                            <form:option value="${listItem.appointmentTime}" label="pepito${listItem.appointmentTime}">${listItem.appointmentTime}</form:option>
                                            <%--<input type="hidden" id="time" name="time" value="${listItem.appointmentTime}">--%>
                                        </c:forEach>
                                    </c:forEach>
                                    </form:select>
                                </div>
                                <security:authorize access="!isAuthenticated()">
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-primary" style="position: absolute; bottom: 0;" onclick="window.location='/showLogIn'">Reservar Turno</button>
                                </div>
                                </security:authorize>
                                <security:authorize access="isAuthenticated()">
                                    <div class="col-sm-2">
                                        <button type="submit" class="btn btn-primary" style="position: absolute; bottom: 0;">Reservar Turno</button>
                                    </div>
                                </security:authorize>
                            </div>
                            <br>
                        </form:form>
                    </div>

                    <h3 id="information">Información Profesional</h3>
                    <br>
                    <br>
                    <h4>Prepagas & Planes</h4>
                    <c:forEach items="${insuranceNameList}" var="insuranceNameList">
                        <b>${insuranceNameList.key}</b>
                        </br>
                        <c:forEach items="${insuranceNameList.key}" var="insurance">
                            <c:forEach items="${insuranceNameList.value}" var="insurancePlan">
                                ${insurancePlan}
                                </br>
                            </c:forEach>
                        </c:forEach>
                        </br>
                    </c:forEach>
                    <c:if test="${doctor.description.education != null}">
                        <h4>Educación</h4>
                        <c:forEach items="${doctor.description.education}" var="education">
                            ${education}
                        </c:forEach>
                        <br>
                        <br>
                    </c:if>
                    <c:if test="${doctor.description.certificate.size() != null}">
                        <h4>Certificación</h4>
                        <c:forEach items="${doctor.description.certificate}" var="certificate">
                            ${certificate}
                        </c:forEach>
                        <br>
                        <br>
                    </c:if>
                    <c:if test="${doctor.description.languages.size() != 0}">
                        <h4>Ídiomas</h4>
                        <c:forEach items="${doctor.description.languages}" var="languages">
                            ${languages}
                        </c:forEach>
                        <br>
                        <br>
                    </c:if>
                    <%--<p>El Dr. Andres Miller ha ejercido la medicina por más de 20 años y se especializa en Medicina Antienvejecimiento y Regenerativa, así como Medicina General Integrativa.</p>--%>
                    <h3 id="reviews">Reseñas</h3>
                    <p>Muy bueno</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

<%--select2 dropdown--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>--%>
<%--<script src="<c:url value="/resources/javascript/dropdowns.js"/>"></script>--%>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>

    // $(function() {
    //     $('#day').on('change', function() {
    //         var val = this.value;
    //         if(val == "no"){
    //             $('#time').prop('disabled', true);
    //             $('#time').val('no');
    //         }
    //         else{
    //             $('#time').prop('disabled', false);
    //             $('#time option').hide().filter(function() {
    //             return this.value.indexOf( val + '_' ) === 0;
    //         })
    //             .show();
    //         }})
    //         .change();
    // });

    function addInput(name) {
        var val = $('#time').val();
        alert(val);
        $('#appointment').append('<input type="hidden" name="'+name+'" value="'+val+'" id="'+name+'"/>');
    }

</script>
</body>
</html>

