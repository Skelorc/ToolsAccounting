package wns.constants;

public enum Filter {

    WITHOUT_FILTER("null"),
    INDIVIDUAL("INDIVIDUAL"),
    LEGAL("LEGAL"),
    BLACKLIST("BLACKLIST"),
    STOCK("Склад"),
    SUBLEASE("Субаренда"),
    GROUPS("По группам"),
    ESTIMATE("По смете");


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
