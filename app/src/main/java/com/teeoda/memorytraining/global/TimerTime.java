package com.teeoda.memorytraining.global;

/**
 * Created by home on 3/7/16.
 */
public class TimerTime {
    int seconds = 0;
    int minutes = 0;
    int hours = 0;

    public void increase()
    {
        seconds ++ ;
        if (seconds >= 60)
        {
            minutes++;
            seconds = 0;
        }

        if (minutes >= 60)
        {
            hours++;
            minutes = 0;
        }
    }

    public int toSeconds()
    {
        return hours*3600+minutes*60+seconds;
    }

    @Override
    public String toString() {

        String s = seconds+"s";
        if (minutes > 0)
            s = minutes+"m "+s;
        if (hours > 0)
            s = hours+"h "+s;
        return s;
    }

    public static String secondsToString(int seconds)
    {
        int h = seconds/3600;
        seconds -= h*3600;
        int m = seconds/60;
        seconds -= m*60;

        String s = seconds+"s";
        if (m > 0)
            s = m+"m "+s;
        if (h > 0)
            s = h+"h "+s;
        return s;
    }
}
