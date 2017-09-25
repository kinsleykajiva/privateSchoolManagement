package sample.main.mControllers;

import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import sample.main.animation.FadeInLeftTransition;
import sample.main.animation.FadeInUpTransition;
import sample.main.mDatabases.DBRecords;
import sample.main.mDatabases.DBSettings;
import sample.main.mInterfaceCallbacks.LoadInterface;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.Student;
import sample.main.mUtility.Loading;
import sample.main.mframeWork.Shared;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static sample.main.mMessages.mDialogs.errorSimpleOKDialg;
import static sample.main.mframeWork.Shared.*;
import static sample.main.mframeWork.StageManager.getStage;

public class ViewStudents implements Initializable, LoadInterface {
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
    private Button btnSaveChanges, btnEditRecord, btnSearchDatabase;
    @FXML
    private HBox searchOptions, searchDataGroup;

    @FXML
    private TableView stdDataTable;
    private CheckBox selectAllCheckBox;

    @FXML
    private TableColumn<Student, String> col_name;
    @FXML
    private ComboBox<String> gradelevel, className;
    @FXML
    private TableColumn<PrimaryLevelStudent, HBox> col_action;
    @FXML
    private TextField txtSearchOthers;
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
    private DBSettings db;

    @FXML
    private ProgressBar ProgressLoading;

