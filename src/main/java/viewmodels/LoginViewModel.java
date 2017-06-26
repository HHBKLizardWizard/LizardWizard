package viewmodels;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User;
import models.UserRights;
import org.mindrot.jbcrypt.BCrypt;
import repositories.IUserRepository;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class LoginViewModel implements Initializable {

    private IUserRepository userRepository;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblLoginFailed;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginAction(){
        lblLoginFailed.setVisible(false);

        String checkUser = txtLogin.getText();

        String checkPw = BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt());

        User user = userRepository.getUserByUsername(checkUser);
        user = new User("root", "root", "root", UserRights.ADMIN,BCrypt.hashpw("root", BCrypt.gensalt()));

        System.out.println(user.getPassword());

        if(user == null || !checkPw.equals(user.getPassword())){
            //user not found error or incorrect password
            lblLoginFailed.setVisible(true);
        }else{
            //open templates window
            showApplication(user.getId());
        }
    }


    private void showApplication(Integer userId){
        try{

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getClassLoader().getResource("user_edit.fxml"));
            loader.load();

            UserEditViewModel userEditViewModel = loader.getController();
            userEditViewModel.setUserId(userId);
            if(userId>0){
                userEditViewModel.setUserData(userId.toString());
            }

            //Open new Window with correct title
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Benutzer bearbeiten");
            stage.setScene(new Scene(p, 600, 400));
            stage.setResizable(false);

            //hide login window
            Stage st = (Stage) btnLogin.getScene().getWindow();
            st.hide();

            //and disable Primary Stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }


}
