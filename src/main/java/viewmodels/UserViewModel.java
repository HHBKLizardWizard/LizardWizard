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

        colName.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        colRights.setCellValueFactory(new PropertyValueFactory<User, UserRights>("rights"));

        tblUsers.getItems().addAll(userList);
    }

    public void deleteUserAction(){
        User selectedUser = (User) tblUsers.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            System.out.println(selectedUser);
            userList.remove(selectedUser);
            tblUsers.getItems().clear();
            tblUsers.getItems().addAll(userList);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Element not found");
            alert.setContentText("There was no element found to modify. " +
                    "Please make sure that you select an item before trying to make changes.");
            alert.showAndWait();
        }
    }

    public void closeButtonAction(){
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    public void addUserAction(){
        try{
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("user_edit.fxml"));

            //Open new Window with correct title
            Stage stage = new Stage();
            stage.setTitle("Benutzer bearbeiten");
            stage.setScene(new Scene(root2, 600, 400));
            stage.setResizable(false);

            //disable Primary Stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); //@todo create appropriate error message for user to contact administrator
        }
    }


}
