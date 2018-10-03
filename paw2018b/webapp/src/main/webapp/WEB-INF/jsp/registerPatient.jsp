<%--
  Created by IntelliJ IDEA.
  User: martinascomazzon
  Date: 1/10/18
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <h1><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="container">
    <br>
    <br>
    <h2>¡Registrate como Paciente!</h2>
    <p>Completa tus datos personales.</p>

    <hr style="border-top: 1px solid #D8D8D8 !important;">
    <form:form modelAttribute="personal" method="POST" action="patientRegistration" accept-charset="ISO-8859-1">
        <div class="row">
            <div class="col">
                <c:if test="${wrongFirstName eq true}">
                    <b style="color: #dc3545">Lo siento! Su nombre es incorrecto, por favor, reinterntarlo</b>
                </c:if>
                <label for="exampleInputEmail1"><strong>Nombre</strong></label>
                <form:input type="text" class="form-control" placeholder="Ingresá tu nombre" path="firstName"/>
                <form:errors path="firstName" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
            <div class="col">
                <c:if test="${wrongLastName eq true}">
                    <b style="color: #dc3545">Lo siento! Su apellido es incorrecto, por favor, reinterntarlo</b>
                </c:if>
                <label for="exampleInputEmail1"><strong>Apellido</strong></label>
                <form:input type="text" class="form-control" placeholder="Ingresá tu apellido" path="lastName"/>
                <form:errors path="lastName" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
        </div>
        <br>
        <c:if test="${wrongEmail eq true}">
            <b style="color: #dc3545">Lo siento! Su email es incorrecto, por favor, reinterntarlo</b>
        </c:if>
        <c:if test="${repeatedEmail eq true}">
            <b style="color: #dc3545">Lo siento! Su email ya se encuentra registrado con otro usuario, por favor, intente otro mail</b>
        </c:if>
        <div>
            <label for="exampleInputEmail1"><strong>Email</strong></label>
            <form:input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Ingresá tu mail" path="email"/>
            <form:errors path="email" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <br>
        <div class="row">
            <c:if test="${noMatchingPassword eq true}">
                <b style="color: #dc3545">Lo siento! Sus contraseñas no coinciden, por favor, reinterntarlo</b>
            </c:if>
            <c:if test="${wrongPassword eq true}">
                <b style="color: #dc3545">Lo siento! Su contraseña es incorrecta, por favor, reinterntarlo</b>
            </c:if>
            <div class="col">
                <label for="inputPassword5"><strong>Contraseña</strong></label>
                <form:input type="password" id="inputPassword5" class="form-control" aria-describedby="passwordHelpBlock" placeholder="Creá tu contraseña" path="password"/>
                <form:errors path="password" cssStyle="color: crimson"  element="p"></form:errors>
                <small id="passwordHelpBlock" class="form-text text-muted">
                    Tu contraseña debe tener al menos 6 caracteres.
                </small>
            </div>
            <div class="col">
                <label for="inputPassword5"><strong>Confirma Contraseña</strong></label>
                <form:input type="password" id="inputPassword5" class="form-control" placeholder="Confirmá tu contraseña" path="passwordConfirmation"/>
                <form:errors path="passwordConfirmation" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
        </div>
        <br>
        <c:if test="${wrongPhoneNumber eq true}">
            <b style="color: #dc3545">Lo siento! Su telefono es incorrecto, por favor, reinterntarlo</b>
        </c:if>
        <div>
            <label for="exampleInputEmail1"><strong>Teléfono</strong></label>
            <form:input class="form-control" id="exampleInputEmail1"  placeholder="Ingresá tu telefono" path="phoneNumber"/>
            <form:errors path="phoneNumber" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <br>

        <input type="submit" class="btn btn-primary custom-btn" value="Registrar" path="submit" />
        <input type="button" class="btn btn-secondary" value="Cancelar" onclick="window.location='<c:url value="/"/>'"/>
    </form:form>
    <br>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
