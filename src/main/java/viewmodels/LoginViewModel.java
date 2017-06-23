package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class LoginViewModel implements Initializable {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblWrongPass;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginAction(){
        lblWrongPass.setVisible(false);

        String checkUser = txtLogin.getText();
        String checkPw = txtPassword.getText();
        if(checkUser.equals("username") && checkPw.equals("password")){ //todo verschlüsselte überprüfung

        //todo perform login

        }
        else{
            lblWrongPass.setVisible(true);
        }
    }
}
