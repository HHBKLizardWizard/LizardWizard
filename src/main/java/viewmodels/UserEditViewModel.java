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
    private ComboBox<UserRights> cbRole;

    @FXML
    private Button btnSave, btnBack;

    @FXML
    private Label lblPwInfo;

    /**
     *   Class        : initialize
     *   Beschreibung : Füllt die ChoiceBoxes mit allen Rechten.
     */
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<UserRights> rightsList = FXCollections.observableArrayList();
        rightsList.addAll(UserRights.ADMIN, UserRights.LEHRER, UserRights.AZUBI);
        cbRole.setItems(rightsList);
        cbRole.getSelectionModel().select(0);
    }

    /**
     *   Class        : setUserData
     *   Beschreibung : Wird im UserViewModel benutzt (deswegen public).
     *                  Füllt alle Felder mit den selektierten Benutzerdaten.
     */
    public void setUserData(String txtUserId) {
        IUserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());
        lblPwInfo.setVisible(true);

        //todo: get user by userID
        //User user = userRepository.getUserById(Integer.valueOf(txtUserId));
        User user = new User("Sil123","Sil","van Vliet", "qwe", UserRights.AZUBI);

        txtFirstName.setText(user.getFirstname());
        txtLastName.setText(user.getLastname());
        txtUsername.setText(user.getUsername());

        cbRole.getSelectionModel().select(user.getRights());

        //password is never shown. if password needs to be updated then Admin fills this field
        //otherwise he should leave it blank and only user info will be update.
    }

    /**
     *   Class        : setUserId
     *   Beschreibung : Wird im UserViewModel benutzt (deswegen public). Setzt die UserID in ein
     *                  "hidden" Feld, so dass in dieser View die (wenn ausgewählt) ausgewählten
     *                  Benutzerdaten angezeigt und aktualisiert werden können.
     */
    public void setUserId(Integer userId) {
        txtUserId.setText(String.valueOf(userId));
    }

    /**
     *   Class        : saveUserAction
     *   Beschreibung : Nach der Überprüfung, ob alle Felder richtig gefüllt sind, wird überprüft,
     *                  ob es sich um eine Benutzeraktualisierung oder um das Anlegen eines neuen Benutzers
     *                  handelt und so entprechend die richtige Aktion ausführt.
     */
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

                //making sure the User was really found
                if(user != null){
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
                }
            }else{
                //No id found => creating new User
                userRepository.registerUser(new User(userName,fName,lName,password, uRight));
            }

            //success message
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(msgTitle);
            alert.setContentText(msgText);
            alert.showAndWait();
        }
    }

    /**
     *   Class        : checkFieldsAction
     *   Beschreibung : Überprüft, ob alle Felder gefüllt sind.
     *   Extra info   : Passwortfeld bei der Benutzeraktualisierung muss nicht
     *                  handelt und so entprechend die richtige Aktion ausführt.
     *                  gefüllt sein. Wenn es leer bleibt, blebt das vorherige Passwort
     *                  bestehen.
     */
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

        if(userId == 0) {
            if(txtPassword.getText().equals("")){
                allGood = false;
                emptyFields = emptyFields.concat("\nPasswort");
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

    /**
     *   Class        : closeButtonAction
     *   Beschreibung : Schließt die User Edit Übersicht.
     */
    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}
