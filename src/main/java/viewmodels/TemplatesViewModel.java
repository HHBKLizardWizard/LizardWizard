package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Template;
import models.User;
import repositories.ITemplateRepository;
import repositories.TemplateRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by : Sil van Vliet
 * Date       : 22.06.2017
 *
 * Model where all the functions are for the template view.
 * Here the user ( when he has the rights to add / edit templates)
 * can add, update or delete templates
 */

public class TemplatesViewModel implements Initializable {

    @FXML
    private CheckBox chk_scenario, chk_kompetenzen, chk_material, chk_technik,
            chk_ergebnis, chk_inhalte, chk_hinweis, chk_leistung;

    @FXML
    private ComboBox<Template> cbTemplate;

    @FXML
    private TextField txtTempName;

    @FXML
    private Button btnBack;

    private User loggedUser;

    private ITemplateRepository templateRepository;

    /**
     * created conection with DB to get Template data
     * @param location used to resolve relative paths for the root object (null if the location is not known).
     * @param resources used to localize the root object (null if the root object was not localized).
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templateRepository = new TemplateRepository(new DatabaseConnector().getUserDataSource());
    }

    /**
     * Fills the template info in editable area. Filling the template name text field and
     * putting all checkboxes on true that the user has previously saved. In case selected
     * template ID is null then clears all fields.
     */
    @FXML
    public void handleTemplateComboBoxAction() {
        Template template = cbTemplate.getSelectionModel().getSelectedItem();

        if(template.getId() != null){
            txtTempName.setText(template.getTemplateName());
            chk_scenario.setSelected(template.isScenario());
            chk_kompetenzen.setSelected(template.isCompetences());
            chk_material.setSelected(template.isMaterials());
            chk_technik.setSelected(template.isTechnics());
            chk_ergebnis.setSelected(template.isResults());
            chk_inhalte.setSelected(template.isContents());
            chk_hinweis.setSelected(template.isNotes());
            chk_leistung.setSelected(template.isAchievements());
        }else{
            clearFields();
        }
    }

    /**
     * Called from different vew model, puts logged User in this view and fills the template drop down
     * with all the created templates by this user.
     * @param user that has logged in.
     */
    public void setUser(User user) {
        loggedUser = user;
        getTemplatesForUser();
        handleTemplateComboBoxAction();
    }

    /**
     * Retrieves all the Templates for the logged user.
     */
    private void getTemplatesForUser() {
        cbTemplate.setItems(templateRepository.getTemplatesByUser(loggedUser, true));
        cbTemplate.getSelectionModel().select(0);

        //convert template to only show name instead of object
        cbTemplate.setConverter(new StringConverter<Template>() {

            @Override
            public String toString(Template object) {
                return object.getTemplateName();
            }

            @Override
            public Template fromString(String string) {
                return cbTemplate.getItems().stream().filter(ap ->
                        ap.getTemplateName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    /**
     * Removes the selected template from the dropdown and out of the database.
     * Does first check if the selected template Id is null or not since this
     * is being used to create new templates.
     */
    public void removeTemplate(){
        Template selectedTemplate = cbTemplate.getSelectionModel().getSelectedItem();
        if(selectedTemplate.getId() != null){
            //Confirmation if the Template should really be deleted.
            ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Sind Sie sicher das Sie der/die Benutzer " + selectedTemplate.getTemplateName() +
                            "löschen willen?",
                    yes,
                    no);
            alert.setTitle("Template löschen");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get().getButtonData().isDefaultButton()){
                cbTemplate.getItems().remove(selectedTemplate);
                templateRepository.deleteTemplate(selectedTemplate);
            }
        }
    }

    /**
     * Saves a new or updates the selected Template. This is being controlled by if the
     * selected template ID is null or not. When yes, new template will be created.
     */
    public void saveTemplate(){
        Template selectedTemplate = cbTemplate.getSelectionModel().getSelectedItem();
        Alert.AlertType alertType = Alert.AlertType.INFORMATION;
        String msgTitle = "Success",
               msgHeader,
               msgText;

        if(selectedTemplate.getId() == null){
            if(!txtTempName.getText().equals("")){
                Template template = new Template(
                         txtTempName.getText(),
                         chk_scenario.isSelected(),
                         chk_kompetenzen.isSelected(),
                         chk_material.isSelected(),
                         chk_technik.isSelected(),
                         chk_ergebnis.isSelected(),
                         chk_inhalte.isSelected(),
                         chk_hinweis.isSelected(),
                         chk_leistung.isSelected(),
                         loggedUser
                );

                templateRepository.createTemplate(template, loggedUser);
                cbTemplate.getItems().add(template);

                msgHeader = "Template erstellt";
                msgText = "Die neue Template " + txtTempName.getText() + "war erforgleich erstellt";

                clearFields();
            }else{
                //Error message
                msgTitle = "ERROR";
                msgHeader = "Name ist Leer";
                msgText = "Es kann kein Template hinterlegt werden ohne Name.";
                alertType = Alert.AlertType.ERROR;
            }
        }else{
            selectedTemplate.setTemplateName(txtTempName.getText());
            selectedTemplate.setScenario(chk_scenario.isSelected());
            selectedTemplate.setCompetences(chk_kompetenzen.isSelected());
            selectedTemplate.setMaterials(chk_material.isSelected());
            selectedTemplate.setTechnics(chk_technik.isSelected());
            selectedTemplate.setResults(chk_ergebnis.isSelected());
            selectedTemplate.setContents(chk_inhalte.isSelected());
            selectedTemplate.setNotes(chk_hinweis.isSelected());
            selectedTemplate.setAchievements(chk_leistung.isSelected());

            templateRepository.updateTemplate(selectedTemplate);
            msgHeader = "Template Erstell";
            msgText = "Die Template " + txtTempName.getText() + "war erforgleich aktualiesiert";

        }

        Alert alert = new Alert(alertType);
        alert.setTitle(msgTitle);
        alert.setHeaderText(msgHeader);
        alert.setContentText(msgText);
        alert.showAndWait();
    }

    /**
     * Empties the Template name text field and puts all the checkboxes False.
     */
    private void clearFields() {
        txtTempName.setText("");
        chk_scenario.setSelected(false);
        chk_kompetenzen.setSelected(false);
        chk_material.setSelected(false);
        chk_technik.setSelected(false);
        chk_ergebnis.setSelected(false);
        chk_inhalte.setSelected(false);
        chk_hinweis.setSelected(false);
        chk_leistung.setSelected(false);
    }

    /**
     * Closes the Template view stage
     */
    public void closeWindow(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
        //todo on refocuss on main window template dropdown needs to be refreshed
    }
}
