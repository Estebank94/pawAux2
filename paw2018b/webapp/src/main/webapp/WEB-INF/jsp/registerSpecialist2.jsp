
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="body-background">
<nav class="navbar navbar-dark" style="background-color: #257CBF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="/">
            <h1><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="container">
    <br>
    <br>
    <h2>Completá tu perfil profesional.</h2>
    <p>Para aparecer en las búsquedas, vas a necesitar completar tu información profesional.</p>

    <hr style="border-top: 1px solid #D8D8D8 !important;">
    <form:form modelAttribute="professional" method="POST" action="/doctorProfile" accept-charset="ISO-8859-1" id="profile" enctype="multipart/form-data">
        <div>
            <label for="exampleFormControlFile1"><strong>Foto de perfil</strong></label>
            <form:input type="file" class="form-control-file" id="exampleFormControlFile1" path="avatar" name="exampleFormControlFile1"/>
            <form:errors path="avatar" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <br>
        <c:if test="${noCertificate eq true}">
            <div>
                <label for="exampleFormControlTextarea1"><strong>Descripción</strong></label>
                <form:textarea class="form-control" id="exampleFormControlTextarea1" rows="5" placeholder="Describi tu completar..." path="certificate"/>
                <form:errors path="certificate" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
            <br>
        </c:if>
        <%--<c:if test="${noCertificate eq false}"> Ya completaste tu descripción </c:if>--%>
        <c:if test="${noEducation eq true}">
            <div>
                <label for="exampleFormControlTextarea1"><strong>Educación</strong></label>
                <form:textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Describi tu estudios academicos..." path="education"/>
                <form:errors path="education" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
            <hr style="border-top: 1px solid #D8D8D8 !important;">
        </c:if>
        <%--<c:if test="${noEducation eq false}"> Ya completaste tu educación</c:if>--%>

        <c:if test="${noLanguage eq true}">
            <div>
                <div>
                    <label for="languages"><strong>Idiomas</strong></label>
                    <select class="custom-select" name="languages" id="languages" onchange="addInput(value, 'languageContainer', 'languages')">
                        <option value="no" label="Idioma" selected="Idioma"/>
                        <option value="Ingles" label="Ingles" />
                        <option value="Italiano" label="Italiano" />
                        <option value="Aleman" label="Aleman" />
                        <option value="Frances" label="Frances" />
                        <option value="Chino" label="Chino"/>
                    </select>
                    <form:errors path="languages" cssStyle="color: crimson"  element="p"></form:errors>
                </div>
                <br>
                <div>
                    <div>
                        <br>
                        <label>Estos son los idiomas que agregaste por ahora:</label>
                        <div class="row container" id="languageContainer">
                        </div>
                    </div>
                </div>
            </div>
            <hr style="border-top: 1px solid #D8D8D8 !important;">
        </c:if>
       <%--<c:if test="${noLanguage eq false}">Ya completaste los idiomas</c:if>--%>
        <div>
            <div>
                <label for="specialty"><strong>Especialidad</strong></label>
                <select id="specialty" class="custom-select" cssStyle="cursor: pointer;" onchange="addInput(value, 'addedSpecialties', 'specialty')">
                    <option value="noSpecialty" label="Especialidades" selected="Especialidades"/>
                    <c:forEach items="${specialtyList}" var="specialty">
                        <option value="${specialty.name}" label="${specialty.name}">
                    </c:forEach>
                </select>
                <%--FALTA AGREGAR VALIDACION--%>
                <form:errors path="specialty" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
            <br>
            <div>
                <br>
                <label>Estas son las especialidades que agregaste por ahora:</label>
                <div id="addedSpecialties" class="row container">
                </div>
            </div>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label for="insurance"><strong>Obra Social</strong></label>
                <select id="insurance" class="custom-select" cssStyle="cursor: pointer;" onchange="myFunc(value)">
                    <option value="no" label="Prepaga" selected="Prepaga"/>
                    <c:forEach items="${insuranceList}" var="insuranceName">
                        <option value="${insuranceName.name}" label="${insuranceName.name}"/>
                    </c:forEach>
                </select>
                <form:errors path="insurance" cssStyle="color: crimson"  element="p"></form:errors>
            </div>
            <br>
            <div>
                <div>
                    <div id="insuranceContainer">
                        <c:forEach items="${insurancePlan}" var="insurancePlanList">
                            <c:forEach items="${insurancePlanList.key}" var="insurancePlansName">
                                <div class="${insurancePlanList.key}" style="display: none">
                                    <c:forEach  items="${insurancePlanList.value}" var="insurancePlanValue">
                                        <input type="checkbox" id="insurancePlan" value="${insurancePlanValue}" label="${insurancePlanValue}">${insurancePlanValue}
                                        </br>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
                    <br>
                    <button type="button" class="btn btn-secondary" onclick="addInputSelect()">Agregar Planes</button>
                    <br>
                </div>
                <div>
                    <br>
                    <label>Estas son las obras sociales que agregaste por ahora:</label>
                    <div id="addedInsurances" class="row container">
                    </div>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label><strong>Horario de Trabajo</strong></label>
            </div>
            <br>
            <c:if test="${EmptyMonday eq true}">
            <div class="form-group row">
                    <label for="monStartWorkingHour" class="col-sm-1 col-form-label">Lunes</label>
                    <div id="monContainer" class="col-sm-4">
                        <div class="input-group">
                            <select class="custom-select" id="monStartWorkingHour" onchange="addStartWorkingHour(value, 'mon')">
                                <option value="no" label="Inicio" selected="Inicio"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                            <select class="custom-select" id="monEndWorkingHour" disabled="true" onchange="addEndWorkingHour(value, 'mon')">
                                <option value="no" label="Fin" selected="Fin"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        </div>
                    </div>
            </div>
            </c:if>
            <c:if test="${EmptyMonday eq false}"><p>Ya registraste tus horarios del dia Lunes<br></p></c:if>
            <c:if test="${EmptyTuesday eq true}">
                <div class="form-group row">
                <label class="col-sm-1 col-form-label">Martes</label>
                <div id="tueContainer" class="col-sm-4">
                    <div class="input-group">
                        <select class="custom-select" id="tueStartWorkingHour" path="workingHoursStart" onchange="addStartWorkingHour(value, 'tue')">
                            <option value="no" label="Inicio" selected="Inicio"/>
                            <c:forEach items="${professional.workingHours}" var="workingHour">
                            <option value="${workingHour}" label="${workingHour}">
                                </c:forEach>
                        </select>
                            <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        <select class="custom-select" id="tueEndWorkingHour" disabled="true" path="workingHoursEnd" onchange="addEndWorkingHour(value, 'tue')">
                            <option value="no" label="Fin" selected="Fin"/>
                            <c:forEach items="${professional.workingHours}" var="workingHour">
                            <option value="${workingHour}" label="${workingHour}">
                                </c:forEach>
                        </select>
                            <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${EmptyTuesday eq false}"><p>Ya registraste tus horarios del dia Martes <br></p></c:if>
            <c:if test="${EmptyWednesday eq true}">
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">Miercoles</label>
                    <div id="wedContainer" class="col-sm-4">
                        <div class="input-group">
                            <select class="custom-select" id="wedStartWorkingHour" path="workingHoursStart" onchange="addStartWorkingHour(value, 'wed')">
                                <option value="no" label="Inicio" selected="Inicio"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                            <select class="custom-select" id="wedEndWorkingHour" disabled="true" path="workingHoursEnd" onchange="addEndWorkingHour(value, 'wed')">
                                <option value="no" label="Fin" selected="Fin"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${EmptyWednesday eq false}"><p>Ya registraste tus horarios del dia Miercoles <br></p></c:if>
            <c:if test="${EmptyThursday eq true}">
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">Jueves</label>
                    <div class="col-sm-4">
                        <div id="thuContainer" class="input-group">
                            <select class="custom-select" id="thuStartWorkingHour" path="workingHoursStart" onchange="addStartWorkingHour(value, 'thu')">
                                <option value="no" label="Inicio" selected="Inicio"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                            <select class="custom-select" id="thuEndWorkingHour" disabled="true" path="workingHoursEnd" onchange="addEndWorkingHour(value, 'thu')">
                                <option value="no" label="Fin" selected="Fin"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${EmptyThursday eq false}"><p>Ya registraste tus horarios del dia Jueves <br></p></c:if>
            <c:if test="${EmptyFriday eq true}">
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">Viernes</label>
                    <div id="friContainer" class="col-sm-4">
                        <div class="input-group">
                            <select class="custom-select" id="friStartWorkingHour" path="workingHoursStart" onchange="addStartWorkingHour(value, 'fri')">
                                <option value="no" label="Inicio" selected="Inicio"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                            <select class="custom-select" id="friEndWorkingHour" disabled="true" path="workingHoursEnd" onchange="addEndWorkingHour(value, 'fri')">
                                <option value="no" label="Fin" selected="Fin"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${EmptyFriday eq false}"> <p>Ya registraste tus horarios del dia Viernes <br></p></c:if>
            <c:if test="${EmptySaturday eq true}">
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">Sabado</label>
                    <div id="satContainer" class="col-sm-4">
                        <div class="input-group">
                            <select class="custom-select" id="satStartWorkingHour" path="workingHoursStart" onchange="addStartWorkingHour(value, 'sat')">
                                <option value="no" label="Inicio" selected="Inicio"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>--%>
                            <select class="custom-select" id="satEndWorkingHour" disabled="true" path="workingHoursEnd" onchange="addEndWorkingHour(value, 'sat')">
                                <option value="no" label="Fin" selected="Fin"/>
                                <c:forEach items="${professional.workingHours}" var="workingHour">
                                <option value="${workingHour}" label="${workingHour}">
                                    </c:forEach>
                            </select>
                                <%--<form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>--%>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${EmptySaturday eq false}"><p> Ya registraste tus horarios del dia Sabado<br></p></c:if>
        </div>
        <br>
        <div class="container row">
            <input type="submit" class="btn btn-primary" value="Registrar" path="submit"/>
            <span style="margin-left: 8px;"></span>
            <c:if test="${cancelButton eq true}">
                <input type="button" class="btn btn-secondary" value="Cancelar" onclick="cancel()"/>
            </c:if>
            <c:if test="${cancelButton eq false}">
                <input type="button" class="btn btn-secondary" value="Cancelar" onclick="window.location='/'"/>
            </c:if>
        </div>
    </form:form>
    <br>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="<c:url value="/resources/javascript/registerSpecialists.js"/>"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
