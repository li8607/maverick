package com.maverick.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/28.
 */
public class TimeUtils {

    public static long getStartTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        return day.getTimeInMillis();
    }

    public static long getSevenDayStartTimeOfDay() {
        Calendar day = Calendar.getInstance();
        day.setTime(new Date());
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        return day.getTimeInMillis() - 7 * 24 * 3600 * 1000;
    }

}
