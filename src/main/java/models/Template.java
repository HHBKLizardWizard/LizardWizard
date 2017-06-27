package models;

/**
 * Created by iho on 22.06.2017.
 */
public class Template {
    private Integer id;
    private String templateName;
    private boolean scenario;
    private boolean competences;
    private boolean materials;
    private boolean technics;
    private boolean results;
    private boolean contents;
    private boolean notes;
    private boolean achievements;
    private User user;

    public Template(){
        // Nothing to do here
    }

    public Template(String templateName){
        this.templateName = templateName;
    }

    public Template(Integer id, String templateName, boolean scenario, boolean competences, boolean materials, boolean technics,
                    boolean results, boolean contents, boolean notes, boolean achievements, User user) {

        this.id = id;
        this.templateName = templateName;
        this.scenario = scenario;
        this.competences = competences;
        this.materials = materials;
        this.technics = technics;
        this.results = results;
        this.contents = contents;
        this.notes = notes;
        this.achievements = achievements;
        this.user = user;
    }

    public Template(String templateName, boolean scenario, boolean competences, boolean materials, boolean technics,
                    boolean results, boolean contents, boolean notes, boolean achievements, User user) {

        this.templateName = templateName;
        this.scenario = scenario;
        this.competences = competences;
        this.materials = materials;
        this.technics = technics;
        this.results = results;
        this.contents = contents;
        this.notes = notes;
        this.achievements = achievements;
        this.user = user;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isScenario() {
        return scenario;
    }

    public void setScenario(boolean scenario) {
        this.scenario = scenario;
    }

    public boolean isCompetences() {
        return competences;
    }

    public void setCompetences(boolean competences) {
        this.competences = competences;
    }

    public boolean isMaterials() {
        return materials;
    }

    public void setMaterials(boolean materials) {
        this.materials = materials;
    }

    public boolean isTechnics() {
        return technics;
    }

    public void setTechnics(boolean technics) {
        this.technics = technics;
    }

    public boolean isResults() {
        return results;
    }

    public void setResults(boolean results) {
        this.results = results;
    }

    public boolean isContents() {
        return contents;
    }

    public void setContents(boolean contents) {
        this.contents = contents;
    }

    public boolean isNotes() {
        return notes;
    }

    public void setNotes(boolean notes) {
        this.notes = notes;
    }

    public boolean isAchievements() {
        return achievements;
    }

    public void setAchievements(boolean achievements) {
        this.achievements = achievements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
