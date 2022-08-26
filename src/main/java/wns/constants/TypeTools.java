package wns.constants;

public enum TypeTools {

    STOCK("Склад"),
    SUBLEASE("Субаренда");

    private final String type;

    TypeTools(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
