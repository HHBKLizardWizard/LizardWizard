package repositories;

import models.User;
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

public class UserRepository implements IUserRepository
{
    Connection con = null;

    public UserRepository() {
        con = new DatabaseConnector().getConnection();
    }

    public User getUserById(long id)
    {
        String sql = "SELECT * FROM users WHERE PK_ID = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "id");

            ResultSet rs = ps.executeQuery();

            while(rs.next() )
            {
                System.out.println(rs.getString("name"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public User registerUser(User user)
    {

        String sql = "INSERT INTO users(Vorname, Nachname, Username, Password, Rights) " +
                "VALUES (?,?,?,?,?)";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());

            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            ps.setString(4, hashed);
            ps.setString(5, user.getRights().toString());

            ps.execute();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    public User deleteUserbyUsername(String username)
    {
        String sql = "DELETE FROM users WHERE Username = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Username");

            ResultSet rs = ps.executeQuery();

            while(rs.next() )
            {
                System.out.println(rs.getString("name"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    public boolean getUserbyUsername(String username)
    {
        String sql = "SELECT Username FROM users WHERE Username = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while(rs.next() )
            {
                System.out.println(rs.getString("Username"));
            }

            if (username.equals(rs))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return true;
    }

    public boolean getPassword(String password)
    {
        String sql = "SELECT Password FROM users WHERE Username = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next() )
            {
                System.out.println(rs.getString("Password"));
            }

            if (BCrypt.checkpw(password, String.valueOf(rs)))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return true;
    }

    public User updateUserbyUsername (String firstname, String lastname,
                                      String username, String password, String rights)
    {
        String sql = "UPDATE users SET Vorname = ?, " +
                                        "Nachname = ?, " +
                                        "Username = ?, " +
                                        "Password = ?," +
                                        "Rights     = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "firstname");
            ps.setString(2, "lastname");
            ps.setString(3, "username");
            ps.setString(4, "password");
            ps.setString(5, "rights");

            ResultSet rs = ps.executeQuery();

            while(rs.next() )
            {
                System.out.println(rs.getString("name"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }
}
