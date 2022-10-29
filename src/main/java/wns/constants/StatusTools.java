package wns.constants;

public enum StatusTools {

    ONLEASE("В аренде", "Red"),
    INSTOCK("На складe", "Green"),
    WAITING("В ожидании","Yellow"),
    BOOKING("Бронь","Orange"),
    REPAIR("В ремонте", "Gray"),
    WRITEOFF("Списано", "Brown"),
    SALE("Продано", "Blue");

    private final String value;
    private final String color;

    StatusTools(String value, String color) {
        this.value = value;
        this.color = color;
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

    public String getData()
    {
        return this.color + " " + this.value;
    }
}
