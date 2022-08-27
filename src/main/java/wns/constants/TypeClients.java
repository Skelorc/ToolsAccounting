package wns.constants;

public enum TypeClients {
    INDIVIDUAL ("Физ.Лицо"),
    LEGAL("Юр.лицо");

    private final String type;

    TypeClients(String type) {
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
