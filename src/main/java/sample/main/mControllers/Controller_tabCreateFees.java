package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.main.mDatabases.DBFees;
import sample.main.mDatabases.DBSettings;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mPojos.SchoolFees;

import java.time.LocalDate;
import java.util.List;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.isType;
import static sample.main.mUtility.mLocalMethods.studentRange;
import static sample.main.mUtility.mLocalStrings.*;

public class Controller_tabCreateFees implements TabContent {
    @FXML
    private JFXButton btnCloseTab;
    @FXML
    private Label txtShowAddnewStudent;

    @FXML
    private ImageView ImageLoading;

    @FXML
    private HBox mainSceenHolder;

    @FXML
    private AnchorPane containerStdDetails , rootView;

    @FXML
    private VBox formData;

    @FXML
    private TextField txtfeeName, txtfeeAmoount, txtfeesAccountNoumber, txtBankName;
    @FXML
    private TextArea stdDescription;
    @FXML
    private ComboBox<String> txtwhoPays, txtfeesDatePeriod, txtfeesPeriodName;
    @FXML
    private Button btnClear, btnSaveChanges;
    private TabPane tabPane = null;

    private DBSettings dbSettings;
    private DBFees dbFees = null;


    public void initialize () {
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
        txtBankName.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if(!txtBankName.getText().trim().isEmpty()){
                txtBankName.getText().trim().toUpperCase();
            }

        });
        txtfeesAccountNoumber.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if(!txtfeesAccountNoumber.getText().trim().isEmpty()){
                txtfeesAccountNoumber.getText().trim().toUpperCase();
            }

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
                feeAmount = feeAmount.replace(",",".");

                if (! isType(feeAmount, "float") || !isType(feeAmount, "double")) {
                    warnningSimpleOKDialg("Amount Error", "Put Valid numeric Values!\n"+feeAmount);
                } else {
                    mainSceenHolder.setDisable(true);
                    ImageLoading.setVisible(true);
                    feesAccountNumber = feesAccountNumber.toUpperCase();
                    String finalFeesAccountNumber = feesAccountNumber;
                    String finalFeeAmount = feeAmount;
                    Task<String> task = new Task<String>() {
                        @Override
                        protected String call () throws Exception {
                            return new DBFees().saveNewFees(new SchoolFees(
                                    feeName, finalFeeAmount, feebank, whoPays, feesDatePeriod, feesPeriodName
                                    , finalFeesAccountNumber, description, LocalDate.now()+"", false
                            ));
                        }
                    };
                    new Thread(task).start();
                    task.setOnSucceeded(event -> {
                        mainSceenHolder.setDisable(false);
                        ImageLoading.setVisible(false);
                        if(task.getValue().equals("failed")){
                            warnningSimpleOKDialg(DATABASE_ACTION_FAILED,"Failed to save please try again");
                        }
                        if(task.getValue().equals("dbFail")){
                            warnningSimpleOKDialg(DATABASE_ACTION_FAILED,"Failed to save please try again");
                        }
                        if(task.getValue().equals("exists")){
                            warnningSimpleOKDialg(DATABASE_ACTION_FAILED,"Will not Save ","This record already exists.\n Find another Name to use !");
                        }
                        if (task.getValue().equals("success")) {
                            infomationSimpleOKDialg("Information", "Saved Successfully");
                            clearForm();
                        }
                    });
                    task.setOnFailed(evc->{
                        errorSimpleOKDialg("Access Error","Something went wrong!\nTry again (Later)");
                        mainSceenHolder.setDisable(false);
                        ImageLoading.setVisible(false);
                    });
                }
            }
        });
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

    private void initDataRequired () {

        TextFields.bindAutoCompletion(txtfeeName, SUGGESTION_SCHOOL_FEES_TYPES);
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
            errorSimpleOKDialg("Database Connnection Error", "Failed to connect !", "Try again Later");
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

    @Override
    public void setMainWindow (Stage stage) {
    }


    /*
    Close the current Tab
     */
    private void closeTab () {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tabPane.getTabs().remove(tab);
    }

    @Override
    public void setTabPane (TabPane pane) {
        this.tabPane = pane;
    }

}
