package viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private ComboBox cbTemplate;

    @FXML
    private ComboBox cbGroup;

    @FXML
    private TextField txtTempName;

    @FXML
    private Button btnNewTemplate, btnDelete, btnSave, btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Class        :
    // Beschreibung :

}
