package sample.main.mControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
    private Button ButtonMinimize1;
    @FXML private Button ButtonClose1;
    @FXML private Button ButtonLogout;
    @FXML private Button ButtonResize;
    @FXML private Button ButtonMaximize1;
    @FXML private ListView<String> ListMenu;
    @FXML private AnchorPane PaneFragment;
    @FXML private ScrollPane fragmentScrollPane;
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
        fragmentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        fragmentScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        ButtonMinimize1.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMinimize1.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        ButtonMinimize1.setOnAction(event -> {
            if(StageManager.getStage().isMaximized()){
                width = rectangle2D.getWidth();
                height = rectangle2D.getHeight();
                StageManager.getStage().setMaximized(false);
                StageManager.getStage().setWidth(width);
                StageManager.getStage().centerOnScreen();
                Platform.runLater(()->{
                    StageManager.getStage().setIconified(true);
                });
            }else {
                StageManager.getStage().setIconified(true);
            }
        });
        ButtonClose1.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonClose1.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        ButtonClose1.setOnAction(ev->{
            Platform.exit();
            System.exit(0);
        });

        ButtonLogout.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonLogout.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonResize.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonResize.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize1.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonMaximize1.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));
        ButtonMaximize1.setOnAction(event -> {
            if (StageManager.getStage().isMaximized()) {
                if (width == rectangle2D.getWidth() && height == rectangle2D.getHeight()) {
                    StageManager.getStage().setMaximized(false);
                    StageManager.getStage().setHeight(600);
                    StageManager.getStage().setWidth(800);
                    StageManager.getStage().centerOnScreen();
                    ButtonMaximize1.getStyleClass().remove("decoration-button-restore");
                    ButtonResize.setVisible(true);
                } else {
                    StageManager.getStage().setMaximized(false);
                    ButtonMaximize1.getStyleClass().remove("decoration-button-restore");
                    ButtonResize.setVisible(true);
                }
            }
        });

        rectangle2D = Screen.getPrimary().getVisualBounds();
        width=0.1;
        height=0.1;

        ListMenu.getItems().addAll("Add Student","View Students","Fees","About");
        ListMenu.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ListMenu.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonMaximize1.getStyleClass().add("decoration-button-restore");
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
                ScreenController.setScreen(ViewController.FEES_VIEW);
                break;
            case 1:
                ScreenController.setScreen(ViewController.VIEW_STUDENTS);
                break;
            case 2:
                ScreenController.setScreen(ViewController.ADD_STUDENT);
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
