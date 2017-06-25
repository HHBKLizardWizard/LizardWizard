package repositories;

import models.User;
import models.UserRights;
import org.mindrot.jbcrypt.BCrypt;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by iho on 22.06.2017.
 */

/*********************************************************************************************\
* Code for hashing a password
*
*
            // -- Hash a password for the first time --
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

            // -- gensalt's log_rounds parameter determines the complexity the work factor is 2**log_rounds, and the default is 10 --
            hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

            // -- Check a hashed password with an unhashed password --
            if (BCrypt.checkpw(password, hashed))
                System.out.println("It matches");
            else
                System.out.println("It does not match");
*
**********************************************************************************************/

public class UserRepository implements IUserRepository{
    Connection con = null;

    public UserRepository() {
        con = new DatabaseConnector().getConnection();
    }

    public boolean registerUser(User user){

        String sql = "INSERT INTO users(Vorname, Nachname, Username, Password, Rights) " +
                "VALUES (?,?,?,?,?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());

            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            ps.setString(4, hashed);
            ps.setString(5, user.getRights().toString());

            ps.execute();

        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean deleteUserbyId(Integer userId){
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

    public User getUserbyId(Integer userId){
        String sql = "SELECT * FROM users WHERE PK_ID = ?";
        User newUser = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.toString());

            ResultSet rs = ps.executeQuery();

            newUser = new User(
                    rs.getString("Username"),
                    rs.getString("Vorname"),
                    rs.getString("Nachname"),
                    UserRights.valueOf(rs.getString("Rights")),
                    rs.getString("Password")
            );
        }catch (Exception e){
            System.out.println(e);
        }

        return newUser;
    }

    public User getUserbyUsername(String userName){
        String sql = "SELECT * FROM users WHERE Username = ?";
        User newUser = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            newUser = new User(
                    rs.getString("Username"),
                    rs.getString("Vorname"),
                    rs.getString("Nachname"),
                    UserRights.valueOf(rs.getString("Rights")),
                    rs.getString("Password")
            );
        }catch (Exception e){
            System.out.println(e);
        }

        return newUser;
    }



    public String getPasswordByUserId(Integer userId){
        String password = "";
        String sql = "SELECT Password FROM users WHERE PK_ID = ?";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.toString());
            ResultSet rs = ps.executeQuery();

            password = rs.getString("Password");

            //todo: unhash password for admin or is that a no go?

        }catch (Exception e){
            System.out.println(e);
        }
        return password;
    }

    public boolean updateUser (User user){
        String sql = "UPDATE users SET Vorname = ?, " +
                                        "Nachname = ?, " +
                                        "Username = ?, " +
                                        "Password = ?," +
                                        "Rights     = ?";

        //@TODO : Update where User id = ?

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRights().toString());

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
}
