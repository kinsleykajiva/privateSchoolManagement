package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import sample.main.mframeWork.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private AnchorPane AddStudent;
    @FXML
    private JFXButton btnAddStudent;
    @FXML
    private AnchorPane holderPane;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initRequiredData();
        iniResources();

        initClickListeners();

    }

    private void initRequiredData () {

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
            long nw=System.nanoTime();
            System.out.println("loading ... ");
            switchView(AddStudent);
            System.out.println("done"+((System.nanoTime()-nw)/3600));
        });
    }

    private void switchView (Node layout) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add(layout);

    }


}
