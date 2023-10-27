var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
function getUrlVars(url) {
  var vars = {};
  var parts = url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}
$("body").on("click", "#get_tools_by_filters", function (event) {
  var getParams = getUrlVars($(".page-item a").attr("href"));
  var postData = {
    filter: "TOOLS_BY_FILTER",
    categoryId: $("#category_tools :selected").val(),
    section: $("#estimate_section :selected").val(),
    page: getParams.page,
    size: getParams.size,
  };
  console.log(postData);
  $.ajax({
    contentType: "application/json; charset=utf-8",
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
    url: "/pagination",
    dataType: "json",
    data: JSON.stringify(postData),
    success: function (res) {
      if (res.response_code == 200) {
        var content = res.data[1].content;
        var tableTR = "";
        content.forEach(function (item, i, arr) {
          tableTR +=
            '<tr class="content_table__info_line row-tool">\n' +
            '   <td class="content_table__info_column">\n ';
          var priceVal = "";
          var NeedId = item.id;
          var tableTRNN = '<input class="ids" type="checkbox">\n';
          var checkedIds = [];
          if (Object.keys(checkedIds).length > 0) {
            checkedIds.forEach(function (item, i, arr) {
              if (item.id == NeedId) {
                tableTRNN = '<input class="ids" type="checkbox" checked>\n';
              }
            });
          }
          tableTR += tableTRNN + "\n";
          tableTR +=
            '<input type="hidden" value="' +
            item.id +
            '">\n' +
            '   <span className="content_table__info_column">' +
            item.id +
            "</span>\n" +
            "       </div>\n" +
            "   </td>\n" +
            '   <td class="content_table__info_column" data-label="Наименование">' +
            item.name +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Штрихкод" class="barcode-tool">' +
            item.barcode +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Группа">' +
            item.category +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="МаркаМодель">' +
            item.model +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Серийный номер">' +
            item.serialNumber +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Комментарий">' +
            item.comment +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Характеристики">' +
            item.characteristics +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Состояние">' +
            item.state +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Цена" class="price-tool">' +
            item.costPrice +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Себестоимость">' +
            item.priceSell +
            "</td>\n" +
            '   <td class="content_table__info_column" data-label="Комплект">' +
            item.equip +
            "</td>\n" +
            "</tr>";
        });
        $("table tbody:last").html(tableTR);
      }
      selectRowsSavedInCheckedIds();
      event.preventDefault();
    },
  });
});
