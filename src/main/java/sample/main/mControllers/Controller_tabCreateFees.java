package sample.main.mControllers;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.main.mInterfaceCallbacks.TabContent;

public class Controller_tabCreateFees implements TabContent {
    private TabPane tabPane = null;
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
    public void initialize(){

    }
    /*
    Close the current Tab
     */
    private void closeTab() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tabPane.getTabs().remove(tab);
    }
    @Override
    public void setTabPane (TabPane pane) {
        this.tabPane = pane;
    }
}
