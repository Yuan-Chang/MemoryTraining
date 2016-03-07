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

    @Override
    public String toString() {

        String s = seconds+"s";
        if (minutes > 0)
            s = minutes+"m "+s;
        if (hours > 0)
            s = hours+"h "+s;
        return s;
    }
}
