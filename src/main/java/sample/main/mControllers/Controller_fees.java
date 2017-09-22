package sample.main.mControllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;
import sample.main.mInterfaceCallbacks.TabContent;
import sample.main.mMessages.mPushMessages;
import sample.main.mframeWork.StageManager;
import sample.main.mframeWork.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static sample.main.mframeWork.StageManager.getStage;

public class Controller_fees /*implements Initializable*/ {
    @FXML
    private TabPane tabePane;
    @FXML
    private JFXButton  btnCreateFees, btnFeesPayment,btnFeesList;
    @FXML
    private HBox tabOptions;

  /*  @Override*/
  /*
  Will be called by the JavaFX class loader
   */
    public void initialize () /*(URL location, ResourceBundle resources)*/ {
        initFrameWork();
        initResorces();
        initClickListeners();

    }

    private void initResorces () {
        btnFeesPayment.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnFeesPayment.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });
        btnCreateFees.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnCreateFees.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });

        btnFeesList.setOnMouseEntered(ev -> {
            getStage().getScene().setCursor(Cursor.HAND);
        });
        btnFeesList.setOnMouseExited(e -> {
            getStage().getScene().setCursor(Cursor.DEFAULT);
        });

    }

    private void initClickListeners () {
        btnCreateFees.setOnAction(ev -> {
            addTab(ViewController.TAB_CREATE_FEES);
        });
        btnFeesPayment.setOnAction(ev -> {
            addTab((ViewController.TAB_FEES_PAYMENT));
        });
        btnFeesList.setOnAction(ev->{
            addTab((ViewController.TAB_VIEW_FES_LIST));
        });
    }

    private void setCloseOtherTabsAction (final Tab tab, final MenuItem menuItem) {
        final EventHandler<ActionEvent> eventHandler = (ActionEvent event) -> {
            final TabPane tabPane = tab.getTabPane();
            // Global.closeTabs(tabPane, tab);
        };

        menuItem.setOnAction(eventHandler);
    }

    private void setCloseAllTabsAction (final Tab tab, final MenuItem menuItem) {
        final EventHandler<ActionEvent> eventHandler = (ActionEvent event) -> {
            closeAllTabs();
        };
        menuItem.setOnAction(eventHandler);
    }

    public boolean closeAllTabs () {
        final ObservableList<Tab> tabs = tabePane.getTabs();
        final List<Tab> tabsToRemove = new ArrayList<>(tabs.size());
        for (Tab tabControl : tabs) {
            tabePane.getSelectionModel().select(tabControl);
            TabContent controller = (TabContent) tabControl.getProperties().get("controller");
            if (! controller.shouldClose()) {
                return false;
            } else {
                tabsToRemove.add(tabControl); //mark this tab to be removed
            }
        }

        tabs.removeAll(tabsToRemove); //actually remove the tags here
        return true;
    }

    private void setCloseTabAction (final Tab tab, final MenuItem menuItem) {

        final EventHandler<ActionEvent> eventHandler = (ActionEvent event) -> {
            final TabPane tabPane = tab.getTabPane();
            tabPane.getSelectionModel().select(tab);
            TabContent controller = (TabContent) tab.getProperties().get("controller");
            if (controller.shouldClose()) {
                tabPane.getTabs().remove(tab);
            }
        };

        menuItem.setOnAction(eventHandler);
    }

    private void addTab (ViewController tab) {
        final String KEY = "fxml";
        tabePane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    /*Lets ensure that there is only one instance of one tab or class or object*/
        ObservableList<Tab> tabs = tabePane.getTabs();
        for (Tab tabinstance : tabs) {
            if (tabinstance.getProperties().get(KEY).toString().equalsIgnoreCase(tab.getFxmlFile())) {
                tabePane.getSelectionModel().select(tabinstance);
                return;
            }
        }

        FXMLLoader loader = new FXMLLoader();
        URL res = this.getClass().getResource(tab.getFxmlFile());
        loader.setLocation(res);
        Parent rootPane;
        try {
            rootPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            new mPushMessages().generalErrorMessages("Error ", "Error in opening Tab " + tab.getTitle() + "Please try again !");
            return;
        }
        final TabContent content = loader.getController();
        content.setMainWindow(StageManager.getStage());
        content.setTabPane(tabePane);
        if (! content.loadData()) {

           // return;
        }
        final String cssDefault =
                "       -fx-shadow-highlight-color, \n" +
                "       -fx-outer-border, \n" +
                "       -fx-inner-border, \n" +

                "  -fx-background-insets: 0 0 -1 0, 0, 1, 2;\n" +
                "  -fx-background-radius: 3px, 3px, 2px, 1px;";
        Tab tab_ = new Tab();
        tab_.getProperties().put("controller", content);
        tab_.getProperties().put(KEY, tab.getFxmlFile());
        tab_.setContent(rootPane);
        tab_.setText(tab.getTitle());
        tab_.setStyle(cssDefault);
        setContextMenu(tab_);
        tab_.setOnCloseRequest((Event event1) -> {
            if (!content.shouldClose()) {
                event1.consume();
            }
        });
        tabePane.getTabs().add(tab_);
        tabePane.getSelectionModel().select(tab_);
        content.putFocusOnNode();


    }
    private void setContextMenu(final Tab tab) {

        final MenuItem closeTabItem = new MenuItem("Close Tab");
        final MenuItem closeOtherTabsItem = new MenuItem("Close Other Tabs");
        final MenuItem closeAllTabsItem = new MenuItem("Close All Tabs");

        final ContextMenu contextMenu = new ContextMenu(closeTabItem, closeOtherTabsItem,
                closeAllTabsItem);

        setCloseTabAction(tab, closeTabItem);
        setCloseOtherTabsAction(tab, closeOtherTabsItem);
        setCloseAllTabsAction(tab, closeAllTabsItem);

        tab.setContextMenu(contextMenu);
    }
    private void initFrameWork () {
        tabePane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab old, Tab newVal) -> {
            if (newVal != null) {
                Platform.runLater(() -> {
                    Object o = newVal.getProperties().get("controller");
                    if (o != null) {
                        ((TabContent) o).putFocusOnNode();
                    }else{
                       // new mPushMessages().generalMessages("Tabs Information","That tab is open");
                    }
                });
            }
        });
        tabOptions.managedProperty().bind(tabOptions.visibleProperty());

    }
    private void closeCommand(){
        getStage().fireEvent(new WindowEvent(getStage(),WindowEvent.WINDOW_CLOSE_REQUEST));

    }
}
