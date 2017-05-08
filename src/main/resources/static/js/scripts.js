$(document).ready(function () {
    let x930 = window.matchMedia("(max-width: 930px)");
    widowSize930(x930);
    x930.addListener(widowSize930);

    document.getElementById('submit-btn').addEventListener('click', booksearch, false);
    document.getElementById('submit-btn-nav').addEventListener('click', booksearchNav, false);

    $('#newShelfInput').blur(function () {
        $(this).closest('div').removeClass('has-error');
    });
    $('#newShelf').on('hide.bs.modal', function () {
        $('#newShelfInput').closest('div').removeClass('has-error');
    });

    $('#renameShelfInput').blur(function () {
        $(this).closest('div').removeClass('has-error');
    });
    $('#renameShelf').on('hide.bs.modal', function (e) {
        $('#renameShelfInput').closest('div').removeClass('has-error');
    });

    document.getElementById('overlay').addEventListener('click', showHide, false);

    if(document.getElementById('bu-id') != null) {
        document.getElementById('to-read-submit').addEventListener('click', toRead, false);
        document.getElementById('start-date-submit').addEventListener('click', startDate, false);
        document.getElementById('date-finished-submit').addEventListener('click', dateFinished, false);
    }

    if(document.getElementById('des-container') != null){
        let id = document.getElementById('api-id').value;
        let url = 'https://www.googleapis.com/books/v1/volumes/'+id;
        $.get(url, function(data){
            let des = data.volumeInfo.description;
            let res = document.getElementById('des-container');

            $.parseHTML(des).forEach(function (elem) {
                res.appendChild(elem);
            });
        });
    }

    $('#delete-book').click(function () {
        let id = $('#book-id').val();
        window.location.href = '/delete/' + id;
    });

    $('[name="readingState"]:radio').on('click', function (e) {
        e.preventDefault();
        let state = $(this).val();
        let val = $('#currentState').val();

        if (state == 'TR' && val != 'TR') {
            $('#toReadModal').modal('show');
        } else if (state == 'CR' && val != 'CR') {
            $('#startDateModal').modal('show');
        } else if (state == 'R' && val != 'R') {
            $('#finishDateModal').modal('show');
        }
    });

    $('#startDateModal').on('hide.bs.modal', function (e) {
        remove_err_1();
        $('#start-date-input').val('');

        remove_err_2();
        $('#currentPage').val('');
    });

    $('#finishDateModal').on('hide.bs.modal', function (e) {
        $('#err3-group').removeClass('has-error');
        $('#err3-message').addClass('hidden');
        $('#date-finished-input').val('');
    });

    $('#updateModal').on('hide.bs.modal', function () {
        $('#err5-message').addClass('hidden');
        $('#err5-group').removeClass('has-error');
        $('#update-page-btn').off('click');

        $('#err4-message').addClass('hidden');
        $('#err4-group').removeClass('has-error');
        $('#update-input-btn').off('click');
        $('#update-input').val('');
    })
});

function remove_err_1() {
    $('#err1-group').removeClass('has-error');
    $('#err1-message').addClass('hidden');
}

function remove_err_2() {
    $('#err2-group').removeClass('has-error');
    $('#err2-message').addClass('hidden');
}

