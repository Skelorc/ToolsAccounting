////////////////////////////////////////////////////

// Таб контент

// function openUnit(evt, unitName) {
//     // Declare all variables
//     var i, tabcontent, tablinks;

//     // Get all elements with class="tabcontent" and hide them
//     tabcontent = document.getElementsByClassName("tabcontent");
//     for (i = 0; i < tabcontent.length; i++) {
//         tabcontent[i].style.display = "none";
//     }

//     // Get all elements with class="tablinks" and remove the class "active"
//     tablinks = document.getElementsByClassName("tablinks");
//     for (i = 0; i < tablinks.length; i++) {
//         tablinks[i].className = tablinks[i].className.replace(" active", "");
//     }

//     // Show the current tab, and add an "active" class to the button that opened the tab
//     document.getElementById(unitName).style.display = "block";
//     evt.currentTarget.className += " active";
// } 

// // Get the element with id="defaultOpen" and click on it
// if(document.getElementById("defaultOpen") !== null)
// {
//     document.getElementById("defaultOpen").click();
// }

//////////////////////////////

// загрузчик фотографий // позволяет менять внешний вид Input File (появится зеленая галочка) после того, как файл будет загружен

(function() {

    'use strict';

    $('.input-file').each(function() {
        var $input = $(this),
            $label = $input.next('.js-labelFile'),
            labelVal = $label.html();

    $input.on('change', function(element) {
        var fileName = '';
        if (element.target.value) fileName = element.target.value.split('\\').pop();
        fileName ? $label.addClass('has-file').find('.js-fileName').html(fileName) : $label.removeClass('has-file').html(labelVal);
    });
    });

})();

// popup modal попап всплывашка

$( document ).ready(function() {

    $('.open-popup').click(function(e) {
        e.preventDefault();
        $('.popup-bg').fadeIn(500);
        $('html').addClass('no-scroll');
    });
    
    // $('.outer-btn-popup').click(function() {
    //     $('.popup-bg').fadeOut(500);
    //     $('html').removeClass('no-scroll');
    // });
    $('.close-popup').click(function() {
        $('.popup-bg').fadeOut(500);
        $('html').removeClass('no-scroll');
    });
});

$(document).ready(function() {
    if(document.getElementById("date_start")!==null) {
        document.getElementById("date_start").value = new Date().toISOString().substring(0, 16);
    }if(document.getElementById("created")!==null) {
        document.getElementById("created").value = new Date().toISOString().substring(0, 16);
    }
});

