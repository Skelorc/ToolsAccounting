package wns.constants;

public enum TypeShift {
    DAY("День"),
    NIGHT("Ночь");

    private final String type;

    TypeShift(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
