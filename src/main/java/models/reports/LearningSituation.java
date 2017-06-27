package models.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class LearningSituation extends LearningSituationTableElement {
    private Integer id;
    private Integer lsnr;
    private Integer lessonHours;
    private String name;
    private String subject;
    private String subjectArea;
    private String contents;
    private String essentialSkills;
    private String learningResult;
    private String organisationalDetails;
    private String classMaterial;
    private String studyTechniques;
    private String certificateOfPerformance;
    private String scenario;
    private String requiredSituation;
    private FieldOfLearning fieldOfLearning;
    private List<LearningTechnique> learningTechniqueList = new ArrayList<>();
    private List<PerformanceRecord> performanceRecordList = new ArrayList<>();

    public FieldOfLearning getFieldOfLearning() {
        return fieldOfLearning;
    }

    public void setFieldOfLearning(FieldOfLearning fieldOfLearning) {
        this.fieldOfLearning = fieldOfLearning;
    }

    public Integer getLsnr() {
        return this.lsnr;
    }

    public void setLsnr(Integer lsnr) {
        this.lsnr = lsnr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessonHours() {
        return lessonHours;
    }

    public void setLessonHours(int lessonHours) {
        this.lessonHours = lessonHours;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getEssentialSkills() {
        return essentialSkills;
    }

    public void setEssentialSkills(String essentialSkills) {
        this.essentialSkills = essentialSkills;
    }

    public String getLearningResult() {
        return learningResult;
    }

    public void setLearningResult(String learningResult) {
        this.learningResult = learningResult;
    }

    public String getOrganisationalDetails() {
        return organisationalDetails;
    }

    public void setOrganisationalDetails(String organisationalDetails) {
        this.organisationalDetails = organisationalDetails;
    }

    public String getClassMaterial() {
        return classMaterial;
    }

    public void setClassMaterial(String classMaterial) {
        this.classMaterial = classMaterial;
    }

    public String getStudyTechniques() {
        return studyTechniques;
    }

    public void setStudyTechniques(String studyTechniques) {
        this.studyTechniques = studyTechniques;
    }

    public String getCertificateOfPerformance() {
        return certificateOfPerformance;
    }

    public void setCertificateOfPerformance(String certificateOfPerformance) {
        this.certificateOfPerformance = certificateOfPerformance;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getRequiredSituation() {
        return requiredSituation;
    }

    public void setRequiredSituation(String requiredSituation) {
        this.requiredSituation = requiredSituation;
    }

    public void setLessonHours(Integer lessonHours)
    {
        this.lessonHours = lessonHours;
    }

    public List<LearningTechnique> getLearningTechniqueList()
    {
        return learningTechniqueList;
    }

    public void setExpertiseList(List<LearningTechnique> expertiseList)
    {
        this.learningTechniqueList = expertiseList;
    }

    public List<PerformanceRecord> getPerformanceRecordList()
    {
        return performanceRecordList;
    }

    public void setPerformanceRecordList(List<PerformanceRecord> performanceRecordList)
    {
        this.performanceRecordList = performanceRecordList;
    }
}
