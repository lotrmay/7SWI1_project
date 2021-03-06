package cz.osu.carservice.models.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConversionUtils {

    public static Time getTimeFromString(String text) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return new Time(formatter.parse(text).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
