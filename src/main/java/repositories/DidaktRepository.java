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
 * Implementation of IDidaktrepository
 */
public class DidaktRepository implements IDidaktRepository {

    Connection con = null;
    /**
     *
     * @param con
     */
    public DidaktRepository(Connection con)
    {
        this.con = con;
        DatabaseConnector databaseConnector = new DatabaseConnector();
    }

    /**
     *
     * @param dataSource
     */
    public DidaktRepository(DataSource dataSource) {
        try {
            this.con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public ObservableList<Profession> getProfessionList() {
        String sql = "SELECT * FROM tbl_beruf " +
                     "INNER JOIN tbl_abteilung " +
                     "ON tbl_beruf.ID_abteilung = tbl_abteilung.AId " +
                     "INNER JOIN tbl_lehrer " +
                     "ON tbl_abteilung.ID_Leiter = tbl_lehrer.LId ";
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
                                                dep);
                prof.setFormOfTeaching(getFormOfTeaching(prof));
                prof.setDurationList(getDuration(prof));
                professionList.add(prof);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return professionList;
    }

    private String getFormOfTeaching(Profession profession){
        String sql = "select distinct Uformname from tbl_uform\n"+
                     "INNER JOIN tbl_uformberuf\n"+
                     "ON UID = ID_UForm\n"+
                     "WHERE ID_Beruf = ?\n";
        String wayOfTeaching = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, profession.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            wayOfTeaching = rs.getString("UFormname");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return wayOfTeaching;
    }
    /**
     *
     * @param profession
     * @return
     */
    private List<Subject> getSubjectList(Profession profession) {
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

    /**
     *
     * @param subject
     * @return
     */
    private List<FieldOfLearning> getFieldList(Subject subject) {
        //Prepared SQL-Statement
        String sql = "SELECT * FROM tbl_lernfeld\n" +
                     "INNER JOIN tbl_beruffach\n" +
                     "ON tbl_lernfeld.ID_Beruffach = tbl_beruffach.BFID\n" +
                     "WHERE tbl_beruffach.ID_Fach = ?";
        //Return Object
        List<FieldOfLearning> fieldOfLearningList = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,subject.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                FieldOfLearning fieldOfLearning = new FieldOfLearning(
                                                          rs.getInt("LFID"),
                                                          rs.getInt("LFDauer"),
                                                          rs.getString("Bezeichnung"),
                                                          rs.getInt("LFNR"));
                fieldOfLearning.setSubject(subject);
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

    /**
     * @param field //A FieldOfLearning consist of multiple LearningSituations
     * @return A List for all LearningSituations within @param field.
     */
    private List<LearningSituation> getLearningSituationList(FieldOfLearning field) {
        //Prepared SQL-Statement
        String sql = "SELECT * FROM tbl_lernsituation WHERE LF_NR = ? AND " +
                "ID_Lernfeld = ?";
        //Return Object
        List<LearningSituation> learningSituationList = new ArrayList<>();
        try{

            //Filling Variables into PreparedStatement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,field.getLfNr());
            ps.setInt(2,field.getId());
            ResultSet rs = ps.executeQuery();

            //Processing ResultSet
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
                learningSituation.setSubjectArea((field.getSubject().getAreaOfEducation()));
                learningSituation.setStartWeek(rs.getInt("Von"));
                learningSituation.setEndWeek(rs.getInt("Bis"));
                learningSituation.setExpertiseList(getLearningTechniqueList(learningSituation));
                learningSituation.setStudyTechniques(learningSituation.getLearningTechniqueList().toString());
                learningSituation.setPerformanceRecordList(getPerformanceRecordList(learningSituation));
                learningSituation.setCertificateOfPerformance(learningSituation.getPerformanceRecordList().toString());
                learningSituationList.add(learningSituation);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return learningSituationList;
    }

    /**
     *
     * @param learningSituation
     * @return A List of PerformanceRecords.
     */
    private List<PerformanceRecord> getPerformanceRecordList(LearningSituation learningSituation) {
        //Prepared SQL-Statement
        String sql = "SELECT LNID, Art from tbl_leistungsnachweis " +
                     "JOIN tbl_lernsituationleistungsnachweis " +
                     "ON LNID = ID_Leistungsnachweis " +
                     "WHERE ID_lernsituation = ?";
        //ReturnObject
        List<PerformanceRecord> performanceRecordList = new ArrayList<>();
        try{
            //Filling in Variables into SQL-Statement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,learningSituation.getId());
            ResultSet rs = ps.executeQuery();

            //Processing ResultSet
            while(rs.next()){
                PerformanceRecord performanceRecord = new PerformanceRecord(rs.getInt("LNID"), rs.getString("Art"));
                performanceRecordList.add(performanceRecord);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return performanceRecordList;
    }

    /**
     *
     * @param situation
     * @return
     */
    private List<LearningTechnique> getLearningTechniqueList(LearningSituation situation)
    {
        //Prepared SQL-Statement
        String sql = "SELECT * FROM tbl_lerntechnik " +
                     "JOIN tbl_lernsituationlerntechnik " +
                     "ON LATID = ID_lerntechnik " +
                     "JOIN tbl_kompetenz " +
                     "ON ID_Kompetenz = KId " +
                     "WHERE ID_Lernsituation = ?";

        //Return Object
        List<LearningTechnique> learningTechniques = new ArrayList<>();
        try
        {
            ////Filling in Variables into SQL-Statement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, situation.getId());
            ResultSet rs = ps.executeQuery();

            //Processing Resultset
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

    /**
     *
     * @param profession
     * @param year
     * @return
     */
    @Override
    public ReportData getReportData(Profession profession, Integer year){
        //Return Object
        ReportData reportData = new ReportData(profession);
        try{
            //Constructing necessary Objects
            String supervisor = profession.getDepartment().getTeacher().getSex() + " " +
                    profession.getDepartment().getTeacher().getName();
            reportData.setReportHeader(new ReportHeader(profession.getDepartment().getName(),
                                                     profession.getName(),
                                                     year,
                                                     profession.getFormOfTeaching(),
                                                     supervisor));
            reportData.getProfession().setSubjects(getSubjectList(reportData.getProfession()));

            //Combining all LearningSituations into 1 List<>
            for (Subject subject : reportData.getProfession().getSubjects()) {
                if(subject.getYear() == year){
                    for (FieldOfLearning field : subject.getFieldOfLearningList())
                    {
                        for (LearningSituation situation : field.getLearningSituationList())
                        {
                            reportData.getLearningSituations().add(situation);
                        }
                    }
                }
            }
        }

        catch(Exception e){
            e.printStackTrace();
        }

        return reportData;
    }

    /**
     * Method to meassure the duration of a Apprenticechip using
     * provided Subjects and the year they are thaught in.
     * @param profession
     * @return
     */
    private ObservableList<Integer> getDuration(Profession profession)
    {
        //Prepared SQL-Statement
        String sql = "SELECT DISTINCT Jahr FROM tbl_beruffach\n" +
                     "LEFT JOIN tbl_uformberuf\n" +
                     "ON tbl_beruffach.ID_UFormBeruf = tbl_uFormBeruf.UBID\n" +
                     "WHERE tbl_UFormBeruf.ID_Beruf = ?";
        //Return Object
        ObservableList<Integer> duration = FXCollections.observableArrayList();
        try {

            //Filling in Variables into SQL-Statement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, profession.getId());
            ResultSet rs = ps.executeQuery();

            //Processsing ResultSet
            while (rs.next()){
                duration.add(rs.getInt("Jahr"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return duration;
    }
}
