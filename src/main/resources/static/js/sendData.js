$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    function send_data(url, http_method, data) {
        $.ajax({
                contentType: "application/json; charset=utf-8",
                type: http_method,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url: url,
                data: JSON.stringify(data),
                dataType: "json",
                success: function (res) {
                    if(parseInt(res.response_code) == 200){
                        window.location.reload();
                    }
                    else if(parseInt(res.response_code) == 300){
                        // if (url.indexOf('/estimate/download-estimate/') > -1) {
                        //     console.log(res.redirect_url);
                        // }
                        // else
                        window.location.replace(res.redirect_url);
                    }
                    else if(parseInt(res.response_code) == 400){
                        $("#alert_message").text(res.message);
                        $("#ex1").modal();
                    }
                }
            }
        );
    }

    $('#save_client').click(function (e) {
        const el = document.getElementById('select_type_client');
        let formData;
        let photos_arr
        let inBlackList = document.getElementById("inBlackList").checked;
        if (el.value === 'INDIVIDUAL') {
            photos_arr = $("#photos_individual").val().split(",", 9999);
            photos_arr.forEach(function (item, i, arr) {
                photos_arr[i] = photos_arr[i].trim();
            });
            formData = {
                typeClient: $("#select_type_client :selected").val(),
                inBlackList,
                fullName: $("#fullName_individual").val(),
                discount: $("#discount_individual").val(),
                phoneNumber: $("#phoneNumber_individual").val(),
                email: $("#email_individual").val(),
                fromComing: $("#fromComing_individual").val(),
                limited: $("#limit_individual").val(),
                note: $("#note_individual").val(),
                birthday: $("#birthday").val(),
                directorOfPhotography: $("#directorOfPhotography").val(),
                production: $("#production").val(),
                numberPassport: $("#numberPassport").val(),
                issuedBy: $("#issuedBy").val(),
                dateIssuePassport: $("#dateIssuePassport").val(),
                addressReal: $("#addressReal_individual").val(),
                photos: photos_arr,
                dateCreating: $("#dateCreating_individual").val(),
            };
        } else {
            photos_arr = $("#photos_legal").val().split(",", 9999);
            photos_arr.forEach(function (item, i, arr) {
                photos_arr[i] = photos_arr[i].trim();
            });
            7
            formData = {
                typeClient: $("#select_type_client :selected").val(),
                inBlackList,
                fullName: $("#fullName_legal").val(),
                legalName: $("#legalName").val(),
                discount: $("#discount_legal").val(),
                phoneNumber: $("#phoneNumber_legal").val(),
                email: $("#email_legal").val(),
                addressReal: $("#addressReal_legal").val(),
                addressLegal: $("#addressLegal").val(),
                fromComing: $("#fromComing_legal").val(),
                limited: $("#limit_legal").val(),
                note: $("#note_legal").val(),
                inn: $("#inn").val(),
                kpp: $("#kpp").val(),
                ogrn: $("#ogrn").val(),
                fullNameSupervisor: $("#fullNameSupervisor").val(),
                jobTitleSupervisor: $("#jobTitleSupervisor").val(),
                inFace: $("#in_face").val(),
                based: $("#based").val(),
                rs: $("#rs").val(),
                bank: $("#bank").val(),
                bik: $("#bik").val(),
                photos: photos_arr,
                dateCreating: $("#dateCreating_individual").val(),
            };
        }
        send_data("/clients/create", "POST", formData);
    });
    $('#update_client').click(function (e) {
        const el = document.getElementById('select_type_client');
        let id = $("#client_id").val();
        let formData;
        let photos_arr;
        let inBlackList = document.getElementById("inBlackList").checked;
        if (el.value === 'INDIVIDUAL') {
            photos_arr = $("#photos_individual").val().split(",", 9999);
            photos_arr.forEach(function (item, i, arr) {
                photos_arr[i] = photos_arr[i].trim();
            });
            formData = {
                id,
                typeClient: $("#select_type_client :selected").val(),
                inBlackList,
                fullName: $("#fullName_individual").val(),
                discount: $("#discount_individual").val(),
                phoneNumber: $("#phoneNumber_individual").val(),
                email: $("#email_individual").val(),
                fromComing: $("#fromComing_individual").val(),
                limited: $("#limit_individual").val(),
                note: $("#note_individual").val(),
                birthday: $("#birthday").val(),
                directorOfPhotography: $("#directorOfPhotography").val(),
                production: $("#production").val(),
                numberPassport: $("#numberPassport").val(),
                issuedBy: $("#issuedBy").val(),
                dateIssuePassport: $("#dateIssuePassport").val(),
                addressReal: $("#addressReal_individual").val(),
                photos: photos_arr,
                dateCreating: $("#dateCreating_individual").val(),
            };
        } else {
            photos_arr = $("#photos_legal").val().split(",", 9999);
            photos_arr.forEach(function (item, i, arr) {
                photos_arr[i] = photos_arr[i].trim();
            });
            formData = {
                id,
                typeClient: $("#select_type_client :selected").val(),
                inBlackList,
                fullName: $("#fullName_legal").val(),
                legalName: $("#legalName").val(),
                discount: $("#discount_legal").val(),
                phoneNumber: $("#phoneNumber_legal").val(),
                email: $("#email_legal").val(),
                addressReal: $("#addressReal_legal").val(),
                addressLegal: $("#addressLegal").val(),
                fromComing: $("#fromComing_legal").val(),
                limited: $("#limit_legal").val(),
                note: $("#note_legal").val(),
                inn: $("#inn").val(),
                kpp: $("#kpp").val(),
                ogrn: $("#ogrn").val(),
                fullNameSupervisor: $("#fullNameSupervisor").val(),
                jobTitleSupervisor: $("#jobTitleSupervisor").val(),
                inFace: $("#in_face").val(),
                based: $("#based").val(),
                rs: $("#rs").val(),
                bank: $("#bank").val(),
                bik: $("#bik").val(),
                photos: photos_arr,
                dateCreating: $("#dateCreating_individual").val(),
            };
        }
        send_data("/clients/edit/" + id, "POST", formData);

    });
    $('.change_status').click(function (e) {
        let status;
        if ($(this).attr('id') === 'status_wait') {
            status = "WAITING";
        } else if ($(this).attr('id') === 'status_instock') {
            status = "INSTOCK";
        }
        send_data("/tools/change-status", "POST", {'items': checkedIds, 'statusTools': status});
    });

    $('#btn_delete_project').click(function (e) {
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });
        send_data("/projects/delete", "POST", {"ids": newIds});
    });

    $('#btn_delete_tools_from_project').click(function (e) {
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });
        let id = $("#id_project").val();
        send_data("/projects/edit/delete-tool/"+id, "POST", {"ids": newIds});
    });

    $('#btn_add_tools_to_project').click(function (e) {
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });
        let id = $("#project_id").val();

        send_data("/projects/edit/add-tool/"+id, "POST", {"ids": newIds});
    });


    $('#replaceBtn').click(function (e) {
        var newIds = "";
        var count = 0;
        checkedIds.forEach(function (item, i, arr) {
            newIds += item.id+",";
            count++;
        });
        var id = window.location.pathname.replace("/projects/edit/", "");
        console.log(count);
        if(count > 0){
            newIds = newIds.substring(0, newIds.length - 1);
            window.location.replace('/tools/replace-tool/'+id+"?ids="+newIds+"&filter=INSTOCK");
        }
    });

    $('#replaceBtnOther').click(function (e) {
        var id = window.location.pathname.replace("/projects/edit/", "");
        window.location.replace('/tools/replace-tool/'+id+"?status=ONLEASE");
    });
    // Replace ids from table

    function removeItemOnce(arr, value) {
        var index = arr.indexOf(value);
        if (index > -1) {
            arr.splice(index, 1);
        }
        return arr;
    }

    function getUrlVars(url){
        var vars = {};
        var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
        });
        return vars;
    }

    var checkedIds = [];
    $('body').on('keyup', '.repair_price, .sell_price, .writeoff_price', function(e){
        var price = $(this).val();
        var id = $(this).parent().parent().find('.ids').next().val();

        if(price != "" && price  > 0){
            $(this).parent().parent().find('.ids').prop('checked', true);

            var inArray = false;
            checkedIds.forEach(function (item, i, arr) {
                if(item.id == id){
                    checkedIds[i] = {'price': price, 'id': id}
                    inArray = true;
                }
            });

            if(!inArray){
                checkedIds.push({'price': price, 'id': id});
            }

        }
        else{
            $(this).parent().parent().find('.ids').prop('checked', false);

            var newCheckIds = [];
            checkedIds.forEach(function (item, i, arr) {
                if(item.id != id){
                    newCheckIds[i] = item;
                }
            });
            checkedIds = newCheckIds;
        }

        console.log(checkedIds);
    });

    $('body').on('click','.ids',function(){
        var checked = $(this).is(":checked");
        var id = $(this).parent().find('input:eq(1)').val();

        if (window.location.pathname.indexOf('repair/create') > -1 ||
            window.location.pathname.indexOf('sale/create') > -1) {
            if (checked){
                var price = $(this).parent().parent().parent().find(".repair_price, .sell_price").val();
                if(price != "" && parseInt(price) > 0){
                    console.log(price);
                    var inArray = false;

                    checkedIds.forEach(function (item, i, arr) {
                        if(item.id == id){
                            checkedIds[i] = {
                                'price': price,
                                'id': id,
                            }
                            inArray = true;
                        }
                    });

                    if(!inArray){
                        checkedIds.push({
                            'price': price,
                            'id': id,
                        });
                    }
                }
            }
            else{
                var newCheckIds = [];
                checkedIds.forEach(function (item, i, arr) {
                    if(item.id != id){
                        newCheckIds[i] = item;
                    }
                });
                checkedIds = newCheckIds;
            }
        }
        else{
            if (checked){
                var inArray = false;
                checkedIds.forEach(function (item, i, arr) {
                    if(item.id == id) inArray = true;
                });
                if(!inArray) checkedIds.push({'id': id});
            }
            else{
                var newCheckIds = [];
                checkedIds.forEach(function (item, i, arr) {
                    if(item.id != id){
                        newCheckIds[i] = item;
                    }
                });
                checkedIds = newCheckIds;
            }
        }

        console.log(checkedIds);

        if (window.location.pathname.indexOf('add-tool-to-project') > -1) {
            $("#btn_replace_tools_to_project").text("Добавить");
        }
        if (window.location.pathname.indexOf('replace-tool') > -1) {
            $("#btn_replace_tools_to_project").text("Заменить");
        }

        if (window.location.pathname.indexOf('/tools/add-tool-to-project') > -1 || window.location.pathname.indexOf('/tools/replace-tool') > -1) {
            if(Object.keys(checkedIds).length > 0) {
                $("#btn_replace_tools_to_project").fadeIn();
            } else {
                $("#btn_replace_tools_to_project").fadeOut();
            }
        }
    });

    $('body').on('click','#btn_replace_tools_to_project',function(){
        var getParams = getUrlVars(window.location.search);
        if(typeof getParams.ids != 'undefined'){
            var oldIds = getParams.ids.split(",");
        }
        else oldIds = [];
        var sendUrl = window.location.pathname;

        var sendUrl;
        if (window.location.pathname.indexOf('add-tool-to-project') > -1) {
            sendUrl = sendUrl.replace('/tools/add-tool-to-project','/projects/add-tool');
        }
        else{
            sendUrl = sendUrl.replace('/tools/replace-tool','/projects/change-tools');
        }
        // sendUrl = sendUrl.substr(0, sendUrl.length - 1);
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });

        send_data(sendUrl, "POST", {'old_ids': oldIds, 'new_ids': newIds});
    });

    $('body').on('click','#removeBtn',function(){
        var sendUrl = window.location.pathname;
        sendUrl = sendUrl.replace('/projects/edit','/projects/remove-tool');
        // sendUrl = sendUrl.substr(0, sendUrl.length - 1);
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });
        if(newIds.length > 0) send_data(sendUrl, "POST", {'ids': newIds, 'statusTools': 'INSTOCK'});
    });

    $('body').on('click','#remove_project',function(){
        // var sendUrl = window.location.pathname;
        // sendUrl = sendUrl.replace('/projects/edit','/projects/remove-tool');
        // sendUrl = sendUrl.substr(0, sendUrl.length - 1);
        var newIds = [];
        checkedIds.forEach(function (item, i, arr) {
            newIds.push(item.id);
        });
        if(newIds.length > 0) send_data('/projects/delete', "POST", {'ids': newIds});
    });

    $('body').on('click','#back_repair', function(){
        send_data("/tools/change-status", "POST", {'items': checkedIds, 'statusTools': 'INSTOCK'});
    });

    $('body').on('click','#write-off_on, #sale_on', function(){
        if($("#write-off_note, #note_sale").val() == ""){
            $("#alert_message").text('Укажите примечание');
            $("#ex1").modal();
        }
        else if($("#write-off_photos, #photos_sale").val() == ""){
            $("#alert_message").text('Укажите фотографии');
            $("#ex1").modal();
        }
        else{
            var photosWr = $("#write-off_photos, #photos_sale").val().split(",");
            var postData = {
                'photos': photosWr,
                'note': $('#write-off_note, #note_sale').val(),
                'items': checkedIds,
            };

            if($(this).attr('id') == 'sale_on') postData.statusTools = 'SALE';
            if($(this).attr('id') == 'write-off_on') postData.statusTools = 'WRITEOFF';

            if(typeof $("#sale_data").val() !== 'undefined') postData.data = $("#sale_data").val();
            send_data("/tools/change-status", "POST", postData);
        }
    });


    function checkRecCalc(){
        var finalSumUsn = $("#finalSumUsn").val();
        var received = $("#received").val();
        if(received == "") received = 0;
        var res = finalSumUsn - received;
        $("#remainder").val(res);
    }

    //PROJECT CALCULATION
    $('body').on('keyup', '#received, #finalSumUsn', function(e){
        checkRecCalc();
    });
    checkRecCalc();

    $('body').on('click','#create_new_project, #update_project',function(){

        if($("#name").val() == ""){
            $("#alert_message").text('Укажите Имя проекта');
            $("#ex1").modal();
        }
        else if($("#status").val() == ""){
            $("#alert_message").text('Укажите статус проекта');
            $("#ex1").modal();
        }
        else if($("#start").val() == ""){
            $("#alert_message").text('Укажите дату начала проекта');
            $("#ex1").modal();
        }
        else if($("#end").val() == ""){
            $("#alert_message").text('Укажите дату окончания проекта');
            $("#ex1").modal();
        }
        else if($("#created").val() == ""){
            $("#alert_message").text('Укажите дату создания проекта');
            $("#ex1").modal();
        }
        else if($("#client_id").val() == ""){
            $("#alert_message").text('Укажите клиента');
            $("#ex1").modal();
        }
        else{
            var newIds = [];
            checkedIds.forEach(function (item, i, arr) {
                newIds.push(item.id);
            });
            var postData = {
                'status': $("#status").find(":selected").val(),
                'name': $("#name").val(),
                'start': $("#start").val(),
                'end': $("#end").val(),

                'typeLease': $("#typeLease").find(":selected").val(),
                'classification': $("#classification").val(),
                'created': $("#created").val(),
                'client_id':  $("#client_id").find(":selected").val(),

                'photos': $("#photos").val().split(","),
                'note': $('#note').val(),
                'items': newIds,
            };
            if($(this).attr('id') == 'create_new_project'){
                if(Object.keys(checkedIds).length <= 0) {
                    $("#alert_message").text('Выберите нужные наименования!');
                    $("#ex1").modal();
                }
                else send_data('/projects/create', "POST", postData);
            }
            else if($(this).attr('id') == 'update_project'){
                postData.id = $("#project_id").val();
                postData.received = $("#received").val();
                postData.remainder = $("#remainder").val();
                send_data('/projects/edit', "POST", postData);
            }
        }
    });

    $('body').on('click','#close_project',function(){
        if($('#remainder').val() == 0){
            var postData = {};
            postData.id = $("#project_id").val();
            send_data('/projects/close', "POST", postData);
        }
        else{
            $("#alert_message").text('Расчет неоплаченного остатка должен быть равен 0!');
            $("#ex1").modal();
        }
    });


    $('body').on('click','#repair_create',function(){
        if(Object.keys(checkedIds).length > 0) {

            if($("#start_repair").val() == ""){
                $("#alert_message").text('Укажите дату начала ремонта');
                $("#ex1").modal();
            }
            else if($("#start_repair").val() == ""){
                $("#alert_message").text('Укажите дату конца ремонта');
                $("#ex1").modal();
            }
            else if($("#photos_repair").val() == ""){
                $("#alert_message").text('Укажите фотографии');
                $("#ex1").modal();
            }
            else{
                var photosRepair = $("#photos_repair").val().split(",");
                var postData = {
                    'start': $("#start_repair").val(),
                    'end': $("#end_repair").val(),
                    'data': $("#data_repair").val(),
                    'photos': photosRepair,
                    'note': $('#note_repair').val(),
                    'items': checkedIds,
                    'statusTools': 'REPAIR',
                };
                console.log(postData);
                send_data('/tools/change-status', "POST", postData);
            }
        }
        else{
            $("#alert_message").text('Выберите нужные наименования и укажите цену ремонта!');
            $("#ex1").modal();
        }
    });

    //
    // $('body').on('click', '.sat', function(event){
    //     if ($('.sat_item').is(':hidden')) $('.sat_item').fadeIn();
    //     else $('.sat_item').fadeOut();
    // });

    function getTimeFormat(time){
        var timeStamp = Date.parse(time);
        var date = new Date(timeStamp);

        var day =   date.getDate();
        var month = date.getMonth()+1;
        var year =  date.getFullYear();
        var hour =  date.getHours();
        var minutes =  date.getMinutes();

        if(day<10) day = "0"+day;
        if(month<10) month = "0"+month;
        if(hour<10) hour = "0"+hour;
        if(minutes<10) minutes = "0"+minutes;

        return day+"-"+month+"-"+year+" "+hour + ":" + minutes;;
    }


    $('.tool_status').each(function (index, value) {

        var current_status = $(this).text();
        var row = $(this).parent().parent();

        if(current_status == 'Создан'){
            row.css("background-color", "orange");
            row.css("color", "#fff");
        }
        else if(current_status == 'В работе'){
            row.css("background-color", "red");
            row.css("color", "#fff");
        }
        else if(current_status == 'Продлён'){
            row.css("background-color", "red");
            row.css("color", "#fff");
        }
        else if(current_status == 'Просрочен'){
            row.css("background-color", "brown");
            row.css("color", "#fff");
        }
        else if(current_status == 'Ожидает оплаты'){
            row.css("background-color", "yellow");
            row.css("color", "#000");
        }
        else if(current_status == 'Закрыт'){
            row.css("background-color", "green");
            row.css("color", "#fff");
        }
        else if(current_status == 'В аренде'){
            row.css("background-color", "red");
            row.css("color", "#fff");
        }
        else if(current_status == 'На складe'){
            row.css("background-color", "green");
            row.css("color", "#fff");
        }
        else if(current_status == 'В ожидании'){
            row.css("background-color", "yellow");
            row.css("color", "#000");
        }
        else if(current_status == 'Бронь'){
            row.css("background-color", "orange");
            row.css("color", "#fff");
        }
        else if(current_status == 'В ремонте'){
            row.css("background-color", "gray");
            row.css("color", "#fff");
        }
        else if(current_status == 'Списано'){
            row.css("background-color", "brown");
            row.css("color", "#fff");
        }
        else if(current_status == 'Продано'){
            row.css("background-color", "blue");
            row.css("color", "#fff");
        }


    });


    //PAGINATION
    $('body').on('click', '.page-item a', function(event){
        var getParams = getUrlVars($(this).attr('href'));

        dataJSON = {};
        dataJSON['page'] = getParams.page;
        dataJSON['size'] = getParams.size;

        var getCurParams = getUrlVars(window.location.search);
        if(typeof getCurParams.filter !== "undefined") dataJSON['filter'] = getCurParams.filter;
        else dataJSON['filter'] = 'WITHOUT_FILTER';

        if(window.location.pathname.indexOf('tools/add-tool-to-project') > -1){
            dataJSON['paginationConst'] = 'TOOLS_ADD';
            dataJSON['filter'] = 'INSTOCK';
        }
        else if(window.location.pathname.indexOf('tool') > -1){
            dataJSON['paginationConst'] = 'TOOLS';
        }
        else if(window.location.pathname.indexOf('repair/create') > -1 ||
            window.location.pathname.indexOf('write-off/create') > -1 ||
            window.location.pathname.indexOf('sale/create') > -1){
            dataJSON['paginationConst'] = 'TOOLS';
            dataJSON['filter'] = 'WAITING';
        }
        else if(window.location.pathname.indexOf('repair') > -1){
            dataJSON['paginationConst'] = 'TOOLS';
            dataJSON['filter'] = 'REPAIR';
        }
        else if(window.location.pathname.indexOf('sale') > -1){
            dataJSON['paginationConst'] = 'TOOLS';
            dataJSON['filter'] = 'SALE';
        }
        else if(window.location.pathname.indexOf('write-off') > -1){
            dataJSON['paginationConst'] = 'TOOLS';
            dataJSON['filter'] = 'WRITEOFF';
        }
        else if(window.location.pathname.indexOf('projects/create') > -1){
            dataJSON['paginationConst'] = 'PROJECT_CREATE';
            dataJSON['filter'] = 'INSTOCK';
        }
        else if(window.location.pathname.indexOf('projects/edit') > -1){
            dataJSON['paginationConst'] = 'PROJECT';
            dataJSON['filter'] = 'GET_TOOLS_BY_PROJECT';
        }
        else if(window.location.pathname.indexOf('clients/edit') > -1){
            dataJSON['paginationConst'] = 'CLIENT';
            dataJSON['filter'] = 'PROJECTS_BY_CLIENTS';
        }
        else if(window.location.pathname.indexOf('clients') > -1){
            dataJSON['paginationConst'] = 'CLIENT';
            dataJSON['filter'] = 'ALL_CLIENTS';
        }
        else if(window.location.pathname.indexOf('estimate-name') > -1){
            dataJSON['paginationConst'] = 'ESTIMATE_NAME';
            dataJSON['filter'] = 'ESTIMATE_NAME';
        }
        else if(window.location.pathname.indexOf('category') > -1){
            dataJSON['paginationConst'] = 'CATEGORY';
            dataJSON['filter'] = 'CATEGORY';
        }
        else if(window.location.pathname == "/") dataJSON['paginationConst'] = 'PROJECT';


        if(typeof $("#project_id").val() !== 'undefined'){
            dataJSON['id'] = $("#project_id").val();
        }
        else if(typeof $("#client_id").val() !== 'undefined'){
            dataJSON['id'] = $("#client_id").val();
        }

        $.ajax({
                contentType: "application/json; charset=utf-8",
                type: 'POST',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url: '/pagination',
                dataType: "json",
                data: JSON.stringify(dataJSON),
                success: function (res) {
                    if(res.response_code == 200){
                        // console.log(res.data);
                        // console.log(res.data[1]);
                        // console.log(res.data[1].content);
                        var content = res.data[1].content;
                        var tableTR = '';

                        if(dataJSON.paginationConst == 'PROJECT' && dataJSON.filter != 'GET_TOOLS_BY_PROJECT') {
                            content.forEach(function (item, i, arr) {
                                if(typeof item.start !== 'undefined') item.start = getTimeFormat(item.start);
                                if(typeof item.end !== 'undefined') item.end = getTimeFormat(item.end);
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                var color_tr = "";
                                if(item.status_value == 'Создан') color_tr = "style='background-color: orange; color: #fff;'";
                                else if(item.status_value == 'В работе') color_tr = "style='background-color: red; color: #fff;'";
                                else if(item.status_value == 'Продлён') color_tr = "style='background-color: red; color: #fff;'";
                                else if(item.status_value == 'Просрочен') color_tr = "style='background-color: brown; color: #fff;'";
                                else if(item.status_value == 'Ожидает оплаты') color_tr = "style='background-color: yellow; color: #000;'";
                                else if(item.status_value == 'Закрыт') color_tr = "style='background-color: gray; color: #fff;'";

                                tableTR += '<tr '+color_tr+'>\n' +
                                    '    <td data-label="#" class="column-table">\n' +
                                    '        <div class="column-table_checkbox-wrapp">\n';

                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) tableTR += '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else  tableTR += '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR += '    <input type="text" value="' + item.id + '" hidden="hidden">\n' +
                                    '            <span class="tools-td-text table-td-text column-table_checkbox-number">' + item.id + '</span>\n' +
                                    '        </div>\n' +
                                    '    </td>\n' +
                                    '    <td data-label="Проект"><a href="/projects/edit/' + item.id + '/" title="Редактирование проекта">' + item.name + '</a></td>\n' +
                                    '    <td data-label="Статус">\n' +
                                    '   <span class="tool_status">'+item.status_value+'</span>\n' +
                                    '   </td>\n' +
                                    '    <td data-label="Клиент">' + item.client_name + '</td>\n' +
                                    '    <td data-label="Телефон">' + item.phoneNumber + '</td>\n' +
                                    '    <td data-label="Начало аренды">' + item.start + '</td>\n' +
                                    '    <td data-label="Окончание аренды">' + item.end + '</td>\n' +
                                    '    <td data-label="Дата и время создания">' + item.created + '</td>\n' +
                                    '    <td data-label="Сотрудник">' + item.employee + '</td>\n' +
                                    '    <td data-label="Примечание">' + item.note + '</td>\n' +
                                    '    <td data-label="Тип">' + item.classification_name + '</td>\n' +
                                    '    <td data-label="Открыть смету"><a href="/estimate/create/' + item.id + '" title="Просмотр/редактирование сметы">Смета</a></td>\n' +
                                    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'CATEGORY' && dataJSON.filter == 'CATEGORY') {
                            content.forEach(function (item, i, arr) {
                                tableTR += '<tr>\n' +
                                    '    <td class="column-table">\n' +
                                    '        <div class="text-center" id="ids_tools">\n' +
                                    '            <input type="hidden" class="id_tool" value="8">\n' +
                                    '            <span class="tools-td-text table-td-text column-table_checkbox-number tool_id">'+item.id+'</span>\n' +
                                    '        </div>\n' +
                                    '    </td>\n' +
                                    '    <td data-label="Название категории" class="align-middle">\n' +
                                    '        <a class="nav-link" style="font-size: medium" href="/category/edit/'+item.id+'/">'+item.name+'</a>\n' +
                                    '    </td>\n' +
                                    '    <td data-label="Код" class="align-middle">\n' +
                                    '        <h1 class="display-6" style="font-size: medium">'+item.code+'</h1>\n' +
                                    '    </td>\n' +
                                    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'ESTIMATE_NAME' && dataJSON.filter == 'ESTIMATE_NAME') {
                            content.forEach(function (item, i, arr) {
                                tableTR += '<tr>\n' +
                                    '   <td class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp " id="ids_tools">\n' +
                                    '           <input class="column-table_checkbox choose_tool ids" type="checkbox">\n' +
                                    '           <input type="hidden" class="id_tool" value="'+item.id+'">\n' +
                                    '           <span class="tools-td-text table-td-text column-table_checkbox-number tool_id">'+item.id+'</span>\n' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Наименование в смете" class="align-middle">\n' +
                                    '       <a class="nav-link" style="font-size: medium" href="/estimate-name/edit/1/">'+item.name+'</a>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Группа" class="align-middle">\n' +
                                    '       <h1 class="display-6" style="font-size: medium">'+item.categoryTools+'</h1>\n' +
                                    '   </td>\n' +
                                    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'CLIENT' && dataJSON.filter == 'PROJECTS_BY_CLIENTS') {
                            content.forEach(function (item, i, arr) {
                                if(typeof item.start !== 'undefined') item.start = getTimeFormat(item.start);
                                if(typeof item.end !== 'undefined') item.end = getTimeFormat(item.end);
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                tableTR += '<tr>\n' +
                                    '   <td data-label="Номер" class="column-table">\n' +
                                    '       <input value="'+item.id+'" hidden="hidden">\n' +
                                    '       <span class="tools-td-text table-td-text column-table_checkbox-number">'+item.id+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Проект">\n' +
                                    '       <a href="/projects/edit/1/" title="Редактирование проекта">'+item.name+'</a>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Клиент">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.client_name+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Телефон">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.phoneNumber+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Начало аренды">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.start+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Окончание аренды">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.end+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Дата и время создания">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.created+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Сотрудник">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.employee+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Примечание">\n' +
                                    '       <span class="tools-td-text table-td-text">'+item.note+'</span>\n' +
                                    '   </td>\n';

                                if(typeof item.photos !== 'undefined') {
                                    if (item.photos.length > 0) {
                                        var photos_code = '';
                                        item.photos.forEach(function (item, i, arr) {
                                            photos_code += '<span class="column-table__photo-mini">\n' +
                                                '   <a rel="modal:open" href="#' + i + '">\n' +
                                                '       <img class="column-table__photo-mini_img" src="' + item + '">\n' +
                                                '           <div id="' + i + '" class="modal">\n' +
                                                '           <img src="' + item + '">\n' +
                                                '       </div>\n' +
                                                '   </a>\n' +
                                                '</span>';
                                        });
                                        tableTR += '<td data-label="Фотографии отгрузки">'+photos_code+'</td>\n';
                                    }
                                    else tableTR += '<td data-label="Фотографии отгрузки"></td>\n';
                                }
                                else tableTR += '<td data-label="Фотографии отгрузки"></td>\n';

                                tableTR +=    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'CLIENT' && dataJSON.filter == 'ALL_CLIENTS') {
                            content.forEach(function (item, i, arr) {


                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) var tableTRCheck = '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else var tableTRCheck = '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR += '<tr>\n' +
                                    '   <td class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n' +
                                    '           '+tableTRCheck+'<input hidden="hidden" value="'+item.id+'"><span class="tools-td-text table-td-text column-table_checkbox-number">'+item.id+'</span>\n' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="ФИО" class="align-middle">\n' +
                                    '       <a class="nav-link" style="font-size: medium" href="/clients/edit/'+item.id+'/">'+item.fullName+'</a>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Телефон"><span class="tools-td-text table-td-text">'+item.phoneNumber+'</span>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Скидка"><span class="tools-td-text table-td-text">'+item.discount+'</span>\n' +
                                    '   </td><td data-label="Кол-во проектов"><span class="tools-td-text table-td-text">Количество\n' +
                                    '           проектов</span>\n' +
                                    '   </td><td data-label="Примечание"><span class="tools-td-text table-td-text">Примечание о индивидуальном</span>\n' +
                                    '   </td><td data-label="Договор"><span class="tools-td-text table-td-text">Договор</span>\n' +
                                    '   </td><td data-label="Черный список"><span class="tools-td-text table-td-text">false</span>\n' +
                                    '   </td>\n' +
                                    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'TOOLS' && dataJSON.filter == 'SALE') {
                            content.forEach(function (item, i, arr) {
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                tableTR += '<tr>\n' +
                                    '   <td data-label="№" class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n';
                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) tableTR += '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else  tableTR += '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR +=    '<input type="hidden" value="'+item.id+'">\n' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Дата создания">'+item.created+'</td>\n' +
                                    '   <td data-label="Исполнитель">'+item.executor+'</td>\n' +
                                    '   <td data-label="Телефон">'+item.phone_number+'</td>\n' +
                                    '   <td data-label="Всего цена продажи">'+item.priceSell+'</td>\n' +
                                    '   <td data-label="Сотрудник">'+item.employee+'</td>\n' +
                                    '   <td data-label="Примечание">'+item.note+'</td>\n' +
                                    '   <td data-label="Статус">'+item.status_value+'</td>\n';
                                if(typeof item.photos !== 'undefined') {
                                    if (item.photos.length > 0) {
                                        var photos_code = '';
                                        item.photos.forEach(function (item, i, arr) {
                                            photos_code += '<span class="column-table__photo-mini">\n' +
                                                '   <a rel="modal:open" href="#' + i + '">\n' +
                                                '       <img class="column-table__photo-mini_img" src="' + item + '">\n' +
                                                '           <div id="' + i + '" class="modal">\n' +
                                                '           <img src="' + item + '">\n' +
                                                '       </div>\n' +
                                                '   </a>\n' +
                                                '</span>';
                                        });
                                        tableTR += '<td data-label="Фотографии">'+photos_code+'</td>\n';
                                    }
                                    else tableTR += '<td data-label="Фотографии"></td>\n';
                                }
                                else tableTR += '<td data-label="Фотографии"></td>\n';
                                tableTR +=    '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if((dataJSON.paginationConst == 'PROJECT_CREATE' && dataJSON.filter == 'INSTOCK') ||
                            (dataJSON.paginationConst == 'PROJECT' && dataJSON.filter == 'GET_TOOLS_BY_PROJECT')){
                            content.forEach(function (item, i, arr) {


                                tableTR += '<tr class="row-tool">\n' +
                                    '   <td class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n';

                                var priceVal = '';
                                var NeedId = item.id;
                                var tableTRNN = '<input class="column-table_checkbox choose_tool ids" type="checkbox">\n';
                                if(Object.keys(checkedIds).length > 0) {
                                    checkedIds.forEach(function (item, i, arr) {
                                        if(item.id == NeedId){
                                            tableTRNN = '<input class="column-table_checkbox choose_tool ids" type="checkbox" checked>\n';
                                        }
                                    });
                                }
                                tableTR += tableTRNN+'\n';


                                tableTR += '<input type="hidden" value="'+item.id+'">\n' +
                                    '   <span className="tools-td-text table-td-text column-table_checkbox-number">'+item.id+'</span>\n' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Наименование">'+item.name+'</td>\n' +
                                    '   <td data-label="Штрихкод" class="barcode-tool">'+item.barcode+'</td>\n' +
                                    '   <td data-label="Группа">'+item.category+'</td>\n' +
                                    '   <td data-label="Марка\\Модель">'+item.model+'</td>\n' +
                                    '   <td data-label="Серийный номер">'+item.serialNumber+'</td>\n' +
                                    '   <td data-label="Комментарий">'+item.comment+'</td>\n' +
                                    '   <td data-label="Характеристики">'+item.characteristics+'</td>\n' +
                                    '   <td data-label="Состояние">'+item.state+'</td>\n' +
                                    '   <td data-label="Цена" class="price-tool">'+item.costPrice+'</td>\n' +
                                    '   <td data-label="Себестоимость">'+item.priceSell+'</td>\n' +
                                    '   <td data-label="Комплект">'+item.equip+'</td>\n' +
                                    '</tr>';
                            });

                            $('table tbody:last').html(tableTR);
                        }
                        else if((dataJSON.paginationConst == 'TOOLS' && dataJSON.filter == 'INSTOCK') ||
                            (dataJSON.paginationConst == 'TOOLS' && dataJSON.filter == 'WAITING') ) {
                            content.forEach(function (item, i, arr) {

                                if(typeof item.start !== 'undefined') item.start = getTimeFormat(item.start);
                                if(typeof item.end !== 'undefined') item.end = getTimeFormat(item.end);
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                tableTR += '<tr class="row-tool">\n' +
                                    '   <td class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n';

                                var priceVal = '';
                                var NeedId = item.id;
                                var tableTRNN = '<input class="column-table_checkbox choose_tool ids" type="checkbox">\n';
                                if(Object.keys(checkedIds).length > 0) {
                                    checkedIds.forEach(function (item, i, arr) {
                                        if(item.id == NeedId){
                                            tableTRNN = '<input class="column-table_checkbox choose_tool ids" type="checkbox" checked>\n';
                                            priceVal = item.price;

                                        }
                                    });
                                }
                                tableTR += tableTRNN+'\n';


                                tableTR += '<input type="hidden" value="'+item.id+'">\n' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Наименование">'+item.name+'</td>\n' +
                                    '   <td data-label="Цена ремонта" class="td-whith-input">\n' +
                                    '       <input class="form-control input-without-style repair_price" type="text" placeholder="Введите цену" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*?)\\..*/g,\'$1\');" aria-describedby="item-human-passport_serial" id="tools_id_with_prices0.price" name="tools_id_with_prices[0].price" value="'+priceVal+'">\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Штрихкод" class="barcode-tool">'+item.barcode+'</td>\n' +
                                    '   <td data-label="Группа">'+item.category+'</td>\n' +
                                    '   <td data-label="Марка\\Модель">'+item.model+'</td>\n' +
                                    '   <td data-label="Серийный номер">'+item.serialNumber+'</td>\n' +
                                    '   <td data-label="Комментарий">'+item.comment+'</td>\n' +
                                    '   <td data-label="Характеристики">'+item.characteristics+'</td>\n' +
                                    '   <td data-label="Состояние">'+item.state+'</td>\n' +
                                    '   <td data-label="Цена">'+item.costPrice+'</td>\n' +
                                    '   <td data-label="Себестоимость">'+item.priceSell+'</td>\n' +
                                    '   <td data-label="Комплект">'+item.equip+'</td>\n' +
                                    '</tr>';
                            });

                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'TOOLS' && dataJSON.filter == 'WRITEOFF') {
                            content.forEach(function (item, i, arr) {

                                if(typeof item.start !== 'undefined') item.start = getTimeFormat(item.start);
                                if(typeof item.end !== 'undefined') item.end = getTimeFormat(item.end);
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                tableTR += '<tr>\n' +
                                    '   <td data-label="№" class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n';


                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) tableTR += '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else  tableTR += '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR += '<input type="hidden" value="'+item.id+'">' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Дата создания">'+item.created+'</td>\n' +
                                    '   <td data-label="Всего сумма списания">'+item.priceOff+'</td>\n' +
                                    '   <td data-label="Сотрудник">'+item.employee+'</td>\n' +
                                    '   <td data-label="Примечание">'+item.note+'</td>\n' +
                                    '   <td data-label="Статус">'+item.status_value+'</td>';
                                if(typeof item.photos !== 'undefined') {
                                    if (item.photos.length > 0) {
                                        var photos_code = '';
                                        item.photos.forEach(function (item, i, arr) {
                                            photos_code += '<span class="column-table__photo-mini">\n' +
                                                '   <a rel="modal:open" href="#' + i + '">\n' +
                                                '       <img class="column-table__photo-mini_img" src="' + item + '">\n' +
                                                '           <div id="' + i + '" class="modal">\n' +
                                                '           <img src="' + item + '">\n' +
                                                '       </div>\n' +
                                                '   </a>\n' +
                                                '</span>';
                                        });
                                        tableTR += '<td data-label="Фотографии">'+photos_code+'</td>\n';
                                    }
                                    else tableTR += '<td data-label="Фотографии"></td>\n';
                                }
                                else tableTR += '<td data-label="Фотографии"></td>\n';

                                tableTR += '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if(dataJSON.paginationConst == 'TOOLS' && dataJSON.filter == 'REPAIR') {
                            content.forEach(function (item, i, arr) {

                                if(typeof item.start !== 'undefined') item.start = getTimeFormat(item.start);
                                if(typeof item.end !== 'undefined') item.end = getTimeFormat(item.end);
                                if(typeof item.created !== 'undefined') item.created = getTimeFormat(item.created);

                                tableTR += '<tr>\n' +
                                    '   <td data-label="№" class="column-table">\n' +
                                    '       <div class="column-table_checkbox-wrapp">\n';


                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) tableTR += '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else  tableTR += '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR += '<input type="hidden" value="'+item.id+'">' +
                                    '       </div>\n' +
                                    '   </td>\n' +
                                    '   <td data-label="Дата создания">'+item.created+'</td>\n' +
                                    '   <td data-label="Название оборудования">'+item.tools+'</td>\n' +
                                    '   <td data-label="Исполнитель">'+item.executor+'</td>\n' +
                                    '   <td data-label="Телефон">'+item.phone_number+'</td>\n' +
                                    '   <td data-label="Начало ремонта">'+item.start+'</td>\n' +
                                    '   <td data-label="Окончание ремонта">'+item.end+'</td>\n' +
                                    '   <td data-label="Всего цена ремонта">'+item.priceRepair+'</td>\n' +
                                    '   <td data-label="Сотрудник">'+item.employee+'</td>\n' +
                                    '   <td data-label="Примечание">'+item.note+'</td>\n' +
                                    '   <td data-label="Статус">'+item.status_value+'</td>\n';
                                if(typeof item.photos !== 'undefined') {
                                    if (item.photos.length > 0) {
                                        var photos_code = '';
                                        item.photos.forEach(function (item, i, arr) {
                                            photos_code += '<span class="column-table__photo-mini">\n' +
                                                '   <a rel="modal:open" href="#' + i + '">\n' +
                                                '       <img class="column-table__photo-mini_img" src="' + item + '">\n' +
                                                '           <div id="' + i + '" class="modal">\n' +
                                                '           <img src="' + item + '">\n' +
                                                '       </div>\n' +
                                                '   </a>\n' +
                                                '</span>';
                                        });
                                        tableTR += '<td data-label="Фотографии">'+photos_code+'</td>\n';
                                    }
                                    else tableTR += '<td data-label="Фотографии"></td>\n';
                                }
                                else tableTR += '<td data-label="Фотографии"></td>\n';

                                tableTR += '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                        else if((dataJSON.paginationConst == 'TOOLS') ||
                            (dataJSON.paginationConst == 'TOOLS_ADD' && dataJSON.filter == 'INSTOCK')){
                            content.forEach(function(item, i, arr) {
                                var color_tr = "";
                                if(item.status_string == 'В аренде') color_tr = "style='background-color: red; color: #fff;'";
                                else if(item.status_string == 'На складe') color_tr = "style='background-color: green; color: #fff;'";
                                else if(item.status_string == 'В ожидании') color_tr = "style='background-color: yellow; color: #000;'";
                                else if(item.status_string == 'Бронь') color_tr = "style='background-color: orange; color: #fff;'";
                                else if(item.status_string == 'В ремонте') color_tr = "style='background-color: gray; color: #fff;'";
                                else if(item.status_string == 'Списано') color_tr = "style='background-color: brown; color: #fff;'";
                                else if(item.status_string == 'Продано') color_tr = "style='background-color: blue; color: #fff;'";

                                tableTR += '<tr '+color_tr+'>\n' +
                                    '<td>' +
                                    '   <div class="column-table_checkbox-wrapp " id="ids_tools">\n';

                                var checked = false;
                                var curId = item.id
                                checkedIds.forEach(function (item, i, arr) {
                                    if(item.id == curId) checked = true;
                                });

                                if (checked) tableTR += '<input class="column-table_checkbox ids" type="checkbox" checked>\n';
                                else  tableTR += '<input class="column-table_checkbox ids" type="checkbox">\n';

                                tableTR += '       <input type="hidden" class="id_tool" value="'+item.id+'">\n' +
                                    '       <span class="tools-td-text table-td-text column-table_checkbox-number tool_id">'+item.id+'</span>\n' +
                                    '   </div>\n' +
                                    '</td>\n' +
                                    '<td><a class="tools-td-text table-td-text tool_name" href="/tools/edit/'+item.id+'/">'+item.name+'</a></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_category">'+item.category+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_barcode">'+item.barcode+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_estimateName">'+item.estimateName+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_model">'+item.model+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_serialNumber">'+item.serialNumber+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_comment">'+item.comment+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_characteristics">'+item.characteristics+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_state">'+item.state+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_priceByDay">'+item.priceByDay+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_costPrice">'+item.costPrice+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_equip">'+item.equip+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_amount">'+item.amount+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_status">'+item.status_string+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_numberWorkingShifts">'+item.numberWorkingShifts+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_project">'+item.project+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_incomeFromTools">'+item.incomeFromTools+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_priceSell">'+item.priceSell+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_incomeSales">'+item.incomeSales+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_incomeInvestorProcents">'+item.incomeInvestorProcents+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_incomeInvestor">'+item.incomeInvestor+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_repairAmount">'+item.repairAmount+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_priceSublease">'+item.priceSublease+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_paymentSublease">'+item.paymentSublease+'</span></td>\n' +
                                    '<td><span class="tools-td-text table-td-text tool_incomeAdditional">'+item.incomeAdditional+'</span></td>\n';

                                if(typeof item.photos !== 'undefined') {
                                    if (item.photos.length > 0) {
                                        var photos_code = '';
                                        item.photos.forEach(function (item, i, arr) {
                                            photos_code += '<span class="column-table__photo-mini">\n' +
                                                '   <a rel="modal:open" href="#' + i + '">\n' +
                                                '       <img class="column-table__photo-mini_img" src="' + item + '">\n' +
                                                '           <div id="' + i + '" class="modal">\n' +
                                                '           <img src="' + item + '">\n' +
                                                '       </div>\n' +
                                                '   </a>\n' +
                                                '</span>';
                                        });
                                        tableTR += '<td>'+photos_code+'</td>\n';
                                    }
                                    else tableTR += '<td></td>\n';
                                }
                                else tableTR += '<td></td>\n';

                                tableTR += '</tr>';
                            });
                            $('table tbody:last').html(tableTR);
                        }
                    }
                }
            }
        );
        event.preventDefault();
    });

    //SMETA
    $(".fIn1, .fIn2, .fIn3, .fIn4").on('keyup', function (e) {
        checkItems($(this));
    });

    function checkItems(row){
        var currentRow = row.parent().parent();
        var fIn1 = currentRow.find('.fIn1').val();
        var fIn2 = currentRow.find('.fIn2').val();
        var fIn3 = currentRow.find('.fIn3').val();
        var fIn4 = currentRow.find('.fIn4').val();
        var priceProject = 0;
        if(fIn4 != "" && fIn4 <= 0){
            priceProject = fIn1*fIn3*fIn2;
        }
        else{
            fIn4 = fIn4 / 100;
            priceProject = (fIn1*fIn3) - (fIn4 * fIn1*fIn3);
            priceProject = priceProject*fIn2;
        }

        var priceSmeta = fIn1 * fIn2;

        currentRow.find("td:eq(6) span").text(Math.round(priceSmeta));
        currentRow.find("td:eq(7) span").text(Math.round(priceProject));

        ccSum();
        allSumm();
    }

    function checkTransport(row){
        var currentRow = row.parent().parent();
        var fIn1 = currentRow.find('.fIn1_t').val();
        var fIn2 = currentRow.find('.fIn2_t').val();
        var fIn3 = currentRow.find('.fIn3_t').val();
        var fIn4 = currentRow.find('.fIn4_t').val();
        var priceProject = 0;
        if(fIn4 != "" && fIn4 <= 0){
            priceProject = fIn1*fIn3*fIn2;
        }
        else{
            fIn4 = fIn4 / 100;
            priceProject = (fIn1*fIn3) - (fIn4 * fIn1*fIn3);
            priceProject = priceProject*fIn2;
        }

        var priceSmeta = fIn1 * fIn2;

        currentRow.find("td:eq(6) span").text(Math.round(priceSmeta));
        currentRow.find("td:eq(7) span").text(Math.round(priceProject));

        ccSum2();
        allSumm();
    }

    $('body').on('keyup', '.fIn1_t, .fIn2_t, .fIn3_t, .fIn4_t', function(e){
        checkTransport($(this));
    });

    function ccSum(){
        var allPrice = 0;
        $('.fIn6').each(function (index, value) {
            allPrice = allPrice + parseInt($(this).text());
        });

        var skPrice = $('.fsIn2').val();
        if(skPrice != "" && skPrice > 0){
            $('.fsIn1').val(Math.round(allPrice));
            $('.fsIn3').val(Math.round(allPrice - (allPrice * (skPrice / 100))));
        }
        else{
            $('.fsIn1').val(Math.round(allPrice));
            $('.fsIn3').val(Math.round(allPrice));
        }
    }

    function ccSum2(){
        var allPrice = 0;
        $('.fIn6_t').each(function (index, value) {
            if($(this).text() != "" && $(this).text() > 0) allPrice = allPrice + parseInt($(this).text());
        });

        if(allPrice != 0) $('.fsIn1_t').val(allPrice);

        var skPrice = $('.fsIn2_t').val();
        if(skPrice != "" && skPrice > 0){
            $('.fsIn3_t').val(Math.round(allPrice - (allPrice * (skPrice / 100))));
        }
        else{
            if(skPrice != "" && skPrice > 0) $('.fsIn3_t').val(Math.round(allPrice));
        }
    }

    function allSumm(){
        var pr1 = $('.fsIn3').val();
        var pr2 = $('.fsIn1_t').val();
        var allPr = 0;
        if(pr1 != "" && pr1 > 0){
            allPr = pr1;
            if(pr2 != "" && pr2 > 0){
                allPr = parseInt(allPr)+parseInt(pr2);
            }
        }

        var perc_USN = $('.fsIn3_USN_t').val();
        if(perc_USN != "" && parseInt(perc_USN) > 0) perc_USN = parseInt($('.fsIn3_USN_t').val());
        else perc_USN = 0;

        if(allPr != "" && allPr > 0) $('.fsIn2_t').val(parseInt(allPr));
        if(allPr != "" && allPr > 0) $('.fsIn3_t').val(parseInt(allPr - (allPr * (perc_USN / 100))));
    }



    $('.fIn1').each(function (index, value) {
        checkItems($(this));
    });

    $('.fIn1_t').each(function (index, value) {
        checkTransport($(this));
    });

    ccSum();
    ccSum2();
    allSumm();

    $(".fsIn2").on('keyup', function (e) {
        ccSum();
        allSumm();
    });

    $(".fsIn3_USN_t").on('keyup', function (e) {
        allSumm();
    });

    $('#save_estimate, #estimate_load_file').click(function (e) {
        var items = [];
        $('.item_id').each(function (index, value) {
            var send_arr = {};
            var row_data = $(this).parent().parent();
            send_arr['id'] = $(this).val();
            send_arr['section'] = row_data.find('.item_category').val();
            send_arr['name'] = row_data.find('td:eq(1)').text();
            send_arr['priceByDay'] = row_data.find('.fIn1').val();
            send_arr['amount'] = row_data.find('.fIn2').val();
            send_arr['countShifts'] = row_data.find('.fIn3').val();
            send_arr['discount'] = row_data.find('.fIn4').val();
            items.push(send_arr);
        });
        var sendUrl = window.location.pathname;

        $('.sat_item').each(function (index, value) {
            var tr = {};
            var row_data = $(this);
            // tr['id'] = $(this).val();
            // tr['section'] = row_data.find('.item_category').val();
            // tr['countShifts'] = row_data.find('.fIn3').val();
            // send_arr['discount'] = row_data.find('.fIn4').val();

            if(row_data.find('.fIn0_t').val() != "" &&
                row_data.find('.fIn1_t').val() &&
                row_data.find('.fIn2_t').val() != "" &&
                row_data.find('.fIn3_t').val() != "") {
                tr['name'] = row_data.find('.fIn0_t').val();
                tr['priceByDay'] = row_data.find('.fIn1_t').val();
                tr['amount'] = row_data.find('.fIn2_t').val();
                tr['countShifts'] = row_data.find('.fIn3_t').val();
                tr['discount'] = row_data.find('.fIn4_t').val();
                tr['section'] = 'SERVICE';
                items.push(tr);
            }

        });

        if($(this).attr('id') == 'estimate_load_file') {
            sendUrl = sendUrl.replace('/estimate/create/', '/estimate/download-estimate/');
        }

        var allJson = {
            'toolsEstimates' : items,
            'params': {
                'id': $('.estimate_id_value').val(),
                'allByPRoject': $('.fsIn1').val(),
                'discountByTools': $('.fsIn2').val(),
                'allByProjectWithDiscount': $('.fsIn3').val(),

                'allByService': $('.fsIn1_t').val(),
                'finalSumByProject': $('.fsIn2_t').val(),
                'procentUsn': $('.fsIn3_USN_t').val(),
                'finalSumWithUsn': $('.fsIn3_t').val(),
            }
        };

        send_data(sendUrl, "POST", allJson);
    });


    $('.add_transport').click(function (e) {
        var tableCell = '<tr class="sat_item">\n' +
            '    <td class="first-th remove_transport" style="cursor: pointer; background: #1ab394; color: #fff; font-size: 20px;">-</td>\n' +
            '    <td><input type="text" class="form-control__table fIn0_t" value="" placeholder="Наименование" style="font-size: 14px;"></td>\n' +
            '    <td><input type="text" class="form-control__table fIn1_t" placeholder="Цена за смену"></td>\n' +
            '    <td><input type="text" class="form-control__table fIn2_t" placeholder="Количество"></td>\n' +
            '    <td><input type="text" class="form-control__table fIn3_t" placeholder="Количество смен"></td>\n' +
            '    <td><input type="text" class="form-control__table fIn4_t" placeholder="Скидка"></td>\n' +
            '    <td><span class="table-td-text fIn5_t"></span></td>\n' +
            '    <td><span class="table-td-text fIn6_t"></span></td>\n' +
            '</tr>';
        $(this).parent().after(tableCell);
    });

    $('body').on('click', '.remove_transport', function(e){
        $(this).parent().remove();
    });



    //BAR CODE

    var vlId = 0;
    var clId = 000;
    var xlId = 000;

    function getVlID(sel){
        qlId = 0;
        var curVal = sel.val();
        $(sel).find('option').each(function (index, value) {
            if($(this).val() == curVal){
                qlId = $(this).attr('data-id')
            }
        });
        return qlId;
    }

    function getXlID(){

        var xlId = 000;
        sel = $("select[name='category']");
        var curVal = sel.val();

        $(sel).find('option').each(function (index, value) {
            if($(this).val() == curVal){
                if(typeof $(this).attr('data-number') !== "undefined"){
                    var xlIdCur = $(this).attr('data-number');
                    if(xlIdCur.length == 1) xlId = "00"+xlIdCur;
                    else if(xlIdCur.length == 2) xlId = "0"+xlIdCur;
                    console.log(xlId);
                }
            }
        });
        return xlId;
    }

    $("#select_type_tool").change(function() {
        vlId = getVlID($(this));
        clId = getVlID($("select[name='category']"));
        xlId = getXlID();
        $('#barcode').val(vlId+clId+xlId);
    });
    vlId = getVlID($("#select_type_tool") );


    $("select[name='category']").change(function() {
        clId = getVlID($(this));
        vlId = getVlID($("#select_type_tool") );
        xlId = getXlID();
        $('#barcode').val(vlId+clId+xlId);
    });
    clId = getVlID($("select[name='category']"));

    xlId = getXlID();
    $('#barcode').val(vlId+clId+xlId);

});





