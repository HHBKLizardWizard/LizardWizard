package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import models.Template;
import models.User;
import repositories.ITemplateRepository;
import repositories.TemplateRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class TemplatesViewModel implements Initializable {

    @FXML
    private CheckBox chk_scenario, chk_kompetenzen, chk_material, chk_technik,
            chk_ergebnis, chk_inhalte, chk_hinweis, chk_leistung;

    @FXML
    private ComboBox<Template> cbTemplate;

    @FXML
    private ComboBox cbGroup;

    @FXML
    private TextField txtTempName;

    @FXML
    private Button btnNewTemplate, btnDelete, btnSave, btnBack;

    private User loggedUser;

    private ITemplateRepository templateRepository;


    /**
     *   Class        :
     *   Beschreibung :
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        templateRepository = new TemplateRepository(new DatabaseConnector().getUserDataSource());
    }

    /**
     *   Class        : handleProfessionComboBoxAction
     *   Beschreibung : hollt sich die Jahre vom ausgewählte Profession Objekt
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
    }


    /**
     *   Class        : setUserId
     *   Beschreibung : Setzt UserId so, dass die richtige Templates aus dem Datenbank gehölt werden kann
     */
    public void setUser(User user) {
        loggedUser = user;
        getTemplatesForUser();
        handleTemplateComboBoxAction();
    }

    /**
     *   Class        : setTemplatesForUser
     *   Beschreibung : hölt templates für benutzer
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
     *   Class        : removeTemplate
     *   Beschreibung : löscht des selektierte Template aus die dropDown und aud dem DatenBank.
     *                  Nur nicht wenn das ID null ist weil das ist für neu anlegen von Tabelle.
     */
    public void removeTemplate(){
        Template selectedTemplate = cbTemplate.getSelectionModel().getSelectedItem();
        if(selectedTemplate.getId() != null){
            cbTemplate.getItems().remove(selectedTemplate);
            templateRepository.deleteTemplate(selectedTemplate);
        }
    }

    /**
     *   Class        : saveTemplate
     *   Beschreibung : Speichert oder aktualiesiert das ausgewählte Template.
     *   Extra Info   : Wenn das ausgewählte Template id null ist erstellt es ein
     *                  neue Template.
     */
    public void saveTemplate(){
        Template selectedTemplate = cbTemplate.getSelectionModel().getSelectedItem();
        Alert.AlertType alertType = Alert.AlertType.CONFIRMATION;
        String msgTitle = "Success",
               msgHeader = "",
               msgText = "";

        if(selectedTemplate.getId() == null){
            //todo in this cause create new Template
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


                msgHeader = "Template Erstell";
                msgText = "";
            }else{
                //Error message
                msgTitle = "ERROR";
                msgHeader = "Name ist Leer";
                msgText = "Es kann kein Template hinterlegt werden ohne Name.";
                alertType = Alert.AlertType.ERROR;
            }
        }else{
            //todo update the selected Template

        }

        Alert alert = new Alert(alertType);
        alert.setTitle(msgTitle);
        alert.setHeaderText(msgHeader);
        alert.setContentText(msgText);
        alert.showAndWait();
    }

}
