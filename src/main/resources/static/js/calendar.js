let workingShifts = []
$(document).ready(function(){
    $('.rabSM').datepicker({
        multidate: true,
        format: 'dd-mm-yyyy'
    });

    $('body').on('click','tr', function(){
        if(this.textContent == 'Выберите рабочие смены'){
            var counter = 1;
            $('body .custom-day__date').each(function() {
                var curDate = parseInt($(this).text());
                if(curDate <= counter && curDate >= counter){
                    counter = counter + 1;
                    $(this).parent().find('.clc_dt').click();
                }
            });
        }
    });

    $('body').on('click','.clc_dt', function(){
        var curDate = $(this).attr('data-time');
        var curClass = $(this).attr('class');
        if(curClass.indexOf('active_d') <= -1){
            $(this).addClass('active_d');
            workingShifts.push(curDate)
        }
        else{
            $(this).removeClass('active_d');
            const index = workingShifts.indexOf(curDate)
            if(index > -1){
                workingShifts.splice(index, 1)
            }
        }
        workingShifts.sort((a, b) => {
            let firstValue  = (Number(a[0]+a[1]) + (Number(a[3]+a[4]) * 100) + (Number(a[6] + a[7] + a[8] + a[9]) * 100000))
            let secondValue = (Number(b[0]+b[1]) + (Number(b[3]+b[4]) * 100) + (Number(b[6] + b[7] + b[8] + b[9]) * 100000))

            return firstValue - secondValue
        })
        console.log(workingShifts.join());
        document.querySelector(".form-control-date").value = workingShifts.join()
    });

    workingShiftStr = "";
    $('#working_shift div').each(function(){
        var dateShift = $(this).find(".dateShift").val().split("-");
        var typeShift = $(this).find(".typeShift").val().toLowerCase();
        
        workingShifts.push(dateShift[2]+"-"+dateShift[1]+"-"+dateShift[0]+"-"+typeShift);

        workingShiftStr = workingShiftStr+dateShift[2]+"-"+dateShift[1]+"-"+dateShift[0]+"-"+typeShift+",";
    });
    if(workingShiftStr.length > 0){
        workingShiftStr = workingShiftStr.slice(0, workingShiftStr.length - 1);
        document.querySelector(".form-control-date").value = workingShiftStr;
    }
});