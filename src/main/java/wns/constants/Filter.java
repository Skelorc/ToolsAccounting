package wns.constants;

public enum Filter {
    //tools
    WITHOUT_FILTER("null"),
    INDIVIDUAL("INDIVIDUAL"),
    LEGAL("LEGAL"),
    BLACKLIST("BLACKLIST"),
    STOCK("Склад"),
    SUBLEASE_TOOLS("Субаренда"),
    GROUPS("По группам"),
    INSTOCK("На складе"),
    WAITING("В ожидании"),
    ONLEASE("В аренде"),
    REPAIR("В ремонте"),
    ESTIMATE("По смете"),
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
