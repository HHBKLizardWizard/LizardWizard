package models;

import java.util.List;

/**
 * Created by iho on 22.06.2017.
 */
public class User {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private List<Template> templateList;
    private UserRights rights;

    public User(){}

    public User(String username, String firstname, String lastname, UserRights rights, String password){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rights   = rights;
        this.password = password;
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

    public List<Template> getTemplateList() {
        return this.templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }
}
