package sample.main;

import com.fasterxml.classmate.AnnotationConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import sample.main.mDatabases.DBRecords;
import sample.main.mDatabases.DBSettings;
import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.Student;
import sample.main.mframeWork.HibernateUtils;
import sample.main.mframeWork.StageManager;
import sample.main.mframeWork.ViewController;

import java.util.logging.LogManager;

import static sample.main.mUtility.mLocalMethods.createAppDataFolder;
import static sample.main.mUtility.mLocalStrings.APPLICATION_MINIMUM_HIGHT;
import static sample.main.mUtility.mLocalStrings.APPLICATION_MINIMUM_WIDTH;
import static sample.main.mUtility.mLocalStrings.getApplicationName;

public class Main extends Application {
    private StageManager stageManager = null;

    @Override
    public void init () throws Exception {
        super.init();
        /*This will run a bit of home keeping for our app*/
        createAppDataFolder();
        DBSettings.getInstance();
        DBRecords.getInstance();
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        stageManager = new StageManager(primaryStage);
        /*Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        primaryStage.setTitle(getApplicationName());
        primaryStage.setScene(new Scene(root, APPLICATION_MINIMUM_WIDTH, APPLICATION_MINIMUM_HIGHT));
        primaryStage.resizableProperty().setValue(Boolean.TRUE);
        primaryStage.show();*/
        stageManager.switchScene(ViewController.DEFAULT_VIEW);



        /*Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(primaryStudent);
        session.getTransaction().commit();
*/

    }


    public static void main (String[] args) {
        LogManager.getLogManager().reset();
        launch(args);
    }
}
