<%--
  Created by IntelliJ IDEA.
  User: estebankramer
  Date: 20/09/2018
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Waldoc</title>
    <meta name="description" content="Roughly 155 characters">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="style.css">
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

<div class="container">
    <br>
    <br>
    <h2>¡Registrate como especialista!</h2>
    <p>Completa tus datos profesionales.</p>

    <hr style="border-top: 1px solid #D8D8D8 !important;">
    <form>
        <div>
            <label for="exampleFormControlFile1">Foto de perfil</label>
            <input type="file" class="form-control-file" id="exampleFormControlFile1">
        </div>
        <br>
        <div>
            <label for="exampleFormControlTextarea1">Descripcion</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="5" placeholder="Describi tu completar..."></textarea>
        </div>
        <br>
        <div>
            <label for="exampleFormControlTextarea1">Educacion</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Describi tu estudios academicos..."></textarea>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label for="inputState">Idiomas</label>
                <select id="inputState" class="custom-select">
                    <option selected>Idioma...</option>
                    <option>Ingles</option>
                </select>
            </div>
            <br>
            <div>
                <div>
                    <label>Estos son los idiomas que agregaste por ahora:</label>
                    <div class="row container">
                        <button type="button" class="btn btn-primary" style="margin-right: 8px">
                            Ingles <span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle"></i></span>
                        </button>
                        <button type="button" class="btn btn-primary">
                            Frances <span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle"></i></span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label for="inputState">Obra Social</label>
                <select id="inputState" class="custom-select">
                    <option selected>Obra Social</option>
                    <option>OSDE</option>
                </select>
            </div>
            <br>
            <div>
                <div>
                    <label for="inputState">Elegi los planes de la obra social</label>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                        <label class="form-check-label" for="inlineCheckbox1">210</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                        <label class="form-check-label" for="inlineCheckbox2">310</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                        <label class="form-check-label" for="inlineCheckbox2">410</label>
                    </div>
                    <br>
                    <button class="btn btn-secondary">Agregar Planes</button>
                </div>
                <br>
                <div>
                    <label>Estas son las obras sociales que agregaste por ahora:</label>
                    <div class="row container">
                        <button type="button" class="btn btn-primary" style="margin-right: 8px">
                            OSDE <span class="badge badge-light" style="margin-right: 8px">3</span> <span><i class="fas fa-times-circle"></i></span>
                            <span class="sr-only">unread messages</span>
                        </button>
                        <button type="button" class="btn btn-primary">
                            Swiss Medical <span class="badge badge-light" style="margin-right: 8px">5</span> <span><i class="fas fa-times-circle"></i></span>
                            <span class="sr-only">unread messages</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label>Horario de trabajo</label>
            </div>
            <br>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-1 col-form-label">Lunes</label>
                <div class="col-sm-4">
                    <div class="input-group">
                        <select class="custom-select" id="ubicacion">
                            <option selected>Inicio</option>
                            <option value="1">7</option>
                            <option value="2">8</option>
                            <option value="3">9</option>
                        </select>
                        <select class="custom-select" id="ubicacion">
                            <option selected>Fin</option>
                            <option value="1">7</option>
                            <option value="2">8</option>
                            <option value="3">9</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <button type="submit" class="btn btn-primary">Siguiente</button>
    </form>
    <br>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
