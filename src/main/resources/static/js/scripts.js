$(document).ready(function () {
    let x = window.matchMedia("(max-width: 930px)");
    widowSize930(x);
    x.addListener(widowSize930);

    $('#delete-book').click(function(){
        let id = $('#book-id').val();
        window.location.href = '/delete/'+id;
    });


    $('#newShelfInput').blur(function(){
        $(this).closest('div').removeClass('has-error');
    });
    $('#newShelf').on('hide.bs.modal', function (e) {
        $('#newShelfInput').closest('div').removeClass('has-error');
    })
});

function widowSize930() {
    if($(window).width() <= 930) {
        $('.sidebar-nav').addClass('sidebar-close-nav');
        $('.sidebar').addClass('sidebar-close');
        $('.main-content').addClass('main-content-close');
        $('.sidebar-open-icon').addClass('sidebar-open-click')
    }
}

function showHide() {
    $('.sidebar-nav').toggleClass('sidebar-close-nav');
    $('.sidebar').toggleClass('sidebar-close');
    $('.main-content').toggleClass('main-content-close');
    $('.sidebar-open-icon').toggleClass('sidebar-open-click')
}

function saveShelf() {
    let selector = $('#newShelfInput');
    let val = selector.val();
    if (val == ''){
        selector.closest('div').addClass('has-error')
    } else {
        $.get('/createShelf/' + val);
        location.reload();
    }
}

function shelfChange(shelf) {
    let checkboxSelector = $('#checkbox'+shelf);
    let checked = checkboxSelector.is(':checked');
    let id = $('#book-id').val();
    let name = checkboxSelector.val()
    if (checked){
        $.get('/addToShelf/'+ id + '/' + name);
    } else if (!checked) {
        $.get('/removeFromShelf/'+ id + '/' + name);
    }
}
