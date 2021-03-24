package cz.osu.carservice.models.utils;

import javafx.scene.control.*;

public class FormUtils {
    //region Style constants for Service Button
    private static final String BORDER_RADIUS = "60";
    private static final String BACKGROUND_RADIUS = "60";
    //endregion

    public static void changeServiceButtonColor(Button btn,String color){
        if (btn == null) throw new IllegalArgumentException("Parametr btn nesmí být null");
        if (TextUtils.isTextEmpty(color)) throw new IllegalArgumentException("Parametr color nesmí být null");

        btn.setStyle(String.format("-fx-background-color: %s;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-radius: %s;" +
                        "-fx-background-radius: %s;",
                color, Colors.HEX_COLOR_BLACK, BORDER_RADIUS, BACKGROUND_RADIUS));
    }
    public static void setTextAndColorToLabel(Label label,String text,String color){
        if (label == null) throw new IllegalArgumentException("Parametr label nesmí být null");
        if (TextUtils.isTextEmpty(text)) throw new IllegalArgumentException("Parametr text nesmí být null");
        if (TextUtils.isTextEmpty(color)) throw new IllegalArgumentException("Parametr color nesmí být null");

        label.setText(text);
        label.setStyle(String.format("-fx-text-fill: %s;", color));
    }

    public static void setTextAndDisable(TextInputControl control, String text){
        if (control == null) throw new IllegalArgumentException("Parametr control nesmí být null!");

        control.setEditable(false);
        control.setText(text);
    }

}
