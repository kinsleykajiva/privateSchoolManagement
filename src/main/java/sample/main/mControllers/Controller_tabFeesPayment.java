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
import sample.main.mDatabases.DBFeePayment;
import sample.main.mDatabases.DBFees;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mPojos.FeesPayment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static sample.main.mMessages.mDialogs.warnningSimpleOKDialg;
import static sample.main.mUtility.mLocalMethods.localTransactionIDMaker;

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
    private TextField txtfeeRegNumber, txtfeeAmoount, txtPaidBy, txtfeesAccountNoumber, txtBankName;

    @FXML
    private ComboBox<String> txtfeesPeriodName, txtpaymentMethod;
    @FXML
    private Button btnClear, btnSaveChanges;

    @FXML
    private JFXButton btnCloseTab, btnFindStudent;
    private TabPane tabPane = null;
    private String[] studentEdits = {"status", "", "", "", "", "", "", "", ""};

    public void initialize () {
        initDataRequired();
        initClickListeners();

    }

    private void initClickListeners () {
        btnFindStudent.setOnAction(ev -> {
            String regnumber = txtfeeRegNumber.getText().trim();
            if (regnumber.isEmpty()) {

                warnningSimpleOKDialg("Warning !", "Cant Find Student If There Is No Registration Number!");
            } else {
                Task<List<String>> findstudentTask = new Task<List<String>>() {
                    @Override
                    protected List<String> call () throws Exception {
                        DBFeePayment ob = new DBFeePayment();
                        List<String> details = ob.getStudentDetails(regnumber);
                        DBFees dbFees = new DBFees();
                        Map<String, String> feeStatus= dbFees.getStudentFeesExpected(details.get(3));
                        feeStatus.forEach((k,v)->{
                            System.out.print(v);
                        });
                        //ob.getStudentFeeStatus(regnumber, details.get(3), details.get(4));
                            return details;


                    }
                };
                new Thread(findstudentTask).start();
                findstudentTask.setOnSucceeded(fr -> {
                    //findstudentTask
                    System.out.println(findstudentTask.getValue());
                    //new mPushMessages().dataBaseMessages("rsponse", findstudentTask.getValue(), 1);
                });
                findstudentTask.setOnFailed(rf -> {
                    System.out.println(findstudentTask.getValue() + "eerrrrr");
                });
            }

        });
        btnSaveChanges.setOnAction(ev -> {
            String regnumber = txtfeeRegNumber.getText().trim();
            String paidAmount = txtfeeAmoount.getText().trim();
            String bank = txtBankName.getText().trim().toUpperCase();
            String bankAccount = txtfeesAccountNoumber.getText().trim().toUpperCase();
            String paidby = txtPaidBy.getText().trim().toUpperCase();
            String termPaid = txtfeesPeriodName.getValue();
            String paymentMethod = txtpaymentMethod.getValue();
            if (regnumber.isEmpty()) {
                warnningSimpleOKDialg("Warning !", "Cant Find Student If There Is No Registration Number!");
            } else {
                if (regnumber.length() < 5) {
                    warnningSimpleOKDialg("Warning !", "Invalid Registration Number!");
                } else {
                    ImageLoading.setVisible(true);
                    Task<String> payementTask = new Task<String>() {
                        @Override
                        protected String call () throws Exception {
                            return new DBFeePayment().payStudentFees(new FeesPayment(0, regnumber, paymentMethod, localTransactionIDMaker(),
                                    paidAmount, LocalDate.now() + "", bank, bankAccount, paidby, studentEdits[2], termPaid, 0));
                        }
                    };
                    new Thread(payementTask).start();
                    payementTask.setOnSucceeded(event -> {
                        ImageLoading.setVisible(false);
                    });
                    payementTask.setOnFailed(event -> {
                        ImageLoading.setVisible(false);
                    });
                }
            }
        });
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

    private void initDataRequired () {

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
