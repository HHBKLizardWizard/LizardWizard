package models;

/**
 * Created by iho on 23.06.2017.
 */
public class ReportHeader {
    private String department;
    private String job;
    private int yearOfTraining;
    private String classForm;
    private String educationalSupervisor;

    public ReportHeader() {
        // Nothing to do here
    }

    public ReportHeader(String department,
                        String job,
                        int yearOfTraining,
                        String classForm, String educationalSupervisor)
    {
        this.department = department;
        this.job = job;
        this.yearOfTraining = yearOfTraining;
        this.classForm = classForm;
        this.educationalSupervisor = educationalSupervisor;
    }

    public String getEducationalSupervisor() {
        return educationalSupervisor;
    }

    public void setEducationalSupervisor(String educationalSupervisor) {
        this.educationalSupervisor = educationalSupervisor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getYearOfTraining() {
        return yearOfTraining;
    }

    public void setYearOfTraining(int yearOfTraining) {
        this.yearOfTraining = yearOfTraining;
    }

    public String getClassForm() {
        return classForm;
    }

    public void setClassForm(String classForm) {
        this.classForm = classForm;
    }
}
