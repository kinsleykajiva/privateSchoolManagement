package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_addStudent implements Initializable {
    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSurname;

    @FXML
    private JFXRadioButton male;

    @FXML
    private JFXRadioButton female;

    @FXML
    private JFXTextField txtDob;

    @FXML
    private ComboBox<String> grade_level;

    @FXML
    private ComboBox<String> class_name;

    @FXML
    private JFXTextField txttown_city;

    @FXML
    private JFXTextField txtcountry;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        iniResources();
        initClickListiners();
    }
    private void initRequiredData () {
        class_name.getItems().setAll("Apple", "Orange", "Pear");
    }

    private void initClickListiners () {
        btnSave.setOnAction(event -> System.out.print("ckic"));
    }

    private void iniResources () {

    }
}
