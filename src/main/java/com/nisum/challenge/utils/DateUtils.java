package com.nisum.challenge.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtils {

    private static final DateUtils INSTANCE;

    static{
        INSTANCE = new DateUtils();
    }

    private DateUtils(){

    }

    public static DateUtils instance() {
        return INSTANCE;
    }

    public Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
