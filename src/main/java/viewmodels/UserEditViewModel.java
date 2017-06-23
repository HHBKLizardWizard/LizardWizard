package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class UserEditViewModel implements Initializable {

    @FXML
    private TextField txtLastName, txtFirstName, txtLogin, txtPassword;

    @FXML
    private ChoiceBox cbRole; //@todo define what type it is.

    @FXML
    private Button btnSave, btnDelete, btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
