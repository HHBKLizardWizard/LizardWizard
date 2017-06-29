package viewmodels;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import repositories.IUserRepository;
import repositories.UserRepository;
import util.DatabaseConnector;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by : Sil van Vliet
 * Date       : 22.06.2017
 *
 * Model where all the functions are for the login view.
 * Here the user needs to login to be able to view any other screen.
 */
public class LoginViewModel implements Initializable {

    private IUserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblLoginFailed;

    /**
     * @param location used to resolve relative paths for the root object (null if the location is not known).
     * @param resources used to localize the root object (null if the root object was not localized).
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Check user login
     */
    public void loginAction(){
        lblLoginFailed.setVisible(false);

        if(checkLoginFields()) {
            String checkUser = txtLogin.getText();

            User user = userRepository.getUserByUsername(checkUser);
            if (user == null || !BCrypt.checkpw(txtPassword.getText(), user.getPassword())) {
                //user not found error or incorrect password
                lblLoginFailed.setVisible(true);
            } else {
                //open templates window
                showApplication(user);
            }
        }
    }

    /**
     * Check, if the login and password field are empty
     * @return true or false depending if all fields are filled correctly
     */
    private boolean checkLoginFields() {
        if(!txtLogin.getText().equals("") && (txtPassword != null && !txtPassword.getText().equals(""))){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Leeres Feld");
            alert.setContentText("Login- oder Passwortfeld ist nicht gefüllt. Bitte beide Felder ausfüllen!");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Close login view and open application view
     * @param user
     */
    private void showApplication(User user) {
        try{
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getClassLoader().getResource("application.fxml"));
            loader.load();

            ApplicationViewModel applicationViewModel = loader.getController();
            applicationViewModel.setUser(user);

            //Open new Window with correct title
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Benutzer bearbeiten");
            stage.setScene(new Scene(p, 600, 400));
            stage.sizeToScene();
            stage.setResizable(false);

            //close login window and show new Stage
            Stage st = (Stage) btnLogin.getScene().getWindow();
            st.close();
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unbekannter Fehler");
            alert.setHeaderText("Bitte kontaktieren Sie Ihren Administrator mit folgender Nachricht");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }
    }
}