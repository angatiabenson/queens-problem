package sample.algorithm;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationWorker {

    public Notifications notify(String text, Node node){
        return Notifications.create()
                .text(text)
                .owner(node)
                .title("QPVisualizer")
                .hideAfter(Duration.seconds(3));
    }
    public Notifications darkNotify(String text, Node node){
        return Notifications.create()
                .text(text)
                .owner(node)
                .darkStyle()
                .title("QPVisualizer")
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_CENTER);
    }
}
