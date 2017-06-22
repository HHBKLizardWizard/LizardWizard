package repositories;

import models.User;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by iho on 22.06.2017.
 */
public class UserRepository implements IUserRepository
{
    Connection con = new DatabaseConnector().getConnection();

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

    public User registerUser(String firstname, String lastname,
                             String username, String password, long rights)
    {

        String sql = "INSERT INTO users(firstname, lastname, username, password, rights) " +
                "VALUES (?,?,?,?,?)";

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

    public User getUserbyUsername(String username)
    {
        String sql = "SELECT * FROM users WHERE Username = ?";

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "username");

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

    public User updateUserbyUsername (String firstname, String lastname,
                                      String username, String password, long rights)
    {
        String sql = "UPDATE users SET Vorname = ?, " +
                "Nachname = ?, " +
                "Username = ?, " +
                "Password = ?," +
                "Roll     = ?";

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
