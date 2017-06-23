package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */

public class UserViewModel implements Initializable {

    @FXML
    private TreeTableView tblUsers;

    @FXML
    private Button btnNew, btnDelete, btnChange, btnBack;

    public void initialize(URL location, ResourceBundle resources) {

    }
}
