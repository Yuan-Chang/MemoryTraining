package com.teeoda.memorytraining.Numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.pwittchen.prefser.library.Prefser;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.TimerTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NumbersPage extends BaseActivity {

    static public boolean restart = false;
    static public int state = State.START;

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

    int mCurrentRoundNum = 1;
    rx.Subscription mTimerSub;
    TimerTime mTimerTime;
    int mHintUsed = 0;
    int mPreSelectedPos = -1;
    boolean mFillingDone = false;


    private RelativeLayout mRootView;
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
    private GridView mGridview;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-08 23:24:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mRootView = (RelativeLayout)findViewById( 0 );
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
        mGridview = (GridView)findViewById( R.id.gridview );
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
            {
                if (mFillingDone)
                {
                    showResultPage();
                }
                else {
                    mFillingDone = true;
                    int current = mPreSelectedPos;
                    if (current == -1)
                    {
                        mFillingDone = false;
                        mFilledNumbers.get(0).isSelected = true;
                        mPreSelectedPos = 0;
                    }
                    else {
                        //go to next
                        current ++;
                        current = current%mSettingDetail.totalNum;

                        while(current != mPreSelectedPos)
                        {
                            if (mFilledNumbers.get(current).num == -1)
                            {
                                mFilledNumbers.get(mPreSelectedPos).isSelected =false;
                                mPreSelectedPos = current;
                                mFilledNumbers.get(current).isSelected = true;
                                mFillingDone = false;
                                break;
                            }
                            current ++;
                            current = current%mSettingDetail.totalNum;
                        }

                    }

                    if (mFillingDone)
                    {
                        if (mDoneBtnText.getText().toString().equals("Next"))
                            mDoneBtnText.setText("Result");
                        else
                            showResultPage();

                    }
                    else {
                        mNumberAdpater.notifyDataSetChanged();
                        mGridview.smoothScrollToPosition(mPreSelectedPos);
                    }
                }

            }
        });

        RxView.clicks(mResultStartOver).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            mResultBeatRecord.setVisibility(View.GONE);
            mCurrentRoundNum++;
            showStartPage();
        });

        //not used
        RxView.clicks(mHintBtn).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            showHint();
        });



    }

    void showHint(){
        mHintUsed++;
        for (int i = 0; i<mSettingDetail.totalNum ; i++)
        {
            if (mNumbers.get(i) != mFilledNumbers.get(i))
                mFilledNumbers.get(i).showHint = true;
        }
        mNumberAdpater.notifyDataSetChanged();

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
        mPreSelectedPos = -1;
        mFillingDone = false;

        if (mFilledNumbers != null)
            mFilledNumbers.clear();

        if (mNumbers != null)
            mNumbers.clear();

        G.dismissKeyboard();

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
        mResultLayout.bringToFront();
        mResultLayout.requestFocus();

        G.dismissKeyboard();

        if (mTimerSub != null)
            mTimerSub.unsubscribe();
        mResultTimeSpend.setText(mTimerTime.toString());
        mResultTitleNum.setText(mSettingDetail.totalNum + "");

        mResultHintUsed.setText(mHintUsed + "");

        int correctNum = 0;
        for (int i = 0; i<mSettingDetail.totalNum ; i++)
        {
            if (mNumbers.get(i).num == mFilledNumbers.get(i).num)
                correctNum++;
        }
        mResultCorrectness.setText(String.format("%d/%d", correctNum, mSettingDetail.totalNum));

        Prefser prefser = G.getInstance().prefser;
        String key = "NumberHistroy:"+mSettingDetail.totalNum;
        int record = prefser.get(key, Integer.class, -1);
        if (record == -1)
        {
            prefser.put(key,mTimerTime.toSeconds());
        }
        else if (record > mTimerTime.toSeconds() && correctNum == mSettingDetail.totalNum)
        {
            prefser.put(key,mTimerTime.toSeconds());
            record = mTimerTime.toSeconds();
            mResultBeatRecord.setVisibility(View.VISIBLE);
        }
        String sTime = TimerTime.secondsToString(record);
        mResultBestRecord.setText(sTime);
        if (sTime.length() > 7)
            mResultBestRecord.setTextSize(14);
        else
            mResultBestRecord.setTextSize(20);

    }



    void showMemoryPage(){
        state = State.MEMORY;
        mGridview.setVisibility(View.VISIBLE);
        mStartLayout.setVisibility(View.GONE);
        mAlertText.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.VISIBLE);
        mResultLayout.setVisibility(View.GONE);
        //mGridview.setClickable(false);
        mReStartBtn.setVisibility(View.VISIBLE);
        mHintBtn.setVisibility(View.GONE);
        mDoneBtnText.setText("Next");
        mControlPannel.bringToFront();

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
        //mGridview.setClickable(true);
        //mHintBtn.setVisibility(View.VISIBLE);
        mDoneBtnText.setText("Next");

        ArrayList<NumberItem> numbers = new ArrayList<>();
        for (int i = 0; i<mSettingDetail.totalNum; i++)
        {
            numbers.add(new NumberItem(-1));
        }
        mFilledNumbers = numbers;

        mNumberAdpater = new NumberAdapter(mFilledNumbers);
        mGridview.setAdapter(mNumberAdpater);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        RxAdapterView.itemClicks(mGridview).throttleFirst(300,TimeUnit.MILLISECONDS)
                .subscribe(position -> {
                    if (mPreSelectedPos != -1)
                        mFilledNumbers.get(mPreSelectedPos).isSelected = false;
                    mPreSelectedPos = position;

                    mFilledNumbers.get(position).isSelected = true;

                    mGridview.smoothScrollToPosition(position);

                    mNumberAdpater.notifyDataSetChanged();
                    Log.d("yuan", "item click pos : " + position);

                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_numbers_page, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (restart)
        {
            restart = false;
            showStartPage();
        }
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
