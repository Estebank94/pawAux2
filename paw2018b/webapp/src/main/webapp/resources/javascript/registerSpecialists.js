/**
 * Created by estebankramer on 27/09/2018.
 */

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

function removeSpaces(val){
    return val.split(' ').join('');
}

function myFunc(val) {
    var container = classConcatenator(val);
    $("#insuranceContainer").children().hide();
    $("."+container).show();
}


function addInput(val, container, name){
    var id = removeSpaces(val);
    if(val!== "no" &&  $("#" + id).length === 0){
        $('#profile').append('<input type="hidden" name="'+name+'" value="'+val+'" class="'+id+'" id="'+name+'"/>');
        $('#'+ container).append('<button type="button" class="btn btn-primary"  id="'+id+'" style="margin-right: 8px; margin-bottom: 8px">'+
            val + '<span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle">'+'</i></span></button>');

    }
}

function addInputSelect(){
    var insurance = $("#insurance").val();
    var id = removeSpaces(insurance);
    insurance = classConcatenator(insurance);

    var selected = [];
    $('.'+insurance+' input:checked').each(function() {
        selected.push($(this).attr('value'));
    });

    if(insurance!== "no" && $("#" + id).length === 0 && selected.length > 0 ){
        $('#profile').append('<input type="hidden" name="insurancePlan"  class="'+id+'" value="' + selected + '" id="insurancePlan"/>');
        $('#profile').append('<input type="hidden" name="insurance" value="'+insurance+'" class="'+id+'" id="insurance"/>');
        $('#addedInsurances').append('<button type="button" class="btn btn-primary" id="'+id+'" style="margin-right: 8px; margin-bottom: 8px;">'+
            $("#insurance").val() + '<span class="badge badge-light" style="margin-left: 8px; margin-right: 4px;">'+ selected.length +'</span><span style="margin-right: 4px; margin-left: 8px"><i class="fas fa-times-circle">'+'</i></span></button>');
    }
}

function addStartWorkingHour(val, day){
    const name = day+"start";
    $('#profile').children('#'+name).remove();
    $('#'+day+'Container').children('p').remove();
    $('#'+day+'Container .input-group').removeClass('animated shake');
    if(val=="no"){
        $('#'+day+'EndWorkingHour').prop('disabled', true);
        $('#profile').children('#'+val+"end").remove();
        $('#'+day+'EndWorkingHour').val('no');
    }
    if(val!== "no"){
        $('#'+day+'EndWorkingHour').prop('disabled', false);
        const valEnd = $('#'+day+'EndWorkingHour').val();
        if(valEnd!= "no"){
            if(getHours(val) => getHours(valEnd)){
                $('#'+day+'Container .input-group').addClass('animated shake');
                $('#'+day+'Container').append('<p style="color: red; font-size: 12px; margin-top: 8px">La hora de inicio no puede ser mayor o igual que la de fin.</p>');
                $('#'+day+'StartWorkingHour').val('no');
                $('#'+day+'EndWorkingHour').val('no');
                $('#'+day+'EndWorkingHour').prop('disabled', true);
            } else{
                $('#profile').append('<input type="hidden" name="'+name+'" value="'+val+'" id="'+name+'"/>');
            }
        }
    }
}

function addEndWorkingHour(val, day){
    const name = day+"end";
    const nameStart = day+"start";

    $('#profile').children('#'+name).remove();
    $('#profile').children('#'+nameStart).remove();
    $('#'+day+'Container').children('p').remove();
    $('#'+day+'Container .input-group').removeClass('animated shake');

    if(val=="no"){
        $('#profile').children('#'+val+"start").remove();
        $('#'+day+'StartWorkingHour').val('no');
        $('#'+day+'EndWorkingHour').prop('disabled', true);
    }
    if(val!== "no"){
        const valStart = $('#'+day+'StartWorkingHour').val();
        if(getHours(val) <= getHours(valStart)){
            $('#'+day+'StartWorkingHour').val('no');
            $('#'+day+'EndWorkingHour').val('no');
            $('#'+day+'EndWorkingHour').prop('disabled', true);
            $('#'+day+'Container .input-group').addClass('animated shake');
            $('#'+day+'Container').append('<p style="color: red; font-size: 12px; margin-top: 8px">La hora de inicio no puede ser mayor o igual que la de fin.</p>');
        }
        else{
            $('#profile').append('<input type="hidden" name="'+name+'" value="'+val+'" id="'+name+'"/>');
            $('#profile').append('<input type="hidden" name="'+nameStart+'" value="'+valStart+'" id="'+nameStart+'"/>');
        }
    }
}

function getHours(val){
    var index = val.indexOf(":");
    if(index > 0){
        val = val.slice(0,index);
    }
    return val;
}

$("#addedInsurances").on("click", ".btn", function(button){
    var id = button.target.id;
    $('#'+id).remove();
    $('#profile').children('.'+id).remove();
});

$("#insuranceContainer").on("click", ".btn", function(button){
    var id = button.target.id;
    $('#'+id).remove();
    $('#profile').children('.'+id).remove();
});

$("#languageContainer").on("click", ".btn", function(button){
    var id = button.target.id;
    $('#'+id).remove();
    $('#profile').children('.'+id).remove();
});

$("#addedSpecialties").on("click", ".btn", function(button){
    var id = button.target.id;
    $('#'+id).remove();
    $('#profile').children('.'+id).remove();
});