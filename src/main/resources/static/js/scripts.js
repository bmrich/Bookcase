$(document).ready(function () {
    let x930 = window.matchMedia("(min-width: 930px)");
    widow_size_930(x930);
    x930.addListener(widow_size_930);

    document.getElementById('overlay').addEventListener('click', show_hide, false);

    document.getElementById('search-input').addEventListener('input', suggestion, false);
    document.getElementById('submit-btn').addEventListener('click', book_search, false);
    document.getElementById('search-input').onkeypress = function (e) {
        if(e.keyCode == 13){
            $('#res').empty();
            book_search();
        }
    };

    document.getElementById('submit-btn-nav').addEventListener('click', book_search_nav, false);
    document.getElementById('search-input-nav').onkeypress = function (e) {
        if(e.keyCode == 13){
            book_search_nav();
        }
    };

    if (document.querySelectorAll('.update-btn').length){
        let update_btns = document.querySelectorAll('.update-btn');
        update_btns.forEach(function (btn) {
            btn.addEventListener('click', update);
        });
    }


    $('#newShelfInput').blur(function () {
        $(this).closest('div').removeClass('has-error');
    });
    $('#newShelf').on('hide.bs.modal', function () {
        $('#newShelfInput').closest('div').removeClass('has-error');
    });

    $('#renameShelfInput').blur(function () {
        $(this).closest('div').removeClass('has-error');
    });
    $('#renameShelf').on('hide.bs.modal', function () {
        $('#renameShelfInput').closest('div').removeClass('has-error');
    });

    if(document.getElementById('des-container') != null){
        let des_con = document.getElementById('des-container');

        $.ajax({
            url: 'https://www.googleapis.com/books/v1/volumes/' + des_con.dataset.bookId,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $.parseHTML(data.volumeInfo.description).forEach(function (elem) {
                    des_con.appendChild(elem);
                });
            }
        });
    }

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

    $('#startDateModal').on('hide.bs.modal', function () {
        remove_err_1();
        $('#start-date-input').val('');

        remove_err_2();
        $('#currentPage').val('');
    });

    $('#finishDateModal').on('hide.bs.modal', function () {
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
    });
});

function suggestion (event){
    let query = this.value;
    let url = "https://suggestqueries.google.com/complete/search?client=books&ds=bo&q=";
    if(query != ''){
        $.ajax({
            url: url + query,
            dataType: "jsonp",
            success: function (response) {
                let con = $('#' + event.target.list.id);
                con.empty();
                response[1].forEach(function(e){
                    let p = document.createElement('option');
                    p.value = e[0];
                    con.append(p);
                });
            }
        });
    }
}

function remove_err_1() {
    $('#err1-group').removeClass('has-error');
    $('#err1-message').addClass('hidden');
}

function remove_err_2() {
    $('#err2-group').removeClass('has-error');
    $('#err2-message').addClass('hidden');
}

function widow_size_930() {
    if ($(window).width() < 930) {
        $('#overlay').addClass('hidden');
    }
    $('.main-content').addClass('main-content-close');
    $('.sidebar-open-icon').addClass('sidebar-open-click');
}

function show_hide() {
    if ($(window).width() < 930) {
        $('#overlay').toggleClass('hidden');
        $('.sidebar').removeClass('yt-scrollbar');
        $('.sidebar').toggleClass('sidebar-open');
    } else {
        $('.sidebar').toggleClass('sidebar-close');
        $('.sidebar-open-icon').toggleClass('sidebar-open-click');
        $('.main-content').toggleClass('main-content-close');
    }
}

function save_shelf() {
    let selector = $('#newShelfInput');
    let val = selector.val();
    if ($.trim(val) == '') {
        selector.closest('div').addClass('has-error');
    } else {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/create',
            data: {shelfName: val},
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function () {
                location.reload();
            }
        });
    }
}

function rename_shelf() {
    let selector = $('#renameShelfInput');
    let name = $('#page-name').text();
    let val = selector.val();
    if ($.trim(val) == '') {
        selector.closest('div').addClass('has-error');
    } else {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/rename',
            type: 'POST',
            data: {name:name, new:val},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function () {
                window.location.href = '/list/shelf?name='+val;
            }
        });
    }
}

function shelf_change(shelf) {
    let checkboxSelector = $('#checkbox' + shelf);
    let checked = checkboxSelector.is(':checked');

    let id = $('#book-id').val();
    let name = checkboxSelector.val();

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    if (checked) {
        $.ajax({
            url: '/shelf/add',
            data: {shelfName: name, id: id},
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function (response) {
                let obj = response[shelf];
                $('#sidebar' + shelf).text(obj[1] - 1);
            }
        });
    } else if (!checked) {
        $.ajax({
            url: '/shelf/remove/' + name + '/' + id,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function (response) {
                let obj = response[shelf];
                $('#sidebar' + shelf).text(obj[1] - 1);
            }
        });
    }
}

function delete_shelf() {
    let shelf = $('#page-name').text();

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/shelf/' + shelf,
        type: 'DELETE',
        beforeSend: function(request) {
            request.setRequestHeader(header, token);
        },
        success: function () {
            window.location.href = '/';
        }
    });
}

