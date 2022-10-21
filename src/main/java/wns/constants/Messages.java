package wns.constants;

public enum Messages {

    OK("Create!","200"),
    RETURN_FILE_URL("File!","300"),
    REPLACE("Create! Redirect!","300"),
    CLIENT_CREATE("Клиент сохранён!","200"),
    CLIENT_EXISTS("Клиент с таким ФИО уже существует!", "400"),
    CLIENT_UPDATE("Клиент обновлён!","200"),
    CLIENT_NOT_FOUND("Клиент не найден!","400"),
    USER_CREATE("Новый пользователь успешно создан!","200"),
    USER_UPDATE("Пользователь обновлён!","200"),
    USER_NOT_FOUND("Пользователь не найден!","400"),
    USER_EXISTS("Пользователь уже существует!", "400"),
    TOOLS_CREATE("Оборудование создано!","200"),
    TOOLS_EXISTS("Оборудование с таким названием уже существует!", "400"),
    NAME_ESTIMATE_CREATE("Наименование в смете успешно создано!","200"),
    NAME_ESTIMATE_EXISTS("Наименование в смете с таким названием уже существует!", "400"),
    STATUS_CREATE("Статус успешно создан!","200"),
    STATUS_CHANGE_FAILED("Ошибка при изменении статуса оборудования","400"),
    DELETE("Удалено!","200"),
    NOT_FOUND("Ошибка удаления! Такого объекта не существует!", "400"),
    CREATE_NEW_PROJECT("Создание нового проекта!", "200"),
    PROJECT_EXISTS("Проект с таким именем уже существует!", "400"),
    PROJECT_UPDATE("Проект обновлён!", "300"),
    PROJECT_ERROR("Ошибка обновления проекта", "400");


    private final String value;
    private final String code;

    Messages(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode()
    {
        return code;
    }
}
