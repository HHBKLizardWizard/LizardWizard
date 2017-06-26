package viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User;
import models.UserRights;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */

public class UserViewModel implements Initializable {

    @FXML
    private TableView tblUsers;

    @FXML
    private TableColumn colName, colFirstName, colRights;

    @FXML
    private Button btnNew, btnDelete, btnChange, btnBack;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        userList.add(new User("Sil123","Sil","van Vliet", UserRights.ADMIN, "qwe"));
        userList.add(new User("Ingo123","Ingo","Hotischeck", UserRights.ADMIN, "qwe"));

        userList.get(0).setId(1);
        userList.get(1).setId(2);


        //fill table
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        colRights.setCellValueFactory(new PropertyValueFactory<User, UserRights>("rights"));

        tblUsers.getItems().addAll(userList);
    }

    public void deleteUserAction(){
        User selectedUser = getSelectedUser();

        if(selectedUser != null){
            userList.remove(selectedUser);
            tblUsers.getItems().clear();
            tblUsers.getItems().addAll(userList);
        }
    }

    public User getSelectedUser(){
        User selectedUser = (User) tblUsers.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            return selectedUser;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Element not found");
            alert.setContentText("There was no element found to modify. " +
                    "Please make sure that you select an item before trying to make changes.");
            alert.showAndWait();
            return null;
        }
    }

    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    public void createUserAction(){
        creatUpdateUser(0);
    }

    public void updateUserAction(){
        User selectedUser = getSelectedUser();
        if(selectedUser != null){
            creatUpdateUser(selectedUser.getId());
        }
    }

    private void creatUpdateUser(Integer userId){
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

            //disable Primary Stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }
}
