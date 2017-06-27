package viewmodels;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Template;
import models.User;
import models.UserRights;
import models.Profession;
import models.ReportData;
import reports.ReportBuilder;
import repositories.*;
import util.DatabaseConnector;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Created by iho on 20.06.2017.
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
    private MenuItem menuUser, menuTemplate, menuLogout, menuExit;

    @FXML
    private SeparatorMenuItem smiLine;

    private ObservableList<Profession> professionList = FXCollections.observableArrayList();
    private ObservableList<Template> templateList = FXCollections.observableArrayList();
    private IDidaktRepository didaktRepository;
    private IUserRepository userRepository;
    private ITemplateRepository templateRepository;
    private DatabaseConnector dbConnector;
    private User loggedUser;
    private Template selectedTemplate;
    private Profession selectedProfession;

    /**
     *   Class        : initialize
     *   Beschreibung : Hollt sich die liste von Fäche, jahre und templates und
     *                  füllt die ComboBoxen.
     */
    public void initialize(URL location, ResourceBundle resources){
        dbConnector = new DatabaseConnector();
        didaktRepository = new DidaktRepository(dbConnector.getDidaktDataSource());
        userRepository = new UserRepository(dbConnector.getUserDataSource());
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
     *   Class        : handleProfessionComboBoxAction
     *   Beschreibung : hollt sich die Jahre vom ausgewählte Profession Objekt
     */
    @FXML
    private void handleProfessionComboBoxAction() {
        Profession profession = cbProfession.getSelectionModel().getSelectedItem();
        cbYear.setItems(profession.getDurationList());
        cbYear.getSelectionModel().select(0);
    }

    /**
    *   Class        : createAnnualReport
    *   Beschreibung : Generiert das pdf
    */
    public void createAnnualReport() {
        // Erstelle Profession Object aus Comboboxen Auswahl
        Profession profession = selectedProfession;

        // Hole Template Object aus Combobox Auswahl
        Template template = selectedTemplate;

        // Hole Jahr aus Combobox
        int year = cbYear.getSelectionModel().getSelectedItem();

        // get data from database and build report

        didaktRepository = new DidaktRepository(new DatabaseConnector().getDidaktDataSource());
        ReportData reportData = didaktRepository.getReportData(profession, year);

        if(template != null){
            //todo ABER ICH WIIIIILLLLL !!!! xD
            new ReportBuilder("dashierliestnochniemand:).pdf", reportData).createReport(template);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("no template selected");
            alert.setContentText("No template was selected, in case you do not have any templates and you are " +
                    "unable to make them due to not have the permissions to then please contact your administrator");
            alert.showAndWait();
        }
    }

    // TODO remove

    /**
     *   Class        : closeButtonAction
     *   Beschreibung : Schließt das Programm
     */
    public void closeButtonAction() {
        Platform.exit();
    }

    /**
     *   Class        : openTargetWindowAction
     *   Beschreibung : Öffnet das Fenster abhängig davon, welches Menu Item ausgewählt wurde.
     */
    public void openTargetWindowAction(ActionEvent event) {
        try {
            String menuItemClickedId = "", stageTitle = "";
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
     *   Class        : setUserId
     *   Beschreibung : Setzt UserId so, dass es in der nächsten View wieder verwendet werden kann
     *   Extra Info   : Muss Public sein, weil es im UserViewModel aufgerufen wird
     */
    public void setUser(User user) {
        loggedUser = user;

        //templates müssen vor Initialize gefüllt werden weil variable loggedUser im Initialize noch null ist.
        templateList = templateRepository.getTemplatesByUser(loggedUser, false);

        cbTemplate.setItems(templateList);
        try{
            selectedTemplate = templateList.get(0);
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
     *   Class        : checkViewRights
     *   Beschreibung : Setzt bestimmte Felder in der nächsten View auf "hidden", abhängig von den Benutzerrechten
     *   Extra Info   : Muss Public sein, weil es im UserViewModel aufgerufen wird
     */
    public void checkViewRights(User user) {
        UserRights loggedUserRights = user.getRights();

        if(loggedUserRights.equals(UserRights.AZUBI)){
            menuUser.setVisible(false);
            menuTemplate.setVisible(false);
            smiLine.setVisible(false);
        }else if(loggedUserRights.equals(UserRights.LEHRER)){
            menuUser.setVisible(false);
        }
    }
}
