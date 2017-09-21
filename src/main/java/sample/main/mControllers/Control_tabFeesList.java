package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.main.mDatabases.DBFees;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mMessages.mPushMessages;
import sample.main.mPojos.SchoolFees;

import java.util.ArrayList;
import java.util.List;

public class Control_tabFeesList implements TabContent {
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
    private ComboBox<String> txtfeesDatePeriod, txtfeesPeriodName, txtwhoPays;
    @FXML
    private TextField txtfeeName, txtfeeAmoount, txtfeesAccountNoumber, txtBankName;
    @FXML
    private TextArea stdDescription;
    @FXML
    private Button btnClear, btnSaveChanges;
    @FXML
    private JFXButton btnCloseTab;
    @FXML
    private TableView<SchoolFees> tableFees;
    private TabPane tabPane = null;
    private DBFees dbFees = new DBFees();
    private ObservableList<SchoolFees> students_list;
    private List<SchoolFees> feeslist = new ArrayList<>();
    @FXML
    private TableColumn<SchoolFees, String> col_name, col_amount, col_whosePaying, col_account, col_bank, col_date_created;

    public void initialize () {
        initDataRequired();
        initClickListeners();
    }

    private void initDataRequired () {
        loadTableData();
    }

    private void loadTableData () {
        col_name.setCellValueFactory(new PropertyValueFactory<>("feeName"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("feeAmoount"));
        col_whosePaying.setCellValueFactory(new PropertyValueFactory<>("whoPays"));
        col_account.setCellValueFactory(new PropertyValueFactory<>("feesAccountNoumber"));
        col_bank.setCellValueFactory(new PropertyValueFactory<>("bank"));
        col_date_created.setCellValueFactory(new PropertyValueFactory<>("DateCreated"));
        Task<ObservableList<SchoolFees>> listTask = new Task<ObservableList<SchoolFees>>() {
            @Override
            protected ObservableList<SchoolFees> call () throws Exception {
                feeslist.addAll(dbFees.getFeesLists());
                return FXCollections.observableArrayList(feeslist);
            }

        };
        new Thread(listTask).start();
        listTask.setOnSucceeded(event -> {

                new mPushMessages ().generalMessages("Database",listTask.getValue().size()==0?"No recoreds to show yet":listTask.getValue().size()+" Fees records");

            tableFees.setItems(listTask.getValue());

        });
    }

    private void initClickListeners () {
        btnCloseTab.setOnAction(ev -> {
            if (shouldClose()) {
                closeTab();
            }
        });
        tableFees.setOnMouseClicked(event -> {
            if(event.isPrimaryButtonDown()){
                if(!feeslist.isEmpty()){
                SchoolFees fee =    tableFees.getItems().get(tableFees.getSelectionModel().getSelectedIndex());
                    txtfeeName.setText(fee.getFeeName());
                }
            }


        });
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
    /*
    Close the current Tab
     */
    private void closeTab () {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tabPane.getTabs().remove(tab);
    }

    @Override
    public void setMainWindow (Stage stage) {

    }

    @Override
    public void setTabPane (TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
