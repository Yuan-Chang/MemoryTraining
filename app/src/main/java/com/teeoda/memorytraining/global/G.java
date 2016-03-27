package com.teeoda.memorytraining.global;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.github.pwittchen.prefser.library.Prefser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by home on 2/28/16.
 */
public class G {
    private static G instance;

    public static G getInstance() {
        if (instance == null)
            instance = new G();
        return instance;
    }

    final static public String NumberSettingDetail = "NumberSettingDetail";

    public Prefser prefser;

    static public void runOnUIThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    static public void dismissKeyboard() {

        Activity activity = BaseActivity.getCurrent();
        if (activity == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            if (activity.getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    static public void showKeyboard(View view) {

        Activity activity = BaseActivity.mCurrentActivity;

        if (activity == null || view == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }

    public static boolean isYesterday(Date date)
    {
        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
            return true;

        return false;
    }

    public static boolean isSameYear(Date date)
    {
        Calendar c1 = Calendar.getInstance(); // today

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);

    }

    public String getDateFormattedString(Date date) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        String sDate = null;
        if (DateUtils.isToday(date.getTime())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            dateFormat.setTimeZone(tz);
            String time = dateFormat.format(date);

            if (time.startsWith("0"))
                time = time.substring(1, time.length());

            sDate = "Today, "+time;
        } else if (isYesterday(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            dateFormat.setTimeZone(tz);
            String time = dateFormat.format(date);

            if (time.startsWith("0"))
                time = time.substring(1, time.length());

            sDate = "Yesterday";
            sDate = sDate + " " + time;

        } else if (isSameYear(date)) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            timeFormat.setTimeZone(tz);
            String time = timeFormat.format(date);
            if (time.startsWith("0"))
                time = time.substring(1, time.length());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
            dateFormat.setTimeZone(tz);
            String datePart = dateFormat.format(date);
            sDate = datePart + ", " + time;

            //Feb 19, 11:00 PM

        } else {
                //Dec 04, 2015, 5:24 PM
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                dateFormat.setTimeZone(tz);
                String time = dateFormat.format(date);
                if (time.startsWith("0"))
                    time = time.substring(1, time.length());

                dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                dateFormat.setTimeZone(tz);
                String datePart = dateFormat.format(date);
                sDate = datePart + ", " + time;
        }

        return sDate;
    }
}
