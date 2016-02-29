package com.teeoda.memorytraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.teeoda.memorytraining.global.BaseActivity;

import java.util.concurrent.TimeUnit;

public class Homepage extends BaseActivity {

    private ImageView mBrainImage;
    private TextView mTitle;
    private TextView mNumberButton;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-28 13:17:11 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mBrainImage = (ImageView)findViewById( R.id.brainImage );
        mTitle = (TextView)findViewById( R.id.title );
        mNumberButton = (TextView)findViewById( R.id.numberButton );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        findViews();

        RxView.clicks(mNumberButton).throttleFirst(400, TimeUnit.MILLISECONDS)
                .subscribe(r->{
                    Intent intent = new Intent(this,NumbersPage.class);
                    startActivity(intent);
                });

    }
}
