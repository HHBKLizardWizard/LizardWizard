package viewmodels;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import models.Template;
import models.User;
import models.UserRights;
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
    private ComboBox<String> cbSector;

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

    private ObservableList<String> professionList = FXCollections.observableArrayList();
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

        professionList = didaktRepository.getProfessions();
        cbSector.setItems(professionList);
        cbSector.getSelectionModel().select(0);

    }

    /**
    *   Class        : createAnnualReport
    *   Beschreibung : Generiert das pdf
    */
    public void createAnnualReport() {
        ReportData reportData = new TestData().getReportDataExample();
        
        // get data from database and build report
        ReportBuilder reportBuilder = new ReportBuilder();
        didaktRepository = new DidaktRepository(new DatabaseConnector().getDidaktDataSource());

        try {
            PdfDocument pdf = reportBuilder.createPdf("test.pdf");
            Document document = reportBuilder.createAnnualReport(pdf, reportData);
            //Document document = reportBuilder.createDetailReport(pdf, reportData);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                root2 = FXMLLoader.load(getClass().getClassLoader().getResource("templates.fxml"));
                stage.setScene(new Scene(root2, 600, 400));
                stageTitle = "Templates";
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
        templateList = templateRepository.getTemplatesByUser(loggedUser);
        cbTemplate.setItems(templateList);
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
