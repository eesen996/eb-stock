package com.yun.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static Date string2Date(String strDate,String mask) throws ParseException {
        SimpleDateFormat df=new SimpleDateFormat(mask);
        return df.parse(strDate);
    }

    public static String getCurrentDate(String mask){
        SimpleDateFormat df=new SimpleDateFormat(mask);
        return df.format(new Date());
    }
}
