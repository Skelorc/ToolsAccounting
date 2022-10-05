package wns.constants;

import org.springframework.http.HttpStatus;

public enum Messages {

    CLIENT_CREATE("Клиент сохранён!",HttpStatus.CREATED),
    CLIENT_EXISTS("Клиент с таким ФИО уже существует!", HttpStatus.OK),
    CLIENT_UPDATE("Клиент обновлён!",HttpStatus.ACCEPTED),
    CLIENT_NOT_FOUND("Клиент не найден!",HttpStatus.OK),
    USER_CREATE("Новый пользователь успешно создан!",HttpStatus.CREATED),
    USER_UPDATE("Пользователь обновлён!",HttpStatus.ACCEPTED),
    USER_NOT_FOUND("Пользователь не найден!",HttpStatus.OK),
    USER_EXISTS("Пользователь уже существует!", HttpStatus.OK),
    TOOLS_CREATE("Оборудование создано!",HttpStatus.CREATED),
    TOOLS_EXISTS("Оборудование с таким названием уже существует!", HttpStatus.OK),
    NAME_ESTIMATE_CREATE("Наименование в смете успешно создано!",HttpStatus.CREATED),
    NAME_ESTIMATE_EXISTS("Наименование в смете с таким названием уже существует!", HttpStatus.OK),
    STATUS_CREATE("Статус успешно создан!",HttpStatus.CREATED),
    STATUS_CHANGE_FAILED("Ошибка при изменении статуса оборудования",HttpStatus.OK),
    DELETE("Удалено!", HttpStatus.ACCEPTED),
    NOT_FOUND("Ошибка удаления! Такого объекта не существует!", HttpStatus.OK),
    CREATE_NEW_PROJECT("Создание нового проекта!", HttpStatus.CREATED),
    PROJECT_EXISTS("Проект с таким именем уже существует!", HttpStatus.OK);


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
