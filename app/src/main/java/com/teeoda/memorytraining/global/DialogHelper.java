package com.teeoda.memorytraining.global;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by home on 3/8/16.
 */
public class DialogHelper {

    public static class Title{


    }

    public static class Message{
        public final static String applyChanges = "Do you want to restart the session to apply the new settings ?";

    }
    public static void popupAlertDialog(Context c,String title, String message, String yesText, final Runnable yes,String noText,final Runnable no)
    {
        popupAlertDialog(c,title,message,true,yesText,yes,noText,no);
    }

    public static void popupAlertDialog(Context c,String title, String message, boolean cancelable,String yesText, final Runnable yes,String noText,final Runnable no)
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setCancelable(cancelable);

        if(title == null) {
            builder.setMessage(message);
        }
        else {
            builder.setTitle(title);
            builder.setMessage(message);
        }

        if(noText != null) {
            builder.setPositiveButton(yesText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (yes != null) {

                        Thread thread = new Thread(yes);
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            builder.setNegativeButton(noText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Thread thread = new Thread(no);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            builder.setPositiveButton(yesText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (yes != null) {

                        Thread thread = new Thread(yes);
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        builder.show();


    }

    public static void popupAlertDialog(Context c,String title, String message,String yesText)
    {
        popupAlertDialog(c, title, message, yesText, null, null, null);
    }

    public static void popupAlertDialog(Context c,String title, String message,String yesText,Runnable finishRunnable)
    {
        popupAlertDialog(c,title,message,yesText,finishRunnable,null,null);
    }

    //Deepak added
    //16Jun15
    //Thanks Vallabh
    //checking weather the intent (market) is present or not
    public static boolean MyStartActivity(Activity mActivity, Intent aIntent) {
        try {
            mActivity.startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

}

