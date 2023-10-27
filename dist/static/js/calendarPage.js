console.log("Helll worm");

const csrfHeaderName = document.querySelector(
  'meta[name="_csrf_header"]'
).content;
const csrfHeaderValue = document.querySelector('meta[name="_csrf"]').content;

async function postData(url = "", data = {}) {
  // Default options are marked with *
  const response = await fetch(url, {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    mode: "cors", // no-cors, *cors, same-origin
    cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json",
      [csrfHeaderName]: csrfHeaderValue,
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: "follow", // manual, *follow, error
    referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data), // body data type must match "Content-Type" header
  });
  return response.json(); // parses JSON response into native JavaScript objects
}

class CalendarPage {
  constructor() {
    this.init();
    this.setCurrentState({
      date: this.getToday(),
      zoom: false,
      type: false,
    });
    this.buildTable();
    this.addEventListeners();
  }

  // ? init
  init() {
    this.current = {
      date: undefined,
      type: undefined,
      zoom: undefined,
      finType: "TOOLS_BY_WEEK",
    };
    this.DOM = {
      findBtn: document.querySelector(".calendar__findBtn"),
      typeInp: document.querySelector(".calendar__typeInp"),
      scaleInp: document.querySelector(".calendar__scaleInp"),
      table: document.querySelector(".calendar__table"),
      thead: document.querySelector(".calendar__thead"),
      tbody: document.querySelector(".calendar__tbody"),
      datepicker: document.querySelector(".calendar__monthMapPlaceholder"),
      filters: {
        names: document.querySelector(".calendar__name-filter-inp"),
        clients: document.querySelector(".calendar__clients-filter-inp"),
        workers: document.querySelector(".calendar__workers-filter-inp"),
        managers: document.querySelector(".calendar__manager-filter-inp"),
        types: document.querySelector(".calendar__type-filter-inp"),
      },
    };
    this.datepicker = new Datepicker(this.DOM.datepicker);
    this.datepicker.setDate(new Date());
  }
  setCurrentState(
    settings = { date: false, type: false, zoom: false, filters: false }
  ) {
    settings.type
      ? (this.current.type = settings.type)
      : (this.current.type = this.DOM.typeInp.value);
    settings.zoom
      ? (this.current.zoom = settings.zoom)
      : (this.current.zoom = this.DOM.scaleInp.value);
    settings.date
      ? (this.current.date = settings.date)
      : (this.current.date = this.datepicker.getDate());
    settings.filters
      ? (this.current.filters = settings.filters)
      : (this.current.filters = {
          names: this.DOM.filters.names.value,
          clients: this.DOM.filters.clients.value,
          workers: this.DOM.filters.workers.value,
          managers: this.DOM.filters.managers.value,
          types: this.DOM.filters.types.value,
        }),
      this.setFinType();
  }
  setFinType() {
    this.current.finType = `${this.DOM.typeInp.value}_BY_${this.DOM.scaleInp.value}`;
  }
  addEventListeners() {
    this.DOM.findBtn.addEventListener("click", () => {
      this.buildTable();
    });

    this.DOM.scaleInp.addEventListener("change", () => {
      this.setCurrentState();
    });
    this.DOM.typeInp.addEventListener("change", () => {
      this.setCurrentState();
    });
    this.DOM.datepicker.addEventListener("changeDate", (e) => {
      this.setCurrentState();
    });
    Object.entries(this.DOM.filters).map(([filterName, filterElement]) => {
      filterElement.addEventListener("change", (e) => {
        this.applyFiltersToCurrentState();
      });
    });
  }

  // ? request
  async calendarPostRequest() {
    let dateStart = this.current.date;
    if (this.current.zoom == "WEEK") {
      let dateStartObj = this.getStartingDateFromCurrentDate(-1);
      dateStart = this.getDayObjFromDateObj(dateStartObj);
    }
    let requestData = {
      filter: this.current.finType,
      dateStart: dateStart.fullDate,
      filters: this.current.filters,
    };

    let response = await postData("/calendar", requestData);
    console.log(response);
    return response;
  }
  async formFiltersRequest() {
    let requestData = { filter: `ALL_${this.current.type}` };
    let response = await postData("/calendar/filters", requestData);
    this.buildFilters(response);
    this.applyCurrentStateToFilters();
  }

