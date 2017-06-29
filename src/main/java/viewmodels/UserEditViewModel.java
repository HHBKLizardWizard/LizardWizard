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
 * Created by : Sil van Vliet
 * Date       : 22.06.2017
 *
 * Model where all the functions are for the user edit view.
 * Here the admin can add or update a user.
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
     * fills the dropdown with all the available rights and adds
     * listeners to the text fields.
     * @param location used to resolve relative paths for the root object (null if the location is not known).
     * @param resources used to localize the root object (null if the root object was not localized).

     */
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<UserRights> rightsList = FXCollections.observableArrayList();
        rightsList.addAll(UserRights.ADMIN, UserRights.LEHRER, UserRights.AZUBI);
        cbRole.setItems(rightsList);
        cbRole.getSelectionModel().select(0);

        //max size for text fields
        addListener(txtUsername);
        addListener(txtFirstName);
        addListener(txtLastName);
        addListener(txtPassword);
    }

    /**
     * fills the text fields / dropdown can be filled with the users data.
     * @param user that has been previously selected
     */
    private void setUserData(User user) {

        //set a hint for password, if you want to update a user
        lblPwInfo.setVisible(true);

        txtFirstName.setText(user.getFirstname());
        txtLastName.setText(user.getLastname());
        txtUsername.setText(user.getUsername());
        cbRole.getSelectionModel().select(user.getRights());

        //password is never shown. if password needs to be updated then Admin fills this field
        //otherwise he should leave it blank and only user info will be update.
    }

    /**
     * After verifying that all the fields are correctly set and checking if
     * it is in regards to creating a new user or updating an already existing
     * one, the appropriate action will be executed
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

                msgTitle = "Benutzer aktualisiert";
                msgText = "Benutzer wurde erfolgreich aktualisiert.";
            }else{
                //No id found => creating new User
                User user = userRepository.registerUser(new User(userName,fName,lName,password, uRight));

                if(user != null){
                    msgTitle = "Benutzer erstellt";
                    msgText = "Benutzer wurde erfolgreich angelegt.";
                }else{
                    alerType = Alert.AlertType.ERROR;
                    msgTitle = "Ein Fehler ist aufgetreten";
                    msgText = "Ein Fehler ist während der Benutzererstellung aufgetreten. Die Daten konnten nicht in die Datenbank geschrieben werden!" +
                            "Bitte kontaktieren sie Ihren Administrator, um das Problem zu beheben!" +
                            "(Fehler: Fehler im Insert statement)";
                }
            }

            //Feedback message
            Alert alert = new Alert(alerType);
            alert.setTitle("Erfolg");
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
     * checks if all the requirements have been met. Password field does not have
     * to be filled when it is in regards to updating a already existing user. When
     * it is not filled it will remain as it was previously.
     */
    private boolean checkFieldsAction() {
        Boolean allGood = true;
        String emptyFields = "Folgende Informationen fehlen:";

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
            alert.setHeaderText("Fehlende Informationen");
            alert.setContentText(emptyFields);
            alert.showAndWait();
        }
        return allGood;
    }

    /**
     * closes the current stage and reopens the user view so that the table can be
     * repopulated with the updated information
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
     * Called from UserViewModel, puts the selected user object in this view so that
     * the text fields / dropdown can be filled with the users data.
     * @param user that is selected
     */
    public void setUser(User user) {
        updateUser = user;
        if (updateUser != null){
            setUserData(user);
        }
    }

    /**
     * Listener to detect that the User does not fill in more then is necessary.
     * @param txtField where the user is currently writing.
     */
    private void addListener(TextField txtField){
        txtField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (txtField.getText().length() > maxTxtLength) {
                String s = txtField.getText().substring(0, maxTxtLength);
                txtField.setText(s);
            }
        });
    }

}
