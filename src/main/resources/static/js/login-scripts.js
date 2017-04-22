
$(document).ready(function() {
    $.backstretch("/img/backgrounds/1.jpg");

    $('.l-form input[type="text"], .l-form input[type="password"], .l-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });

    $('.r-form input[type="text"], .r-form input[type="password"], .r-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
});
