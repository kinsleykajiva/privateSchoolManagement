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

import java.util.List;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.studentRange;
import static sample.main.mUtility.mLocalStrings.SETUP_ERROR;
import static sample.main.mUtility.mLocalStrings.SUGGETION_SCHOOL_FEES_TYPES;

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
    private AnchorPane containerStdDetails;

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
    private DBFees dbFees=null;





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

        btnSaveChanges.setOnAction(ev -> {
            String feeName = txtfeeName.getText().trim();
            String feeAmount = txtfeeAmoount.getText().trim();
            String feesAccountNumber = txtfeesAccountNoumber.getText().trim();
            String BsnkNam = txtBankName.getText().trim();
            String description = stdDescription.getText().trim();
            String whoPays = txtwhoPays.getValue();
            String feesDatePeriod = txtfeesDatePeriod.getValue();
            String feesPeriodName = txtfeesPeriodName.getValue();

            if(feeName.isEmpty()||feeAmount.isEmpty()||feesAccountNumber.isEmpty()||BsnkNam.isEmpty()||description.isEmpty()
                    ||whoPays.equals("Select")||
                    feesDatePeriod.equals("Select")||feesPeriodName.equals("Select")){
                warnningSimpleOKDialg("Input Error","One of the fields needs your input!");
            }else{
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call () throws Exception {
                        return null;
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event -> {
                    if(task.getValue()){
                        infomationSimpleOKDialg("Infomation","Saved Successfully");
                    }else{
                        infomationSimpleOKDialg("Processing Error","Failed to save.","Please try again");
                    }
                });
            }
        });
    }

    private void initDataRequired () {

        TextFields.bindAutoCompletion(txtfeeName,SUGGETION_SCHOOL_FEES_TYPES);
        Task<List<String>> dbFeesTask = new Task<List<String>>() {
            @Override
            protected List<String> call () throws Exception {
                DBSettings db=new DBSettings();
                return db.getGradeLevelClass(true);
            }
        };
        new Thread(dbFeesTask).start();
        dbFeesTask.setOnSucceeded(ev->{

            if(dbFeesTask.getValue().isEmpty()){
                txtwhoPays.getItems().setAll(SETUP_ERROR);

                txtwhoPays.getSelectionModel().selectFirst();
                errorSimpleOKDialg("SetUp Error","It Seems like you had not configured the system Correctly !","Go To settings to fix this");
            }else{
                txtwhoPays.getItems().setAll(studentRange(dbFeesTask.getValue()));

                txtwhoPays.getSelectionModel().selectFirst();
            }

        });
        dbFeesTask.setOnFailed(ev->{
            errorSimpleOKDialg("Database Connnection Error","Failed to connect !","Try again Later");
        });



        String Whopayes ="" ;
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
