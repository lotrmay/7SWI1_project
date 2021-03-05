package cz.osu.carservice.models.utils;

public class ValidationUtils {
    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isNumberOnly(String text){
        return text.matches("[0-9]+");
    }
    public static String removeAllWhiteSpaces(String text){
        return text.replaceAll("\\s","");
    }
}
