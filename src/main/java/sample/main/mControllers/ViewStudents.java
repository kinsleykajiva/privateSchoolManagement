package sample.main.mControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.SecondaryLevelStudent;
import sample.main.mPojos.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static sample.main.mframeWork.Shared.IsPrimaryStudents;

public class ViewStudents implements Initializable {
    @FXML
    private AnchorPane containerStdDetails, containerStudentDetailsTable;
    @FXML
    private HBox mainSceenHolder;

    @FXML
    private TextField stdSurname, stdName, stdTown, stdDOB;

    @FXML
    private ComboBox<?> stdSex;

    @FXML
    private ComboBox<?> stdGradeLevel;

    @FXML
    private ComboBox<?> stdClassName;

    @FXML
    private ComboBox<?> stdCountry;

    @FXML
    private TextArea stdAddress;

    @FXML
    private Button btnSaveChanges, btnEditRecord;

    @FXML
    private TableView stdDataTable;

    @FXML
    private TableColumn<Student, String> ColumnVendorBudget;

    @FXML
    private TableColumn<Student, String> ColumnStatus;

    @FXML
    private TableColumn<Student, String> ColumnAssignDate;

    @FXML
    private TableColumn<Student, String> ColumnDP_RO;

    @FXML
    private TableColumn<Student, String> ColumnDP_NA;

    @FXML
    private TableColumn<Student, String> ColumnJobNumber;

    @FXML
    private TableColumn<Student, String> ColumnJobTitle;

    @FXML
    private TableColumn<Student, String> ColumnJobType;

    @FXML
    private TableColumn<Student, String> ColumnClient;

    @FXML
    private TableColumn<Student, String> ColumnCS;

    @FXML
    private TableColumn<Student, String> ColumnFieldStart;



    @FXML
    private ProgressBar ProgressLoading;

    @FXML
    private ImageView ImageLoading;
    private ObservableList<Student> students_list;
    ;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
    }

    private void initRequiredData () {
       /* containerStdDetails.setManaged(true);
        containerStdDetails.setVisible(false);
        containerStdDetails.managedProperty().bind(containerStdDetails.visibleProperty());*/

        //mainSceenHolder.getChildren().remove(containerStdDetails);
        ColumnVendorBudget.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnStatus.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnAssignDate.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnClient.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnDP_RO.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnCS.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnDP_NA.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnJobNumber.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnJobTitle.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnJobType.setCellValueFactory(new PropertyValueFactory<>("__name"));
        ColumnFieldStart.setCellValueFactory(new PropertyValueFactory<>("__name"));
        List<PrimaryLevelStudent> primaryLevelStudents = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            primaryLevelStudents.add(
                    new PrimaryLevelStudent("name " + i, "surname " + i, "addresss " + i,
                            "bob", "zw", "male", "male", "e0002",

                            "1", "green"));
        }
        students_list = FXCollections.observableArrayList(primaryLevelStudents);
        System.out.print(students_list.get(0).get__registrationNumber());
        System.out.print(students_list.get(0).get__country());
        stdDataTable.setItems(students_list);
        final boolean[] editselect = {false};
        btnEditRecord.setOnAction(ev->{
            if(! editselect[0]) {
                editselect[0] =true;
                //
                mainSceenHolder.getChildren().get(0).setVisible(false);
                mainSceenHolder.getChildren().remove(containerStdDetails);
            }else{
                editselect[0] =false;
                mainSceenHolder.getChildren().get(0).setVisible(true);
                mainSceenHolder.getChildren().add(containerStdDetails);
            }

        });

    }
}
