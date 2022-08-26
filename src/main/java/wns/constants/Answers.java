package wns.constants;

public enum Answers {

    CLIENT_CREATE("Клиент сохранён!"),
    CLIENT_EXISTS("Клиент с таким ФИО уже существует!"),
    USER_CREATE("Новый пользователь успешно создан"),
    USER_EXISTS("Пользователь уже существует"),
    TOOLS_CREATE("Оборудование создано!"),
    TOOLS_EXISTS("Оборудование с таким названием уже существует!");



    private final String value;

    Answers(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
