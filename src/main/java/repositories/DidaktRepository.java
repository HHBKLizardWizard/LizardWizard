package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
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
    public ObservableList<Profession> getProfessionList() {
        String sql = "SELECT * FROM tbl_beruf " +
                     "INNER JOIN tbl_abteilung " +
                     "ON tbl_beruf.ID_abteilung = tbl_abteilung.AId " +
                     "INNER JOIN tbl_lehrer " +
                     "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId " +
                     "INNER JOIN tbl_uformberuf " +
                     "ON BId = ID_Beruf INNER JOIN tbl_uform ON ID_UForm = UID;";;
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
                Profession prof = new Profession(rs.getInt("BId"),
                                                rs.getString("Berufname"),
                                                dep, rs.getString("UFormname"));
                prof.setDurationList(getDuration(prof));
                professionList.add(prof);
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
                Subject subject = new Subject(rs.getInt("FID"),
                                          rs.getInt("Jahr"),
                                          rs.getInt("Position"),
                                          rs.getInt("Lernbereich"),
                                          rs.getString("Bezeichnung"));
                subject.setFieldOfLearningList(getFieldList(subject));
                subjects.add(subject);
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
                FieldOfLearning fieldOfLearning = new FieldOfLearning(rs.getInt("LFDauer"),
                                                          rs.getString("Bezeichnung"),
                                                          rs.getInt("LFNR"));
                fieldOfLearning.setLearningSituationList(getLearningSituationList(fieldOfLearning));
                fieldOfLearningList.add(fieldOfLearning);
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
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,field.getLfNr());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                LearningSituation learningSituation = new LearningSituation();
                learningSituation.setId(rs.getInt("LSID"));
                learningSituation.setLsnr(rs.getInt("LSNR"));
                learningSituation.setLessonHours(rs.getInt("UStunden"));
                learningSituation.setName(rs.getString("Name"));
                learningSituation.setContents(rs.getString("Inhalte"));
                learningSituation.setEssentialSkills(rs.getString("Kompetenzen"));
                learningSituation.setLearningResult(rs.getString("Handlungsprodukt"));
                learningSituation.setOrganisationalDetails(rs.getString("Organisation"));
                learningSituation.setClassMaterial(rs.getString("Umaterial"));
                learningSituation.setScenario(rs.getString("Szenario"));
                learningSituation.setFieldOfLearning(field);
                learningSituation.setSubject(field.getSubject().getName());
                learningSituation.setSubjectArea((field.getSubject().getAoeID()));
                learningSituation.setStartWeek(rs.getInt("Von"));
                learningSituation.setEndWeek(rs.getInt("Bis"));
                learningSituation.setExpertiseList(getLearningTechniqueList(learningSituation));
                learningSituation.setPerformanceRecordList(getPerformanceRecordList(learningSituation));
                learningSituationList.add(learningSituation);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return learningSituationList;
    }

    public List<PerformanceRecord> getPerformanceRecordList(LearningSituation learningSituation) {
        String sql = "SELECT LNID, Art from tbl_leistungsnachweis " +
                     "JOIN tbl_lernsituationleistungsnachweis " +
                     "ON LNID = ID_Leistungsnachweis " +
                     "WHERE ID_lernsituation = ?";
        List<PerformanceRecord> performanceRecordList = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,learningSituation.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                PerformanceRecord performanceRecord = new PerformanceRecord(rs.getInt("LNID"), rs.getString("Art"));
                performanceRecordList.add(performanceRecord);
            }
        }
        catch (Exception e){

        }
        return performanceRecordList;
    }

    @Override
    public List<LearningTechnique> getLearningTechniqueList(LearningSituation situation)
    {
        String sql = "SELECT * FROM tbl_lerntechnik " +
                     "JOIN tbl_lernsituationlerntechnik " +
                     "ON LATID = ID_lerntechnik " +
                     "JOIN tbl_kompetenz " +
                     "ON ID_Kompetenz = KId " +
                     "WHERE ID_Lernsituation = ?";
        List<LearningTechnique> learningTechniques = new ArrayList<>();
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, situation.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LearningTechnique lt = new LearningTechnique(rs.getInt("LATID"),
                                                             rs.getString("Technik"),
                                                             rs.getString("Text"),
                                                             situation);
                learningTechniques.add(lt);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return learningTechniques;
    }

    @Override
    public ReportData getReportData(Profession profession, Integer year){
        ReportData reportData = new ReportData(profession);
        try{
            String supervisor = profession.getDepartment().getTeacher().getSex() + " " +
                    profession.getDepartment().getTeacher();

            reportData.setReportHeader(new ReportHeader(profession.getDepartment().getName(),
                                                     profession.getName(),
                                                     year,
                                                     profession.getFormOfTeaching(),
                                                     supervisor));
            reportData.getProfession().setSubjects(getSubjectList(reportData.getProfession()));
            for (Subject subject : reportData.getProfession().getSubjects()) {
                for (FieldOfLearning field : subject.getFieldOfLearningList()) {
                    for (LearningSituation situation : field.getLearningSituationList()) {
                        reportData.getLearningSituations().add(situation);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return reportData;
    }

    public ObservableList<Integer> getDuration(Profession profession)
    {
        String sql = "SELECT DISTINCT Jahr FROM tbl_beruffach\n" +
                     "LEFT JOIN tbl_uformberuf\n" +
                     "ON tbl_beruffach.ID_UFormBeruf = tbl_uFormBeruf.UBID\n" +
                     "WHERE tbl_UFormBeruf.ID_Beruf = ?";

        ObservableList<Integer> duration = FXCollections.observableArrayList();
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
