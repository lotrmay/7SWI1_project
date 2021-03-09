package cz.osu.carservice.models.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class FormUtils {
    //region Style constants for Service Button
    private static final String HEX_COLOR_WHITE = "#ffffff";
    private static final String HEX_COLOR_BLACK = "#000000";
    private static final String HEX_COLOR_GREEN = "#8cff66";
    private static final String HEX_COLOR_RED = "#f55d42";
    private static final String BORDER_RADIUS = "60";
    private static final String BACKGROUND_RADIUS = "60";
    //endregion

    public static void setServiceBtnGreen(Button btn) {
        btn.setStyle(String.format("-fx-background-color: %s;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-radius: %s;" +
                        "-fx-background-radius: %s;",
                HEX_COLOR_GREEN, HEX_COLOR_BLACK, BORDER_RADIUS, BACKGROUND_RADIUS));
    }

    public static void setServiceBtnWhite(Button btn) {
        btn.setStyle(String.format("-fx-background-color: %s;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-radius: %s;" +
                        "-fx-background-radius: %s;",
                HEX_COLOR_WHITE, HEX_COLOR_BLACK, BORDER_RADIUS, BACKGROUND_RADIUS));
    }

    public static void setTextAndColorToLabel(Label label,String text,String color){
        label.setText(text);
        label.setStyle(String.format("-fx-text-fill: %s;", color));
    }

    public static void setTextAndGreenColorToLabel(Label label,String text){
        label.setText(text);
        label.setStyle(String.format("-fx-text-fill: %s;", HEX_COLOR_GREEN));
    }

    public static void setTextAndRedColorToLabel(Label label,String text){
        label.setText(text);
        label.setStyle(String.format("-fx-text-fill: %s;", HEX_COLOR_RED));
    }
}