function start_date() {
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

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/state',
            data: arr,
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#all-badge').text(data["ALL"]);
                $('#tr-badge').text(data["TR"]);
                $('#cr-badge').text(data["CR"]);
                $('#r-badge').text(data["R"]);
            }
        });

        let radio = document.getElementById('CR');
        radio.checked = true;

        document.getElementById('currentState').value = 'CR';

        $('#startDateModal').modal('hide')
    }
}

function date_finished() {
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

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/state',
            data: arr,
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#all-badge').text(data["ALL"]);
                $('#tr-badge').text(data["TR"]);
                $('#cr-badge').text(data["CR"]);
                $('#r-badge').text(data["R"]);
            }
        });

        let radio = document.getElementById('R');
        radio.checked = true;

        document.getElementById('currentState').value = 'R';

        $('#finishDateModal').modal('hide')
    }
}

function to_read(){
    let arr = [{name: 'id', value: $('#bu-id').val()},{name: 'state', value: 'TR'}];

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/shelf/state',
        data: arr,
        type: 'POST',
        beforeSend: function(request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#all-badge').text(data["ALL"]);
            $('#tr-badge').text(data["TR"]);
            $('#cr-badge').text(data["CR"]);
            $('#r-badge').text(data["R"]);
        }
    });

    let radio = document.getElementById('TR');
    radio.checked = true;

    document.getElementById('currentState').value = 'TR';

    $('#toReadModal').modal('hide');
}

function update(e) {
    $.ajax({
        url: '/progress',
        data: {buid:e.target.dataset.buid},
        type: 'GET',
        success: function (data) {
            $('#updateModalLabel').text('Update ' + data['title']);
            $('#update-page')
                .val(data['currentPage'])
                .attr({"max":data['pageCount']-1});

            $('#update-page-btn')
                .click({
                    buid:e.target.dataset.buid,
                    index:e.target.dataset.index,
                    pageCount:data['pageCount']
                }, update_page);

            $('#update-input-btn').click({buid:e.target.dataset.buid},update_finished);
            $('#updateModal').modal('show');
        }
    });
}

function delete_book() {
    let id = Number($('#book-id').val());
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/book/'+id,
        type: 'DELETE',
        beforeSend: function(request) {
            request.setRequestHeader(header, token);
        },
        success: function () {
            window.location.href = '/';
        }
    });
}

function update_page(event) {
    let currentPage = Number($('#update-page').val());
    let page_count = event.data.pageCount;
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

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/progress',
            type: 'POST',
            data: bu_arr,
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#updateModal').modal('hide');
                let progress = Math.round((Number(currentPage) / event.data.pageCount) * 100);
                $('[data-progress=\''+ event.data.index +'\']')
                    .text(progress + '%')
                    .attr({
                        'aria-valuenow': progress
                    })
                    .css('width', progress + '%');
                $('[data-current-page=\''+ event.data.index +'\']').text('  ' + currentPage);
            }
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

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/shelf/state',
            data: bu_arr,
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function () {
                window.location.href = '/list/completed';
            }
        });
    }
}

function book_search() {
    let search = document.getElementById('search-input').value;

    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes?q=" + search + "&orderBy=relevance&maxResults=40",
        dataType: 'json',
        type: 'GET',
        success: function (data) {
             parse_data(data);
        }
    });
}

function book_search_nav() {
    let search = document.getElementById('search-input-nav').value;

    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes?q=" + search + "&orderBy=relevance&maxResults=40",
        dataType: 'json',
        type: 'GET',
        success: function (data) {
            parse_data(data);
            show_hide()
        }
    });
}

function parse_data(data) {
    if (data.totalItems == 0) {
        $('#con').html('<h2 style="text-align: center">No Results</h2>');
        return;
    }

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
        let ind = data.items[i].volumeInfo.industryIdentifiers;
        if (typeof ind != 'undefined') {
            let isbn10 = document.createElement('td');
            let isbn13 = document.createElement('td');
            if (ind[0]['type'] == 'ISBN_13' || ind[0]['type'] == 'ISBN_10') {
                for (j = 0; j < ind.length; j++) {
                    if (ind[j]['type'] == 'ISBN_13') {
                        isbn13.textContent = ind[j]['identifier'];
                    } else if (ind[j]['type'] == 'ISBN_10') {
                        isbn10.textContent = ind[j]['identifier'];
                    }
                }

                let tr = document.createElement('tr');

                let cover = document.createElement('td');
                let img = document.createElement('img');
                img.className = 'cover';
                if (typeof data.items[i].volumeInfo.imageLinks != 'undefined') {
                    let str = data.items[i].volumeInfo.imageLinks.smallThumbnail;
                    img.src = str.substring(5);
                }
                cover.appendChild(img);

                let title = document.createElement('td');
                let title_a = document.createElement('a');
                title_a.href = 'https://books.google.com/books?id=' + data.items[i].id + '&hl=&source=gbs_api';
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
                button.dataset.bookId = data.items[i].id;
                button.addEventListener('click', save_book);

                save.appendChild(button);
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

function save_book(e) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/book/save',
        type: 'POST',
        data: {id:e.target.dataset.bookId},
        beforeSend: function(request) {
            request.setRequestHeader(header, token);
        },
        success: function (res) {
            window.location.href = '/book/info/'+res;
        }
    });
}