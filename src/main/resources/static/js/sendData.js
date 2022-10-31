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
                    if(parseIstatus_waitnt(res.response_code) == 200){
                        window.location.reload();
                    }
                    else if(parseInt(res.response_code) == 300){
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
                    formData[count] = {'id': id, 'statusTools': status};
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
        send_data("/projects/delete", "POST", {"ids": formData});
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
        count = 0;
        $('.id_ob').each(function (index, value) {
            if ($(this).parent().find('.ids')[0].checked) {
                if(formData != "") formData += ",";
                formData += $(this).attr("value");
                count++;
            }
        });
        var id = window.location.pathname.replace("/projects/edit/", "");
        console.log(count);
        if(count > 0){
            window.location.replace('/tools/replace-tool/'+id+"?ids="+formData+"&status=INSTOCK");
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

    var replaceIds = [];

    $('body').on('click','.choose_tool',function(){
        var checked = $(this).is(":checked");
        var id = $(this).parent().find('.id_tool').val();
        if(checked) replaceIds.push(id);
        else removeItemOnce(replaceIds, id);

        console.log(checked+" - "+id);
        console.log(replaceIds);

        if (window.location.pathname.indexOf('add-tool-to-project') > -1) {
            $("#btn_replace_tools_to_project").text("Добавить");
        }
        if (window.location.pathname.indexOf('replace-tool') > -1) {
            $("#btn_replace_tools_to_project").text("Заменить");
        }

        if (window.location.pathname.indexOf('/tools/') > -1) {
            if (replaceIds.length > 0) {
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
        send_data(sendUrl, "POST", {'old_ids': oldIds, 'new_ids': replaceIds});
    });

    $('body').on('click','#removeBtn',function(){
        var sendUrl = window.location.pathname;
        sendUrl = sendUrl.replace('/projects/edit','/projects/remove-tool');
        // sendUrl = sendUrl.substr(0, sendUrl.length - 1);
        send_data(sendUrl, "POST", {'ids': replaceIds});
    });

    $('body').on('click', '.page-item a', function(event){
        var getParams = getUrlVars(window.location.search);
        var linkData = $(this).attr('href');
        linkData = linkData.replace('/tools','/tools/pagination');

        if(typeof getParams.status !== 'undefined') {
            if (linkData.indexOf('?') > -1) linkData = linkData + "&status="+getParams.status;
            else linkData = linkData + "?status="+getParams.status;
            console.log(linkData);
        }
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
                        // console.log(res.data);
                        // console.log(res.data[1]);
                        // console.log(res.data[1].content);
                        var content = res.data[1].content;
                        var tableTR = '';
                        content.forEach(function(item, i, arr) {
                                tableTR += '<tr>\n' +
                                '<td>' +
                                '   <div class="column-table_checkbox-wrapp " id="ids_tools">\n';
                            if (replaceIds.indexOf(String(item.id)) > -1)  tableTR += '<input class="column-table_checkbox choose_tool" type="checkbox" checked>\n';
                            else tableTR += '<input class="column-table_checkbox choose_tool" type="checkbox">\n';

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
                            $('table tbody').html(tableTR);
                        });
                    }
                }
            }
        );
        event.preventDefault();
    });

    //SMETA
    $(".fIn1, .fIn2, .fIn3, .fIn4").on('keyup', function (e) {
        var currentRow = $(this).parent().parent();
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

        ccSum()
    });

    function ccSum(){
        var allPrice = 0;
        $('.fIn6').each(function (index, value) {
            allPrice = allPrice + parseInt($(this).text());
        });

        $('.fsIn1').val(allPrice);

        var skPrice = $('.fsIn2').val();
        console.log(skPrice);
        if(skPrice != "" && skPrice > 0){
            $('.fsIn3').val(Math.round(allPrice - (allPrice * (skPrice / 100))));
        }
        else{
            $('.fsIn3').val(Math.round(allPrice));
        }
    }
    ccSum()

    $(".fsIn2").on('keyup', function (e) {
        ccSum()
    });
});





