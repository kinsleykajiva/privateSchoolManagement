package sample.main.mframeWork;

import javafx.fxml.FXMLLoader;

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
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();}
                break;
            case VIEW_STUDENTS:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.VIEW_STUDENTS.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();}
                break;
            case STUDENT_DATA:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.STUDENT_DATA.getFxmlFile())));
                    viewController =screen;
                } catch (IOException e) {e.printStackTrace();}
                break;
            case EDIT_STUDENTS:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.EDIT_STUDENTS.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;
            case ABOUT:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.ABOUT.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;
            case SETTINGS:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.SETTINGS.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;
            case FEES_VIEW:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.FEES_VIEW.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;
            case TAB_VIEW_FES_LIST:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.TAB_VIEW_FES_LIST.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;
            case TAB_FEES_PAYMENT:
                try {
                    StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(ViewController.TAB_FEES_PAYMENT.getFxmlFile())));
                    viewController = screen;
                } catch (IOException e) {e.printStackTrace();} break;


                default:
                    break;


        }
    }
}
