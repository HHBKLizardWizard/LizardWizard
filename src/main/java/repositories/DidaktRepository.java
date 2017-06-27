package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.reports.*;
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
        String sql = "SELECT * FROM tbl_beruf " +
                     "INNER JOIN tbl_abteilung " +
                     "ON tbl_beruf.ID = tbl_abteilung.AId " +
                     "INNER JOIN tbl_lehrer " +
                     "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId";
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
    public ObservableList<Profession> getProfessionList() {
        String sql = "SELECT * FROM tbl_beruf " +
                     "INNER JOIN tbl_abteilung " +
                     "ON tbl_beruf.ID = tbl_abteilung.AId " +
                     "INNER JOIN tbl_lehrer " +
                     "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId";;
        ObservableList<Profession> professionList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Teacher teach = new Teacher(rs.getInt("LId"),
                                            rs.getString("Lehrername"),
                                            rs.getString("Geschlecht"));
                Department dep = new Department(rs.getInt("AId"),
                                                rs.getString("Abteilungsname"),
                                                teach);
                Profession foo = new Profession(rs.getInt("BId"),
                                                rs.getString("Berufname"),
                                                dep);
                foo.setDurationList(getDuration(foo));
                professionList.add(foo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return professionList;
    }

    @Override
    public List<Subject> getSubjectList(Profession profession) {
        String sql = "SELECT * FROM tbl_fach\n" +
                     "INNER JOIN tbl_beruffach\n" +
                     "ON tbl_fach.FID = tbl_beruffach.ID_Fach\n" +
                     "INNER JOIN tbl_uformberuf\n" +
                     "ON tbl_beruffach.ID_UFormBeruf = tbl_uformberuf.UBID\n" +
                     "WHERE tbl_uformberuf.ID_Beruf = ?";
        List<Subject> subjects = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, profession.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Subject foo = new Subject(rs.getInt("FID"),
                                          rs.getInt("Jahr"),
                                          rs.getInt("Position"),
                                          rs.getInt("Lernbereich"),
                                          rs.getString("Bezeichnung"));
                ;
                subjects.add(foo);
            }
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        return subjects;
    }

    @Override
    public List<FieldOfLearning> getFieldList(Subject subject) {
        String sql = "SELECT * FROM tbl_lernfeld\n" +
                     "INNER JOIN tbl_beruffach\n" +
                     "ON tbl_lernfeld.ID_Beruffach = tbl_beruffach.BFID\n" +
                     "WHERE tbl_beruffach.ID_Fach = ?";
        List<FieldOfLearning> fieldOfLearningList = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,subject.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                FieldOfLearning foo = new FieldOfLearning();
                foo.setLearningSituationList(getLearningSituationList(foo));
                fieldOfLearningList.add(foo);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fieldOfLearningList;
    }

    @Override
    public List<LearningSituation> getLearningSituationList(FieldOfLearning field) {
        String sql = "SELECT * FROM tbl_lernsituation WHERE LF_NR = ?";
        List<LearningSituation> learningSituationList = new ArrayList<>();
        try{
            PreparedStatement
            LearningSituation foo = new LearningSituation();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReportData getReportData(Profession profession){
        ReportData dataSet = new ReportData();

        try{
            for (Subject subject : profession.getSubjectList()) {
                subject.setAreaOfEducation(dataSet.getAreaOfEducationList().get(subject.getAoeID()-1));
                dataSet.getAreaOfEducationList().get(subject.getAoeID()-1).getSubjectList().add(subject);
            }

        }
        catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    @Override

    public List<Integer> getDuration(Profession profession)
    {
        String sql = "SELECT DISTINCT Jahr from tbl_beruffach\n" +
                     "LEFT JOIN tbl_uformberuf\n" +
                     "ON tbl_beruffach.ID_UFormBeruf = tbl_uFormBeruf.UBID\n" +
                     "WHERE tbl_UFormBeruf.ID_Beruf = ?";
        List<Integer> duration = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, profession.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                duration.add(rs.getInt("Jahr"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return duration;
    }


    private ReportHeader getReportHeader(){
        String sql = "SELECTä";
        ReportHeader rh = null;
        try
        {
       /*     String teacher = null;
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
                                               teacher);*/
        } catch (Exception e)
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
