<%--
  Created by IntelliJ IDEA.
  User: estebankramer
  Date: 20/09/2018
  Time: 18:07
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
    <title><spring:message code="brand.name"/></title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <h1><strong><spring:message code="brand.name"/></strong></h1>
        </a>
    </div>
</nav>

<div class="container">
    <br>
    <br>
    <h2><spring:message code="dropdown.doctorRegister"/></h2>
    <p><spring:message code="register.personalInfo"/>.</p>

    <hr style="border-top: 1px solid #D8D8D8 !important;">
    <c:url value="/doctorRegistration" var="doctorRegistration"/>
    <form:form modelAttribute="personal" method="POST" action="${doctorRegistration}" accept-charset="ISO-8859-1">
        <div class="row">
            <div class="col">
                <label for="exampleInputEmail1"><strong><spring:message code="registration.name"/></strong></label>
                <spring:message code="holder.firstName" var="firstName"/>
                <form:input type="text" class="form-control" placeholder="${firstName}" path="firstName"/>
                <form:errors path="firstName" cssClass="wrong"  element="p"></form:errors>
                <c:if test="${wrongFirstName eq true}">
                    <p class="wrong"><spring:message code="error.badName"/></p>
                </c:if>
            </div>
            <div class="col">
                <label for="exampleInputEmail1"><strong>Apellido</strong></label>
                <spring:message code="holder.lastName" var="lastName"/>
                <form:input type="text" class="form-control" placeholder="${lastName}" path="lastName"/>
                <form:errors path="lastName" cssClass="wrong" element="p"></form:errors>
                <c:if test="${wrongLastName eq true}">
                    <p class="wrong"><spring:message code="error.badLastName"/></p>
                </c:if>
            </div>
        </div>
        <br>
        <div>
            <label for="exampleInputEmail1"><strong><spring:message code="registration.mail"/></strong></label>
            <spring:message code="holder.mail" var="mail"/>
            <form:input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="${mail}" path="email"/>
            <form:errors path="email" cssClass="wrong"  element="p"></form:errors>
            <c:if test="${wrongEmail eq true}">
                <p class="wrong"><spring:message code="error.badMail"/></p>
            </c:if>
            <c:if test="${repeatedEmail eq true}">
                <p class="wrong"><spring:message code="error.repetedMail"/></p>
            </c:if>
        </div>
        <br>
        <div class="row">
            <div class="col">
                <label for="inputPassword5"><strong><spring:message code="registration.password"/></strong></label>
                <spring:message code="holder.password" var="password"/>
                <form:input type="password" id="inputPassword5" class="form-control" aria-describedby="passwordHelpBlock" placeholder="${password}" path="password"/>
                <form:errors path="password" cssClass="wrong" element="p"></form:errors>
                <small id="passwordHelpBlock" class="form-text text-muted">
                    <spring:message code="registration.password.message"/>
                </small>
                <c:if test="${noMatchingPassword eq true}">
                    <p class="wrong"><spring:message code="error.notmatching"/></p>
                </c:if>
                <c:if test="${wrongPassword eq true}">
                    <p class="wrong"><spring:message code="error.wrongPassword"/></p>
                </c:if>
            </div>
            <div class="col">
                <label for="inputPassword5"><strong><spring:message code="registration.repeatPassword"/></strong></label>
                <spring:message code="holder.confirmationPass" var="confirmation"/>
                <form:input type="password" id="inputPassword5" class="form-control" placeholder="${confirmation}" path="passwordConfirmation"/>
                <form:errors path="passwordConfirmation" cssClass="wrong"  element="p"></form:errors>
            </div>
        </div>
        <br>
        <div>
            <label for="exampleInputEmail1"><strong><spring:message code="registration.phone"/></strong></label>
            <spring:message code="holder.phone" var="phone"/>
            <form:input class="form-control" id="exampleInputEmail1"  placeholder="${phone}" path="phoneNumber"/>
            <small class="form-text text-muted">
                <spring:message code="registration.phone.hint"/>
            </small>
            <form:errors path="phoneNumber" cssClass="wrong"  element="p"></form:errors>
            <c:if test="${wrongPhoneNumber eq true}">
                <p class="wrong"><spring:message code="error.wrongNumber"/></p>
            </c:if>
        </div>
        <br>
        <div>
            <label for="exampleInputEmail1"><strong><spring:message code="registration.address"/></strong></label>
            <spring:message code="holder.address" var="address"/>
            <form:input class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="${address}" path="address"/>
            <form:errors path="address" cssClass="wrong"  element="p"></form:errors>
            <c:if test="${wrongAddress eq true}">
                <p class="wrong"><spring:message code="error.badAddress"/></p>
            </c:if>
        </div>
        <br>
        <div>
            <label for="exampleFormControlSelect1"><strong><spring:message code="registration.sex"/></strong></label>
            <form:select class="custom-select" id="exampleFormControlSelect1" path="sex">
                <option value="F"><spring:message code="registration.female"/></option>
                <option value="M"><spring:message code="registration.male"/></option>
            </form:select>
            <form:errors path="sex" cssClass="wrong"  element="p"></form:errors>
            <c:if test="${wrongSex eq true}">
                <p class="wrong"><spring:message code="error.sex"/></p>
            </c:if>
        </div>
        <br>
        <div>
            <label for="exampleInputEmail1"><strong><spring:message code="registration.licence"/></strong></label>
            <spring:message code="holder.licence" var="licence"/>
            <form:input class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="${licence}" path="licence"/>
            <form:errors path="licence" cssClass="wrong"  element="p"></form:errors>
            <c:if test="${repeatedLicence eq true}">
                <p class="wrong"><spring:message code="error.repeatedLicence"/></p>
            </c:if>
            <c:if test="${wrongLicence eq true}">
                <p class="wrong"><spring:message code="error.badLicence"/></p>
            </c:if>
        </div>
        <br>
        <input type="submit" class="btn btn-primary custom-btn" value="Siguiente" path="submit" />
        <input type="button" class="btn btn-secondary" value="Cancelar" onclick="window.location='<c:url value="/"/>'"/>
    </form:form>
    <br>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
