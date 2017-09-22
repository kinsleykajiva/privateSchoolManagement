package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.main.mDatabases.DBFees;
import sample.main.mDatabases.DBSettings;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mMessages.mPushMessages;
import sample.main.mPojos.SchoolFees;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.isType;
import static sample.main.mUtility.mLocalMethods.studentRange;
import static sample.main.mUtility.mLocalStrings.*;
import static sample.main.mframeWork.StageManager.getStage;

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
    private TextArea stdDescription;
    @FXML
    private Button btnClear, btnSaveChanges;
    @FXML
    private JFXButton btnCloseTab ,btnRefresh;
    @FXML
    private TableView<SchoolFees> tableFees;
    private TabPane tabPane = null;
    private DBFees dbFees = new DBFees();
    private ObservableList<SchoolFees> students_list;
    private List<SchoolFees> feeslist = new ArrayList<>();
    private String[] feeslistEdit = new String[3];
    private ContextMenu tableViewMenu = new ContextMenu();
    @FXML
    private TableColumn<SchoolFees, String> col_name, col_amount, col_whosePaying, col_account, col_bank, col_date_created;
    @FXML
    private TextField txtfeeName, txtfeeAmoount, txtfeesAccountNoumber, txtBankName;

    public void initialize () {
        initDataRequired();
        initClickListeners();
    }

    private void initDataRequired () {
        btnClear.setDisable(true);
        btnSaveChanges.setDisable(true);
        MenuItem deletOption = new MenuItem("  Delete Record   ");
        tableViewMenu.getItems().add(deletOption);
        tableViewMenu.setAutoHide(true);
        deletOption.setOnAction(event -> {
            if(yesNoDialog(DATABASE_ACTION,"Are you sure you want to delete ?","Your about to delete this fee recored","Yes,Delete","No,Cancel",2)){
                Task<String> deleteTask = new Task<String>() {
                    @Override
                    protected String call () throws Exception {
                        return   new DBFees().deletRecord(feeslistEdit[0]);
                    }
                };
                ImageLoading.setVisible(true);
                new Thread(deleteTask).start();
                deleteTask.setOnSucceeded(ev->{
                    ImageLoading.setVisible(false);
                    if(deleteTask.getValue().equals("success")){
                        infomationSimpleOKDialg("Information",  "Deleted Successfully");
                        feeslist.clear();
                        loadTableData();
                    }
                });
            }
        });

        TextFields.bindAutoCompletion(txtfeeName, SUGGESTION_SCHOOL_FEES_TYPES);
        ImageLoading.setVisible(true);
        loadTableData();

        Task<List<String>> dbFeesTask = new Task<List<String>>() {
            @Override
            protected List<String> call () throws Exception {
                DBSettings db = new DBSettings();
                return db.getGradeLevelClass(true);
            }
        };
        new Thread(dbFeesTask).start();

        dbFeesTask.setOnSucceeded(ev -> {
            if (dbFeesTask.getValue().isEmpty()) {
                txtwhoPays.getItems().setAll(SETUP_ERROR);
                txtwhoPays.getSelectionModel().selectFirst();
                errorSimpleOKDialg("SetUp Error", SETTINGS_SETUP, "Go To settings to fix this");
            } else {
                txtwhoPays.getItems().setAll(studentRange(dbFeesTask.getValue()));
                txtwhoPays.getSelectionModel().selectFirst();
                txtfeesDatePeriod.getItems().setAll(FEES_PERIOD);
                txtfeesDatePeriod.getSelectionModel().selectFirst();
                txtfeesPeriodName.getItems().setAll(TERMS);
                txtfeesPeriodName.getSelectionModel().selectFirst();
            }
        });
        dbFeesTask.setOnFailed(ev -> {
            errorSimpleOKDialg("Database Connection Error", "Failed to connect !", "Try again Later");
        });
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
            new mPushMessages().dataBaseMessages("Database", listTask.getValue().size() == 0 ? "No records to show yet" : listTask.getValue().size()==1? listTask.getValue().size()+" Fee record":listTask.getValue().size() +" Fees records",2);
            tableFees.setItems(listTask.getValue());
            ImageLoading.setVisible(ImageLoading.isVisible()?false:false);

        });
        listTask.setOnFailed(event -> {
            new mPushMessages().dataBaseMessages("Database Error", "Error Occurred Whilst Fetching data",1);
            ImageLoading.setVisible(ImageLoading.isVisible()?false:false);
        });
    }

    private void initClickListeners () {

        btnRefresh.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnRefresh.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });
        btnRefresh.setOnAction(ev->{
            ImageLoading.setVisible(false);
            feeslist.clear();
            loadTableData ();
        });
        btnCloseTab.setOnAction(ev -> {
            if (shouldClose()) {
                closeTab();
            }
        });
        btnClear.setOnAction(ev -> {
            clearForm();

        });
        txtfeeName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtfeeAmoount.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtfeesAccountNoumber.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtBankName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        tableFees.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2 && (! feeslist.isEmpty())) {
                SchoolFees fee = tableFees.getItems().get(tableFees.getSelectionModel().getSelectedIndex());
                txtfeeName.setText(fee.getFeeName());
                feeslistEdit[0] = fee.getFeeName();
                txtfeeAmoount.setText(fee.getFeeAmoount());
                txtfeesAccountNoumber.setText(fee.getFeesAccountNoumber());
                txtBankName.setText(fee.getBank());
                txtwhoPays.getSelectionModel().select(fee.getWhoPays());
                txtfeesDatePeriod.getSelectionModel().select(fee.getFeesDatePeriod());
                txtfeesPeriodName.getSelectionModel().select(fee.getFeesPeriodName());
                stdDescription.setText(fee.getFeesDescription());
                feeslistEdit[1] = fee.getDateCreated();
                feeslistEdit[2] = "false";
            }

        });
        tableFees.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY && !feeslist.isEmpty()) {
                SchoolFees fee = tableFees.getItems().get(tableFees.getSelectionModel().getSelectedIndex());
                feeslistEdit[0] = fee.getFeeName();
                feeslistEdit[1] = fee.getDateCreated();
                feeslistEdit[2] = "false";
                tableViewMenu.show(tableFees, event.getScreenX(), event.getScreenY());
            }
        });
        stdDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtfeesDatePeriod.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtfeesPeriodName.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        txtwhoPays.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        btnSaveChanges.setOnAction(ev -> {
            String feeName = txtfeeName.getText().trim();
            String feeAmount = txtfeeAmoount.getText().trim();
            String feesAccountNumber = txtfeesAccountNoumber.getText().trim();
            String feebank = txtBankName.getText().trim().toUpperCase();
            String description = stdDescription.getText().trim();
            String whoPays = txtwhoPays.getValue();
            String feesDatePeriod = txtfeesDatePeriod.getValue();
            String feesPeriodName = txtfeesPeriodName.getValue();

            if (feeName.isEmpty() || feeAmount.isEmpty() || feesAccountNumber.isEmpty() || feebank.isEmpty() || description.isEmpty()
                    || whoPays.equals("Select") ||
                    feesDatePeriod.equals("Select") || feesPeriodName.equals("Select")) {
                warnningSimpleOKDialg("Input Error", "One of the fields needs your input!");
            } else {
                feeAmount = feeAmount.replace(",", ".");
                if (! isType(feeAmount, "float") || ! isType(feeAmount, "double")) {
                    warnningSimpleOKDialg("Amount Error", "Put Valid numeric Values!\n" + feeAmount);
                } else {
                    if (! feeslistEdit[0].equals(feeName)) {
                        if (yesNoDialog("DataBase Action Confirmation", "Are you sure you want to create a new Fee.", "You are about to Replace " + feeslistEdit[0] + " with " + feeName, "Yes,Replace", "No,Cancel", 2)) {
                            feeslistEdit[2] = "true";
                            feesAccountNumber = feesAccountNumber.toUpperCase();
                            String finalFeesAccountNumber = feesAccountNumber;
                            String finalFeeAmount = feeAmount;
                            Task<String> task = new Task<String>() {
                                @Override
                                protected String call () throws Exception {
                                    return new DBFees().updateRecord(feeslistEdit, new SchoolFees(
                                            feeName, finalFeeAmount, feebank, whoPays, feesDatePeriod, feesPeriodName
                                            , finalFeesAccountNumber, description, feeslistEdit[2].equals("true") ? LocalDate.now() + "" : feeslistEdit[1], false
                                    ));
                                }
                            };
                            containerStdDetails.setDisable(true);
                            ImageLoading.setVisible(true);
                            new Thread(task).start();
                            task.setOnSucceeded(event -> {
                                ImageLoading.setVisible(false);
                                containerStdDetails.setDisable(false);
                                if (task.getValue().equals("success")) {
                                    infomationSimpleOKDialg("Information", feeslistEdit[2].equals("true") ? "Replaced Successfully" : "Saved Successfully");
                                    feeslist.clear();
                                    loadTableData ();
                                    clearForm();
                                }
                                if (task.getValue().equals("failed")) {
                                    infomationSimpleOKDialg("Saving Error", "Failed to save .", "Please try again");
                                } if(task.getValue().equals("dbFailure")) {
                                    infomationSimpleOKDialg("Database Error", "Failed to save.", "Please try again");
                                }

                            });
                            task.setOnFailed(efv->{
                                containerStdDetails.setDisable(false);
                                ImageLoading.setVisible(false);
                            });
                        } else {
                            return;
                        }
                    }

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

    private void clearForm () {
        txtfeeName.setText("");
        txtfeeAmoount.setText("");
        txtfeesAccountNoumber.setText("");
        txtBankName.setText("");
        txtwhoPays.getSelectionModel().selectFirst();
        txtfeesDatePeriod.getSelectionModel().selectFirst();
        txtfeesPeriodName.getSelectionModel().selectFirst();
        stdDescription.setText("");
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
