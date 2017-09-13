package sample.main.mControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import sample.main.mUtility.mLocalMethods;
import sample.main.mframeWork.ScreenController;
import sample.main.mframeWork.StageManager;
import sample.main.mframeWork.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.main.mframeWork.StageManager.getStage;

public class DefaultController implements Initializable {

    @FXML
    private Button ButtonMinimize;
    @FXML private Button ButtonClose;
    @FXML private Button ButtonLogout;
    @FXML private Button ButtonResize;
    @FXML private Button ButtonMaximize;
    @FXML private ListView<String> ListMenu;
    @FXML private AnchorPane PaneFragment;
    private Rectangle2D rectangle2D;
    private double width;
    private double height;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        initialize();
    }
    public void initialize() {
        StageManager.setPane(PaneFragment);

        mLocalMethods.setLargeScreen();

        ButtonMinimize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMinimize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonClose.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonClose.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonLogout.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonLogout.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonResize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonResize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMaximize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        rectangle2D = Screen.getPrimary().getVisualBounds();
        width=0.1;
        height=0.1;

        ListMenu.getItems().addAll("Add Student","View Students","About");
        ListMenu.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ListMenu.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize.getStyleClass().add("decoration-button-restore");
        ButtonResize.setVisible(false);
        ListMenu.getSelectionModel().select(0);
        ListMenu.requestFocus();
        try {
            modeListMenu(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modeListMenu(MouseEvent event) throws IOException {
        switch(ListMenu.getSelectionModel().getSelectedIndex()){
            case 0:
                ScreenController.setScreen(ViewController.STUDENT_DATA);
                break;
            case 1:
                ScreenController.setScreen(ViewController.VIEW_STUDENTS);
                break;
           /* case 2:
                ScreenController.setScreen(ScreenController.Screen.TEAM_PROJECTS);
                break;
            case 3:
                ScreenController.setScreen(ScreenController.Screen.ADMINISTRATION);
                break;
            case 4:
                ScreenController.setScreen(ScreenController.Screen.ABOUT);
                break;*/
            default:
                break;
        }
    }
    @FXML
    public void modeLogout() throws IOException {
       // SmallScreen.setSmallScreen();
       // ScreenController.setScreen(ScreenController.Screen.LOGIN);
    }
}
