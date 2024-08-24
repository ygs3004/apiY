package kr.co.apiy.global.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static String LocalDateToFormat(LocalDateTime localDateTime, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static String LocalDateToGlobalFormat(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.GLOBAL_DATE_FORMAT);
        return localDate.format(formatter);
    }

    public static String LocalDateTimeToGlobalFormat(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT);
        return localDateTime.format(formatter);
    }
}
