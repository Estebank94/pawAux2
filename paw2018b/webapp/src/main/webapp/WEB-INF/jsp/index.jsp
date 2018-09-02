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
<nav class="navbar navbar-dark" style="background-color: #FFFFFF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="/">
            <h1 class="navbar-brand-home"><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="jumbotron jumbotron-background">
    <div class="container padding-top-big padding-bottom-big">
        <p class="jumbotron-subtitle">Sacá turnos con los mejores médicos, al instante.</p>
        <div class="navbar-search-home">
            <form:form action="processForm" method="GET" modelAttribute="search" accept-charset="ISO-8859-1">
                <div class="input-group container">
                    <form:input type="text" aria-label="Buscar por nombre del médico" placeholder="Nombre del médico" class="form-control" path="name"/>
                    <form:input type="text" aria-label="Buscar por especialidad" placeholder="Especialidad" class="form-control" path="specialty"/>
                    <form:select class="custom-select" id="insurance" path="insurance" items="${insuranceList}" itemLabel="name" itemValue="id">
                        <form:option selected="Prepaga" value="">Prepaga</form:option>
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
        <p class="jumbotron-subtitle">Sacar un turno nunca fue tan fácil</p>
        <p class="jumbotron-text">Seguí estos simples pasos</p>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="/resources/images/1.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3>Buscá</h3>
                <p class="doctor-text">Contamos con los mejores medicos.</p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row-reverse margin-bottom-medium">
        <img src="/resources/images/2.jpg" class="image-rectangle-right">
        <div>
            <div class="list-home-right">
                <h3>Elegí</h3>
                <p>Lee las especificaciones de los medicos y elegi el adecuado.</p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="/resources/images/3.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3>Reservá</h3>
                <p>Usa la informacion de contacto para reservar un turno.</p>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>

