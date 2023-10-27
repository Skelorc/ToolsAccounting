class SearchHandler {
  constructor() {
    this.dom = {
      searchInput: document.querySelector("#barcode_search_input"),
    };
    this.init();
  }
  handleSearchInputKeyUp() {
    let $this = this;
    let newValue = $this.dom.searchInput.value.toUpperCase();
    for (let tableRow of [...document.querySelectorAll(".row-tool")]) {
      let tableRowBarcode = tableRow
        .querySelector(".barcode-tool")
        .innerText.toUpperCase();
      tableRowBarcode.includes(newValue)
        ? tableRow.classList.remove("row_hidden")
        : tableRow.classList.add("row_hidden");
    }
  }
  init() {
    // adding event listeners
    this.dom.searchInput.addEventListener(
      "keyup",
      this.handleSearchInputKeyUp.bind(this)
    );
  }
}
let searchHandler = new SearchHandler();
