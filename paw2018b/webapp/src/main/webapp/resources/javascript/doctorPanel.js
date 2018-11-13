
function cancelAppointment(doctorid, day, time, message) {

    //alert("cancelar appointment");
    if (confirm(message)){
        $('#appointment').append('<input type="hidden" name="doctorid" value="'+doctorid+'" id="doctorid"/>');
        $('#appointment').append('<input type="hidden" name="day" value="'+day+'" id="day"/>');
        $('#appointment').append('<input type="hidden" name="time" value="'+time+'" id="time"/>');
        $( "#appointment" ).submit();
    } else {

    }

}