function widowSize930() {
    if ($(window).width() < 930) {
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
    if ($(window).width() < 930) {
        $('#overlay').toggleClass('hidden');
        $('.sidebar').removeClass('yt-scrollbar')
    }
}

function saveShelf() {
    let selector = $('#newShelfInput');
    let val = selector.val();
    if ($.trim(val) == '') {
        selector.closest('div').addClass('has-error');
    } else {
        $.get('/createShelf/' + val, function (event) {
            location.reload();
        });
    }
}

function renameShelf() {
    let selector = $('#renameShelfInput');
    let name = $('#page-name').text();
    let val = selector.val();
    if ($.trim(val) == '') {
        selector.closest('div').addClass('has-error');
    } else {
        window.location.href = '/renameShelf/' + name + '/' + val;
    }
}

function shelfChange(shelf) {
    let checkboxSelector = $('#checkbox' + shelf);
    let checked = checkboxSelector.is(':checked');
    let id = $('#book-id').val();
    let name = checkboxSelector.val();
    if (checked) {
        $.get('/addToShelf/' + id + '/' + name, function (response) {
            let obj = response[shelf];
            $('#sidebar' + shelf).text(obj[1] - 1);
        });
    } else if (!checked) {
        $.get('/removeFromShelf/' + id + '/' + name, function (response) {
            let obj = response[shelf];
            $('#sidebar' + shelf).text(obj[1] - 1);
        });
    }
}

function deleteShelf() {
    let shelf = $('#page-name').text();
    window.location.href = '/deleteShelf/' + shelf;
}

function startDate() {
    let has_errors = false;

    let user_input = $('#start-date-input').val();
    let user_date = new Date(user_input);
    let diff = user_date.getTimezoneOffset();
    let date = new Date(user_date.getTime() + diff*60000);
    let today = new Date();
    if (today < date){
        let message = $('#err1-message');
        message.removeClass('hidden');
        message.text('Date is out of range');
        $('#err1-group').addClass('has-error');
        has_errors = true;
    } else {
        remove_err_1();
    }

    let page_num = Number($('#page-count').text());
    let current_page = $('#currentPage').val();
    let comp = Number(current_page);
    if(comp < 1 || comp > (page_num-1)) {
        let message = $('#err2-message');
        message.removeClass('hidden');
        message.text('Page number must be between 1 and ' + (page_num-1));
        $('#err2-group').addClass('has-error');
        has_errors = true;
    } else {
        remove_err_2();
    }

    let date_trim = $.trim(user_input);
    let current_page_trim = $.trim(current_page);
    if(date_trim == '' || current_page_trim == ''){
        if(date_trim == '') {
            let message = $('#err1-message');
            message.removeClass('hidden');
            message.text('Field cannot be blank');
            $('#err1-group').addClass('has-error');
        }
        if (current_page_trim == ''){
            let message = $('#err2-message');
            message.removeClass('hidden');
            message.text('Field cannot be blank');
            $('#err2-group').addClass('has-error');
        }
        has_errors = true;
    }

    if (!has_errors) {
        let arr = $('#start-date-form :input[type=date], input[type=number]').serializeArray();
        arr.push({name: 'id', value: $('#bu-id').val()}, {name: 'state', value: 'CR'});

        $.get('/state', arr, function (data) {
            $('#all-badge').text(data["ALL"]);
            $('#tr-badge').text(data["TR"]);
            $('#cr-badge').text(data["CR"]);
            $('#r-badge').text(data["R"]);
        });

        let radio = document.getElementById('CR');
        radio.checked = true;

        document.getElementById('currentState').value = 'CR';

        $('#startDateModal').modal('hide')
    }
}

function dateFinished() {
    let has_errors = false;

    let user_input = $.trim($('#date-finished-input').val());
    if(user_input == ''){
        let message = $('#err3-message');
        message.removeClass('hidden');
        message.text('Field cannot be blank');
        $('#err3-group').addClass('has-error');
        has_errors = true;
    }

    let user_date = new Date(user_input);
    let diff = user_date.getTimezoneOffset();
    let date = new Date(user_date.getTime() + diff*60000);
    let today = new Date();
    if (today < date){
        let message = $('#err3-message');
        message.removeClass('hidden');
        message.text('Date is out of range');
        $('#err3-group').addClass('has-error');
        has_errors = true;
    }

    if(!has_errors) {
        let arr = $('#date-finished-form :input[type=date]').serializeArray();
        arr.push({name: 'id', value: $('#bu-id').val()}, {name: 'state', value: 'R'});

        $.get('/state', arr, function (data) {
            $('#all-badge').text(data["ALL"]);
            $('#tr-badge').text(data["TR"]);
            $('#cr-badge').text(data["CR"]);
            $('#r-badge').text(data["R"]);
        });

        let radio = document.getElementById('R');
        radio.checked = true;

        document.getElementById('currentState').value = 'R';

        $('#finishDateModal').modal('hide')
    }
}

function toRead(){
    let arr = [{name: 'id', value: $('#bu-id').val()},{name: 'state', value: 'TR'}];

    $.get('/state', arr, function (data) {
        $('#all-badge').text(data["ALL"]);
        $('#tr-badge').text(data["TR"]);
        $('#cr-badge').text(data["CR"]);
        $('#r-badge').text(data["R"]);
    });

    let radio = document.getElementById('TR');
    radio.checked = true;

    document.getElementById('currentState').value = 'TR';

    $('#toReadModal').modal('hide');
}

function update(index) {
    let buid = $('#update-buid_'+index).val();
    let author = $('#update-author_'+index).text();
    let page_count = Number($('#update-pageCount_'+index).val());
    let currentPage = Number($('#update-currentPage_'+index).val());
    $('#updateModalLabel').text('Update ' + author);
    $('#update-page')
        .val(currentPage)
        .attr({"max":page_count-1});
    $('#update-page-btn').click({buid:buid, index:index, pageCount:page_count},update_page);
    $('#update-input-btn').click({buid:buid},update_finished);
    $('#updateModal').modal('show');
}

function update_page(event) {
    let currentPage = Number($('#update-page').val());
    let page_count = Number($('#update-pageCount_'+event.data.index).val());
    let has_errors = false;

    if(currentPage < 1 || currentPage > (page_count-1)){
        $('#err5-group').addClass('has-error');
        $('#err5-message')
            .removeClass('hidden')
            .text('Current Page must be between 1 and '+(page_count-1));
        has_errors = true;
    }

    if(!has_errors) {
        let buid = event.data.buid;
        let bu_arr = {'id': buid, 'state': 'CRU', 'currentPage': currentPage};
        $.get('/updatecr', bu_arr, function (data) {
            $('#updateModal').modal('hide');
            let progress = Math.round((Number(currentPage) / event.data.pageCount) * 100);
            $('#progress-bar_' + event.data.index)
                .text(progress + '%')
                .attr({
                    'aria-valuenow': progress
                })
                .css('width', progress + '%');
            $('#update-current_' + event.data.index).text('  ' + currentPage);
            $('#update-currentPage_' + event.data.index).val(currentPage);
        });
        $('#updateModal').modal('hide');
    }
}

function update_finished(event) {
    let input_date = $('#update-input').val();
    let has_errors = false;

    let user_input = $.trim(input_date);
    if(user_input == ''){
        let message = $('#err4-message');
        message.removeClass('hidden');
        message.text('Field cannot be blank');
        $('#err4-group').addClass('has-error');
        has_errors = true;
    }

    let user_date = new Date(user_input);
    let diff = user_date.getTimezoneOffset();
    let date = new Date(user_date.getTime() + diff*60000);
    let today = new Date();
    if (today < date){
        let message = $('#err4-message');
        message.removeClass('hidden');
        message.text('Date is out of range');
        $('#err4-group').addClass('has-error');
        has_errors = true;
    }

    if(!has_errors){
        let bu_arr = {'id':event.data.buid,'state':'R', 'dateFinished':user_input};
        $.get('/state', bu_arr, function () {
            window.location.href = '/perm/R/10';
        })
    }
}

function booksearch() {
    let search = document.getElementById('search-input').value;

    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes?q=" + search + "&orderBy=relevance&maxResults=40",
        dataType: 'json',
        type: 'GET',
        success: function (data) {
            parseData(data);
        }
    });
}

