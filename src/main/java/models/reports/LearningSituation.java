package models.reports;

/**
 * Created by iho on 23.06.2017.
 */
public class LearningSituation {
    private long id;
    private String name;
    private int startWeek;
    private int endWeek;
    private int lessonHours;
    private String subject;
    private String subjectArea;
    private String contents;
    private String essentialSkills;
    private String learningResult;
    private String organisationalDetails;
    private String classMaterial;
    private String studyTechniques;
    private String ceritficateOfPerformance;
    private String scenario;
    private String requiredSituation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
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

    public String getCeritficateOfPerformance() {
        return ceritficateOfPerformance;
    }

    public void setCeritficateOfPerformance(String ceritficateOfPerformance) {
        this.ceritficateOfPerformance = ceritficateOfPerformance;
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
}
