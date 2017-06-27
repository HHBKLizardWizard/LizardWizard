package viewmodels;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Template;
import models.User;
import models.UserRights;
import models.reports.Profession;
import models.reports.ReportData;
import reports.ReportBuilder;
import repositories.*;
import util.DatabaseConnector;
import util.TestData;

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
        Profession profession = new Profession(1, "IT-Systemelektroniker/in");

        // Hole Template Object aus Combobox Auswahl
        Template template = new Template();

        // Hole Jahr aus Combobox
        int year = 1;

        // get data from database and build report

        didaktRepository = new DidaktRepository(new DatabaseConnector().getDidaktDataSource());
        ReportData reportData = didaktRepository.getReportData(profession, year);

        new ReportBuilder("dashierliestnochniemand:).pdf", reportData).createReport(template);
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

        /* back to id when exporting to pdf
        combo.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("Selected airport: " + newval.getName()
                        + ". ID: " + newval.getID());
        });*/





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
