package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.main.mDatabases.DBRecords;
import sample.main.mDatabases.DBSettings;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mUtility.Loading;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.main.mMessages.mDialogs.errorSimpleOKDialg;
import static sample.main.mMessages.mDialogs.infomationSimpleOKDialg;
import static sample.main.mUtility.mLocalMethods.getCountriesList;

public class Controller_addStudent implements Initializable {
    @FXML
    private HBox mainSceenHolder;
    @FXML
    private VBox formData;

    @FXML
    private AnchorPane containerStdDetails;
    @FXML
    private TextField stdSurname, stdDOB, stdTown, stdName,stdPaidAmounta,stdAccount;

    @FXML
    private ComboBox<String> stdGradeLevel, stdClassName, stdCountry, stdSex;

    @FXML
    private TextArea stdAddress;

    @FXML
    private Button btnSaveChanges, btnClear;
    private DBSettings db ;private DBRecords dbStd;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        iniResources();
        initClickListiners();
    }

    private void initRequiredData () {
        db = DBSettings.getInstance();
        dbStd = DBRecords.getInstance();
        stdGradeLevel.getItems().setAll(db.getGradeLevelClass(true));
        stdClassName.getItems().setAll(db.getGradeLevelClass(false));
        stdSex.getItems().setAll("Male", "Female");
        stdCountry.getItems().setAll(getCountriesList(true));
        stdCountry.getSelectionModel().selectLast();
    }

    private void initClickListiners () {
        btnSaveChanges.setOnAction(event -> {
            String name = stdName.getText().trim() ;
            String surname = stdSurname.getText().trim() ;
            String dob = stdDOB.getText().trim() ;
            String town = stdTown.getText().trim() ;
            String sex = stdSex.getValue();
            String grade=stdGradeLevel.getValue();
            String className = stdClassName.getValue();
            String address = stdAddress.getText().trim() ;
            String country =stdCountry.getValue();
            String feesPaid = stdPaidAmounta.getText().trim();
            String accountNumber = stdAccount.getText().trim();

            if(name.isEmpty()||surname.isEmpty()||dob.isEmpty()||town.isEmpty()||sex.isEmpty()||grade.isEmpty()||
                    className.isEmpty()||address.isEmpty()||feesPaid.isEmpty()){
                errorSimpleOKDialg("Faild to save","One of the fiels is empty","Put required data in all fields to save");

            }else{
                formData.setDisable(true);
                dbStd.savePrimaryStudent(new PrimaryLevelStudent(false,name,surname,address,dob,country,town,sex,
                        accountNumber,grade,className,feesPaid));
                infomationSimpleOKDialg("Record Saved","Student Added Successfully");
                formData.setDisable(false);
                resetForm();
            }



        });
        btnClear.setOnAction(ev -> {
           resetForm();
        });
    }
private void resetForm(){
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
        Task task= Loading.load();
    }
}
