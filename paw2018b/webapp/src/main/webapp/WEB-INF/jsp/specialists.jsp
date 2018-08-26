<%--
  Created by IntelliJ IDEA.
  User: martinascomazzon
  Date: 25/8/18
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <a class="navbar-brand" href="#">
            <h1><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="navbar-search">
    <div class="input-group container">
        <input type="text" aria-label="Buscar por especialista" placeholder="Buscar por especialista, sintoma o procedimiento..." class="form-control">
        <select class="custom-select col-sm-3" id="ubicacion">
            <option selected>Ubicación</option>
            <option value="1">Palermo</option>
            <option value="2">Belgrano</option>
            <option value="3">Recoleta</option>
        </select>
        <select class="custom-select col-sm-3" id="prepaga">
            <option selected>Prepaga y Plan</option>
            <option value="1">OSDE</option>
            <option value="2">Swiss Medical</option>
            <option value="3">Omint</option>
        </select>
        <div class="input-group-append">
            <button class="btn btn-outline-light" type="button"> <i class="fas fa-search"></i> Buscar</button>
        </div>
    </div>
</div>


<div class="main container">
    <div class="row">
        <div class="col-sm-12">
            <div class="card card-doctor d-flex flex-row">
                <img src="http://cdn1.thr.com/sites/default/files/2017/08/gettyimages-630421358_-_h_2017.jpg" class="avatar">
                <div class="card-body">
                    <div class="card-text">
                        <h3>Dr. Andres Miller</h3>
                        <p class="doctor-specialty">Medico Clinico</p>
                        <br>
                        <div class="row container">
                            <i class="fas fa-star star-yellow"></i>
                            <i class="fas fa-star star-yellow"></i>
                            <i class="fas fa-star star-yellow"></i>
                            <i class="fas fa-star star-yellow"></i>
                            <i class="fas fa-star star-grey"></i>
                        </div>
                        <p class="doctor-text">"Muy buena atencion, muy puntual"</p>
                        <br>
                        <p class="doctor-text"><i class="far fa-clock"></i>  Lunes a Viernes de 8 a 16hs</p>
                        <p class="doctor-text"><i class="fas fa-map-marker-alt"></i>  Av. Libertador 3000, Buenos Aires</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
