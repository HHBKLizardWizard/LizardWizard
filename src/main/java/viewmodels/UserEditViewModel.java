package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import models.User;
import models.UserRights;
import repositories.IUserRepository;
import repositories.UserRepository;

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
    private ChoiceBox<UserRights> cbRole; //@todo define what type it is.

    @FXML
    private Button btnSave, btnDelete, btnBack;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public Integer createOrUpdateUser() {

        Integer userId = Integer.parseInt(txtUserId.getText());
        UserRights uRight = cbRole.getSelectionModel().getSelectedItem();
        String  lName = txtLastName.getText(),
                fName = txtFirstName.getText(),
                userName = txtUsername.getText(),
                uPass = txtPassword.getText(),
                password = txtPassword.getText();

        if(!userId.equals("") && userId > 0){
            //User was found, check if password field is filled,
            // case it isn't don't update password
            if(!password.equals("")){
                //password field is filled so update it
                IUserRepository userRepository = new UserRepository();
                User user = userRepository.getUserbyId(userId);

                user.setFirstname(fName);
                user.setLastname(lName);
                user.setPassword("blabla");
                user.setRights(uRight);

                userRepository.updateUser(user);
            }else{
                //password field is not filled so only update User info

            }
        }else{
            //No id found => creating new User
            UserRepository userRepository = new UserRepository();
            userRepository.registerUser(new User(userName,fName,lName,uRight,password));
        }


        return 0;

    }
}
