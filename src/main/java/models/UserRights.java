package models;

/**
 * Created by iho on 23.06.2017.
 */
public enum UserRights {
    AZUBI("azubi"), LEHRER("lehrer"), ADMIN("admin");

    private String value;

    UserRights(String value) {
        this.value = value;
    }


}
