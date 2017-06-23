package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by svv on 22.06.2017.
 */
public class TemplatesViewModel implements Initializable {

    @FXML
    private CheckBox chk_zenario, chk_kompetenzen, chk_material, chk_technik,
                     chk_produkt, chk_inhalte, chk_hinweis, chk_leistung;

    @FXML
    private ChoiceBox cbTemplate;

    @FXML
    private ChoiceBox cbGroup;

    @FXML
    private TextField txtTempName;

    @FXML
    private Button btnNewTemplate, btnDelete, btnSave, btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
