package sample.main.mControllers;

import javafx.concurrent.Task;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.main.mDatabases.DBRecords;
import sample.main.mDatabases.DBSettings;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mframeWork.ScreenController;
import sample.main.mframeWork.Shared;
import sample.main.mframeWork.ViewController;

import static sample.main.mMessages.mDialogs.errorSimpleOKDialg;
import static sample.main.mMessages.mDialogs.infomationSimpleOKDialg;
import static sample.main.mMessages.mDialogs.warnningSimpleOKDialg;
import static sample.main.mUtility.mLocalMethods.getCountriesList;
import static sample.main.mUtility.mLocalMethods.isDateValid;
import static sample.main.mframeWork.Shared.editedRecord;
import static sample.main.mframeWork.Shared.student;

public class Controller_editStudent implements Initializable {
    @FXML
    private HBox mainSceenHolder;

    @FXML
    private AnchorPane containerStdDetails;
    @FXML
    private VBox formData;
    @FXML
    private ComboBox<String> stdSex, stdClassName ,stdCountry,stdGradeLevel;
    @FXML
    private TextField stdName,stdSurname,stdPaidAmounta,stdTown ,stdAccount,stdDOB;
    @FXML
    private TextArea stdAddress;
    @FXML
    private Label reg_numberDisplay;
    @FXML
    private Button btnClear,btnSaveChanges,btnBack;
    @FXML
    private ProgressBar ProgressLoading;
    @FXML
    private ImageView ImageLoading;
    private DBSettings db;
    public static DBRecords dbStd;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        initResours();
            settData();


        initClickListers();

    }

    private void initResours () {
        btnClear.setDisable(true);
        btnSaveChanges.setDisable(true);
        stdGradeLevel.getItems().setAll(db.getGradeLevelClass(true));
        stdClassName.getItems().setAll(db.getGradeLevelClass(false));
        stdSex.getItems().setAll("Male", "Female");
        stdCountry.getItems().setAll(getCountriesList(true));
    }

    private void settData () {
        stdName.setText(student.get__name());
        stdSurname.setText(student.get__surname());
        stdAddress.setText(student.get__address());
        stdAccount.setText(student.getAccountNumber());
        stdDOB.setText(student.get__dateOFBirth());
        stdSex.getSelectionModel().select(student.get__sex());
        stdClassName.getSelectionModel().select(student.get__class_name());
        stdCountry.getSelectionModel().select(student.get__country());
        stdGradeLevel.getSelectionModel().select(student.get__classGrade_level());
        stdPaidAmounta.setText(student.get__feesPaid());
        stdTown.setText(student.get__town_city());
        reg_numberDisplay.setText("Student ID Number : "+student.get__registrationNumber());

    }

    private void initClickListers () {
        stdName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdPaidAmounta.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdTown.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });

        stdSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);
        });
        stdAccount.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdDOB.textProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdSex.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdGradeLevel.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdClassName.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        stdCountry.valueProperty().addListener((observable, oldValue, newValue) -> {
            btnClear.setDisable(false);
            btnSaveChanges.setDisable(false);

        });
        btnBack.setOnAction(ev->{
            ScreenController.setScreen(ViewController.VIEW_STUDENTS);
        });
        btnSaveChanges.setOnAction(ev->{
            //validation
            String name = stdName.getText().trim();
            String surname = stdSurname.getText().trim();
            String dob = stdDOB.getText().trim();
            String town = stdTown.getText().trim();
            String sex = stdSex.getValue();
            String grade = stdGradeLevel.getValue();
            String className = stdClassName.getValue();
            String address = stdAddress.getText().trim();
            String country = stdCountry.getValue();
            String feesPaid = stdPaidAmounta.getText().trim();
            String accountNumber = stdAccount.getText().trim();
            if (name.isEmpty() || surname.isEmpty() || dob.isEmpty() || town.isEmpty() || sex.isEmpty() || grade.isEmpty() ||
                    className.isEmpty() || address.isEmpty() || feesPaid.isEmpty()) {
                errorSimpleOKDialg("Faild to save", "One of the fiels is empty", "Put required data in all fields to save");

            }else{
                if(!isDateValid(dob)){
                    warnningSimpleOKDialg("Date format Error", "Please put a valid date format", "Required format is dd/mm/yyyy.");
                }else {
                    ImageLoading.setVisible(true);
                    formData.setDisable(true);
                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        protected void succeeded () {
                            super.succeeded();
                            ImageLoading.setVisible(false);
                            formData.setDisable(false);
                        }

                        @Override
                        protected void failed () {
                            super.failed();
                            formData.setDisable(false);
                            ImageLoading.setVisible(false);
                        }

                        @Override
                        protected Boolean call () throws Exception {
                            return dbStd.updateRecord(new PrimaryLevelStudent(student.get__registrationNumber(), false, name, surname, address, dob, country, town, sex,
                                    accountNumber, grade, className, feesPaid));
                        }
                    };
                    new Thread(task).start();
                    task.setOnSucceeded(evc -> {
                        if (task.getValue()) {
                            editedRecord[0]=name;
                            editedRecord[1]=surname;
                            editedRecord[3]=dob;
                            editedRecord[4]=town;
                            editedRecord[5]=sex;
                            editedRecord[6]=grade;
                            editedRecord[7]=className;
                            editedRecord[8]=address;
                            editedRecord[9]=feesPaid;
                            editedRecord[10]=country;
                            editedRecord[11]=student.get__registrationNumber();
                            Shared.hasJustEditedRecord =true;
                            infomationSimpleOKDialg("Record Saved", "Student Record updated Successfully");
                        } else {
                            errorSimpleOKDialg("Saving Failed", "Failed to save the update.", " Please try again.");
                        }
                    });
                }
            }
        });
        btnClear.setOnAction(ev->{
            resetForm();
        });
    }

    private void initRequiredData () {
        db = DBSettings.getInstance();
        dbStd = DBRecords.getInstance();

    }
    void resetForm () {
        stdSurname.setText("");
        stdDOB.setText("");
        stdTown.setText("");
        stdName.setText("");
        stdAddress.setText("");
        stdAccount.setText("");
        stdPaidAmounta.setText("");
        stdGradeLevel.valueProperty().set(null);
        stdClassName.valueProperty().set(null);
        stdSex.valueProperty().set(null);
    }
}
