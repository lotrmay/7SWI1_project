package cz.osu.carservice.models.utils;

public class TextUtils {
    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
