package sample.main.mControllers;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.main.mInterfaceCallbacks.TabContent;

public class Controller_tabFeesPayment implements TabContent {

    @Override
    public boolean shouldClose () {
        return false;
    }

    @Override
    public void putFocusOnNode () {

    }

    @Override
    public boolean loadData () {
        return false;
    }

    @Override
    public void setMainWindow (Stage stage) {

    }

    @Override
    public void setTabPane (TabPane tabPane) {

    }
}
