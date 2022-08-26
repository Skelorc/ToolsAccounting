package wns.constants;

public enum TypeLease {

    STRAIGHT("Прямая"),
    SUBLEASE("Субаренда");

    private final String type;

    TypeLease(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
