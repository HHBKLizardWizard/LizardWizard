package viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import models.UserRights;
import org.mindrot.jbcrypt.BCrypt;
import repositories.IUserRepository;
import repositories.UserRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class UserEditViewModel implements Initializable {

    @FXML
    private TextField txtLastName, txtFirstName, txtUserId, txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ChoiceBox<UserRights> cbRole; //@todo define what type it is.

    @FXML
    private Button btnSave, btnBack;

    @FXML
    private Label lblPwInfo;

    // Class        : initialize
    // Beschreibung : Füllt die ChoiceBox mit alle Rechten Daten.
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<UserRights> rightsList = FXCollections.observableArrayList();
        rightsList.addAll(UserRights.ADMIN, UserRights.LEHRER, UserRights.AZUBI);
        cbRole.setItems(rightsList);
    }

    // Class        : setUserData
    // Beschreibung : würd im UserViewModel benutzt (deswegen public).
    //                Füllt alle felder mit die selected user daten.
    public void setUserData(String txtUserId) {
        IUserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());
        lblPwInfo.setVisible(true);

        //todo: get user by userID
        //User user = userRepository.getUserbyId(Integer.valueOf(txtUserId));
        User user = new User("Sil123","Sil","van Vliet", UserRights.AZUBI, "qwe");

        txtFirstName.setText(user.getFirstname());
        txtLastName.setText(user.getLastname());
        txtUsername.setText(user.getUsername());
        cbRole.getSelectionModel().select(user.getRights());

        //password is never shown. if passwaord needs to be updated then Admin fills this field
        //otherwise he should leave it blank and only user info will be update.
    }


    //needs to be public due to being used in UserViewModel
    // Class        : setUserId
    // Beschreibung : würd im UserViewModel benutzt (deswegen public). Setzt die UserID im ein
    //                hidden feld so das im diese view die (wenn ausgewählt) ausgewählte user
    //                daten anzeigen und aktualiesieren kann.
    public void setUserId(Integer userId) {
        txtUserId.setText(String.valueOf(userId));
    }

    // Class        : saveUserAction
    // Beschreibung : Nach überprufung ob alle Felder richtig gefüllt sind wird überpruft
    //                ob es um ein benutzer aktualiesierung oder ein neue Benutzer anlegen geht
    //                und so entprechend die richtige aktion ausführt
    public void saveUserAction() {
        UserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());

        String msgTitle = "", msgText = "";

        //check if all form fields are good to go
        boolean allGood = checkFieldsAction();

        if(allGood){
            Integer userId = Integer.parseInt(txtUserId.getText());
            UserRights uRight = cbRole.getSelectionModel().getSelectedItem();
            String  lName = txtLastName.getText(),
                    fName = txtFirstName.getText(),
                    userName = txtUsername.getText(),
                    password = txtPassword.getText();

            if(userId > 0){
                //User was found
                User user = userRepository.getUserById(userId);
                user.setFirstname(fName);
                user.setLastname(lName);
                user.setUsername(userName);
                user.setRights(uRight);

                //check if password field was filled, if so, update
                if(!password.equals("")){
                    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                    user.setPassword(hashed);
                }

                //update the User
                userRepository.updateUser(user);

                msgText = "The User was successfully created.";
                msgTitle = "User created";
            }else{
                //No id found => creating new User
                userRepository.registerUser(new User(userName,fName,lName,uRight,password));
            }

            //success message
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(msgTitle);
            alert.setContentText(msgText);
            alert.showAndWait();
        }
    }

    // Class        : checkFieldsAction
    // Beschreibung : Überpruft ob alle Felder gefüllt sind
    // Extra info   : passwort feld bei benutzer aktualiesierung muss nicht
    //                gefüllt sein. Wenn Leer das blebt das passwort wie vorher.
    private boolean checkFieldsAction() {
        Integer userId = Integer.parseInt(txtUserId.getText());
        Boolean allGood = true;
        String emptyFields = "The following Data is missing:";

        if(txtUsername.getText().equals("")){
            allGood = false;
            emptyFields = emptyFields.concat("\nBenutzername");
        }

        if(txtFirstName.getText().equals("")){
            allGood = false;
            emptyFields = emptyFields.concat("\nVorname");
        }

        if(txtLastName.getText().equals("")){
            allGood = false;
            emptyFields = emptyFields.concat("\nName");
        }

        if(cbRole.getSelectionModel().getSelectedIndex() < 1) {
            allGood = false;
            emptyFields = emptyFields.concat("\nRolle");
        }

        if(userId == 0) {
            if(txtPassword.getText().equals("")){
                allGood = false;
                emptyFields = emptyFields.concat("\nPaswort");
            }
        }

        if(!allGood){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Missing Info");
            alert.setContentText(emptyFields);
            alert.showAndWait();
        }

        return allGood;
    }

    // Class        : closeButtonAction
    // Beschreibung : Schließt die User Edit übersicht
    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}
