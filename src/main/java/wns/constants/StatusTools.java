package wns.constants;

public enum StatusTools {

    ONLEASE("В аренде", "Red"),
    INSTOCK("На складе", "Green"),
    BOOKING("Бронь","Orange"),
    REPAIR("В ремонте", "Gray"),
    WRITENOFF("Списано", "Brown"),
    SALES("Продано", "Blue");

    private final String color;
    private final String value;

    StatusTools(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor()
    {
        return this.color;
    }

    public String getValue()
    {
        return this.value;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
