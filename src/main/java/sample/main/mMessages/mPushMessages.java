package sample.main.mMessages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URISyntaxException;

public class mPushMessages {

    public void generalMessages (String title, String message) {
        try {
            Image image = new Image(getClass().getResource("/drawables/tick.png").toURI().toString());

            Notifications notificationBuilder = Notifications.create()
                    .title(title)
                    .text(message)
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(8))
                    .position(Pos.TOP_RIGHT)
                    .onAction(arg0 -> System.out.println("Notification clicked on!"));
            notificationBuilder.show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param title
     * @param message
     * @param type  error - 1 , success -2
     */
    public void dataBaseMessages (String title, String message , int type ) {
        try {

            Image image = new Image(getClass().getResource(type==1?"/drawables/db_error.png":"/drawables/db_success.png").toURI().toString());

            Notifications notificationBuilder = Notifications.create()
                    .title(title)
                    .text(message)
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(8))
                    .position(Pos.TOP_RIGHT)
                    .onAction(arg0 -> System.out.println("Notification clicked on!"));
            notificationBuilder.show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void generalErrorMessages (String title, String message){
        try{
            Image image  = new Image(getClass().getResource("/drawables/error.png").toURI().toString());

            Notifications notificationBuilder = Notifications.create()
                    .title(title)
                    .text(message)
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(8))
                    .position(Pos.TOP_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent arg0) {
                            System.out.println("Notification clicked on!");
                        }
                    });
            notificationBuilder.show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }
}
