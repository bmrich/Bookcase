$(document).ready(function () {
    $('#delete-book').click(function(){
        var id = $('#delete-hidden').val();
        window.location.href = '/delete/'+id;
    });
});

function showHide() {
    $('.sidebar-nav').toggleClass('sidebar-close-nav');
    $('.sidebar').toggleClass('sidebar-close');
    $('.main-content').toggleClass('main-content-close');
    $('.sidebar-open-icon').toggleClass('sidebar-open-click')
}
