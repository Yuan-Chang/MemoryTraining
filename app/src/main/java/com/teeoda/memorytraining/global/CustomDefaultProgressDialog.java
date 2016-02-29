package com.teeoda.memorytraining.global;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teeoda.memorytraining.R;

/**
 * Created by home on 2/28/16.
 */
public class CustomDefaultProgressDialog {
    public static final String DEFAULT_TITLE = "Loading in progress";

    TextView title;
    RelativeLayout backgroundLayout;
    RelativeLayout rootLayout;
    ProgressBar progressBar;
    //ImageView runningDabby;
    Activity a;
    View view;
    boolean isCancelled = true;
    boolean cancelable = false;
    public Object lock;


    public CustomDefaultProgressDialog(Activity a) {
        LayoutInflater inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_default_progress_dialog, null);
        title = (TextView) view.findViewById(R.id.title);
        backgroundLayout = (RelativeLayout) view.findViewById(R.id.backgroundLayout);
        rootLayout = (RelativeLayout) view.findViewById(R.id.rootLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        // runningDabby = (ImageView) view.findViewById(R.id.running_dabby);
        a.addContentView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setVisibility(View.GONE);
        this.a = a;
        lock = new Object();

        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel();
            }
        });
    }

//    public void setCancelable(boolean cancelable) {
//        this.cancelable = cancelable;
//    }

    public void show() {
        synchronized (lock) {
            isCancelled = false;
            view.setVisibility(View.VISIBLE);
            view.bringToFront();
        }
    }

    public void dismiss() {
        cancel();
    }

    public boolean isShowing() {
        return view.getVisibility() == View.VISIBLE;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void cancel() {

        synchronized (lock) {
            isCancelled = true;
            view.setVisibility(View.GONE);

            if (onCancelListener != null)
                onCancelListener.onCancel(CustomDefaultProgressDialog.this);
        }

    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public OnCancelListener onCancelListener;

    public interface OnCancelListener {
        void onCancel(CustomDefaultProgressDialog dialog);
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
    }
}
