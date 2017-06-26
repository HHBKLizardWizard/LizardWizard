package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;
import models.UserRights;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by patrick on 22.06.2017.
 */

public class UserRepository implements IUserRepository{

    Connection con = null;
    public ObservableList<User> userList = FXCollections.observableArrayList();

    public UserRepository(DataSource dataSource) {
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User registerUser(User user){

        String sql = "INSERT INTO users(username, firstname, lastname, password, rights) " +
                "VALUES (?,?,?,?,?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());

            //TODO encryption fixen
            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRights().toString());

            ps.execute();

            ResultSet rs = con.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();

            while(rs.next()) {
                user.setId(rs.getInt(1));
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return user;
    }

    public boolean deleteUserById(Integer userId){
        String sql = "DELETE FROM users WHERE PK_ID = ?";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.toString());

            ResultSet rs = ps.executeQuery();

        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public User getUserById(Integer userId){
        String sql = "SELECT * FROM users WHERE PK_ID = ?";
        User newUser = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.toString());

            ResultSet rs = ps.executeQuery();

            newUser = new User(
                    rs.getString("username"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("password"),
                    UserRights.valueOf(rs.getString("rights"))
            );
        }catch (Exception e){
            System.out.println(e);
        }

        return newUser;
    }

    public User getUserByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                user = new User(
                        rs.getInt("pk_id"),
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("password"),
                        UserRights.valueOf(rs.getString("rights").toUpperCase())
                );
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }



    public String getPasswordByUserId(Integer userId){
        String password = "";
        String sql = "SELECT password FROM users WHERE PK_ID = ?";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.toString());
            ResultSet rs = ps.executeQuery();

            password = rs.getString("password");

        }catch (Exception e){
            System.out.println(e);
        }
        return password;
    }

    public boolean updateUser (User user){
        String sql = "UPDATE users SET firstname = ?, " +
                                        "lastname = ?, " +
                                        "username = ?, " +
                                        "password = ?," +
                                        "rights    = ? " +
                                        "WHERE pk_id = ?";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getId().toString());
            ps.setString(6, user.getRights().toString());

            ResultSet rs = ps.executeQuery();

            while(rs.next() ){
                System.out.println(rs.getString("name"));
            }

        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public ObservableList<User> getAllUsers()
    {
        String sql = "SELECT * FROM users";

        try{
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            User user = new User(
                    rs.getString("username"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("password"),
                    UserRights.valueOf(rs.getString("rights"))
            );
            userList.add(user);

        }catch (Exception e){
            System.out.println(e);
        }

        return userList;
    }
}
