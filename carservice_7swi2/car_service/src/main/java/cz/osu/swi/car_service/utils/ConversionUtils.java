package cz.osu.swi.car_service.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ConversionUtils {

    /**
     * Converts String to Time data type.
     * Uses {@link TextUtils#isTextEmpty(String)} to check the input.
     *
     * @param text string to be converted to Time
     * @return Time date type if the conversion was successful, otherwise returns null
     */
    public static Time getTimeFromString(String text) {
        if(TextUtils.isTextEmpty(text)) throw new IllegalArgumentException("Vstupní parametr nesmí být prázdný");

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return new Time(formatter.parse(text).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts Time to String data type in "H:mm" format.
     *
     * @param time Time to be converted to String
     * @return converted String
     */
    public static String getStringFromTime(Time time){
        Date myDate=new Date(time.getTime());
        DateFormat df=new SimpleDateFormat("H:mm");
        return df.format(myDate);
    }

    /**
     * Converts String to LocalDate data type in "yyyy-MM-dd" format.
     *
     * @param date String to be converted to LocalDate
     * @return converted LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
