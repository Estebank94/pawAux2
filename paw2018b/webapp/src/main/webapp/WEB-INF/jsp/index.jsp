<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2-bootstrap4.css" />">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #FFFFFF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <h1 class="navbar-brand-home"><strong><spring:message code="brand.name"/></strong></h1>
        </a>
        <a>
            <div class="row">
                <div class="dropdown center-vertical">
                    <%--<c:if test="${loggedInName != null}">--%>
                    <%--</c:if>--%>
                    <security:authorize access="!isAuthenticated()">
                    <button class="btn btn-light dropdown-toggle" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; color: #257CBF !important;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="register"/>
                    </button>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button" onclick="window.location='<c:url value="/patientRegistration"/>'"><spring:message code="dropdown.patientRegister"/></button>
                        <button class="dropdown-item" type="button" onclick="window.location='<c:url value="/doctorRegistration"/>'"><spring:message code="dropdown.doctorRegister"/></button>
                    </div>
                    </security:authorize>
                </div>
                <div class="center-vertical">
                    <security:authorize access="!isAuthenticated()">
                        <button class="btn btn-light" style="background-color:transparent; border-color:transparent; color: #257CBF !important;" type="button" onclick="window.location='<c:url value="/showLogIn"/>'">
                            <spring:message code="login.message"/>
                        </button>
                    </security:authorize>
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/logout" var="logout"/>
                        <form:form action="logout" method="post">
                            <security:authentication property="principal.username" var="userName"/>
                            <div class="dropdown center-vertical">
                                <button class="btn btn-light dropdown-toggle" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; color: #257CBF !important;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><b><c:out value="${userName}"/></b></button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                    <button class="dropdown-item" type="submit"><spring:message code="logout.message"/></button>
                                    <security:authorize access="hasRole('ROLE_DOCTOR')">
                                        <button class="btn btn-light btn-primary custom-btn dropdown-item" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; !important;" type="button" onclick="window.location='<c:out value="${pageContext.request.contextPath}/doctorPanel/"/>'">
                                            <spring:message code="dropdown.viewProfile"/>
                                        </button>
                                        <button class="btn btn-light btn-primary custom-btn dropdown-item" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; !important;" type="button" onclick="window.location='<c:out value="specialist/${doctorID}"/>'">
                                            <spring:message code="dropdown.viewInfo"/>
                                        </button>
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_PATIENT') and !hasRole('ROLE_DOCTOR')">
                                        <button class="btn btn-light dropdown-item" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; !important;" type="button" onclick="window.location='<c:out value="${pageContext.request.contextPath}/patientPanel/"/>'">
                                            <spring:message code="dropdown.viewProfile"/>
                                        </button>
                                    </security:authorize>

                                    <%--<button class="dropdown-item" type="button" onclick="window.location='/doctorPanel'">Ver perfil</button>--%>
                                </div>
                            </div>
                        </form:form>
                    </security:authorize>
                </div>
                <hr>
                    <%--<security:authorize access="hasRole('DOCTOR')">--%>
                        <%--<button class="btn btn-light btn-primary custom-btn" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; color: #257CBF !important;" type="button" onclick="window.location='${pageContext.request.contextPath}/doctorPanel/'">--%>
                           <%--Perfil Doctor--%>
                        <%--</button>--%>
                    <%--</security:authorize>--%>
                    <%--<security:authorize access="hasRole('PACIENTE')">--%>
                        <%--<button class="btn btn-light" style="margin-right: 8px; background-color:transparent; border-color:#257CBF; color: #257CBF !important;" type="button" onclick="window.location='${pageContext.request.contextPath}/patientPanel/'">--%>
                            <%--Perfil Paciente--%>
                        <%--</button>--%>
                    <%--</security:authorize>--%>
                <hr>
            </div>
        </a>
    </div>
</nav>

<div class="jumbotron jumbotron-background">
    <div class="container padding-top-big padding-bottom-big">
        <p class="jumbotron-subtitle"><spring:message code="brand.slogan" /></p>
        <div class="navbar-search-home">
            <c:url value="/processForm" var="processForm"/>
            <form:form action="${processForm}" method="POST" modelAttribute="search" accept-charset="ISO-8859-1">
                <div class="input-group container">
                    <spring:message code="holder.doctorName" var="name"/>
                    <form:input type="text" aria-label="Buscar por nombre del médico" placeholder="${name}" class="form-control" path="name"/>
                    <form:select class="custom-select" id="speciality" path="specialty" cssStyle="cursor: pointer;">
                        <form:option value="noSpecialty" label="Especialidad" selected="Especialidad"/>
                        <form:options items="${specialtyList}" itemValue="name" itemLabel="name" />
                    </form:select>
                    <%--<form:input type="text" aria-label="Buscar por especialidad" placeholder="Especialidad" class="form-control" path="specialty"/>--%>
                    <form:select class="custom-select" id="insurance" path="insurance" cssStyle="cursor: pointer;">
                        <form:option value="no" label="Prepaga" selected="Prepaga"/>
                        <form:options items="${insuranceList}" itemValue="name" itemLabel="name" />
                    </form:select>
                    <div class="input-group-append">
                        <input type="submit" class="btn btn-primary custom-btn" value="Buscar" path="submit"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<div class="container">
    <div class="margin-big">
        <p class="jumbotron-subtitle"><spring:message code="explanation.title" /></p>
        <p class="jumbotron-text"><spring:message code="explanation.subtitle" /></p>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="https://i.imgur.com/StIDems.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3><spring:message code="explanation.searchTitle" /></h3>
                <p class="doctor-text"><spring:message code="explanation.searchSubtitle" /></p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row-reverse margin-bottom-medium">
        <img src="https://i.imgur.com/N7X4FiE.jpg" class="image-rectangle-right">
        <div>
            <div class="list-home-right">
                <h3><spring:message code="explanation.chooseTitle" /></h3>
                <p><spring:message code="explanation.chooseSubtitle" /></p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="https://i.imgur.com/yjHKj1P.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3><spring:message code="explanation.reserveTitle" /></h3>
                <p><spring:message code="explanation.reserveSubtitle" /></p>
            </div>
        </div>
    </div>
</div>

<!--<div class="footer-grey">
  <div class="container">
    <p class="footer-text">© Copyright 2018. Waldoc</p>
  </div>
</div>-->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

<%--select2 dropdown--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<script src="<c:url value="/resources/javascript/dropdowns.js"/>"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>