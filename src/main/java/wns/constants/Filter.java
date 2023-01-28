package wns.constants;

public enum Filter {
    //tools
    ALL_TOOLS("Всё оборудование"),
    STOCK("Склад"),
    GROUPS("По группам"),
    //status
    INSTOCK("На складе"),
    WAITING("В ожидании"),
    ONLEASE("В аренде"),
    REPAIR("В ремонте"),
    SALE("Продано"),
    WRITEOFF("Списано"),
    //estimate
    ESTIMATE("По смете"),
    //clients
    INDIVIDUAL("INDIVIDUAL"),
    LEGAL("LEGAL"),
    BLACKLIST("BLACKLIST"),
    ALL_CLIENTS("Все клиенты"),
    PROJECTS_BY_CLIENTS("Все проекты клиента"),
    //project
    ALL_PROJECTS("Все проекты"),
    GET_TOOLS_BY_PROJECT("Получить оборудование по проекту"),
    ONE_TIME("Разовый"),
    LONG("Длинный"),
    TEST("Тестовый"),
    SUBLEASE_PROJECT("Субаренда"),
    //ассоциативный массив
    ESTIMATE_NAME("Ассоциативный массив"),
    //Категория
    CATEGORY("Категория"),
    //Контакты
    ALL_CONTACTS("Все контакты"),
    CONTACTS_BY_ROLE("Контакты по роли");

    private final String value;

    Filter(String value) {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Filter getFilterByString(String filter)
    {
        return Filter.valueOf(filter.toUpperCase());
    }
}
