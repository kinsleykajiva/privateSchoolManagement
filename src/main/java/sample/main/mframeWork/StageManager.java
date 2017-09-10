package sample.main.mframeWork;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.main.mUtility.mLocalStrings.APPLICATION_MINIMUM_HIGHT;
import static sample.main.mUtility.mLocalStrings.APPLICATION_MINIMUM_WIDTH;
import static sample.main.mUtility.mLocalStrings.getApplicationName;

public class StageManager {
    private final Stage stage;

    public StageManager (Stage stage) {
        this.stage = stage;
    }

    public void switchScene (ViewController viewController) {

        try {
            Parent  parent = FXMLLoader.load(getClass().getResource(viewController.getFxmlFile()));
            stage.setTitle(getApplicationName());
            stage.centerOnScreen();
            stage.setScene(new Scene(parent, APPLICATION_MINIMUM_WIDTH, APPLICATION_MINIMUM_HIGHT));
            stage.resizableProperty().setValue(Boolean.TRUE);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            /*close the platform or application as whole when the error happens*/
            Platform.exit();
        }

    }


}