function booksearchNav() {
    let search = document.getElementById('search-input-nav').value;

    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes?q=" + search + "&orderBy=relevance&maxResults=40",
        dataType: 'json',
        type: 'GET',
        success: function (data) {
            parseData(data);
            showHide();
        }
    });
}

function parseData(data) {
    let results = document.getElementById('con');

    let table = document.createElement('table');
    table.className = 'table table-striped';
    let tableBody = document.createElement('tbody');
    let tr = document.createElement('tr');
    tr.innerHTML = '<th>Cover</th>' +
        '<th>Title</th>' +
        '<th>Author</th>' +
        '<th>Page Count</th>' +
        '<th>Publisher</th>' +
        '<th>Date Published</th>' +
        '<th>ISBN 10</th>' +
        '<th>ISBN 13</th>' +
        '<th>Save</th>';

    tableBody.appendChild(tr);
    for (i = 0; i < data.items.length; i++) {
        let tr = document.createElement('tr');

        let cover = document.createElement('td');
        let img = document.createElement('img');
        img.className = 'cover';
        if (typeof data.items[i].volumeInfo.imageLinks != 'undefined') {
            img.src = data.items[i].volumeInfo.imageLinks.smallThumbnail;
        }
        cover.appendChild(img);

        let title = document.createElement('td');
        let title_a = document.createElement('a');
        // title_a.href = data.items[i].volumeInfo.infoLink;
        title_a.href = 'https://books.google.com/books?id='+ data.items[i].id +'&hl=&source=gbs_api'
        title_a.setAttribute('target', '_blank');
        title_a.textContent = data.items[i].volumeInfo.title;
        title.appendChild(title_a);

        let author = document.createElement('td');
        author.textContent = data.items[i].volumeInfo.authors;

        let pageCount = document.createElement('td');
        pageCount.textContent = data.items[i].volumeInfo.pageCount;

        let publisher = document.createElement('td');
        publisher.textContent = data.items[i].volumeInfo.publisher;

        let datePublished = document.createElement('td');
        datePublished.textContent = data.items[i].volumeInfo.publishedDate;

        let isbn10 = document.createElement('td');
        let isbn13 = document.createElement('td');
        let ind = data.items[i].volumeInfo.industryIdentifiers;
        if (typeof ind != 'undefined') {
            if (ind[0]['type'] == 'ISBN_13' || ind[0]['type'] == 'ISBN_10') {
                for (j = 0; j < ind.length; j++) {
                    if (ind[j]['type'] == 'ISBN_13') {
                        isbn13.textContent = ind[j]['identifier'];
                    } else if (ind[j]['type'] == 'ISBN_10') {
                        isbn10.textContent = ind[j]['identifier'];
                    }
                }

                tr.appendChild(cover);
                tr.appendChild(title);
                tr.appendChild(author);
                tr.appendChild(pageCount);
                tr.appendChild(publisher);
                tr.appendChild(datePublished);
                tr.appendChild(isbn10);
                tr.appendChild(isbn13);

                let save = document.createElement('td');

                let button = document.createElement('button');
                button.type = 'submit';
                button.className = 'btn btn-default';
                button.textContent = 'Save';

                let form = document.createElement('form');

                form.action = '/save/' + data.items[i].id;
                form.method = 'GET';

                form.appendChild(button);

                save.appendChild(form);

                tr.appendChild(save);

                tableBody.appendChild(tr);
            }
        }
    }

    let tableWrapper = document.createElement('div');
    tableWrapper.className = 'table-responsive';
    table.appendChild(tableBody);
    tableWrapper.appendChild(table);

    let heading = document.createElement('h2');
    heading.textContent = 'Google Books search results';

    results.innerHTML = '';
    results.appendChild(heading);
    results.appendChild(tableWrapper);
}