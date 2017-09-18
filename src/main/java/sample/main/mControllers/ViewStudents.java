package sample.main.mControllers;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sample.main.animation.FadeInLeftTransition;
import sample.main.animation.FadeInUpTransition;
import sample.main.mDatabases.DBRecords;
import sample.main.mInterfaceCallbacks.LoadInterface;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.Student;
import sample.main.mUtility.Loading;
import sample.main.mframeWork.Shared;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static sample.main.mMessages.mDialogs.errorSimpleOKDialg;
import static sample.main.mframeWork.Shared.editedRecord;
import static sample.main.mframeWork.Shared.primaryLevelStudentsCache;
import static sample.main.mframeWork.Shared.student;

public class ViewStudents implements Initializable ,LoadInterface {
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
    private HBox searchOptions;

    @FXML
    private TableView stdDataTable;
    private CheckBox selectAllCheckBox;

    @FXML
    private TableColumn<Student, String> col_name;
    @FXML
    private TableColumn<PrimaryLevelStudent, HBox> col_action;

    @FXML
    private TableColumn<Student, String> col_surname;

    @FXML
    private TableColumn<Student, String> col_address;

    @FXML
    private TableColumn<Student, String> col_townCity;

    @FXML
    private TableColumn<Student, String> col_sex;

    @FXML
    private TableColumn<Student, String> col_id;

    @FXML
    private TableColumn<Student, String> col_accountNumber;

    @FXML
    private TableColumn<Student, String> col_gradeLevel;

    @FXML
    private TableColumn<Student, String> col_dateOfBirth;

    @FXML
    private TableColumn<Student, String> col_country;

    @FXML
    private TableColumn<Student, String> col_className;
    private DBRecords dbStd;

    @FXML
    private ProgressBar ProgressLoading;

