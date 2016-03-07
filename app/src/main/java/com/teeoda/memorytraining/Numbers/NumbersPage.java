package com.teeoda.memorytraining.Numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.TimerTime;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NumbersPage extends BaseActivity {

    final public static class State{
        final public static int START = 0;
        final public static int MEMORY = 1;
        final public static int FILLING = 2;
        final public static int RESULT = 3;
    }

    NumberSettingDetail mSettingDetail;
    NumberAdapter mNumberAdpater;
    ArrayList<NumberItem> mNumbers;
    ArrayList<NumberItem> mFilledNumbers;
    int state = State.START;
    int mCurrentRoundNum = 1;
    rx.Subscription mTimerSub;
    TimerTime mTimerTime;

    private RelativeLayout mRootView;
    private GridView mGridview;
    private RelativeLayout mStartLayout;
    private RelativeLayout mRoundLayout;
    private TextView mRoundText;
    private TextView mRoundNum;
    private RelativeLayout mTotalNumsLayout;
    private TextView mTotalNumsText;
    private TextView mTotalNums;
    private RelativeLayout mMinNumLayout;
    private TextView mMinNumText;
    private TextView mMinNum;
    private RelativeLayout mMaxNumLayout;
    private TextView mMaxNumText;
    private TextView mMaxNum;
    private RelativeLayout mStartBtn;
    private RelativeLayout mResultLayout;
    private RelativeLayout mResultTitleLayout;
    private TextView mResultTitleNum;
    private TextView mResultTitleText;
    private RelativeLayout mCorrectnessLayout;
    private TextView mCorrectnessText;
    private TextView mResultCorrectness;
    private RelativeLayout mHintUsedLayout;
    private TextView mHintUsedText;
    private TextView mResultHintUsed;
    private RelativeLayout mTimeSpendLayout;
    private TextView mTimeSpendText;
    private TextView mResultTimeSpend;
    private RelativeLayout mBestRecordLayout;
    private TextView mBestRecordText;
    private TextView mResultBestRecord;
    private TextView mResultBeatRecord;
    private RelativeLayout mResultStartOver;
    private TextView mAlertText;
    private RelativeLayout mControlPannel;
    private LinearLayout mTimerLayout;
    private TextView mTimeText;
    private TextView mTime;
    private RelativeLayout mReStartBtn;
    private RelativeLayout mHintBtn;
    private RelativeLayout mDoneBtn;
    private TextView mDoneBtnText;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-07 07:20:29 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mRootView = (RelativeLayout)findViewById( 0 );
        mGridview = (GridView)findViewById( R.id.gridview );
        mStartLayout = (RelativeLayout)findViewById( R.id.startLayout );
        mRoundLayout = (RelativeLayout)findViewById( R.id.roundLayout );
        mRoundText = (TextView)findViewById( R.id.roundText );
        mRoundNum = (TextView)findViewById( R.id.roundNum );
        mTotalNumsLayout = (RelativeLayout)findViewById( R.id.totalNumsLayout );
        mTotalNumsText = (TextView)findViewById( R.id.totalNumsText );
        mTotalNums = (TextView)findViewById( R.id.totalNums );
        mMinNumLayout = (RelativeLayout)findViewById( R.id.minNumLayout );
        mMinNumText = (TextView)findViewById( R.id.minNumText );
        mMinNum = (TextView)findViewById( R.id.minNum );
        mMaxNumLayout = (RelativeLayout)findViewById( R.id.maxNumLayout );
        mMaxNumText = (TextView)findViewById( R.id.maxNumText );
        mMaxNum = (TextView)findViewById( R.id.maxNum );
        mStartBtn = (RelativeLayout)findViewById( R.id.startBtn );
        mResultLayout = (RelativeLayout)findViewById( R.id.resultLayout );
        mResultTitleLayout = (RelativeLayout)findViewById( R.id.resultTitleLayout );
        mResultTitleNum = (TextView)findViewById( R.id.resultTitleNum );
        mResultTitleText = (TextView)findViewById( R.id.resultTitleText );
        mCorrectnessLayout = (RelativeLayout)findViewById( R.id.correctnessLayout );
        mCorrectnessText = (TextView)findViewById( R.id.correctnessText );
        mResultCorrectness = (TextView)findViewById( R.id.resultCorrectness );
        mHintUsedLayout = (RelativeLayout)findViewById( R.id.hintUsedLayout );
        mHintUsedText = (TextView)findViewById( R.id.hintUsedText );
        mResultHintUsed = (TextView)findViewById( R.id.resultHintUsed );
        mTimeSpendLayout = (RelativeLayout)findViewById( R.id.timeSpendLayout );
        mTimeSpendText = (TextView)findViewById( R.id.timeSpendText );
        mResultTimeSpend = (TextView)findViewById( R.id.resultTimeSpend );
        mBestRecordLayout = (RelativeLayout)findViewById( R.id.bestRecordLayout );
        mBestRecordText = (TextView)findViewById( R.id.bestRecordText );
        mResultBestRecord = (TextView)findViewById( R.id.resultBestRecord );
        mResultBeatRecord = (TextView)findViewById( R.id.resultBeatRecord );
        mResultStartOver = (RelativeLayout)findViewById( R.id.resultStartOver );
        mAlertText = (TextView)findViewById( R.id.alertText );
        mControlPannel = (RelativeLayout)findViewById( R.id.controlPannel );
        mTimerLayout = (LinearLayout)findViewById( R.id.timerLayout );
        mTimeText = (TextView)findViewById( R.id.timeText );
        mTime = (TextView)findViewById( R.id.time );
        mReStartBtn = (RelativeLayout)findViewById( R.id.reStartBtn );
        mHintBtn = (RelativeLayout)findViewById( R.id.hintBtn );
        mDoneBtn = (RelativeLayout)findViewById( R.id.DoneBtn );
        mDoneBtnText = (TextView)findViewById( R.id.doneBtnText );
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Number training");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setSupportActionBar(toolbar);

        findViews();

        showStartPage();

        RxView.clicks(mStartBtn).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {

            showMemoryPage();

            mTimerTime = new TimerTime();
            mTimerSub = rx.Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(r1->{
                        mTimerTime.increase();
                        mTime.setText(mTimerTime.toString());
                    });

        });

        RxView.clicks(mReStartBtn).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            showStartPage();
        });

        RxView.clicks(mDoneBtn).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            if (state == State.MEMORY)
                showFillingPage();
            else if (state == State.FILLING)
                showResultPage();
        });

        RxView.clicks(mResultStartOver).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            mCurrentRoundNum++;
            showStartPage();
        });

        RxView.clicks(mHintBtn).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            showHint();
        });



    }

    void showHint(){

    }

    void showStartPage() {
        state = State.START;
        mSettingDetail = G.getInstance().prefser.get(G.NumberSettingDetail,NumberSettingDetail.class,new NumberSettingDetail());
        mTotalNums.setText(mSettingDetail.totalNum + "");
        mMinNum.setText(mSettingDetail.minNum + "");
        mMaxNum.setText(mSettingDetail.maxNum + "");
        mRoundNum.setText(mCurrentRoundNum + "");
        mStartLayout.setVisibility(View.VISIBLE);
        mAlertText.setVisibility(View.VISIBLE);
        mGridview.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.GONE);
        mResultLayout.setVisibility(View.GONE);
        mTime.setText("0s");

        if (mTimerSub != null)
            mTimerSub.unsubscribe();
    }

    void showResultPage(){
        state = State.RESULT;

        mGridview.setVisibility(View.VISIBLE);
        mStartLayout.setVisibility(View.GONE);
        mAlertText.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.GONE);
        mResultLayout.setVisibility(View.VISIBLE);

        if (mTimerSub != null)
            mTimerSub.unsubscribe();
        mResultTimeSpend.setText(mTimerTime.toString());
        mResultTitleNum.setText(mSettingDetail.totalNum+"");

        int correctNum = 0;
        for (int i = 0; i<mSettingDetail.totalNum ; i++)
        {
            if (mNumbers.get(i) == mFilledNumbers.get(i))
                correctNum++;
        }
        mResultCorrectness.setText(String.format("%d/%d",correctNum,mSettingDetail.totalNum));

    }

    void showMemoryPage(){
        state = State.MEMORY;
        mGridview.setVisibility(View.VISIBLE);
        mStartLayout.setVisibility(View.GONE);
        mAlertText.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);

        mReStartBtn.setVisibility(View.VISIBLE);
        mHintBtn.setVisibility(View.GONE);
        mDoneBtnText.setText("Start");

        if (mNumbers == null)
            mNumbers = new ArrayList<NumberItem>();
        else
            mNumbers.clear();

        ArrayList<NumberItem> numbers = mNumbers;
        for (int i = 0; i<mSettingDetail.totalNum; i++)
        {
            numbers.add(new NumberItem(randInt(mSettingDetail.minNum,mSettingDetail.maxNum)));
        }
        mNumberAdpater = new NumberAdapter(numbers);
        mGridview.setAdapter(mNumberAdpater);
    }

    void showFillingPage(){
        state = State.FILLING;
        mGridview.setVisibility(View.VISIBLE);
        mStartLayout.setVisibility(View.GONE);
        mAlertText.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);

        mHintBtn.setVisibility(View.VISIBLE);
        mDoneBtnText.setText("Result");

        ArrayList<NumberItem> numbers = new ArrayList<>();
        for (int i = 0; i<mSettingDetail.totalNum; i++)
        {
            numbers.add(new NumberItem(-1));
        }
        mFilledNumbers = numbers;
        mNumberAdpater = new NumberAdapter(numbers);
        mGridview.setAdapter(mNumberAdpater);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_numbers_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.restart:
                mCurrentRoundNum = 1;
                showStartPage();
                break;
            case R.id.settings:
                Intent intent = new Intent(this,NumberSettingPage.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
