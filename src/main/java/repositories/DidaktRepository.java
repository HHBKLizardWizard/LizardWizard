package repositories;

import models.reports.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ObservableList<String> getProfessions() {
        String sql = "SELECT Berufname FROM tbl_beruf";
        ObservableList<String> professionList = FXCollections.observableArrayList();
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

    @Override
    public List<Profession> getProfessionList() {
        String sql = "SELECT * FROM tbl_beruf";
        List<Profession> professionList = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                professionList.add(new Profession(rs.getInt("BId"), rs.getString("Berufname")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return professionList;
    }

    @Override
    public ReportData getReportData(String profName, Integer year){
        String sql = "SELECT * FROM tbl_fach\n" +
                "LEFT JOIN tbl_beruffach\n" +
                "ON tbl_fach.FID = tbl_beruffach.ID_Fach\n" +
                "INNER JOIN tbl_uformberuf\n" +
                "ON tbl_beruffach.ID_UFormBeruf = tbl_uformberuf.UBID\n" +
                "INNER JOIN tbl_uform\n"+
                "ON tbl_uformberuf.ID_UForm = tbl_uform.UID\n" +
                "INNER JOIN tbl_beruf\n" +
                "ON tbl_uformberuf.ID_Beruf = tbl_beruf.BId\n"+
                "INNER JOIN tbl_abteilung\n" +
                "ON tbl_abteilung.Aid = tbl_beruf.ID_Abteilung\n" +
                "INNER JOIN tbl_lehrer\n" +
                "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId\n" +
                "WHERE tbl_beruf.Berufname = ?" +
                "AND tbl_fach.jahr = ?";
                ReportData dataSet = null;

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, profName);
            ps.setString(2, year.toString());
            ResultSet rs = ps.executeQuery();
            dataSet = new ReportData();

            while(rs.next()){
                Integer aoe = rs.getInt("Jahr");

            }
            ReportHeader rh = getReportHeader(rs);


        }
        catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    @Override
    public Integer getDuration(String profName)
    {
        String sql = "SELECT DISTINCT count(Jahr) FROM tbl_beruffach\n" +
                    "INNER JOIN tbl_uformberuf\n" +
                    "ON tbl_beruffach.ID_UFormBeruf = tbl_uformberuf.UBID\n" +
                    "INNER JOIN tbl_beruf ON tbl_uformberuf.ID_Beruf = tbl_beruf.BId\n" +
                    "WHERE tbl_beruf.Berufname = ?";
        Integer duration = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, profName);
            ResultSet rs = ps.executeQuery();
            duration = rs.getInt("Jahr");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return duration;
    }


    private ReportHeader getReportHeader(ResultSet rs){
        ReportHeader rh = null;
        try
        {
            String teacher = null;
            if (rs.getString("Geschlecht") == "W"){
                teacher = ("Frau" + rs.getString("Lehrername"));
            }
            else{
                teacher = ("Herr" + rs.getString("Lehrername"));
            }
            rh = new ReportHeader(rs.getString("Abteilungsname"),
                                               rs.getString("Berufname"),
                                               rs.getInt("Jahr"),
                                               rs.getString("UFormname"),
                                               teacher);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return  rh;
    }

   /* private Subject getSubject(ResultSet rs, AreaOfEducation area){
        Subject foo = null;
        try {
            foo = new Subject(rs.getInt("FId"),
                                      rs.getString("Bezeichnung"),
                                      area,
                                      getFieldOfLearning(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foo;
    }

    private List<FieldOfLearning> getFieldOfLearning(){
        return null;
    }
ää

   /* public Department getDepartment(Integer bid)
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

    */
}
