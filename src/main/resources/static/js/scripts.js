$(document).ready(function () {
    let x930 = window.matchMedia("(max-width: 930px)");
    widowSize930(x930);
    x930.addListener(widowSize930);

    $('#delete-book').click(function(){
        let id = $('#book-id').val();
        window.location.href = '/delete/'+id;
    });

    $('#newShelfInput').blur(function(){
        $(this).closest('div').removeClass('has-error');
    });
    $('#newShelf').on('hide.bs.modal', function (e) {
        $('#newShelfInput').closest('div').removeClass('has-error');
    });

    $('#renameShelfInput').blur(function(){
        $(this).closest('div').removeClass('has-error');
    });
    $('#renameShelf').on('hide.bs.modal', function (e) {
        $('#renameShelfInput').closest('div').removeClass('has-error');
    });

    $('#overlay').click(function () {
        showHide();
    });

    $('#reading-state-radio input[type=radio]').on('change',function(){
        let state = $(this).val();
        let id = $('#bu-id').val()
        if (state == 'TR') {
            return false;
        } else if (state == 'CR') {

        } else if (state == 'R') {

        }
    })
});

function widowSize930() {
    if($(window).width() < 930) {
        $('.sidebar-nav').addClass('sidebar-close-nav');
        $('.sidebar').addClass('sidebar-close');
        $('.main-content').addClass('main-content-close');
        $('.sidebar-open-icon').addClass('sidebar-open-click');
    }
    $('#overlay').addClass('hidden');
}

function showHide() {
    $('.sidebar-nav').toggleClass('sidebar-close-nav');
    $('.sidebar').toggleClass('sidebar-close');
    $('.sidebar-open-icon').toggleClass('sidebar-open-click');
    $('.main-content').toggleClass('main-content-close');
    if($(window).width() < 930) {
        $('#overlay').toggleClass('hidden');
        $('.sidebar').removeClass('yt-scrollbar')
    }
}

function saveShelf() {
    let selector = $('#newShelfInput');
    let val = selector.val();
    if (val == ''){
        selector.closest('div').addClass('has-error');
    } else {
        $.get('/createShelf/' + val);
        location.reload();
    }
}

function renameShelf() {
    let selector = $('#renameShelfInput');
    let name = $('#page-name').text();
    let val = selector.val();
    if (val == ''){
        selector.closest('div').addClass('has-error');
    } else {
        $.get('/renameShelf/' + name + '/' + val);
        window.location.href = '/getShelf/' + encodeURIComponent(val);
    }
}

function shelfChange(shelf) {
    let checkboxSelector = $('#checkbox'+shelf);
    let checked = checkboxSelector.is(':checked');
    let id = $('#book-id').val();
    let name = checkboxSelector.val()
    if (checked){
        $.get('/addToShelf/'+ id + '/' + name, function (response) {
            let obj = response[shelf];
            $('#sidebar'+shelf).text(obj[1]-1);
        });
    } else if (!checked) {
        $.get('/removeFromShelf/'+ id + '/' + name, function (response) {
            let obj = response[shelf];
            $('#sidebar'+shelf).text(obj[1]-1);
        });
    }
}

function deleteShelf() {
    let shelf = $('#page-name').text();
    window.location.href = '/deleteShelf/' + shelf;
}