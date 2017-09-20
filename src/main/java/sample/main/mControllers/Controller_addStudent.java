package sample.main.mControllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.main.animation.FadeInRightTransition;
import sample.main.animation.FadeInUpTransition;
import sample.main.mDatabases.DBRecords;
import sample.main.mDatabases.DBSettings;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mUtility.Loading;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.*;

public class Controller_addStudent implements Initializable {
    @FXML
    private HBox mainSceenHolder;
    @FXML
    private VBox formData;
    @FXML
    private Label txtShowAddnewStudent;

    @FXML
    private AnchorPane containerStdDetails;
    @FXML
    private TextField stdSurname, stdDOB, stdTown, stdName, stdPaidAmounta, stdAccount;

    @FXML
    private ComboBox<String> stdGradeLevel, stdClassName, stdCountry, stdSex;

    @FXML
    private TextArea stdAddress;
    @FXML
    private ImageView ImageLoading;
    @FXML
    private ProgressBar ProgressLoading;

    @FXML
    private Button btnSaveChanges, btnClear;
    private DBSettings db;
    private DBRecords dbStd;



    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        iniResources();
        initClickListiners();

    }

    private void initRequiredData () {
        db =new  DBSettings();
        dbStd =new  DBRecords();
        stdGradeLevel.getItems().setAll(db.getGradeLevelClass(true));
        stdGradeLevel.getSelectionModel().select(0);
        stdClassName.getItems().setAll(db.getGradeLevelClass(false));
        stdClassName.getSelectionModel().select(0);
        stdSex.getItems().setAll("Select","Male", "Female");
        stdSex.getSelectionModel().select(0);
        stdCountry.getItems().setAll(getCountriesList(true));
        stdCountry.getSelectionModel().selectLast();
    }

    private void initClickListiners () {
        btnSaveChanges.setOnAction(event -> {
            String name = stdName.getText().trim();
            String surname = stdSurname.getText().trim();
            String dob = stdDOB.getText().trim();
            String town = stdTown.getText().trim();
            String sex = stdSex.getSelectionModel().getSelectedItem();
            String grade = stdGradeLevel.getSelectionModel().getSelectedItem();
            String className = stdClassName.getValue();
            String address = stdAddress.getText().trim();
            String country = stdCountry.getValue();
            String feesPaid = stdPaidAmounta.getText().trim();
            String accountNumber = stdAccount.getText().trim();

            if (name.isEmpty() ||
                    surname.isEmpty() ||
                    dob.isEmpty()
                    || town.isEmpty()
                    || sex.equals("Select")
                    || grade.isEmpty() ||
                    className.isEmpty() ||
                    address.isEmpty() || feesPaid.isEmpty()) {
                errorSimpleOKDialg("Faild to save", "One of the fiels is empty", "Put required data in all fields to save");

            } else {
                if(!isDateValid(dob)){
                    warnningSimpleOKDialg("Date format Error", "Please put a valid date format", "Required format is dd/mm/yyyy.");
                }else {
                    if(!isType(feesPaid,"float")){
                        warnningSimpleOKDialg("Fees format Error", "Please put a valid number format", "00.00");
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
                                return dbStd.savePrimaryStudent(new PrimaryLevelStudent(localIDMaker(), false, name, surname, address, dob, country, town, sex,
                                        accountNumber, grade, className, feesPaid));
                            }
                        };
                        new Thread(task).start();
                        task.setOnSucceeded(cv -> {
                            if (task.getValue()) {

                                infomationSimpleOKDialg("Record Saved", "Student Details saved Successfully");
                                resetForm();
                            } else {
                                errorSimpleOKDialg("Saving Failed", "Failed to save details.", " Please try again.");
                            }
                        });
                    }
                }
            }


        });
        btnClear.setOnAction(ev -> {


           resetForm();
        });
    }

    private void resetForm () {
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

    private void iniResources () {
        Task task = Loading.load();
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(ev -> {


            ProgressLoading.setVisible(false);
            txtShowAddnewStudent.setVisible(true);
            new FadeInUpTransition(mainSceenHolder).play();
            mainSceenHolder.setVisible(true);
            new FadeInRightTransition(txtShowAddnewStudent).play();
        });

    }
}
