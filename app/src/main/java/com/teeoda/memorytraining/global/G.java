package com.teeoda.memorytraining.global;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.github.pwittchen.prefser.library.Prefser;

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

    static public void dismissKeyboard(Activity activity) {

        if (activity == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            if (activity.getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    static public void showKeyboard(Activity activity, View view) {

        if (activity == null || view == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }
}
