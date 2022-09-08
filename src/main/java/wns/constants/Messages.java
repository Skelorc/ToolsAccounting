package wns.constants;

import org.springframework.http.HttpStatus;

public enum Messages {

    CLIENT_CREATE("Клиент сохранён!",HttpStatus.CREATED),
    CLIENT_EXISTS("Клиент с таким ФИО уже существует!", HttpStatus.CONFLICT),
    CLIENT_UPDATE("Клиент обновлён!",HttpStatus.ACCEPTED),
    CLIENT_NOT_FOUND("Клиент не найден!",HttpStatus.CONFLICT),
    USER_CREATE("Новый пользователь успешно создан!",HttpStatus.CREATED),
    USER_UPDATE("Пользователь обновлён!",HttpStatus.ACCEPTED),
    USER_NOT_FOUND("Пользователь не найден!",HttpStatus.NOT_FOUND),
    USER_EXISTS("Пользователь уже существует!", HttpStatus.CONFLICT),
    TOOLS_CREATE("Оборудование создано!",HttpStatus.CREATED),
    TOOLS_EXISTS("Оборудование с таким названием уже существует!", HttpStatus.CONFLICT);



    private final String value;
    private final HttpStatus status;

    Messages(String value, HttpStatus status) {
        this.value = value;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public HttpStatus getStatus()
    {
        return status;
    }
}
