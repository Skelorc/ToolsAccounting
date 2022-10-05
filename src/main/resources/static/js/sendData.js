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
                    if (url === "/admin-panel" || url === "/tools" || url === "/repair/create" || url === "/tools/change-status" || url === "/sale/create" || url === "/write-off/create") {
                        window.location.reload();
                    } else if (url === "/admin-panel/edit/" + data.id) {
                        window.location.replace("/admin-panel");
                    } else if (url === "/projects/create") {
                        if (res.status === 'CREATED') {
                            window.location.replace("/estimate/create");
                        } else {
                            var message = res.message;
                            $("#alert_message").text(message.toString());
                            $("#ex1").modal();
                        }
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
    $('#update_user').click(function (e) {
        id = $("#user_id").val();
        var formData = {
            id,
            username: $("#username").val(),
            password: $("#password").val(),
            fullName: $("#fullName").val(),
            roles: $("#roles :selected").val(),
        };
        send_data("/admin-panel/edit/" + id, "POST", formData);
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
        console.log(formData);
        send_data("/tools/change-status", "POST", formData);
    });
    $('#btn_create_project').click(function (e) {
        let photos_arr;
        photos_arr = $("#photos").val().split(",", 9999);
        photos_arr.forEach(function (item, i, arr) {
            photos_arr[i] = photos_arr[i].trim();
        });
        var identifiers = [];
        let count = 0;
        $('.choose_tool').each(function (index, value) {
            if ($(this)[0].checked) {
                var id = $(this).next().attr("value");
                var val = $(this)[0].checked;
                var price = 0;
                if (val !== null) {
                    identifiers[count] = {'id': id};
                    count++;
                }
            }
        });
        var formData = {
            status: $('#select_status_project').val(),
            name: $('#name_project').val(),
            start: $('#date_start').val(),
            end: $('#date_end').val(),
            typeLease: $('#type').val(),
            classification: $('#classification').val(),
            created: $('#created').val(),
            client_id: $('#client').val(),
            note: $('#note').val(),
            photos: photos_arr,
            tools_id: identifiers,
            sum: $('#sum').val(),
            priceTools: $('#priceTools').val(),
            discountByProject: $('#discountByProject').val(),
            sumWithDiscount: $('#sumWithDiscount').val(),
            finalSumUsn: $('#finalSumUsn').val(),
            priceWork: $('#priceWork').val(),
            received: $('#received').val(),
            remainder: $('#remainder').val()
        };
        send_data("/projects/create", "POST", formData);
    });
});





