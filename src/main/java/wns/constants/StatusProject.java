package wns.constants;

public enum StatusProject {

    CREATE("Orange","Создан"),
    IN_WORK("Red","В работе"),
    EXTENDED("Red","Продлён"),
    OVERDUE("Brown","Просрочен"),
    WAITING_PAY("Yellow","Ожидает оплаты"),
    CLOSED("Green","Закрыт");

    private final String color;
    private final String status;

    StatusProject(String color, String status) {
        this.color = color;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
