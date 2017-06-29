package viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.User;
import models.UserRights;
import repositories.IUserRepository;
import repositories.UserRepository;
import util.DatabaseConnector;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by : Sil van Vliet
 * Date       : 22.06.2017
 *
 * Model where all the functions are for the user view.
 * Here the admin can view all the users and perform action
 * such as add, update or delete users.
 */

public class UserViewModel implements Initializable {

    @FXML
    private TableView tblUsers;

    @FXML
    private TableColumn colName, colFirstName, colRights;

    @FXML
    private Button btnBack;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private IUserRepository userRepository;

    /**
     * fills the user table with all the existing users.
     * @param location used to resolve relative paths for the root object (null if the location is not known).
     * @param resources used to localize the root object (null if the root object was not localized).
     */
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        userRepository = new UserRepository(new DatabaseConnector().getUserDataSource());
        userList.addAll(userRepository.getAllUsers());

        //fill table
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        colRights.setCellValueFactory(new PropertyValueFactory<User, UserRights>("rights"));

        tblUsers.getItems().addAll(userList);
    }

    /**
     * deletes the selected user after confirmation
     */
    public void deleteUserAction(){
        User selectedUser = getSelectedUser();
        if(selectedUser != null){
            if(selectedUser.getId() != 1){
                //Confirmation if the User should really be deleted.
                ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Sind Sie sicher das Sie den/die Benutzer/in " + selectedUser.getFirstname() + " "
                        + selectedUser.getLastname() + " löschen wollen?",
                        yes,
                        no);
                alert.setTitle("Benutzer löschen");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get().getButtonData().isDefaultButton()){
                    userRepository.deleteUser(selectedUser);
                    userList.remove(selectedUser);
                    tblUsers.getItems().remove(tblUsers.getSelectionModel().getSelectedItem());
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Achtung!");
                alert.setHeaderText("Administrator kann nicht gelöscht werden");
                alert.setContentText("Es ist keine gute Idee, den Administrator zu löschen! Wer soll denn dann" +
                                        "die Administration übernehmen?");
                alert.showAndWait();
            }
        }
    }

    /**
     * gets the selected user element from the table. When do element was selected
     * an error message pop up
     * @return user that was selected
     */
    private User getSelectedUser(){
        User selectedUser = (User) tblUsers.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            return selectedUser;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Benutzer nicht gefunden");
            alert.setContentText("Kein Benutzer zum Ändern gefunden. " +
                    "Bitte achten Sie darauf, dass Sie einen Benutzer ausgewählt haben, bevor Sie Änderungen vornehmen.");
            alert.showAndWait();
            return null;
        }
    }

    /**
     * Close the current stage
     */
    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    /**
     * launches createUpdateUser with null and so creating a new user
     */
    public void createUserAction(){
        creatUpdateUser(null);
    }

    /**
     * checks if there is a selected user or not. if there is it launches
     * createUpdateUser with the selected user and so updating ita new user
     */
    public void updateUserAction(){
        User selectedUser = getSelectedUser();
        if(selectedUser != null){
            creatUpdateUser(selectedUser);
        }
    }

    /**
     * Opens user edit view with the selected user data. When no user selected
     * it leaves everything blank so that a new user can be created.
     * @param user to check if new user is to be created or update existing one
     */
    private void creatUpdateUser(User user){
        try{

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getClassLoader().getResource("user_edit.fxml"));
            loader.load();

            UserEditViewModel userEditViewModel = loader.getController();
            userEditViewModel.setUser(user);

            //Open new Window with correct title
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Benutzer bearbeiten");
            stage.setScene(new Scene(p, 600, 400));
            stage.setResizable(false);
            stage.sizeToScene();

            //close login window and show new Stage
            Stage st = (Stage) btnBack.getScene().getWindow();
            st.close();
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unbekannter Fehler");
            alert.setHeaderText("Bitte kontaktieren Sie Ihren Administrator mit folgender Nachricht");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
