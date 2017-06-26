package repositories;

import models.ui_util.Department;
import models.ui_util.Teacher;
import models.ui_util.WayOfTeachingProfession;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by walde on 25.06.2017.
 */
public class DidaktRepository implements IDidaktRepository {
    Connection con = null;
    DatabaseConnector databaseConnector = new DatabaseConnector();
    @Override
    public List<String> getProfessions() {
        con = databaseConnector.getConnection();
        String sql = "SELECT Berufname from tbl_beruf";
        List<String> professionList = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Integer chiefID = null;
            Integer depID = null;
            do{
                professionList.add(rs.getString("Berufname"));
            }while(rs.next());
        }catch (Exception e){
            System.out.println(e);
        }
        return professionList;
    }

    public Department getDepartment(Integer bid)
    {
        con = databaseConnector.getConnection();
        String sql = "SELECT * FROM tbl_abteilung WHERE Aid = ?";
        Department dep;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bid.toString());

            ResultSet rs = ps.executeQuery();
            dep = new Department(rs.getInt("AId"),
                             rs.getString("Abteilungsname"),
                             rs.getString("Abteilungskuerzel"),
                             rs.getString("Abteilungserlaeuterung"),
                             getTeacher(rs.getInt("ID_Leiter")),
                             getTeacher(rs.getInt("ID_Vertreiter")));
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public Teacher getTeacher(Integer id){
        con = databaseConnector.getConnection();
        String sql = "SELECT * FROM tbl_lehrer WHERE LId = ?";
        Teacher teacher = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();
            teacher = new Teacher(rs.getInt("LId"),
                                  rs.getString("Lehrername"),
                                  rs.getString("Geschlecht"));
        }
        catch (Exception e){
            System.out.println();
        }
        return teacher;
    }

    public WayOfTeachingProfession getWayOfTeachingProfession(Integer id){
        con = databaseConnector.getConnection();
        String sql = "SELECT";
        return null;
    }
}
