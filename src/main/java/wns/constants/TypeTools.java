package wns.constants;

public enum TypeTools {

    STOCK("Склад"),
    SUBLEASE_TOOLS("Субаренда");

    private final String type;

    TypeTools(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return this.name();
    }

    public String getType()
    {
        return this.type;
    }


}
