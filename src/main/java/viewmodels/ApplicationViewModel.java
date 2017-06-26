package viewmodels;

import javafx.application.Platform;
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
import models.Template;
import models.User;
import models.UserRights;
import models.reports.ReportData;
import models.ui_util.Profession;
import reports.ReportBuilder;
import repositories.DidaktRepository;
import repositories.IDidaktRepository;
import repositories.IUserRepository;
import repositories.UserRepository;
import util.DatabaseConnector;
import util.TestData;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Created by iho on 20.06.2017.
 */
public class ApplicationViewModel implements Initializable {

    @FXML
    private ChoiceBox<String> cbSector; //@todo define what type it is?

    @FXML
    private ChoiceBox<Integer> cbYear;

    @FXML
    private ChoiceBox<Template> cbTemplate;

    @FXML
    private Button btnExport;

    @FXML
    private MenuItem menuUser, menuTemplate, menuLogout, menuExit;

    @FXML
    private SeparatorMenuItem smiLine;

    public ObservableList<String> professions;
    private List<String> professionList;
    private IDidaktRepository didaktRepository;
    private IUserRepository userRepository;
    private DatabaseConnector dbConnector;

    @FXML
    private TextField txtUserId;

    // Class        : createAnnualReport
    // Beschreibung : Generiert das pdf
    public void createAnnualReport() {
        ReportData reportData = new TestData().getReportDataExample();

        // get data from database and build report
        ReportBuilder reportBuilder = new ReportBuilder();

        try {

            List<String> professionList = didaktRepository.getProfessions();

            for (String string : professionList) {
                System.out.println(string);
            }


            /*
            PdfDocument pdf = reportBuilder.createPdf("test.pdf");

            //Document document = reportBuilder.createAnnualReport(pdf, reportData);
            Document document = reportBuilder.createDetailReport(pdf, reportData);
            document.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources){
        dbConnector = new DatabaseConnector();
        didaktRepository = new DidaktRepository(dbConnector.getDidaktDataSource());
        userRepository = new UserRepository(dbConnector.getUserDataSource());

        professionList = didaktRepository.getProfessions();
    }


    // Class        : closeButtonAction
    // Beschreibung : Schließt das Programm
    public void closeButtonAction() {
        Platform.exit();
    }

    // Class        : openTargetWindowAction
    // Beschreibung : Öffnet das fenster abhängig von welche Menu Item ausgewählt war.
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

    // Class        : setUserId
    // Beschreibung : Setzt UserId so das es im nächste view wieder verwendet werden kann
    // Extra Info   : muss Public sein weil es im UserViewModel aufgerüfen würd
    public void setUserId(Integer userId) {
        txtUserId.setText(String.valueOf(userId));
        checkViewRights(userId);
    }

    // Class        : checkViewRights
    // Beschreibung : Setzt bestimmte Felder hidden im nächste view abhängig von Benutzer Rechten
    // Extra Info   : muss Public sein weil es im UserViewModel aufgerüfen würd
    public void checkViewRights(Integer userId) {
        IUserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());
        //User loggedUser = userRepository.getUserbyId(userId);
        User loggedUser = new User(1, "root","root","root", UserRights.ADMIN, "root");

        UserRights loggedUserRights = loggedUser.getRights();

        if(loggedUserRights.equals(UserRights.AZUBI)){
            menuUser.setVisible(false);
            menuTemplate.setVisible(false);
            smiLine.setVisible(false);
        }else if(loggedUserRights.equals(UserRights.LEHRER)){
            menuUser.setVisible(false);
        }
    }
}
