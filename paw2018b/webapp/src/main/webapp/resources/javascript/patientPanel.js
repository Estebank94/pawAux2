/**
 * Created by estebankramer on 07/11/2018.
 */

function cancelAppointment(doctorid, day, time) {
    alert("cancelar appointment");
    $('#appointment').append('<input type="hidden" name="doctorid" value="'+doctorid+'" id="doctorid"/>');
    $('#appointment').append('<input type="hidden" name="day" value="'+day+'" id="day"/>');
    $('#appointment').append('<input type="hidden" name="time" value="'+time+'" id="time"/>');
    $( "#appointment" ).submit();
}