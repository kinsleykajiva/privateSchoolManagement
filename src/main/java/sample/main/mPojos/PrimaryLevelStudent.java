package sample.main.mPojos;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.main.mControllers.Controller_editStudent;
import sample.main.mDatabases.DBRecords;
import sample.main.mInterfaceCallbacks.LoadInterface;
import sample.main.mframeWork.ScreenController;
import sample.main.mframeWork.ViewController;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.List;

import static sample.main.mMessages.mDialogs.*;
import static sample.main.mUtility.mLocalMethods.getCurrentTime;
import static sample.main.mUtility.mLocalMethods.registrationDate;
import static sample.main.mframeWork.Shared.primaryLevelStudentsCache;
import static sample.main.mframeWork.Shared.student;
import static sample.main.mframeWork.Shared.viewController;
import static sample.main.mframeWork.StageManager.getStage;

/**This is my class using hibernate for data persistence*/
@Entity
@DiscriminatorValue("primarylevelstudent ")
public final class PrimaryLevelStudent extends Student{


    private String __classGrade_level;
    private String __class_name;
    private LoadInterface loadInterface;

    /**Student registration process*/
    public PrimaryLevelStudent(String registrationNumber , boolean selected,String name , String surname , String address , String dateOFBirth ,
                               String country , String town_city , String sex ,  String AccountNumber , String grade_level, String className , String feesPaid  ) {

       set__name(name);
       set__registrationNumber(registrationNumber);
       set__feesPaid(feesPaid);
       setSelected(selected);
       set__surname(surname);
       set__address(address);
       set__dateOFBirth(dateOFBirth);
       set__town_city(town_city);
       set__country(country);
       set__sex(sex);
       setAccountNumber(AccountNumber);
    //    loadInterface

       __classGrade_level = grade_level;
       __class_name = className;

    }

    public HBox getAction(){
        HBox action = new HBox(5);
        action.setAlignment(Pos.CENTER);
       // loadInterface =this;

        Label delete = new Label("Del");
        delete.setOnMouseEntered(ev->{getStage().getScene().setCursor(Cursor.HAND);});
        delete.setOnMouseExited(e->{getStage().getScene().setCursor(Cursor.DEFAULT);});
        delete.setTextFill(Color.web("#0099FF"));
        delete.setFont(Font.font("Segoe UI Semilight", FontWeight.BOLD,12));
        delete.setOnMouseClicked(ev->{


           if(yesNoDialog("Deletion Confirmation","Database Access Action!","Are you sure you want delete this recored from the database ?","Yes,Delete!","No,Cancel",2)){

             final  DBRecords   db= DBRecords.getInstance();

               if(db == null){
                   warnningSimpleOKDialg("Database Connection","Failed to connect to the Database","Please reload the Application !");
                   return;
               }
                   Task<Boolean> DeleteTask = new Task<Boolean>() {
                       @Override
                       protected Boolean call () throws Exception {
                           return db.deleteRecord(get__registrationNumber());
                       }
                   };
               new Thread(DeleteTask).start();
                   DeleteTask.setOnSucceeded(e -> {
                       if (DeleteTask.getValue()) {
                           infomationSimpleOKDialg("Record Information", "Deleted Successfully");
                        //   final int position = primaryLevelStudentsCache.indexOf(student);
                           primaryLevelStudentsCache.remove(this);
                           ScreenController.setScreen(ViewController.VIEW_STUDENTS);

                       } else {
                           errorSimpleOKDialg("Database Error Occurred", "Something went Wrong", "Please try again!");
                       }
                   });
                   DeleteTask.setOnFailed(e -> {
                       errorSimpleOKDialg("Error Occurred", "Something went Wrong", "Please try again!");
                   });

           }


        });
        Label edit = new Label("Edit");
        edit.setOnMouseEntered(ev->{getStage().getScene().setCursor(Cursor.HAND);});
        edit.setOnMouseExited(e->{getStage().getScene().setCursor(Cursor.DEFAULT);});
        edit.setTextFill(Color.web("#0099FF"));
        edit.setFont(Font.font("Segoe UI Semilight", FontWeight.BOLD,12));
        edit.setOnMouseClicked(ev->{
            /*this is temporary*/
            student = PrimaryLevelStudent.this;
            ScreenController.setScreen(ViewController.EDIT_STUDENTS);

        });
        action.getChildren().addAll(delete,edit);
        return action;
    }

    public String get__classGrade_level () {
        return __classGrade_level;
    }

    public String get__class_name () {
        return __class_name;
    }

    public void set__classGrade_level (String __classGrade_level) {
        this.__classGrade_level = __classGrade_level;
    }

    public void set__class_name (String __class_name) {
        this.__class_name = __class_name;
    }
}
