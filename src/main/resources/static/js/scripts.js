$(document).ready(function () {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $('[data-toggle=offcanvas]').click(function () {
        $('.row-offcanvas').toggleClass('active');
    });

    $('.collapse').on('show.bs.collapse', function (e) {
        $('.collapse').not(e.target).removeClass('in');
    });

    $('.dropdown-menu-center').click(function(e) {
        e.stopPropagation();
    });

    $('#delete-book').click(function(){
        var id = $('#delete-hidden').val();
        window.location.href = '/delete/'+id;
    });
});
