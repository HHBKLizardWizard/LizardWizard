package viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import models.UserRights;
import repositories.UserRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class UserEditViewModel implements Initializable {

    @FXML
    private TextField txtLastName, txtFirstName, txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<UserRights> cbRole;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblPwInfo;

    private User updateUser;
    private Integer maxTxtLength = 40;

    /**
     *   Class        : initialize
     *   Beschreibung : Füllt die ChoiceBoxes mit allen Rechten.
     */
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<UserRights> rightsList = FXCollections.observableArrayList();
        rightsList.addAll(UserRights.ADMIN, UserRights.LEHRER, UserRights.AZUBI);
        cbRole.setItems(rightsList);
        cbRole.getSelectionModel().select(0);

        //max größe für text felder
        addListener(txtUsername);
        addListener(txtFirstName);
        addListener(txtLastName);
        addListener(txtPassword);
    }

    /**
     *   Class        : setUserData
     *   Beschreibung : Wird im UserViewModel benutzt (deswegen public).
     *                  Füllt alle Felder mit den selektierten Benutzerdaten.
     */
    private void setUserData(User user) {

        //setze hinweis wegen password wenn man ein Benutzer aktualiesieren will
        lblPwInfo.setVisible(true);

        txtFirstName.setText(user.getFirstname());
        txtLastName.setText(user.getLastname());
        txtUsername.setText(user.getUsername());
        cbRole.getSelectionModel().select(user.getRights());

        //password is never shown. if password needs to be updated then Admin fills this field
        //otherwise he should leave it blank and only user info will be update.
    }

    /**
     *   Class        : saveUserAction
     *   Beschreibung : Nach der Überprüfung, ob alle Felder richtig gefüllt sind, wird überprüft,
     *                  ob es sich um eine Benutzeraktualisierung oder um das Anlegen eines neuen Benutzers
     *                  handelt und so entprechend die richtige Aktion ausführt.
     */
    public void saveUserAction() {
        UserRepository userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());
        String msgTitle, msgText;
        Alert.AlertType alerType = Alert.AlertType.INFORMATION;

        //check if all form fields are good to go
        boolean allGood = checkFieldsAction();

        if(allGood){
            UserRights uRight = cbRole.getSelectionModel().getSelectedItem();
            String  lName = txtLastName.getText(),
                    fName = txtFirstName.getText(),
                    userName = txtUsername.getText(),
                    password = txtPassword.getText();

            if(updateUser != null){
                updateUser.setFirstname(fName);
                updateUser.setLastname(lName);
                updateUser.setUsername(userName);
                updateUser.setRights(uRight);

                //check if password field was filled, if so, update
                if(!password.equals("")){
                    updateUser.setPassword(password);
                }

                //update the User
                userRepository.updateUser(updateUser);

                msgTitle = "User updated";
                msgText = "The User was successfully updated.";
            }else{
                //No id found => creating new User
                User user = userRepository.registerUser(new User(userName,fName,lName,password, uRight));

                if(user != null){
                    msgTitle = "User created";
                    msgText = "The User was successfully created.";
                }else{
                    alerType = Alert.AlertType.ERROR;
                    msgTitle = "An error has occured";
                    msgText = "An error has occured while creating the User in the Database, please contact" +
                            "your administrator to solve the issue (issue: error with Insert statement";
                }
            }

            //Feedback Nachricht
            Alert alert = new Alert(alerType);
            alert.setTitle("Success");
            alert.setHeaderText(msgTitle);
            alert.setContentText(msgText);
            alert.showAndWait();

            //autom
            if(alerType.equals(Alert.AlertType.CONFIRMATION)) {
                closeButtonAction();
            }
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

        //ist kein muss wenn benutzer schon existiert
        if(updateUser == null) {
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

        try{
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getClassLoader().getResource("users.fxml"));
            loader.load();

            //Open new Window with correct title
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Benutzer");
            stage.setScene(new Scene(p, 600, 400));
            stage.setResizable(false);
            stage.sizeToScene();

            //close login window and show new Stage
            Stage st = (Stage) btnBack.getScene().getWindow();
            st.close();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }

    /**
     *   Class        : setUserId
     *   Beschreibung : Setzt UserId so, dass die richtige Templates aus dem Datenbank gehölt werden kann
     */
    public void setUser(User user) {
        updateUser = user;
        if (updateUser != null){
            setUserData(user);
        }
    }

    private void addListener(TextField txtField){
        txtField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (txtField.getText().length() > maxTxtLength) {
                String s = txtField.getText().substring(0, maxTxtLength);
                txtField.setText(s);
            }
        });
    }

}
