package viewmodels;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import models.*;
import reports.ReportBuilder;
import repositories.DidaktRepository;
import repositories.IDidaktRepository;
import repositories.ITemplateRepository;
import repositories.TemplateRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Created by : Sil van Vliet
 * Date       : 22.06.2017
 *
 * Model where all the functions are for the application view.
 * This is the main screen that all users can see and extract PDFs.
 */
public class ApplicationViewModel implements Initializable {

    @FXML
    private ComboBox<Profession> cbProfession;

    @FXML
    private ComboBox<Integer> cbYear;

    @FXML
    private ComboBox<Template> cbTemplate;

    @FXML
    private Button btnExport;

    @FXML
    private MenuItem menuUser, menuTemplate, menuLogout;

    @FXML
    private SeparatorMenuItem smiLine;

    private IDidaktRepository didaktRepository;
    private ITemplateRepository templateRepository;
    private User loggedUser;
    private Template selectedTemplate;
    private Profession selectedProfession;
    private ObservableList<Template> userTemplateList;

    /**
     * Gets the List of all the Professions and Years from within the Professions object and
     * fills the combo boxes. Also adds a listener to update certain elements on combo box change
     * @param location used to resolve relative paths for the root object (null if the location is not known).
     * @param resources used to localize the root object (null if the root object was not localized).
     */
    public void initialize(URL location, ResourceBundle resources){
        DatabaseConnector dbConnector = new DatabaseConnector();
        didaktRepository = new DidaktRepository(dbConnector.getDidaktDataSource());
        templateRepository = new TemplateRepository(dbConnector.getUserDataSource());

        cbProfession.setItems(didaktRepository.getProfessionList());
        selectedProfession = didaktRepository.getProfessionList().get(0);

        //convert professionList to only show name instead of object
        cbProfession.setConverter(new StringConverter<Profession>() {

            @Override
            public String toString(Profession object) {
                return object.getName();
            }

            @Override
            public Profession fromString(String string) {
                return cbProfession.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        cbProfession.getSelectionModel().select(0);

        //eigentlich für onChangeAction aber es muss hier auch gefüllt werden
        handleProfessionComboBoxAction();

        /* store selected Template on change*/
        cbTemplate.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                selectedTemplate = newval;
        });

        /* store selected Template on change*/
        cbProfession.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                selectedProfession = newval;
        });
    }

    /**
     * Gets the years from the selected Profession Object.
     */
    @FXML
    private void handleProfessionComboBoxAction() {
        Profession profession = cbProfession.getSelectionModel().getSelectedItem();
        cbYear.setItems(profession.getDurationList());
        cbYear.getSelectionModel().select(0);
    }

    /**
     * Generates the PDF.
     */
    public void createAnnualReport() {
        // get Profession object from combobox selection
        Profession profession = selectedProfession;

        // get Template object from combobox selection
        Template template = selectedTemplate;

        // get year from comboBox selection
        int year = cbYear.getSelectionModel().getSelectedItem();


        // get connection to database and fetch data
        didaktRepository = new DidaktRepository(new DatabaseConnector().getDidaktDataSource());
        ReportData reportData = didaktRepository.getReportData(profession, year);

        if (template != null){
            new ReportBuilder(reportData).createReport(template);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("no template selected");
            alert.setContentText("No template was selected, in case you do not have any templates and you are " +
                    "unable to make them due to not have the permissions to then please contact your administrator");
            alert.showAndWait();
        }
    }

    /**
     * Closes the program.
     */
    public void closeButtonAction() {
        Platform.exit();
    }

    /**
     * Opens the correct view depending on the element (MenuItem) that has been clicked.
     * @param event that is happening through which we can get the Object that has been clicked.
     */
    @SuppressWarnings("ConstantConditions")
    public void openTargetWindowAction(ActionEvent event) {
        try {
            String menuItemClickedId = "", stageTitle;
            Object eventSource = event.getSource();
            Stage stage = new Stage();
            Parent root2;

            if (eventSource instanceof MenuItem) {
                MenuItem menuItemClicked = (MenuItem) eventSource;
                menuItemClickedId = menuItemClicked.getId();
            }

            //check what button was pressed to be able to determine what fxml file to open
            if (Objects.equals(menuItemClickedId, menuTemplate.getId())) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("templates.fxml"));
                loader.load();

                Parent p = loader.getRoot();
                stage.setScene(new Scene(p, 600, 328));
                stageTitle = "Templates";
                TemplatesViewModel templatesViewModel = loader.getController();
                templatesViewModel.setUser(loggedUser);
                stage.setOnCloseRequest(event1 -> updateTemplateList());

            } else if (Objects.equals(menuItemClickedId, menuUser.getId())) {
                root2 = FXMLLoader.load(getClass().getClassLoader().getResource("users.fxml"));
                stage.setScene(new Scene(root2, 600, 400));
                stageTitle = "Benutzer";
            } else if(Objects.equals(menuItemClickedId, menuLogout.getId())){
                root2 = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
                stage.setScene(new Scene(root2, 350, 200));
                stageTitle = "Login";

                //with logout the current window has to be closed
                Stage st = (Stage) btnExport.getScene().getWindow();
                st.close();
            }else{
                throw new Exception(); //@todo create own exception? is it worth it?
            }

            //Open new Window with correct title
            stage.setTitle(stageTitle);
            stage.setResizable(false);
            //disable Primary Stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }

    /**
     * Puts the logged user element in this view so that certain Rights / views can be set.
     * Also fills the dropdown fields with the user's personal Data.
     * @param user that is logged into the application
     */
    public void setUser(User user) {
        loggedUser = user;

        //templates müssen vor Initialize gefüllt werden weil variable loggedUser im Initialize noch null ist.
        userTemplateList = templateRepository.getTemplatesByUser(loggedUser, false);

        cbTemplate.setItems(userTemplateList);
        try{
            selectedTemplate = userTemplateList.get(0);
        }catch (Exception e){
            selectedTemplate = null;
        }

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



        cbTemplate.getSelectionModel().select(0);
        checkViewRights(user);
    }

    /**
     * Puts certain element as hidden depending on the Users permission.
     * @param user for whom we have to check the Permissions.
     */
    private void checkViewRights(User user) {
        UserRights loggedUserRights = user.getRights();

        if(loggedUserRights.equals(UserRights.AZUBI)){
            menuUser.setVisible(false);
            menuTemplate.setVisible(false);
            smiLine.setVisible(false);
        }else if(loggedUserRights.equals(UserRights.LEHRER)){
            menuUser.setVisible(false);
        }
    }

    private void updateTemplateList() {
        this.userTemplateList = templateRepository.getTemplatesByUser(loggedUser,false);
        cbTemplate.getItems().setAll(userTemplateList);
    }

}
