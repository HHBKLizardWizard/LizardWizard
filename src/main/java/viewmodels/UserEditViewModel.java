package viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
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
    private Button btnSave, btnBack;

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<UserRights> rightsList = FXCollections.observableArrayList();
        rightsList.add(UserRights.ADMIN);
        rightsList.add(UserRights.LEHRER);
        rightsList.add(UserRights.ADMIN);
        cbRole.setItems(rightsList);

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

    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}
