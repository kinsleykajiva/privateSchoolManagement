package sample.main.mControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.Student;
import sample.main.mPojos.passMe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentData implements Initializable {

    @FXML
    private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private GridPane PaneTabel;
    @FXML private HBox PaneTop;
    @FXML private AnchorPane PaneMain;
    @FXML
    private TableView<passMe> tableData;

    @FXML
    private TableColumn<passMe, String> col_name;
    @FXML
    private TableColumn<passMe, String> col_surname;
    @FXML
    private TableColumn<passMe, String> col_sex;

    private ObservableList<passMe> students_list;
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        List<passMe> primaryLevelStudents = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            primaryLevelStudents.add(
                    new passMe("name " + i, "surname " + i, "Male " + i));
        }
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        students_list = FXCollections.observableArrayList((
                new passMe("name " , "surname " , "Male " )));
       // tableData.getItems().addAll(students_list);
        tableData.setItems(students_list);
    }
    @FXML
    public void searchInTables(){

    }
    @FXML
    public void showSearch(){

    }
}