    @FXML
    private ImageView ImageLoading;
    private ObservableList<Student> students_list;
    ;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
       initRequiredData();
        initResources();

    }

    private void initResources () {
        // ImageLoading.setVisible(true);
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        Task task = Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(ev -> {


            searchOptions.setVisible(true);
            ProgressLoading.setVisible(false);
            new FadeInLeftTransition(stdDataTable).play();
            new FadeInUpTransition(searchOptions).play();
            stdDataTable.setVisible(true);
        });
    }

    private void initRequiredData () {
        dbStd = DBRecords.getInstance();
         col_action.setCellValueFactory(new PropertyValueFactory<>("action"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("__name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("__surname"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("__address"));
        col_dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("__dateOFBirth"));
        col_townCity.setCellValueFactory(new PropertyValueFactory<>("__town_city"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("__country"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("__sex"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("__registrationNumber"));
        col_accountNumber.setCellValueFactory(new PropertyValueFactory<>("AccountNumber"));
        col_gradeLevel.setCellValueFactory(new PropertyValueFactory<>("__classGrade_level"));
        col_className.setCellValueFactory(new PropertyValueFactory<>("__class_name"));


        /*for (int i = 0; i < 140; i++) {
            primaryLevelStudentsCache.add(
                    new PrimaryLevelStudent(localIDMaker(),false, "name " + i, "surname " + i, "addresss " + i,
                            "9/15/17", "Zimbabwe", "Harare", "male", "e0002",
                            "1", "Green",""+(231*i)));
            dbStd.savePrimaryStudent( new PrimaryLevelStudent(localIDMaker(),false, "name " + i, "surname " + i, "addresss " + i,
                    "9/15/17", "Zimbabwe", "Harare", "male", "e0002",
                    "1", "Green",""+(231*i)));
        }*/
        if (! Shared.hasJustEditedRecord) {

            Task<List<PrimaryLevelStudent>> getstudentsTask = new Task<List<PrimaryLevelStudent>>() {
                @Override
                protected List<PrimaryLevelStudent> call () throws Exception {
                    return dbStd.getStudent();
                }
            };
            new Thread(getstudentsTask).start();
            //ProgressLoading.visibleProperty().bind(getstudentsTask.runningProperty());
            //ProgressLoading.progressProperty().bind(getstudentsTask.progressProperty());

            getstudentsTask.setOnSucceeded(event -> {
                primaryLevelStudentsCache.clear();
                primaryLevelStudentsCache.addAll(getstudentsTask.getValue());
                students_list = FXCollections.observableArrayList(primaryLevelStudentsCache);
                stdDataTable.setItems(students_list);
                ImageLoading.setVisible(false);

            });
        } else {
            if(Shared.hasJustDeletedRecord){
                students_list = FXCollections.observableArrayList(primaryLevelStudentsCache);
                stdDataTable.setItems(students_list);
                ImageLoading.setVisible(false);
                Shared.hasJustDeletedRecord=false;
            }else {
                final int position = primaryLevelStudentsCache.indexOf(student);
                primaryLevelStudentsCache.get(position).set__name(editedRecord[0]);
                primaryLevelStudentsCache.get(position).set__surname(editedRecord[1]);
                primaryLevelStudentsCache.get(position).set__dateOFBirth(editedRecord[3]);
                primaryLevelStudentsCache.get(position).set__town_city(editedRecord[4]);
                primaryLevelStudentsCache.get(position).set__sex(editedRecord[5]);
                primaryLevelStudentsCache.get(position).set__classGrade_level(editedRecord[6]);
                primaryLevelStudentsCache.get(position).set__class_name(editedRecord[7]);
                primaryLevelStudentsCache.get(position).set__address(editedRecord[8]);
                primaryLevelStudentsCache.get(position).set__feesPaid(editedRecord[9]);
                primaryLevelStudentsCache.get(position).set__country(editedRecord[10]);
                primaryLevelStudentsCache.get(position).set__registrationNumber(editedRecord[11]);
                Task<ObservableList<Student>> threadTask = new Task<ObservableList<Student>>() {
                    @Override
                    protected ObservableList<Student> call () throws Exception {
                        return FXCollections.observableArrayList(primaryLevelStudentsCache);
                    }
                };
                new Thread(threadTask).start();
                threadTask.setOnSucceeded(event -> {
                    stdDataTable.setItems(threadTask.getValue());
                    ImageLoading.setVisible(false);
                    Shared.hasJustEditedRecord=false;
                });
                threadTask.setOnFailed(event -> {
                    ImageLoading.setVisible(false);
                    Shared.hasJustEditedRecord=false;
                    errorSimpleOKDialg("Error Occurred", "Something went wrong", " Please try again.");
                });
            }
        }
        //tableFactory(students_list);

    }

    private void tableFactory (ObservableList<Student> students_list) {
        // Checking for an unselected students in the table view.
        boolean unSelectedFlag = false;
        for (Student student : students_list) {
            if (! student.isSelected()) {
                unSelectedFlag = false;
                break;
            }
        }
        /*if at least one student is not selected then deselect every check box but if all are selectd then check the box in the header*/
        if (unSelectedFlag) {
            getSelectAllCheckBox().setSelected(false);

        } else {
            getSelectAllCheckBox().setSelected(true);

        }
        // Checking for a selected student in the table view.
        boolean selectedFlag = false;
        for (Student student : students_list) {
            if (student.isSelected()) {
                selectedFlag = true;
                break;
            }
        }
        /*if at least one student is select then enable the ....*/
        if (selectedFlag) {
            System.out.print("do this effect");
        } else {
            System.out.print("do this effect 2222");

        }


    }

    /**
     * Lazy getter for the selectAllCheckBox.
     *
     * @return selectAllCheckBox
     */
    public CheckBox getSelectAllCheckBox () {
        if (selectAllCheckBox == null) {
            final CheckBox selectAllCheckBox = javafx.scene.control.CheckBoxBuilder.create().build();
            // adding event listner to the check box to select / deselect all employess in the table
            selectAllCheckBox.setOnAction(event -> {
                // setting the value in all the students
                for (Student student : students_list) {
                    student.setSelected(selectAllCheckBox.isSelected());
                }
            });
            this.selectAllCheckBox = selectAllCheckBox;
        }
        return selectAllCheckBox;
    }

    @SuppressWarnings("unchecked")
    public TableView<Student> getStudentTable () {
        if (stdDataTable == null) {
            // select column
            TableColumn<Student, Boolean> selectCol = new TableColumn<Student, Boolean>();
            selectCol.setMinWidth(50);
            selectCol.setGraphic(getSelectAllCheckBox());
            selectCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
            selectCol.setCellFactory(param -> {
                final TableCell<Student, Boolean> cell = new TableCell<Student, Boolean>() {
                    @Override
                    protected void updateItem (Boolean item, boolean empty) {
                        if (item == null) {
                            return;
                        }
                        super.updateItem(item, empty);
                        if (! isEmpty()) {
                            final Student student = getTableView().getItems().get(getIndex());
                            CheckBox checkBox = new CheckBox();

                            //checkBox.selectedProperty().bindBidirectional();
                            setGraphic(checkBox);

                        }

                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
            TableView<Student> tableView = new TableView<>();

            TableColumn<Student, String> name = new TableColumn<>();
            name.setCellValueFactory(new PropertyValueFactory<>("__name"));

            TableColumn<Student, String> surname = new TableColumn<>();
            surname.setCellValueFactory(new PropertyValueFactory<>("__surname"));

            TableColumn<Student, String> sex = new TableColumn<>();
            sex.setCellValueFactory(new PropertyValueFactory<>("__sex"));

            TableColumn<Student, String> address = new TableColumn<>();
            address.setCellValueFactory(new PropertyValueFactory<>("address"));

            TableColumn<Student, String> dateOFBirth = new TableColumn<>();
            dateOFBirth.setCellValueFactory(new PropertyValueFactory<>("__dateOFBirth"));
            TableColumn<Student, String> country = new TableColumn<>();
            country.setCellValueFactory(new PropertyValueFactory<>("__country"));
            TableColumn<Student, String> town = new TableColumn<>();
            town.setCellValueFactory(new PropertyValueFactory<>("__town_city"));
            TableColumn<Student, String> AccountNumber = new TableColumn<>();
            AccountNumber.setCellValueFactory(new PropertyValueFactory<>("AccountNumber"));
            TableColumn<Student, String> __classGrade_level = new TableColumn<>();
            __classGrade_level.setCellValueFactory(new PropertyValueFactory<>("__classGrade_level"));
            TableColumn<Student, String> __class_name = new TableColumn<>();
            __class_name.setCellValueFactory(new PropertyValueFactory<>("__class_name"));
            tableView.setItems(students_list);
            tableView.getColumns().addAll(surname, name, address, sex, dateOFBirth, country, town,
                    AccountNumber, __class_name, __classGrade_level);

            ListBinding<Boolean> lb = new ListBinding<Boolean>() {
                {
                    bind(stdDataTable.getItems());
                }

                @Override
                protected ObservableList<Boolean> computeValue () {
                    ObservableList<Boolean> list = FXCollections.observableArrayList();
                    for (Student student : students_list) {
                        list.add(student.isSelected());
                    }
                    return list;
                }
            };
            lb.addListener((observable, oldValue, l) -> {
                boolean unSelectedFlag = false;
                for (boolean b : l) {
                    if (! b) {
                        unSelectedFlag = true;
                        break;
                    }

                }
                /**
                 * if at least one student is not selected , then deselecting the check box  in the colmn header
                 */
                if (unSelectedFlag) {
                    getSelectAllCheckBox().setSelected(false);
                } else {
                    getSelectAllCheckBox().setSelected(false);
                }
                // cheking for a slected item in the tableview
                boolean selectedFlag = false;
                for (boolean b : l) {
                    if (! b) {
                        selectedFlag = true;
                        break;
                    }
                }
                /**is at leasdt one student is  selected then enable some efect*/
                if (selectedFlag) {
                    System.out.print("11111111");
                } else {
                    System.out.print("333333");
                }
            });
            tableView.getItems().addListener((InvalidationListener) observable -> System.out.print("invalide error"));
            tableView.getItems().addListener((InvalidationListener) observable -> System.out.print("changed vall"));
            this.stdDataTable = tableView;
        }
        return stdDataTable;
    }

    @Override
    public void reloadTable (List<PrimaryLevelStudent> levelStudentList) {
        System.out.print("44444444");
    }
}
