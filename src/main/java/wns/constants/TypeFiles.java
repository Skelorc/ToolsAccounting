package wns.constants;

/*
 *@author Skelorc
 */

public enum TypeFiles {

    XLSX("Эксель"),
    PDF("Пдф");

    private final String type;

    TypeFiles(String type) {
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
