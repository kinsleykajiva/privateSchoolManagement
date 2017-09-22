package sample.main.mControllers;

import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.main.mInterfaceCallbacks.TabContent;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class Controller_tabFeesPayment implements TabContent {
    @FXML
    private Label txtShowAddnewStudent;
    @FXML
    private ImageView ImageLoading;
    @FXML
    private HBox mainSceenHolder;
    @FXML
    private AnchorPane containerStdDetails;
    @FXML
    private VBox formData;
    @FXML
    private TextField txtfeeRegNumber ,txtfeeAmoount,txtPaidBy,txtBankName;
    @FXML
    private ComboBox<?> txtfeesDatePeriod ,txtfeesPeriodName,txtfeesAccountNoumber;
    @FXML
    private Button btnClear ,btnSaveChanges;
    @FXML
    private JFXButton btnCloseTab;
    private TabPane tabPane = null;

    public void initialize(){
        initDataRequired();
        initClickListeners();

    }

    private void initClickListeners () {
        btnCloseTab.setOnAction(ev -> {
            if (shouldClose()) {
                closeTab();
            }
        });
        btnClear.setOnAction(ev -> {
            clearForm();

        });
    }

    private void clearForm () {

    }

    private void initDataRequired (){

    }

    @Override
    public boolean shouldClose () {
        return true;
    }

    @Override
    public void putFocusOnNode () {

    }

    @Override
    public boolean loadData () {
        return false;
    }

    @Override
    public void setMainWindow (Stage stage) {

    }

    /*
    Close the current Tab
     */
    private void closeTab () {
        javafx.scene.control.Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tabPane.getTabs().remove(tab);
    }

    @Override
    public void setTabPane (TabPane pane) {
        this.tabPane = pane;
    }
}
