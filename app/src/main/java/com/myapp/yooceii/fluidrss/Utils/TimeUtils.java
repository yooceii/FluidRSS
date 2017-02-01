package com.myapp.yooceii.fluidrss.Utils;

import android.content.Context;
import android.content.res.Resources;

import com.myapp.yooceii.fluidrss.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yooceii on 2016/11/8.
 */

public class TimeUtils {
    public static Calendar string2Calendar(String date)
    {
        DateFormat dateFormatterRssPubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        Calendar cc=Calendar.getInstance();
        try
        {
            cc.setTime(dateFormatterRssPubDate.parse(date));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cc;
    }
    public static String timadifference(Calendar time,Context context)
    {
        Calendar now=Calendar.getInstance();
        Date nowdate=now.getTime();
        Date date=time.getTime();
        Long l=nowdate.getTime()-date.getTime();
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        if(day<1)
            return hour+" "+ context.getString(R.string.hour);
        else
            return day+" "+context.getString(R.string.day)+hour+" "+ context.getString(R.string.hour);
    }

}
