package sample.main.mMessages;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Created by Kajiva Kinsley on 7/17/2017.
 */
public final class mDialogs {
    private mDialogs() {
        new Exception("cant create an instance of this class");
    }

    public static void errorDialog(String title, String header, String explation, String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title + "Exception Dialog");
        alert.setHeaderText(header);
        alert.setContentText(explation);
        alert.initStyle(StageStyle.UTILITY);
        Exception ex = new FileNotFoundException(error);
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static void errorSimpleOKDialg(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    public static void warnningSimpleOKDialg(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    public static void infomationSimpleOKDialg(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    public static void infomationSimpleOKDialg(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * This will show an alert dialog box.<br>
     *
     * @param type 1-for CONFIRMATION , 2-for WARNING , 3-for Error
     * @return boolean    if yes returns true , if no returns false
     */
    public static boolean yesNoDialog(String title, String header, String message, String yesButton, String noButton, int type) {
        Alert alert = new Alert(type == 1 ? Alert.AlertType.CONFIRMATION : type == 2 ? Alert.AlertType.WARNING : Alert.AlertType.ERROR);
        alert.setTitle(title );
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UTILITY);
        ButtonType yesResponse = new ButtonType(yesButton);
        ButtonType noResponse = new ButtonType(noButton);


        //ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesResponse, noResponse/*, buttonTypeThree, buttonTypeCancel*/);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == yesResponse;
    }

    /**
     * This is a simple dialog box that can be removed any where the user clicks to close it.
     */
    public static void InfoDialog(StackPane stackPane, String title, String message) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        Text text = new Text();
        text.setText(title);
        text.setStyle("-fx-font: 9 arial; -fx-base: #ee2211;");
        dialogLayout.setHeading(text);
        dialogLayout.setBody(new Text(message));
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("okay");
        button.setStyle("-fx-font: 15 arial; -fx-base: #004cfc;");
        button.setOnAction(eve_ -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
    }

    /**
     * This will show a bottom snack bar message for 12 seconds
     */
    public static void simpleSnackBar(Pane pane, String message) {

        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(message, 12000);


    }
}
