package com.example.paper.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    public static LocalDateTime StringToLocalDateTime(String time){
        return LocalDateTime.parse(time.replace(" ","T"));

    }
    public static LocalDate StringToLocalDateTime(String format,String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(time,df);
    }
}
