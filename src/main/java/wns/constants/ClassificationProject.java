package wns.constants;

public enum ClassificationProject {
    ONE_TIME("Разовый"),
    LONG("Длинный"),
    SUBLEASE("Субаренда"),
    TEST("Тестовый");

    private final String value;

    ClassificationProject(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
