package wns.constants;

public enum TypeLease {

    STRAIGHT("Прямая"),
    SUBLEASE("Даем в субаренду");

    private final String type;

    TypeLease(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return this.name();
    }

    public String getType()
    {
        return type;
    }
}