  // ? dom manipulations
  async buildTable() {
    let itemsArray = await this.calendarPostRequest();
    await this.formFiltersRequest();
    this.buildTableHead();
    this.buildRowsInTable(itemsArray);
  }
  buildTableHead() {
    let newHeadRow = document.createElement("tr");
    newHeadRow.classList.add("calendar__days");
    if (this.current.zoom == "WEEK") {
      let startingDate = this.getStartingDateFromCurrentDate(-1);
      let days = this.getXdaysFromDateStart(startingDate, 7);
      newHeadRow.innerHTML = `                  
        <td class="calendar__headNameCell">
          Название
        </td>`;
      days.map((day) => {
        let newDay = document.createElement("td");
        newDay.classList.add("calendar__day");
        newDay.colSpan = 8;
        newDay.innerHTML = `
        <div class="calendar__dayName">${this.numberToDay(day.day)}</div>
        <div class="calendar__dayDate">${day.date}.${day.month}</div>
        `;
        newHeadRow.appendChild(newDay);
      });
    } else {
      let dayDate = new Date(this.current.date);
      let dayNumber = dayDate.getDay();
      newHeadRow.innerHTML = `
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber - 2)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber - 1)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber + 1)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber + 2)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber + 3)}</div>
      </td>
      <td class="calendar__day" colspan="8">
        <div class="calendar__dayName">${this.numberToDay(dayNumber + 4)}</div>
      </td>
      `;
    }
    this.DOM.thead.innerHTML = "";
    this.DOM.thead.appendChild(newHeadRow);
  }
  async buildRowsInTable(arr = []) {
    this.DOM.tbody.innerHTML = "";
    if (this.current.zoom == "WEEK") {
      this.buildRowsWeek(arr);
    } else {
      this.buildRowsMonth(arr);
    }
  }
  buildRowsWeek(arr) {
    let startingDate = this.getStartingDateFromCurrentDate(-1);
    let days = this.getXdaysFromDateStart(startingDate, 7);
    arr?.map((calendarItem) => {
      let newTr = document.createElement("tr");
      newTr.classList.add("calendar__item");
      newTr.innerHTML = `          
          <td class="calendar__itemName">
            ${
              this.current.type == "PROJECTS"
                ? `<a href="/projects/edit/${calendarItem.id}">${calendarItem.name}</a>`
                : `${calendarItem.name}`
            }
          </td>`;
      days.map((day) => {
        let dayHtml = `
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 00:00`,
              calendarItem.listStatusWithDate
            )} calendar__hours_day-start" data-time="${
          day.fullDate
        } 00:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 03:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 03:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 06:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 06:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 09:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 09:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 12:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 12:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 15:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 15:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 18:00`,
              calendarItem.listStatusWithDate
            )}" data-time="${day.fullDate} 18:00"></td>
            <td class="calendar__hours ${this.runThroughStatusWithDatesList(
              `${day.fullDate} 21:00`,
              calendarItem.listStatusWithDate
            )} calendar__hours_day-end" data-time="${
          day.fullDate
        } 21:00"></td>            
          `;
        newTr.innerHTML += dayHtml;
      });
      if (newTr.querySelectorAll(".calendar__hours_active").length > 0) {
        this.DOM.tbody.appendChild(newTr);
      }
    });
  }
  buildRowsMonth(arr) {
    let startingDate = this.getStartingDateFromCurrentDate(-2);
    let days = this.getXdaysFromDateStart(startingDate, 35);
    this.buildMonthWithoutInstances(days);
    this.addInstancesToMonth(arr);
  }
  buildMonthWithoutInstances(days) {
    let weeks = [
      [days[0], days[1], days[2], days[3], days[4], days[5], days[6]],
      [days[7], days[8], days[9], days[10], days[11], days[12], days[13]],
      [days[14], days[15], days[16], days[17], days[18], days[19], days[20]],
      [days[21], days[22], days[23], days[24], days[25], days[26], days[27]],
      [days[28], days[29], days[30], days[31], days[32], days[33], days[34]],
    ];
    weeks.map((week) => {
      this.DOM.tbody.innerHTML += `
      <tr class="calendar__monthly-week">
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[0].date}.${week[0].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[0].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[1].date}.${week[1].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[1].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[2].date}.${week[2].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[2].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[3].date}.${week[3].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[3].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[4].date}.${week[4].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[4].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[5].date}.${week[5].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[5].fullDate}T00:00:00">
          
        </ul>
      </td>
      <td class="calendar__monthly-day" colspan="8">
        <div class="calendar__monthly-date">
          ${week[6].date}.${week[6].month}
        </div>
        <ul class="calendar__monthly-day-entities" data-date="${week[6].fullDate}T00:00:00">
          
        </ul>
      </td>
      
    </tr>
      `;
    });
  }
  addInstancesToMonth(arr) {
    [...document.querySelectorAll(".calendar__monthly-day-entities")].map(
      (ul) => {
        let thisUlDate = new Date(ul.dataset.date);
        arr.map((instance) => {
          instance.listStatusWithDate.map((statusWithDates) => {
            let thisStatusWithDatesStart = new Date(statusWithDates.start);
            let thisStatusWithDatesEnd = new Date(statusWithDates.end);
            if (
              thisUlDate.valueOf() >= thisStatusWithDatesStart.valueOf() &&
              thisUlDate.valueOf() <= thisStatusWithDatesEnd.valueOf()
            ) {
              ul.innerHTML += `
                <li class="calendar__monthly-day-entity">
                ${
                  this.current.type == "PROJECTS"
                    ? `<a href="/projects/edit/${instance.id} " class="calendar__monthly-entity-link calendar__monthly-entity-link_${statusWithDates.status}">
                    ${instance.name}
                    </a>`
                    : `<span class="calendar__monthly-entity-link calendar__monthly-entity-link_${statusWithDates.status}">
                    ${instance.name}
                    </span>`
                }

                </li>
              `;
            }
          });
        });
      }
    );
  }
  buildFilters(filtersObj) {
    Object.values(this.DOM.filters).map((filterSelect) => {
      filterSelect.innerHTML = `<option value="" selected>Все</option>`;
    });
    Object.entries(filtersObj).map(([filterName, valuesArr]) => {
      valuesArr.map((optionValue) => {
        this.DOM.filters[
          filterName
        ].innerHTML += `<option value="${optionValue}">${optionValue}</option>`;
      });
    });
  }
  applyFiltersToCurrentState() {
    Object.entries(this.DOM.filters).map(([filterName, filterElement]) => {
      this.current.filters[filterName] = filterElement.value;
    });
  }
  applyCurrentStateToFilters() {
    Object.entries(this.DOM.filters).map(([filterName, filterElement]) => {
      // filterElement.querySelector(
      //   `option[value="${this.current.filters[filterName]}"]`
      // );
      filterElement.value = this.current.filters[filterName];
    });
  }

  // ? dom manipulations - secondary

  runThroughStatusWithDatesList(tdDateTime, statusWithDatesList) {
    let resultString = "";
    for (let i = 0; i < statusWithDatesList.length; i++) {
      resultString = this.figureOutTdStatus(
        tdDateTime,
        statusWithDatesList[i].start,
        statusWithDatesList[i].end,
        statusWithDatesList[i].status
      );
      if (resultString != "") {
        break;
      }
    }
    return resultString;
  }
  figureOutTdStatus(tdDateTime, objStartDateTime, objEndDateTime, status) {
    let tdDateInstVal = new Date(tdDateTime).valueOf();
    let startDateInstVal = new Date(objStartDateTime).valueOf();
    let endDateInstVal = new Date(objEndDateTime).valueOf();
    if (tdDateInstVal <= endDateInstVal && tdDateInstVal >= startDateInstVal) {
      return `calendar__hours_active calendar__hours_${status}`;
    } else {
      return "";
    }
  }

  // ? calendar methods
  numberToDay(num = 0) {
    while (num < 0) {
      num += 7;
    }
    while (num > 6) {
      num -= 7;
    }
    let daysArr = ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"];
    return daysArr[num];
  }
  getToday() {
    let date = new Date();
    let dateDate = date.getDate();
    let dateMonth = date.getMonth() + 1;
    dateMonth.toString.length < 2
      ? (dateMonth = `0${dateMonth}`)
      : (dateMonth = `${dateMonth}`);
    let dateYear = date.getFullYear();
    let dateDay = date.getDay();
    let resultString = `${dateYear}-${dateMonth}-${dateDate}`;
    return resultString;
  }
  getStartingDateFromCurrentDate(n) {
    let currentDate = new Date(this.current.date);
    let startingDate = new Date(currentDate.valueOf() + n * (1000 * 3600 * 24));
    return startingDate;
  }
  getXdaysFromDateStart(dateStart, x) {
    let initialDate = new Date(dateStart);
    let days = [];
    for (let i = 0; i < x; i++) {
      let newDate = new Date(initialDate.valueOf() + i * (1000 * 3600 * 24));
      let newDayObj = this.getDayObjFromDateObj(newDate);
      days.push(newDayObj);
    }
    return days;
  }
  getDayObjFromDateObj(dateObj) {
    let dayObj = {
      date: dateObj.getDate(),
      day: dateObj.getDay(),
      month: dateObj.getMonth() + 1,
      year: dateObj.getFullYear(),
      dateInst: dateObj,
    };
    let dayMonthStr = String(dayObj.month);
    let dayDateStr = String(dayObj.date);
    if (dayMonthStr.length < 2) {
      dayMonthStr = "0" + dayMonthStr;
    }
    if (dayDateStr.length < 2) {
      dayDateStr = "0" + dayDateStr;
    }
    dayObj.fullDate = `${dayObj.year}-${dayMonthStr}-${dayDateStr}`;
    return dayObj;
  }
}

window.addEventListener("DOMContentLoaded", async function () {
  let calendarPage = new CalendarPage();
});
