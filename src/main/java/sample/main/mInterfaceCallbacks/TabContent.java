package sample.main.mInterfaceCallbacks;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;


/**
 * Will make  on all Tabs
 */
public interface TabContent {
    boolean shouldClose ();
    void putFocusOnNode ();
    boolean loadData ();
    void setMainWindow (Stage stage);
    void setTabPane (TabPane tabPane);
}
