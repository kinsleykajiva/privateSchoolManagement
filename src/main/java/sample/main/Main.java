package sample.main;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import sample.main.mDatabases.DBManager;
import sample.main.mframeWork.StageManager;
import sample.main.mframeWork.ViewController;

import java.util.logging.LogManager;

import static sample.main.mUtility.mLocalMethods.createAppDataFolder;

public class Main extends Application {
    private StageManager stageManager = null;

    @Override
    public void init () throws Exception {
        super.init();
        /*This will run a bit of home keeping for our app*/
        createAppDataFolder();
        new Thread(new Task<Void>() {
            @Override
            protected Void call () throws Exception {
                DBManager.getInstance();
                return null;
            }
        }).start();


    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        stageManager = new StageManager(primaryStage);

        stageManager.switchScene(ViewController.DEFAULT_VIEW);



    }


    public static void main (String[] args) {
        LogManager.getLogManager().reset();
        launch(args);
    }
}
