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
                    if(res.response_code == 200){
                        window.location.reload();
                    }
                    else if(res.response_code == 300){
                        window.location.replace(res.redirect_url);
                    }
                    else if(res.response_code == 400){
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
        var formData = [];
        let status;
        if ($(this).attr('id') === 'status_wait') {
            status = "WAITING";
        } else if ($(this).attr('id') === 'status_instock') {
            status = "INSTOCK";
        }
        let count = 0;
        $('.choose_tool').each(function (index, value) {
            if ($(this)[0].checked) {
                var id = $(this).next().attr("value");
                var val = $(this)[0].checked;
                if (val !== null) {
                    formData[count] = {'id': id, 'status': status};
                    count++;
                }
            }
        });
        send_data("/tools/change-status", "POST", formData);
    });
    $('#btn_delete_project').click(function (e) {
        var formData = [];
        let count = 0;
        $('.ids').each(function (index, value) {
            if ($(this)[0].checked) {
                formData[count] = $(this).next().attr("value");
                count++;
            }
        });
        send_data("/projects", "POST", formData);
    });

    $('#btn_delete_tools_from_project').click(function (e) {
        var formData = [];
        let count = 0;
        $('.ids').each(function (index, value) {
            if ($(this)[0].checked) {
                formData[count] = $(this).next().attr("value");
                count++;
            }
        });
        let id = $("#id_project").val();
        send_data("/projects/edit/delete-tool/"+id, "POST", formData);
    });

    $('#btn_add_tools_to_project').click(function (e) {
        var formData = [];
        let count = 0;
        $('.id_tool').each(function (index, value) {
            if ($(this).parent().find('.choose_tool')[0].checked) {
                formData[count] = $(this).attr("value");
                count++;
            }
        });
        let id = $("#project_id").val();

        send_data("/projects/edit/add-tool/"+id, "POST", formData);
    });


    $('#replaceBtn').click(function (e) {
        var formData = "";
        count = 0
        $('.id_ob').each(function (index, value) {
            if ($(this).parent().find('.ids')[0].checked) {
                if(formData != "") formData += ",";
                formData += $(this).attr("value");
                count++;
            }
        });
        var id = window.location.pathname.replace("/projects/edit/", "");
         if(count > 0) window.location.replace('/tools/replace-tool/'+id+"?ids="+formData);
        //console.log('/projects/replace-tool/'+id+"?ids="+JSON.stringify(formData))


        /*if(count > 0) send_data('/projects/replace-tool/'+id+"?ids="+formData, "GET");*/
    });


    $('body').on('click', '.page-item a', function(event){
        var linkData = $(this).attr('href');
        linkData = linkData.replace('/tools','/tools/pagination');
        $.ajax({
                contentType: "application/json; charset=utf-8",
                type: 'GET',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url: linkData,
                dataType: "json",
                success: function (res) {
                    if(res.response_code == 200){
                        console.log(res.data);
                        console.log(res.data[1]);
                        console.log(res.data[1].content);
                        var content = res.data[1].content;
                        // content.each(function (index, value) {
                        //     console.log(index);
                        //     console.log(value);
                        // });

                        content.forEach(function(item, i, arr) {
                          console.log( i + ": " + item + " (массив:" + arr + ")" );
                          $('tbody tr:eq('+i+')').find('.tool.category').text(item.tool.category);
                        });
                    }
                }
            }
        );
        event.preventDefault();
    });
});





