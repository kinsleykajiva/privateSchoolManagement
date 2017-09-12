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
    private Button btnSaveChanges;

    @FXML
    private TableView<Student> stdDataTable;

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
    private Button btnEditRecord;

    @FXML
    private ProgressBar ProgressLoading;

    @FXML
    private ImageView ImageLoading;
    private ObservableList<Student> students_list = FXCollections.observableArrayList();
    ;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
    }

    private void initRequiredData () {
       /* containerStdDetails.setManaged(true);
        containerStdDetails.setVisible(false);
        containerStdDetails.managedProperty().bind(containerStdDetails.visibleProperty());*/

        mainSceenHolder.getChildren().remove(containerStdDetails);
        ColumnVendorBudget.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnStatus.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnAssignDate.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnClient.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnDP_RO.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnCS.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnDP_NA.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnJobNumber.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnJobTitle.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnJobType.setCellValueFactory(new PropertyValueFactory<>(""));
        ColumnFieldStart.setCellValueFactory(new PropertyValueFactory<>(""));
        for (int i = 0; i < 20; i++) {
            students_list.add(IsPrimaryStudents ?
                    new PrimaryLevelStudent("name " + i, "surname " + i, "addresss " + i,
                            "bob", "zw", "male", "male", "e0002",
                            "1", "green") : new SecondaryLevelStudent());
        }

        stdDataTable.setItems(students_list);

    }
}
