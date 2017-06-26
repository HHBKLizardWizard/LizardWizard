package models;

/**
 * Created by iho on 22.06.2017.
 */
public class Template {
    private long id;
    private String  templateName;
    private boolean scenario;
    private boolean competences;
    private boolean materials;
    private boolean technics;
    private boolean results;
    private boolean contents;
    private boolean notes;
    private boolean achievments;

    public Template(){

    }

    public Template(boolean scenario, boolean competences, boolean materials, boolean technics,
                    boolean results, boolean contents, boolean notes, boolean achievments) {

        this.scenario = scenario;
        this.competences = competences;
        this.materials = materials;
        this.technics = technics;
        this.results = results;
        this.contents = contents;
        this.notes = notes;
        this.achievments = achievments;
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

    public boolean isAchievments() {
        return achievments;
    }

    public void setAchievments(boolean achievments) {
        this.achievments = achievments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
