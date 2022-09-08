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
                    if(url==="/admin_panel") {
                        window.location.reload();
                        showMessage(res);
                    }
                    else if(url==="/admin_panel/edit/"+data.id)
                    {
                        window.location.replace("/admin_panel");
                        showMessage(res);
                    }
                    else
                    {
                        showMessage(res);
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
        if(el.value==='INDIVIDUAL')
        {
            photos_arr = $("#photos_individual").val().split(",",9999);
            photos_arr.forEach(function(item, i, arr) {
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
        }
        else
        {
            photos_arr = $("#photos_legal").val().split(",",9999);
            photos_arr.forEach(function(item, i, arr) {
                photos_arr[i] = photos_arr[i].trim();
            });
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
        send_data("/clients/create","POST",formData);
    });
    $('#update_client').click(function (e) {
        const el = document.getElementById('select_type_client');
        let id = $("#client_id").val();
        let formData;
        let photos_arr;
        let inBlackList = document.getElementById("inBlackList").checked;
        if(el.value==='INDIVIDUAL')
        {
            photos_arr = $("#photos_individual").val().split(",",9999);
            photos_arr.forEach(function(item, i, arr) {
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
        }
        else
        {
            photos_arr = $("#photos_legal").val().split(",",9999);
            photos_arr.forEach(function(item, i, arr) {
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
        send_data("/clients/edit/"+id,"POST",formData);

    });
    $('#update_user').click(function (e) {
        id = $("#user_id").val();
        var formData = {
            id,
            username: $("#username").val(),
            password: $("#password").val(),
            fullName: $("#fullName").val(),
            roles: $("#roles :selected").val(),
        };
        send_data("/admin_panel/edit/"+id,"POST",formData);
    });
    $('#save_user').click(function (e) {
        var formData = {
            username: $("#username").val(),
            password: $("#password").val(),
            fullName: $("#fullName").val(),
            roles: $("#roles :selected").val(),
        };
        send_data("/admin_panel","POST",formData);
    });
});

function showMessage(res)
{
    $("#alert_message").text(res.message);
    $("#ex1").modal();
}



