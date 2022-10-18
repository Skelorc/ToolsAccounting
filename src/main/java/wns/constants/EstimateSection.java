package wns.constants;

public enum EstimateSection {

    CAMERA(1, "Камера"),
    ACCESORIES(2, "Аксессуары"),
    CAMERA_EQUIPMENT(3,"Операторская техника"),
    PLAYBACK(4,"Playback"),
    LENSES(5, "Оптика"),
    FILTERS(6, "Фильтры"),
    OPTIONAL_EQUIPMENT(7, "Дополнительное оборудование"),
    SERVICE_AND_TRANSPORT(8, "Сервис и транспорт"),
    SERVICE(9, "Обслуживание");

    private final int number;
    private final String value;

    EstimateSection(int number, String value) {
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getValue()
    {
        return this.value;
    }

    public Integer getNumber()
    {
        return this.number;
    }
}
