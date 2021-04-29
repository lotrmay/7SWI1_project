package cz.osu.swi.car_service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    /**
     * Checks if text is null or an empty String.
     *
     * @param text String to be checked
     * @return true if text is null or an empty String, otherwise returns false
     */
    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Checks if the input is a valid email address
     *
     * @param email String to be checked
     * @return true if text is a valid email address, otherwise returns false
     */
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Checks if text contains numbers only.
     *
     * @param text String to be checked
     * @return true if text contains only numbers, otherwise returns false
     */
    public static boolean isNumberOnly(String text){
        return text.matches("[0-9]+");
    }

    /**
     * Checks if text contains letters only.
     *
     * @param text String to be checked
     * @return true if text contains only letters, otherwise returns false
     */
    public static boolean isLetterOnly(String text){
        return text.chars().allMatch(Character::isLetter);
    }

    /**
     * Removes all white spaces from a String.
     *
     * @param text String to get white spaces removed
     * @return String without white spaces
     */
    public static String removeAllWhiteSpaces(String text){
        return text.replaceAll("\\s","");
    }

    /**
     * Sets the first character to uppercase and rest lowercase.
     *
     * @param text String to be modified
     * @return String with the first letter in uppercase and rest in lowercase
     */
    public static String firstUpperRestLower(String text){
        if (text.length() > 1) return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
        return text;
    }

    /**
     * Checks if the input is a valid telephone format.
     * String is considered a valid telephone number if it starts with 6 - 9 and continues with 8 single digit numbers.
     *
     * @param text String to be checked
     * @return true if String meets all criteria, otherwise it returns false
     */
    public static boolean checkTelephoneFormat(String text){
        return text.matches("[6-9][0-9]{8}");
    }
}
