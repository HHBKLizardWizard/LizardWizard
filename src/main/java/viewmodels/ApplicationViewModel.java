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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Template;
import util.AnnualReport;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by iho on 20.06.2017.
 */
public class ApplicationViewModel implements Initializable {

    @FXML
    private ChoiceBox cbSector; //@todo define what type it is.

    @FXML
    private ChoiceBox<Integer> cbYear;

    @FXML
    private ChoiceBox<Template> cbTemplate;

    @FXML
    private Button btnExport;

    @FXML
    private MenuItem menuReport, menuUser, menuTemplate;

    @FXML Menu menuLogout, menuExit;


    public void exportPdf() {
        AnnualReport pdf = new AnnualReport();
        try {
            pdf.createDocument(pdf.createPdf("test.pdf"), true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
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
            FXMLLoader fxmlLoader;

            //check what button was pressed to be able to determine what fxml file to open
            if (eventSource == menuReport) {
                fxmlLoader = new FXMLLoader(getClass().getResource("application.fxml"));
            } else if (eventSource == menuTemplate) {
                fxmlLoader = new FXMLLoader(getClass().getResource("templates.fxml"));
            } else if (eventSource == menuUser) {
                fxmlLoader = new FXMLLoader(getClass().getResource("users.fxml"));
            }else{
                throw new Exception(); //@todo create own exception? is it worth it?
            }

            //open scene with chosen fxml
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }
}
