/**
 * Created by estebankramer on 30/09/2018.
 */

// alert("Hola");

$(function() {
    $('#day').on('change', function() {
        var val = this.value;
        if(val == "no"){
            $('#time').prop('disabled', true);
            $('#time').val('no');
        }
        else{
            $('#time').prop('disabled', false);
            $('#time').val('no');
            $('#time option').hide().filter(function() {
                return this.value.indexOf( val + '_' ) === 0;
            })
                .show();
        }})
        .change();
});



$('#time').on('change', function() {
    var selected = $('#time option:selected');
    var day = selected.attr("day");
    var time = selected.attr("time");
    $('#appointment').children('input[type="hidden"]').remove();
    $('#appointment').append('<input type="hidden" name="time" value="'+time+'" id="time"/>');
    $('#appointment').append('<input type="hidden" name="day" value="'+day+'" id="day"/>');
});

$('.specialist-select').select2({theme: 'bootstrap4'});
// $('.custom-select').select2({theme: 'bootstrap4'});