package wns.constants;

public enum Filter {
    //tools
    WITHOUT_FILTER("null"),
    STOCK("Склад"),
    SUBLEASE_TOOLS("Субаренда"),
    GROUPS("По группам"),
    //status
    INSTOCK("На складе"),
    WAITING("В ожидании"),
    ONLEASE("В аренде"),
    REPAIR("В ремонте"),
    SALE("Продано"),
    //estimate
    ESTIMATE("По смете"),
    //clients
    INDIVIDUAL("INDIVIDUAL"),
    LEGAL("LEGAL"),
    BLACKLIST("BLACKLIST"),
    //project
    ONE_TIME("Разовый"),
    LONG("Длинный"),
    TEST("Тестовый"),
    SUBLEASE_PROJECT("Субаренда");


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
        if(filter == null)
            return Filter.WITHOUT_FILTER;
        return Filter.valueOf(filter.toUpperCase());
    }
}
