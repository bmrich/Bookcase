$(document).ready(function () {
    $('#delete-book').click(function(){
        var id = $('#book-id').val();
        window.location.href = '/delete/'+id;
    });
});

function showHide() {
    $('.sidebar-nav').toggleClass('sidebar-close-nav');
    $('.sidebar').toggleClass('sidebar-close');
    $('.main-content').toggleClass('main-content-close');
    $('.sidebar-open-icon').toggleClass('sidebar-open-click')
}

function saveShelf() {
    var val = $('#newShelfInput').val();
    $.get('/createShelf/'+ val);
    location.reload();
}

function shelfChange(shelf) {
    var checked = $('#checkbox'+shelf).is(':checked');
    var id = $('#book-id').val();
    var name = $('#checkbox'+shelf).val()
    console.log(name)
    if (checked){
        $.get('/addToShelf/'+ id + '/' + name);
    } else if (!checked) {
        console.log('remove book id '+id+ ' from shelf' +shelf);
    }
}
