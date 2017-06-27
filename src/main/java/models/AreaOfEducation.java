package models;

/**
 * Created by Slade on 27.06.2017.
 */
public enum AreaOfEducation {
    BERUFSBEZOGEN("Berufsbezogener Lernbereich"),
    BERUFSÜBERGREIFEND("Berufsübergreifender Lernbereich"),
    DIFFERENZIERUNG("Differenzierungsbereich");

    private String value;

    AreaOfEducation(String string) {
        value = string;
    }

    public String getValue() {
        return this.value;
    }
}
