package wns.constants;

public enum DateCalendar {
    HOUR("Часы"),
    DAY("Дни"),
    MONTH("Недели");

    private final String value;

    DateCalendar(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getValue() {
        return value;
    }
}
