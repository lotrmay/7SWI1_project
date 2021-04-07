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

    public static String getStringFromTime(Time time){
        if(time == null) throw new IllegalArgumentException("Vstupní parametr nesmí být null");

        Date myDate=new Date(time.getTime());
        DateFormat df=new SimpleDateFormat("H:mm");
        return df.format(myDate);
    }

    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
