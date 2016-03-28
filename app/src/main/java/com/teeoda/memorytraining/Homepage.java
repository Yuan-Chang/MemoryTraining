package com.teeoda.memorytraining;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pwittchen.prefser.library.Prefser;
import com.jakewharton.rxbinding.view.RxView;
import com.teeoda.memorytraining.Numbers.NumberSettingDetail;
import com.teeoda.memorytraining.Numbers.NumbersPage;
import com.teeoda.memorytraining.RecordBoard.RecordPage;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.GreenDAO.DBHelper;

import java.util.concurrent.TimeUnit;

public class Homepage extends BaseActivity {

    private ImageView mBrainImage;
    private TextView mTitle;
    private TextView mNumberButton;
    private TextView mRecordBoardButton;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-18 18:56:43 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mBrainImage = (ImageView)findViewById( R.id.brainImage );
        mTitle = (TextView)findViewById( R.id.title );
        mNumberButton = (TextView)findViewById( R.id.numberButton );
        mRecordBoardButton = (TextView)findViewById( R.id.recordBoardButton );
    }


    public void appInit()
    {
        if (G.getInstance().prefser == null)
            G.getInstance().prefser = new Prefser(this.getApplication());
        Prefser prefser = G.getInstance().prefser;

        if (!prefser.contains(G.NumberSettingDetail))
            prefser.put(G.NumberSettingDetail,new NumberSettingDetail());

        //initialize sqlite
        DBHelper.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //app initialization
        appInit();

        findViews();

        RxView.clicks(mNumberButton).throttleFirst(400, TimeUnit.MILLISECONDS)
                .subscribe(r -> {
                    Intent intent = new Intent(this, NumbersPage.class);
                    startActivity(intent);
                });

        RxView.clicks(mRecordBoardButton).throttleFirst(400, TimeUnit.MILLISECONDS)
                .subscribe(r->{
                    Intent intent = new Intent(this,RecordPage.class);
                    startActivity(intent);
                });

    }
}
