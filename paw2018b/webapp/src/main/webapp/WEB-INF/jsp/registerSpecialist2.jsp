
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
    <link rel="stylesheet" type="text/css" href="style.css">
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
    <h2>¡Registrate como especialista!</h2>
    <p>Completa tus datos profesionales.</p>

    <hr style="border-top: 1px solid #D8D8D8 !important;">
    <form:form modelAttribute="professional" method="POST" action="doctorProfile/${doctor.id}" accept-charset="ISO-8859-1" id="profile">
        <div>
            <label for="exampleFormControlFile1">Foto de perfil</label>
            <form:input type="file" class="form-control-file" id="exampleFormControlFile1" path="avatar"/>
            <form:errors path="avatar" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <br>
        <div>
            <label for="exampleFormControlTextarea1">Descripcion</label>
            <form:textarea class="form-control" id="exampleFormControlTextarea1" rows="5" placeholder="Describi tu completar..." path="description"/>
            <form:errors path="description" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <br>
        <div>
            <label for="exampleFormControlTextarea1">Educacion</label>
            <form:textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Describi tu estudios academicos..." path="education"/>
            <form:errors path="education" cssStyle="color: crimson"  element="p"></form:errors>
        </div>
        <hr style="border-top: 1px solid #D8D8D8 !important;">
        <div>
            <div>
                <label for="languages">Idiomas</label>
                <select class="custom-select" name="languages" id="languages" onchange="addInput(value, 'languageContainer')">
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
        <div>
            <div>
                <label for="specialty">Especialidad</label>
                <select id="specialty" class="custom-select" cssStyle="cursor: pointer;" onchange="addInput(value, 'addedSpecialties')">
                    <option value="no" label="Prepaga" selected="Prepaga"/>
                    <c:forEach items="${specialtyList}" var="specialtyName">
                        <option value="${specialtyName.name}" label="${specialtyName.name}"/>
                    </c:forEach>
                </select>
                <%--FALTA AGREGAR VALIDACION--%>
                <%--<form:errors path="" cssStyle="color: crimson"  element="p"></form:errors>--%>
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
                <label for="insurance">Obra Social</label>
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
                <label>Horario de trabajo</label>
            </div>
            <br>
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-1 col-form-label">Lunes</label>
                <div class="col-sm-4">
                    <div class="input-group">
                        <form:select class="custom-select" id="ubicacion" path="workingHoursStart">
                            <option selected>Inicio</option>
                            <option value="1">7</option>
                            <option value="2">8</option>
                            <option value="3">9</option>
                        </form:select>
                        <form:errors path="workingHoursStart" cssStyle="color: crimson"  element="p"></form:errors>
                        <form:select class="custom-select" id="ubicacion" path="workingHoursEnd">
                            <option selected>Fin</option>
                            <option value="1">7</option>
                            <option value="2">8</option>
                            <option value="3">9</option>
                        </form:select>
                        <form:errors path="workingHoursEnd" cssStyle="color: crimson"  element="p"></form:errors>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <input type="submit" class="btn btn-primary" value="Registrar" path="submit"/>
    </form:form>
    <br>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>

    function classConcatenator(val){
        var index = val.indexOf(" ");
        var val2 = "";
        if(index > 0){
            var aux;
            aux = val.slice(0,index);
            val2 = "."+val.slice(index+1, val.length);
            val = aux;
            val=val+val2;
        }
        return val;
    }
    function myFunc(val) {
        var container = classConcatenator(val);
        $("#insuranceContainer").children().hide();
        $("."+container).show();
    }

    // function addInput(val){
    //     $('#profile').append('<input type="hidden" name="languages" value="'+val+'" id="languages"/>');
    // }

    function addInput(val, container){
        if(val!== "no" &&  $("#" + val).length === 0){
            $('#profile').append('<input type="hidden" name="languages" value="'+val+'" id="languages"/>');
            $('#'+ container).append('<button type="button" class="btn btn-primary"  id="'+val+'" style="margin-right: 8px">'+
                val + '<span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle">'+'</i></span></button>');

        }
    }

    function addInputSelect(){
        var insurance = $("#insurance").val();
        insurance = classConcatenator(insurance);

        $('#profile').append('<input type="hidden" name="insurance" value="'+insurance+'" id="insurance"/>');

        var selected = [];
        $('.'+insurance+' input:checked').each(function() {
            selected.push($(this).attr('value'));
        });

        $('#profile').append('<input type="hidden" name="insurancePlan" value="'+selected+'" id="insurancePlan"/>');

        if(insurance!== "no" && $("#" + insurance).length === 0 && selected.length > 0){
            $('#profile').append('<input type="hidden" name="languages" value="'+insurance+'" id="languages"/>');
            $('#addedInsurances').append('<button type="button" class="btn btn-primary"  id="'+insurance+'" style="margin-right: 8px; margin-bottom: 8px;" data-toggle="tooltip" data-placement="bottom" title="Tooltip on bottom">'+
                $("#insurance").val() + '<span class="badge badge-light" style="margin-left: 8px; margin-right: 4px;">'+ selected.length +'</span><span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle">'+'</i></span></button>');

        }
    }

</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
