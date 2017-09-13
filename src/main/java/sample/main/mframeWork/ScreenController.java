package sample.main.mframeWork;

import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;

import java.io.IOException;

import static sample.main.mframeWork.Shared.viewController;


public class ScreenController {


    private ScreenController(){
        new Exception ("Can not create an Object from this Class");
    }
    public static void setScreen(ViewController screen) {
        switch (screen){
            case ADD_STUDENT:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.ADD_STUDENT.getFxmlFile())));
                    viewController = ViewController.ADD_STUDENT;
                } catch (IOException e) {e.printStackTrace();}
                break;
            case VIEW_STUDENTS:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.VIEW_STUDENTS.getFxmlFile())));
                    viewController = ViewController.VIEW_STUDENTS;
                } catch (IOException e) {e.printStackTrace();}
                break;
            case STUDENT_DATA:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.STUDENT_DATA.getFxmlFile())));
                    viewController = ViewController.STUDENT_DATA;
                } catch (IOException e) {e.printStackTrace();}
                break;

                default:
                    break;


        }
    }
}
