package com.teeoda.memorytraining.global;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by home on 2/28/16.
 */
public class BaseActivity extends AppCompatActivity {
    static public BaseActivity mPreviousAcitvity = null;
    static public BaseActivity mCurrentActivity = null;
    static public CustomDefaultProgressDialog mProgressDialog;
    static public Timer mProgressDialogTimer;

    static private Timer mActivityTransitionTimer;
    static private TimerTask mActivityTransitionTimerTask;

    static private boolean mShowUserPresence = false;
    static private Timer mUserPresenceTimer = null;

    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPreviousAcitvity = this;
        super.onCreate(savedInstanceState);
        mCurrentActivity = this;

    }



    @Override
    protected void onResume() {

        mCurrentActivity = this;
        mProgressDialog = new CustomDefaultProgressDialog(this);

        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasInBackground) {
            //Do specific came-here-from-background code
            onBackFromBackground();
        }
        stopActivityTransitionTimer();
    }

    protected void onBackFromBackground() {
    }

    protected void onGoIntoBackground() {

    }


    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();

    }

    @Override
    protected void onPause() {
        super.onPause();
        startActivityTransitionTimer();
        dismissProgressDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public static void showProgressDialog() {
        G.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setTitle(CustomDefaultProgressDialog.DEFAULT_TITLE);
                mProgressDialog.show();

                if (mProgressDialogTimer != null)
                    mProgressDialogTimer.cancel();
                mProgressDialogTimer = new Timer();
                mProgressDialogTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                    }
                }, 20000);
            }
        });

    }

    public static void showProgressDialog(final String title) {
        G.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setTitle(title);
                mProgressDialog.show();
                Log.d("yuan_progress_dialog", "called in acitivity:" + BaseActivity.mCurrentActivity.getClass());
            }
        });

    }

    public static void dismissProgressDialog() {
        G.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialogTimer != null)
                    mProgressDialogTimer.cancel();
                mProgressDialog.dismiss();
            }
        });

    }

    public static boolean isProgressDialogCancelled() {
        return mProgressDialog.isCancelled();
    }

    public void startActivityTransitionTimer() {
        mActivityTransitionTimer = new Timer();
        mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                Log.d("yuan_transition_timer", "onGoIntoBackground");

                wasInBackground = true;
                onGoIntoBackground();

                //UpdateUserPresence 5mins
                if (mUserPresenceTimer != null)
                    mUserPresenceTimer.cancel();
                mUserPresenceTimer = new Timer();
                mUserPresenceTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mShowUserPresence = true;
                        Log.d("yuan_update_presence", "start timer");
                    }
                }, 1000);


            }
        };

        mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);

        Log.d("yuan_transition_timer", "go into background , start task");

    }

    public void stopActivityTransitionTimer() {
        if (mActivityTransitionTimerTask != null) {
            mActivityTransitionTimerTask.cancel();
        }

        if (mActivityTransitionTimer != null) {
            mActivityTransitionTimer.cancel();
        }

        Log.d("yuan_transition_timer", "back in 2 secs stop task");

        this.wasInBackground = false;
    }

    public boolean isInBackground() {
        return wasInBackground;
    }

    static public BaseActivity getCurrent()
    {
        return mCurrentActivity;
    }

}
