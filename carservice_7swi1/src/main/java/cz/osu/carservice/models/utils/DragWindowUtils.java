package cz.osu.carservice.models.utils;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class DragWindowUtils {
    private static double x;
    private static double y;

    public static void moveWindow(Parent root, Stage primaryStage){
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - x);
            primaryStage.setY(e.getScreenY() - y);
        });

    }
}
