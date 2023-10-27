// реализовывать будем через класс, типа у нас синглтон считалки кароч будет:
class TotalCounter{
  // для начала зададим конструктор класса
  constructor( {  // Параметры которые будем передавать при создании считалки
                 // сюда положим всё, что связано с html. Типа это у нас карта с конечными поинтами, с которыми будем взаимодействовать
                 DOM: {
                   // для начала все наши инпуты, в которые будем вычислять цену, скидки и тп сюда передаем сразу элементы, например полученные через document.querySelector('#totalSumInput')
                   totalSumInput:          totalSumInputDomElem,       // итоговая сумма
                   selfCostInput:          selfCostInputElem,          // себестоимость оборудования
                   projectDiscountInput:   projectDiscountInputElem,   // скидка на проект
                   summWithDiscountInput:  summWithDiscountInputElem,  // сумма со скидкой
                   resultUSNInput:         resultUSNInputElem,         // итоговая УСН
                   serviceCostInput:       serviceCostInputElem,       // стоимость работ
                   alreadyPaidInput:       alreadyPaidInputElem,       // оплачено
                   yetUnpaidInput:         yetUnpaidInputElem,         // расчет неоплаченного остатка
                   // По инпутам у нас всё, на всякий случай передадим еще элемент таблицы товаров
                   tableElem:              tableElem
                 },

                 // сюда положим все ключи для поиска вспомогательных элементов - это классы или айдишники строк таблицы, ячеек в них, чекбоксов и все прочего. Поскольку это контент динамический то сразу его получить как элементы не получится, будем получать походу пьессы. Сюда будем передавать строки для поиска через document.querySelector() / document.querySelectorAll(). Вид строки: "#idOfElement" или ".classOfElement"
                 nameOf: {
                   tableRow:       tableRowNameString,       // для поиска табличных строк
                   checkBox:       checkBoxNameString,       // для поиска чекбокса
                   tableCellPrice: tableCellPriceNameString, // для поиска табличных строк
                 }
               }
  ){
    // При запуске конструктора сохраняем переданные эелементы как свойства экземпляра. Доступ к ним будет по пути totalCounter.DOM.нужныйЭлемент
    this.DOM = {
      // для начала все наши инпуты, в которые будем вычислять цену, скидки и тп сюда передаем сразу элементы, например полученные через document.querySelector('#totalSumInput')
      totalSumInput:          totalSumInputDomElem,       // итоговая сумма
      selfCostInput:          selfCostInputElem,          // себестоимость оборудования
      projectDiscountInput:   projectDiscountInputElem,   // скидка на проект
      summWithDiscountInput:  summWithDiscountInputElem,  // сумма со скидкой
      resultUSNInput:         resultUSNInputElem,         // итоговая УСН
      serviceCostInput:       serviceCostInputElem,       // стоимость работ
      alreadyPaidInput:       alreadyPaidInputElem,       // оплачено
      yetUnpaidInput:         yetUnpaidInputElem,         // расчет неоплаченного остатка
      tableElem:              tableElem
    }

    // Сохраняем переданные строки с именами элементов
    this.nameOf = {
      tableRow:       tableRowNameString,       // для поиска табличных строк
      checkBox:       checkBoxNameString,       // для поиска чекбокса
      tableCellPrice: tableCellPriceNameString, // для поиска табличных строк
    }

    // Создаём пустой массив для табличных строк, а именно - их реальных элементов в верстке, которые будем отслеживать. Пока что при перезагрузке страницы всё будет "анчекаться" и массив будет становиться пустым, не думаю, что вы там стейт страницы в локальном сторадже сохраняете, так что это не важно... :)
    this.rowsToTrack = []

    this.addRows()
    this.addEventListeners()
  }

  // теперь наобъявляем методов

  addRow(rowElem){      // этим методом добавляем очередную строку таблицы, за которой будем следить. Это наш ключевой метод, единственный публичный, в него будем закидывать строки таблицы при их создании
    let $this = this
    // добавляем строку себе в память
    $this.rowsToTrack = [rowElem, ...$this.rowsToTrack]
    // вешаем событие на чекбокс, который в этой строке,
    rowElem.querySelector($this.nameOf.checkBox).addEventListener('change', $this.handleCheckboxClick.bind($this))
  }
  runCalculations(){    // запуск всех вычислений последовательно

    let $this = this
    $this.calcTotalCost()
    $this.calcDiscount()
    $this.calcRemainder()

  }

