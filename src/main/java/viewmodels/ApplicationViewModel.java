package viewmodels;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Template;
import repositories.IUserRepository;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//import util.ReportBuilder;

/**
 * Created by iho on 20.06.2017.
 */
public class ApplicationViewModel implements Initializable {

    private IUserRepository userRepository;

    @FXML
    private ChoiceBox cbSector; //@todo define what type it is?

    @FXML
    private ChoiceBox<Integer> cbYear;

    @FXML
    private ChoiceBox<Template> cbTemplate;

    @FXML
    private Button btnExport;

    @FXML
    private MenuItem menuUser, menuTemplate, menuLogout, menuExit;

    /*
    public void exportPdf() {
        ReportBuilder reportBuilder = new ReportBuilder();

        try {
            PdfDocument pdf = reportBuilder.createPdf("test.pdf");
            reportBuilder.createDocument(pdf, false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
*/
  //  @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeButtonAction(){
        Platform.exit();
    }

    public void logoutAction(){
        //@todo well ... everything is to do !! get a move on!!
    }

    public void openTargetWindowAction(ActionEvent event){
        try{
            Object eventSource= event.getSource();
            Parent root2;
            String menuItemClickedId = "", stageTitle = "";

            if(eventSource instanceof MenuItem){
                MenuItem menuItemClicked = (MenuItem) eventSource;
                menuItemClickedId = menuItemClicked.getId();
            }

            //check what button was pressed to be able to determine what fxml file to open
            if (Objects.equals(menuItemClickedId, menuTemplate.getId())) {
                root2 = FXMLLoader.load(getClass().getClassLoader().getResource("templates.fxml"));
                stageTitle = "Templates";
            } else if (Objects.equals(menuItemClickedId, menuUser.getId())) {
                root2 = FXMLLoader.load(getClass().getClassLoader().getResource("users.fxml"));
                stageTitle = "Benutzer";
            }else{
                throw new Exception(); //@todo create own exception? is it worth it?
            }

            //Open new Window with correct title
            Stage stage = new Stage();
            stage.setTitle(stageTitle);
            stage.setScene(new Scene(root2, 600, 400));
            stage.setResizable(false);
            //disable Primary Stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }

    public void test() {

        String user = "root";
        String pw = "test";


        //we can not do anything with true or false .. if need to get the user by ID or
        //by username, we need the data of that person back. not true or false...


/*        if ((userRepository.getUserbyUsername(user)))
            System.out.println("Looks good!");
        else
            System.out.println("Something went wrong;");

        if ((userRepository.get(pw)))
            System.out.println("Looks good!");
        else
            System.out.println("Something went wrong;");*/




        /*
        User user = new User("Admin", "admin", "root", UserRights.ADMIN,"test");
        userRepository.registerUser(user);*/
    }

}
