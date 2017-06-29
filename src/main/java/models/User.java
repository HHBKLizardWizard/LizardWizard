package models;

/**
 * Created by iho on 22.06.2017.
 */
public class User {
    private Integer id;
    private String username, firstname, lastname, password;
    private UserRights rights;

    public User(){}

    public User(String username, String firstname, String lastname, String password, UserRights rights){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.rights   = rights;
    }

    public User(Integer id, String username, String firstname, String lastname, String password, UserRights rights){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rights   = rights;
        this.password = password;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public UserRights getRights() {
        return rights;
    }

    public void setRights(UserRights rights) {
        this.rights = rights;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
