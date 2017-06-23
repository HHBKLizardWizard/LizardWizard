package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class UserEditViewModel implements Initializable {

    @FXML
    private TextField txtLastName, txtFirstName, txtLogin,
                      txtUserId, txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ChoiceBox cbRole; //@todo define what type it is.

    @FXML
    private Button btnSave, btnDelete, btnBack;

    //@Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Integer createUser() {

        Integer userId = Integer.parseInt(txtUserId.getText());
        String  lName = txtLastName.getText(),
                fName = txtFirstName.getText(),
                uName = txtUsername.getText();
        //todo gett right, missing right module
        String uRights = "blabla";
        String password = txtPassword.getText();

        if(!userId.equals("") && userId > 0){
            //todo update the existing user (only update password
            //todo if filled otherwise leave password as it was)
        }else{
            //todo create new User
            //User user = new User(uName, fName, lName, uRights, password); //todo add Right (right module missing??

        }


        return 0;

    }
}