  calcTotalCost(){
    let $this = this

    let checkedRows = [...$this.DOM.tableElem.querySelectorAll($this.nameOf.tableRow)].filter(row => $this.checkIfRowHasSelectedCheckbox(row))

    let totalCost = 0
    for(let checkedRow of checkedRows){
      totalCost += Math.round(Number(checkedRow.querySelector($this.nameOf.tableCellPrice).innerText))
    }

    $this.DOM.totalSumInput.value = totalCost

  }
  calcDiscount(){
    let $this = this
    let total = 0
    if(Math.round(Number($this.DOM.totalSumInput.value)) >= 0){
      total = Math.round(Number($this.DOM.totalSumInput.value))
      if($this.DOM.projectDiscountInput.value && $this.DOM.projectDiscountInput.value > 0){
        total = total - ((total / 100) * Math.round(Number($this.DOM.projectDiscountInput.value)))
      }
    }
    if(total > 0){
      $this.DOM.summWithDiscountInput.value = Math.ceil(total)
    } else {
      $this.DOM.summWithDiscountInput.value = total
    }
  }
  calcRemainder(){
    let $this =  this
    if($this.DOM.summWithDiscountInput.value && $this.DOM.summWithDiscountInput.value > 0){
      if($this.DOM.alreadyPaidInput.value && $this.DOM.alreadyPaidInput.value > 0){
        $this.DOM.yetUnpaidInput.value = Math.ceil(Number($this.DOM.summWithDiscountInput.value) - Number($this.DOM.alreadyPaidInput.value))
      } else {
        $this.DOM.yetUnpaidInput.value = $this.DOM.summWithDiscountInput.value
      }
    } else {
      $this.DOM.yetUnpaidInput.value = ''
    }
  }



  checkIfRowHasSelectedCheckbox(row){
    let $this = this
    if(row.querySelector($this.nameOf.checkBox).checked){
      return true
    }
  }


  // события
  handleCheckboxClick(){ //этот метод будет запускаться каждый раз, когда мы кликнули по чекбоксу
    let $this = this
    $this.runCalculations()
  }
  handleDiscountChange(){
    let $this = this
    $this.runCalculations()
  }
  handleRecievedChange(){
    let $this = this
    $this.runCalculations()
  }



  addEventListeners(){
    let $this = this
    $this.DOM.projectDiscountInput.addEventListener('keyup', $this.handleDiscountChange.bind($this))
    $this.DOM.alreadyPaidInput.addEventListener('keyup', $this.handleRecievedChange.bind($this))
  }
  // инициализация
  addRows(){
    let $this = this
    for(let tableRow of $this.DOM.tableElem.querySelectorAll($this.nameOf.tableRow)){
      $this.addRow(tableRow)
    }
  }
}


let totalCounter = new TotalCounter({
  DOM: {
    totalSumInput:          document.querySelector('#sum'),                 // итоговая сумма
    selfCostInput:          document.querySelector('#priceTools'),          // себестоимость оборудования
    projectDiscountInput:   document.querySelector('#discountByProject'),   // скидка на проект
    summWithDiscountInput:  document.querySelector('#sumWithDiscount'),     // сумма со скидкой
    resultUSNInput:         document.querySelector('#finalSumUsn'),         // итоговая УСН
    serviceCostInput:       document.querySelector('#priceWork'),           // стоимость работ
    alreadyPaidInput:       document.querySelector('#received'),            // оплачено
    yetUnpaidInput:         document.querySelector('#remainder'),           // расчет неоплаченного остатка
    // По инпутам у нас всё, на всякий случай передадим еще элемент таблицы товаров
    tableElem:              document.querySelector('.in4dr-form')
  },
  nameOf: {
    tableRow:       '.row-tool',       // для поиска табличных строк
    checkBox:       '.column-table_checkbox',       // для поиска чекбокса
    tableCellPrice: '.price-tool', // для поиска табличных строк
  }
})

