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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.pwittchen.prefser.library.Prefser;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.GreenDAO.DBHelper;
import com.teeoda.memorytraining.global.GreenDAO.TrainingHistory;
import com.teeoda.memorytraining.global.GreenDAO.TrainingHistoryDao;
import com.teeoda.memorytraining.global.TimerTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.query.QueryBuilder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NumbersPage extends BaseActivity {

    static public boolean restart = false;
    static public int state = State.START;

    final public static class State {
        final public static int START = 0;
        final public static int MEMORY = 1;
        final public static int FILLING = 2;
        final public static int RESULT = 3;
    }

    NumberSettingDetail mSettingDetail;
    NumberAdapter mNumberAdpater;
    ArrayList<NumberItem> mNumbers;
    ArrayList<NumberItem> mFilledNumbers;

    int mCurrentRoundNum = 0;
    rx.Subscription mTimerSub;
    TimerTime mTimerTime;
    int mHintUsed = 0;
    int mPreSelectedPos = -1;
    boolean mFillingDone = false;
    HashSet<Integer> mFilledNumCount;


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
    private RelativeLayout mResultXBtn;
    private RelativeLayout mResultStartOver;
    private TextView mAlertText;
    private RelativeLayout mControlPannel;
    private RelativeLayout mReStartBtn;
    private RelativeLayout mHintBtn;
    private RelativeLayout mResultBtn;
    private RelativeLayout mDoneBtn;
    private TextView mDoneBtnText;
    private GridView mGridview;
    private LinearLayout mTimerLayout;
    private TextView mTimeText;
    private TextView mTime;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-19 09:10:14 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
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
        mResultXBtn = (RelativeLayout)findViewById( R.id.resultXBtn );
        mResultStartOver = (RelativeLayout)findViewById( R.id.resultStartOver );
        mAlertText = (TextView)findViewById( R.id.alertText );
        mControlPannel = (RelativeLayout)findViewById( R.id.controlPannel );
        mReStartBtn = (RelativeLayout)findViewById( R.id.reStartBtn );
        mHintBtn = (RelativeLayout)findViewById( R.id.hintBtn );
        mResultBtn = (RelativeLayout)findViewById( R.id.resultBtn );
        mDoneBtn = (RelativeLayout)findViewById( R.id.DoneBtn );
        mDoneBtnText = (TextView)findViewById( R.id.doneBtnText );
        mGridview = (GridView)findViewById( R.id.gridview );
        mTimerLayout = (LinearLayout)findViewById( R.id.timerLayout );
        mTimeText = (TextView)findViewById( R.id.timeText );
        mTime = (TextView)findViewById( R.id.time );
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
            startTimer();

        });

        RxView.clicks(mReStartBtn).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {
            showStartPage();
        });

        RxView.clicks(mDoneBtn).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {
            if (state == State.MEMORY)
                showFillingPage();
            else if (state == State.FILLING) {

                String s = mDoneBtnText.getText().toString();
                if (s.equals("Confirm")) {
                    showResultPage();
                } else {
                    mFilledNumbers.get(mPreSelectedPos).isSelected = false;
                    mPreSelectedPos = (mPreSelectedPos + 1) % mSettingDetail.totalNum;
                    mFilledNumbers.get(mPreSelectedPos).isSelected = true;
                    mFilledNumbers.get(mPreSelectedPos).num = -1;

                    mNumberAdpater.notifyDataSetChanged();
                    mGridview.smoothScrollToPosition(mPreSelectedPos);

                }
            }
            else if (state == State.RESULT)
            {
                showStartPage();
            }
        });

        RxView.clicks(mResultStartOver).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {
            mResultBeatRecord.setVisibility(View.GONE);
            showStartPage();
        });

        //not used
        RxView.clicks(mHintBtn).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {
            showHint();
        });

        RxView.clicks(mResultXBtn).throttleFirst(400, TimeUnit.MILLISECONDS).subscribe(r -> {
            mResultLayout.setVisibility(View.GONE);
            mControlPannel.setVisibility(View.VISIBLE);
            mReStartBtn.setVisibility(View.GONE);
            mTimerLayout.setVisibility(View.GONE);
            mResultBtn.setVisibility(View.VISIBLE);
            mDoneBtnText.setText("Restart");
        });

        RxView.clicks(mResultBtn).throttleFirst(400,TimeUnit.MILLISECONDS).subscribe(r->{
            mResultLayout.setVisibility(View.VISIBLE);
        });


    }

    void startTimer()
    {
        mTimerSub = rx.Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(r1 -> {
                    mTimerTime.increase();
                    mTime.setText(mTimerTime.toString());
                });
    }

    public void switchNextToConfirm()
    {
        mDoneBtnText.setText("Confirm");
    }

    void showHint() {
        mHintUsed++;
        for (int i = 0; i < mSettingDetail.totalNum; i++) {
            if (mNumbers.get(i) != mFilledNumbers.get(i))
                mFilledNumbers.get(i).showHint = true;
        }
        mNumberAdpater.notifyDataSetChanged();

    }

    void showStartPage() {
        state = State.START;
        mCurrentRoundNum++;
        mSettingDetail = G.getInstance().prefser.get(G.NumberSettingDetail, NumberSettingDetail.class, new NumberSettingDetail());
        mTotalNums.setText(mSettingDetail.totalNum + "");
        mMinNum.setText(mSettingDetail.minNum + "");
        mMaxNum.setText(mSettingDetail.maxNum + "");
        mRoundNum.setText(mCurrentRoundNum + "");
        mStartLayout.setVisibility(View.VISIBLE);
        mAlertText.setVisibility(View.VISIBLE);
        mGridview.setVisibility(View.GONE);
        mControlPannel.setVisibility(View.GONE);
        mResultLayout.setVisibility(View.GONE);
        mResultBtn.setVisibility(View.GONE);
        mTime.setText("0s");
        mPreSelectedPos = -1;
        mFillingDone = false;
        mTimerLayout.setVisibility(View.GONE);


        if (mFilledNumbers != null)
            mFilledNumbers.clear();

        if (mNumbers != null)
            mNumbers.clear();

        G.dismissKeyboard();

        if (mTimerSub != null)
            mTimerSub.unsubscribe();
    }

    void showResultPage() {
        state = State.RESULT;

        mGridview.setVisibility(View.VISIBLE);
        mStartLayout.setVisibility(View.GONE);
        mAlertText.setVisibility(View.GONE);

        //setup control pannel
        mControlPannel.setVisibility(View.VISIBLE);
        mReStartBtn.setVisibility(View.GONE);
        mTimerLayout.setVisibility(View.GONE);
        mDoneBtnText.setText("Restart");

        mResultLayout.setVisibility(View.VISIBLE);
        mResultLayout.bringToFront();
        mResultLayout.requestFocus();

        G.dismissKeyboard();

        if (mTimerSub != null)
            mTimerSub.unsubscribe();
        mResultTimeSpend.setText(mTimerTime.toString());
        mResultTitleNum.setText(mSettingDetail.totalNum + "");

        mResultHintUsed.setText(mHintUsed + "");

        //start compare the answer
        int correctNum = 0;
        for (int i = 0; i < mSettingDetail.totalNum; i++) {
            if (mNumbers.get(i).num == mFilledNumbers.get(i).num)
                correctNum++;
            else
                mFilledNumbers.get(i).showAnswer = true;
        }
        mResultCorrectness.setText(String.format("%d/%d", correctNum, mSettingDetail.totalNum));

        //get the best record
        TrainingHistoryDao trainingHistoryDao = DBHelper.getInstance().getTrainingHitoryDAO();
        QueryBuilder builder = DBHelper.getInstance().getTrainingHitoryDAO().queryBuilder();
        builder.where(TrainingHistoryDao.Properties.Type.eq("Number"));
        builder.where(TrainingHistoryDao.Properties.Total.eq(mSettingDetail.totalNum));
        builder.where(TrainingHistoryDao.Properties.IsBest.eq(true));
        builder.limit(1).build();
        List<TrainingHistory> list = builder.list();

        mResultBestRecord.setVisibility(View.VISIBLE);
        mBestRecordText.setVisibility(View.VISIBLE);
        mResultBeatRecord.setVisibility(View.GONE);
        int bestRecordTime = mTimerTime.toSeconds();
        int newTime = mTimerTime.toSeconds();
        if (!list.isEmpty())
        {
            TrainingHistory entry = list.get(0);

            if (entry.getTimeSpent() > newTime && mSettingDetail.totalNum == correctNum)
            {
                //break record
                entry.setIsBest(false);
                trainingHistoryDao.update(entry);
                mResultBeatRecord.setVisibility(View.VISIBLE);

                TrainingHistory newEntry = new TrainingHistory(null,"Number",mSettingDetail.totalNum,correctNum,mTimerTime.toSeconds(),new Date(),true);
                trainingHistoryDao.insert(newEntry);
            }
            else
            {
                //not break record
                bestRecordTime = entry.getTimeSpent();

                TrainingHistory newEntry = new TrainingHistory(null,"Number",mSettingDetail.totalNum,correctNum,mTimerTime.toSeconds(),new Date(),false);
                trainingHistoryDao.insert(newEntry);
            }
        }
        else
        {
            //first record
            TrainingHistory newEntry = new TrainingHistory(null,"Number",mSettingDetail.totalNum,correctNum,mTimerTime.toSeconds(),new Date(),mSettingDetail.totalNum == correctNum);
            trainingHistoryDao.insert(newEntry);

            mBestRecordText.setVisibility(View.GONE);
            mResultBestRecord.setVisibility(View.GONE);

        }

        String sTime = TimerTime.secondsToString(bestRecordTime);
        mResultBestRecord.setText(sTime);


//        for (int i=0;i<400;i++)
//        {
//            TrainingHistory entry = new TrainingHistory(null,"Number",mSettingDetail.totalNum,i,mTimerTime.toString(),new Date(),false);
//            DBHelper.getInstance().getTrainingHitoryDAO().insert(entry);
//        }


    }


    void showMemoryPage() {
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
        mTimerLayout.setVisibility(View.VISIBLE);

        if (mNumbers == null)
            mNumbers = new ArrayList<NumberItem>();
        else
            mNumbers.clear();

        ArrayList<NumberItem> numbers = mNumbers;
        for (int i = 0; i < mSettingDetail.totalNum; i++) {
            numbers.add(new NumberItem(randInt(mSettingDetail.minNum, mSettingDetail.maxNum)));
        }
        mNumberAdpater = new NumberAdapter(numbers);
        mGridview.setAdapter(mNumberAdpater);
    }

    void showFillingPage() {
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
        for (int i = 0; i < mSettingDetail.totalNum; i++) {
            numbers.add(new NumberItem(-1));
        }
        numbers.get(0).isSelected = true;
        mPreSelectedPos = 0;
        mFilledNumbers = numbers;

        mFilledNumCount = new HashSet<>();

        Observable.timer(200,TimeUnit.MILLISECONDS).subscribe(r->{

            if (numbers.get(0).myEditText != null)
            {
                numbers.get(0).myEditText.requestFocus();
                G.showKeyboard(numbers.get(0).myEditText);
            }
        });

        mNumberAdpater = new NumberAdapter(mFilledNumbers, mNumbers, mFilledNumCount);
        mGridview.setAdapter(mNumberAdpater);

        RxAdapterView.itemClicks(mGridview).throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(position -> {
                    if (mPreSelectedPos != -1)
                        mFilledNumbers.get(mPreSelectedPos).isSelected = false;
                    mPreSelectedPos = position;

                    mFilledNumbers.get(position).isSelected = true;
                    mFilledNumbers.get(position).num = -1;

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

        if (restart) {
            restart = false;
            showStartPage();
        }

        if (state == State.MEMORY || state == State.FILLING)
        {
            startTimer();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                G.dismissKeyboard();
                this.finish();
                return true;
            case R.id.restart:
                mCurrentRoundNum = 0;
                G.dismissKeyboard();
                showStartPage();
                break;
            case R.id.settings:
                Intent intent = new Intent(this, NumberSettingPage.class);
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

    @Override
    protected void onPause() {
        super.onPause();

        if (state == State.MEMORY || state == State.FILLING)
        {
            mTimerSub.unsubscribe();
        }
    }


}
