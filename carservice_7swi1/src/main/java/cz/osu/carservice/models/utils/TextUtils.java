package cz.osu.carservice.models.utils;

import javafx.scene.control.Label;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isNumberOnly(String text){
        return text.matches("[0-9]+");
    }
    public static boolean isLetterOnly(String text){
        return text.chars().allMatch(Character::isLetter);
    }
    public static String removeAllWhiteSpaces(String text){
        return text.replaceAll("\\s","");
    }
    public static String firstUpperRestLower(String text){
        if (text.length() > 1) return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
        return text;
    }
    public static boolean checkTelephoneFormat(String text){
        return text.matches("[6-9][0-9]{8}");
    }
    public static boolean checkLengthOfText(Label infoLbl, String text, int maxLength, int minLength, String maxErrorMessage, String minErrorMessage)
    {
        boolean temp = false;

        if (text.length() < minLength)
        {
            FormUtils.setTextAndRedColorToLabel(infoLbl,minErrorMessage);
            temp = true;
        }
        else if (text.length() > maxLength)
        {
            FormUtils.setTextAndRedColorToLabel(infoLbl,maxErrorMessage);
            temp = true;
        }

        return temp;
    }
}
