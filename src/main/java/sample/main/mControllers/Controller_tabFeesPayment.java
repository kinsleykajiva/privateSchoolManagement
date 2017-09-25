package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.main.mDatabases.DBFeePayment;
import sample.main.mDatabases.DBFees;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mPojos.FeesPayment;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.isType;
import static sample.main.mUtility.mLocalMethods.localTransactionIDMaker;
import static sample.main.mUtility.mLocalStrings.PAYMENT_METHOD;
import static sample.main.mUtility.mLocalStrings.TERMS;
import static sample.main.mframeWork.StageManager.getStage;

public class Controller_tabFeesPayment implements TabContent {

    @FXML
    private Label txtShowAddnewStudent, lblFoundStatus, lblBalance;
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
    private boolean wasStudentFound = false;

    public void initialize () {
        initDataRequired();
        initClickListeners();

    }

    private void initClickListeners () {
        btnFindStudent.setOnAction(ev -> {
            String regnumber = txtfeeRegNumber.getText().trim().replace(" ", "");
            if (regnumber.isEmpty()) {

                warnningSimpleOKDialg("Warning !", "Cant Find Student If There Is No Registration Number!");
            } else {
                ImageLoading.setVisible(true);
                Task<Map<String, String>> findstudentTask = new Task<Map<String, String>>() {
                    @Override
                    protected Map<String, String> call () throws Exception {
                        DBFeePayment ob = new DBFeePayment();
                        List<String> details = ob.getStudentDetails(regnumber);
                        if (details.isEmpty()) {
                            return Collections.emptyMap();
                        }
                        studentEdits[3] = details.get(0) + " " + details.get(1);
                        DBFees dbFees = new DBFees();
                        Map<String, String> feeStatus = dbFees.getStudentFeesExpected(details.get(3));

                        return feeStatus;
                    }
                };
                new Thread(findstudentTask).start();
                findstudentTask.setOnSucceeded(fr -> {
                    //findstudentTask
                    ImageLoading.setVisible(false);
                    if (! findstudentTask.getValue().isEmpty()) {
                        studentEdits[1] = findstudentTask.getValue().get("grade");
                        studentEdits[2] = findstudentTask.getValue().get("balance");
                        lblFoundStatus.setTextFill(Color.BLUE);
                        wasStudentFound = true;
                        lblBalance.setText(
                                "$ " + findstudentTask.getValue().get("balance"));
                        lblFoundStatus.setText(
                                studentEdits[3] + " \nGrade " + findstudentTask.getValue().get("grade"));
                    } else {
                        warnningSimpleOKDialg("Warning !", "Student not found with this Registration Number!",
                                "Make sure that this student is  Registered in this system.");
                    }
                });
                findstudentTask.setOnFailed(rf -> {
                    ImageLoading.setVisible(false);
                    lblFoundStatus.setText("Error occurred .Try again!");
                    lblFoundStatus.setTextFill(Color.RED);
                    // System.out.println(findstudentTask.getValue() + "eerrrrr");
                });
            }

        });
        btnSaveChanges.setOnAction(ev -> {
            if (wasStudentFound) {
                String regnumber = txtfeeRegNumber.getText().trim().replace(" ", "");
                String paidAmount = txtfeeAmoount.getText().trim().replace(" ", "");
                String bank = txtBankName.getText().trim().toUpperCase();
                String bankAccount = txtfeesAccountNoumber.getText().trim().replace(" ", "").toUpperCase();
                String paidby = txtPaidBy.getText().trim().toUpperCase();
                String termPaid = txtfeesPeriodName.getValue();
                String paymentMethod = txtpaymentMethod.getValue();
                if (regnumber.isEmpty()) {
                    warnningSimpleOKDialg("Warning !", "Cant Find Student If There Is No Registration Number!");
                } else {
                    if (regnumber.length() < 5) {
                        warnningSimpleOKDialg("Warning !", "Invalid Registration Number!");
                    } else {
                        if (! isType(bankAccount, "float") || ! isType(bankAccount, "double")) {
                            warnningSimpleOKDialg("Amount Error", "Put Valid numeric Values!");
                        } else {
                            ImageLoading.setVisible(true);
                            Task<String> payementTask = new Task<String>() {
                                @Override
                                protected String call () throws Exception {
                                    return new DBFeePayment().payStudentFees(new FeesPayment(0, regnumber, paymentMethod,
                                            localTransactionIDMaker(),
                                            paidAmount, LocalDate.now() + "", bank, bankAccount, paidby,
                                            studentEdits[2], termPaid, 0));
                                }
                            };
                            new Thread(payementTask).start();
                            payementTask.setOnSucceeded(event -> {
                                if (payementTask.getValue().equals("success")) {
                                    infomationSimpleOKDialg("Information", "Details Saved Successfully");
                                    clearForm ();
                                } else {
                                    errorSimpleOKDialg("Access Error", "Something went wrong!\nTry again (Later)");
                                }
                                ImageLoading.setVisible(false);
                            });
                            payementTask.setOnFailed(event -> {
                                errorSimpleOKDialg("Access Error", "Something went wrong!\nTry again (Later)");
                                ImageLoading.setVisible(false);
                            });
                        }
                    }
                }
            } else {
                warnningSimpleOKDialg("Warning !", "You need to find the student info first using Registration Number!");
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
        wasStudentFound = false;
        txtfeeRegNumber.setText("");
        txtfeeAmoount.setText("");
        txtBankName.setText("");
        txtfeesAccountNoumber.setText("");
        txtPaidBy.setText("");
        txtfeesPeriodName.getSelectionModel().selectFirst();
        txtpaymentMethod.getSelectionModel().selectFirst();
    }

    private void initDataRequired () {
        txtpaymentMethod.getItems().setAll(PAYMENT_METHOD);
        txtpaymentMethod.getSelectionModel().selectFirst();
        txtfeesPeriodName.getItems().setAll(TERMS);
        txtfeesPeriodName.getSelectionModel().selectFirst();

        btnSaveChanges.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnSaveChanges.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });

        btnCloseTab.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnCloseTab.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });


        btnClear.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnClear.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });


        btnFindStudent.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnFindStudent.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
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
        javafx.scene.control.Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tabPane.getTabs().remove(tab);
    }

    @Override
    public void setTabPane (TabPane pane) {
        this.tabPane = pane;
    }
}
