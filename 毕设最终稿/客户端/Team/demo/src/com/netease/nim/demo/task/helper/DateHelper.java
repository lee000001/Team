package com.netease.nim.demo.task.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        return format.format(date);
    }

    public static Date getTime(String times) throws ParseException {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        return format.parse(times);
    }
}