    @FXML
    private ImageView ImageLoading;
    private ObservableList<Student> students_list;
    private boolean isUsingSearch = false;
    private boolean isGradeLevelStill_in_use=false;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        initResources();
        initClickListners();

    }

    private void initClickListners () {
        btnSearchDatabase.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
        btnSearchDatabase.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));
        //  HBox started at x: 900    y:13
        // HBox target  translation location : x:260    y:13  decrease x to move to left
        // grow

        btnSearchDatabase.setOnAction(event -> {
            // 1 move button to new position
            if (isUsingSearch) {
                isUsingSearch = false;
                SequentialTransition sequentialTransition;
                FadeTransition fadeTransition
                        = new FadeTransition(Duration.millis(500), searchDataGroup);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                //fadeTransition.play();
                // move the button back
                final Timeline moveButton = new Timeline();
                moveButton.setCycleCount(1);
                moveButton.setAutoReverse(true);
                final KeyValue kv = new KeyValue(searchOptions.layoutXProperty(), 760);
                final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
                moveButton.getKeyFrames().add(kf);
                sequentialTransition = new SequentialTransition(fadeTransition, moveButton);
                sequentialTransition.play();
            } else {
                SequentialTransition sequentialTransition;
                final Timeline moveButton = new Timeline();
                moveButton.setCycleCount(1);
                moveButton.setAutoReverse(true);
                final KeyValue kv = new KeyValue(searchOptions.layoutXProperty(), 260);
                final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
                moveButton.getKeyFrames().add(kf);
                //moveButton.play();
                // 2 grow the size of the HBox Note site size of HBox at width :48
                // target width 724
                searchOptions.setMinWidth(724);
                // 3 show other Hbox Childern
                FadeTransition transition = new FadeTransition(Duration.millis(3500));
                transition.setNode(searchDataGroup);
                transition.setFromValue(.1);
                transition.setToValue(1);
                transition.setCycleCount(1);
                transition.setAutoReverse(false);
                //transition.play();
                sequentialTransition = new SequentialTransition(moveButton, transition);
                sequentialTransition.play();
                searchDataGroup.setVisible(true);
                isUsingSearch = true;
            }


        });
        gradelevel.valueProperty().addListener((observable, oldValue, newValue) -> {
            final String txtSearch = txtSearchOthers.getText().trim().toLowerCase();

           if(!gradelevel.getValue().equals("Select")){
               ImageLoading.setVisible(true);
               FilteredList<Student> filter = new FilteredList<>(students_list, e -> true);
               filter.setPredicate((Predicate<? super Student>) std -> {
                   if (newValue == null || newValue.isEmpty()) {
                       return true;
                   }
                   if (students_list.isEmpty()) {
                       return true;
                   }
                   String i = txtSearch;

                   if (std.get__name().toLowerCase().contains(i) && std.get__classGrade_level().equals(newValue)) {
                       return true;
                   } else if (std.get__registrationNumber().toLowerCase().contains(i)&& std.get__classGrade_level().equals(newValue)) {
                       return true;
                   } else if (std.get__town_city().toLowerCase().contains(i)&& std.get__classGrade_level().equals(newValue)) {
                       return true;
                   }

                   return false;


               });
               SortedList<Student> sortedList = new SortedList<Student>(filter);
               sortedList.comparatorProperty().bind(stdDataTable.comparatorProperty());
               stdDataTable.setItems(sortedList);
               ImageLoading.setVisible(false);
               isGradeLevelStill_in_use =true;
           }else{
                isGradeLevelStill_in_use  = false;
            }

        });

    }

    private void initResources () {
        // ImageLoading.setVisible(true);
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        Task task = Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(ev -> {

            ProgressLoading.setVisible(false);
            new FadeInLeftTransition(stdDataTable).play();

            new FadeInUpTransition(searchOptions).play();
            stdDataTable.setVisible(true);
            searchOptions.setVisible(true);
        });
    }

    private void searchForRecord () {
        FilteredList<Student> filter = new FilteredList<>(students_list, e -> true);
        txtSearchOthers.setOnKeyReleased((observ) -> {
            ImageLoading.setVisible(true);
            txtSearchOthers.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate((Predicate<? super Student>) std -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (students_list.isEmpty()) {
                        return true;
                    }
                    String i = newValue.toLowerCase();
                            if(isGradeLevelStill_in_use){
                                if (std.get__name().toLowerCase().contains(i) ) {
                                    return true;
                                } else if (std.get__registrationNumber().toLowerCase().contains(i) && std.get__classGrade_level().equals(gradelevel.getValue())) {
                                    return true;
                                } else if (std.get__town_city().toLowerCase().contains(i)&& std.get__classGrade_level().equals(gradelevel.getValue())) {
                                    return true;
                                }else if (std.get__surname().toLowerCase().contains(i)&& std.get__classGrade_level().equals(gradelevel.getValue())) {
                                    return true;
                                }
                            }else {
                                if (std.get__name().toLowerCase().contains(i)) {
                                    return true;
                                } else if (std.get__registrationNumber().toLowerCase().contains(i)) {
                                    return true;
                                } else if (std.get__town_city().toLowerCase().contains(i)) {
                                    return true;
                                }else if (std.get__surname().toLowerCase().contains(i)) {
                                    return true;
                                }
                            }

                    return false;


                });
            });

            SortedList<Student> sortedList = new SortedList<Student>(filter);
            sortedList.comparatorProperty().bind(stdDataTable.comparatorProperty());
            stdDataTable.setItems(sortedList);
            ImageLoading.setVisible(false);
        });
    }

    private void initRequiredData () {
        dbStd = new DBRecords();
        db = new DBSettings();
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

        stdDataTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        List<String> gradeLevel__ = new ArrayList<>(Arrays.asList("Select"));
        gradeLevel__.addAll(db.getGradeLevelClass(true));
        gradelevel.getItems().setAll(gradeLevel__);
        gradelevel.getSelectionModel().selectFirst();


        List<String> class__names = new ArrayList<>(Arrays.asList("Select"));
        class__names.addAll(db.getGradeLevelClass(false));
        className.getItems().setAll(class__names);
        className.getSelectionModel().selectFirst();


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
                searchForRecord();

            });
        } else {
            if (Shared.hasJustDeletedRecord) {
                students_list = FXCollections.observableArrayList(primaryLevelStudentsCache);


                stdDataTable.setItems(students_list);
                ImageLoading.setVisible(false);
                Shared.hasJustDeletedRecord = false;
            } else {
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
                    Shared.hasJustEditedRecord = false;

                });
                threadTask.setOnFailed(event -> {
                    ImageLoading.setVisible(false);
                    Shared.hasJustEditedRecord = false;
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
