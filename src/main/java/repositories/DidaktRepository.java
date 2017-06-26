package repositories;

import models.reports.ReportData;
import models.reports.ReportHeader;
import models.ui_util.Department;
import models.ui_util.Teacher;
import models.ui_util.WayOfTeachingProfession;
import util.DatabaseConnector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by walde on 25.06.2017.
 */
public class DidaktRepository implements IDidaktRepository {
    Connection con = null;

    public DidaktRepository(Connection con)
    {
        this.con = con;
        DatabaseConnector databaseConnector = new DatabaseConnector();
    }

    public DidaktRepository(DataSource dataSource) {
        try {
            this.con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getProfessions() {
        String sql = "SELECT Berufname FROM tbl_beruf";
        List<String> professionList = new ArrayList<>();

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                professionList.add(rs.getString("Berufname"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return professionList;
    }

    public ReportData getDataSet(String profName){
        String sql = "SELECT * FROM tbl_fach\n" +
                "INNER JOIN tbl_beruffach\n" +
                "ON tbl_fach.FID = tbl_beruffach.ID_Fach\n" +
                "INNER JOIN tbl_uformberuf \n" +
                "ON tbl_beruffach.ID_UFormBeruf = tbl_uformberuf.UBID\n" +
                "INNER JOIN tbl_uform\n" +
                "ON tbl_uformberuf.ID_UForm = tbl_uform.UID\n" +
                "INNER JOIN tbl_beruf\n" +
                "ON tbl_uformberuf.ID_Beruf = tbl_beruf.BId\n" +
                "INNER JOIN tbl_abteilung\n" +
                "ON tbl_abteilung.Aid = tbl_beruf.ID_Abteilung\n" +
                "INNER JOIN tbl_lehrer\n" +
                "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId\n" +
                "INNER JOIN tbl_lernfeld \n" +
                "ON tbl_beruffach.BFID = tbl_lernfeld.ID_Beruffach\n" +
                "INNER JOIN tbl_lernsituation \n" +
                "ON tbl_lernfeld.LFID = tbl_lernsituation.ID_Lernfeld\n" +
                "WHERE tbl_beruf.Berufname = ?";
            ReportData dataSet = null;

            try{
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, profName);
                ResultSet rs = ps.executeQuery();

                String teacher = null;
                if (rs.getString("Geschlecht") == "W"){
                    teacher = ("Frau" + rs.getString("Lehrername"));
                }
                else{
                    teacher = ("Herr" + rs.getString("Lehrername"));
                }
                dataSet = new ReportData();
                ReportHeader rh = new ReportHeader(rs.getString
                                            ("Abteilungsname"),
                                           rs.getString("Berufname"),
                                           rs.getInt("Jahr"),
                                           rs.getString("UFormname"),
                                           teacher);

                while(rs.next()){
                

                }


            }
            catch(Exception e){

            }

        return null;
    }

    public Department getDepartment(Integer bid)
    {
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
        String sql = "SELECT";

        return null;
    }
}
