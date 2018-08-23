<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<body class="body-background">zz
<nav class="navbar navbar-dark" style="background-color: #FFFFFF; padding-bottom: 0px;">
    <div class="container">
        <a class="navbar-brand" href="#">
            <h1 class="navbar-brand-home"><strong>Waldoc</strong></h1>
        </a>
    </div>
</nav>

<div class="jumbotron jumbotron-background">
    <div class="container padding-top-big padding-bottom-big">
        <p class="jumbotron-subtitle">Sacá turnos con los mejores medicos, al instante.</p>
        <div class="navbar-search-home">
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
                    <button class="btn btn-primary custom-btn" type="button"><a  class="a-white" href="specialists.html"><i class="fas fa-search"></i> Buscar</a></button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="margin-big">
        <p class="jumbotron-subtitle">Sacar un turno nunca fue tan facil</p>
        <p class="jumbotron-text">Seguí estos simples pasos</p>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="./img/1.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3>1. Defini tus sintomas</h3>
                <p class="doctor-text">Lorem ipsum dolor sit. Tenemos el medico adecuado.</p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row-reverse margin-bottom-medium">
        <img src="./img/2.jpg" class="image-rectangle-right">
        <div>
            <div class="list-home-right">
                <h3>2. Encontra al medico que mas te gusta.</h3>
                <p>Lorem ipsum dolor sit. Tenemos el medico adecuado.</p>
            </div>
        </div>
    </div>

    <div class="d-flex flex-row margin-bottom-medium">
        <img src="./img/3.jpg" class="image-rectangle">
        <div>
            <div class="list-home">
                <h3>3. Saca tu turno.</h3>
                <p>Lorem ipsum dolor sit. Tenemos el medico adecuado.</p>
            </div>
        </div>
    </div>
</div>

<!-- <div class="footer-grey">
  <div class="container">
    <p class="footer-text">© Copyright 2018. Waldoc</p>
  </div>
</div> -->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>

