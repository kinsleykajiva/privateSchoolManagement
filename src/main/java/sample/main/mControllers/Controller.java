package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.main.mframeWork.ScreenController;
import sample.main.mframeWork.StageManager;
import sample.main.mframeWork.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private AnchorPane AddStudent;
    @FXML
    private JFXButton btnAddStudent;
    @FXML
    private JFXButton btnViewStudents;
    @FXML
    private AnchorPane holderPane;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        iniResources();

        initClickListeners();

    }

    private void initRequiredData () {
        StageManager.setPane(holderPane);
    }


    private void iniResources () {
        try {
            AddStudent = FXMLLoader.load(getClass().getResource("/layouts/add_student.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initClickListeners () {
        btnAddStudent.setOnAction(ev -> {
           // switchView(AddStudent);
            ScreenController.setScreen(ViewController.ADD_STUDENT);
        });
        btnViewStudents.setOnAction(ev->{
            ScreenController.setScreen(ViewController.VIEW_STUDENTS);
        });
    }

    private void switchView (Node layout) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add(layout);
        /*animate view as it shows on screen*/
        fadeAnimateView(layout);





    }
private void fadeAnimateView(Node node){
    FadeTransition transition = new FadeTransition(Duration.millis(1500));
    transition.setNode(node);
    transition.setFromValue(.1);
    transition.setToValue(1);
    transition.setCycleCount(1);
    transition.setAutoReverse(false);
    transition.play();
}

}
