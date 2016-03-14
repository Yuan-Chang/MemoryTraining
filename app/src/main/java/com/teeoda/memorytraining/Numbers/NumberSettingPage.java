package com.teeoda.memorytraining.Numbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.DialogHelper;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.NumberInputFilterMinMax;

import rx.Observable;

public class NumberSettingPage extends BaseActivity {

    NumberSettingDetail mSettingDetail;
    Prefser mPrefser;
    boolean mModified = false;

    private EditText mTotolNum;
    private EditText mMinNum;
    private EditText mMaxNum;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-03 08:23:10 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mTotolNum = (EditText)findViewById( R.id.totolNum );
        mMinNum = (EditText)findViewById( R.id.minNum );
        mMaxNum = (EditText)findViewById( R.id.maxNum );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_setting);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViews();

        mPrefser = G.getInstance().prefser;
        mSettingDetail = mPrefser.get(G.NumberSettingDetail,NumberSettingDetail.class,new NumberSettingDetail());

        mTotolNum.setText(mSettingDetail.totalNum+"");
        mMinNum.setText(mSettingDetail.minNum+"");
        mMaxNum.setText(mSettingDetail.maxNum + "");

        mTotolNum.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 9999)});
        mMinNum.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 9999)});
        mMaxNum.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 9999)});

        RxView.focusChanges(mTotolNum).subscribe(r -> {

            if (!mTotolNum.isFocused()) {
                Log.d("yuan", "total num unfocus");
                if (mTotolNum.getText().toString().isEmpty())
                    mTotolNum.setText(mSettingDetail.totalNum + "");
                else {
                    int num = Integer.valueOf(mTotolNum.getText().toString());
                    if (num < 10) {
                        Toast.makeText(this, "The minimum total number is 10", Toast.LENGTH_SHORT).show();
                        num = 10;
                    }

                    if (mSettingDetail.totalNum != num)
                    {
                        mSettingDetail.totalNum = num;
                        mModified = true;
                    }
                }

                mTotolNum.setText(mSettingDetail.totalNum + "");
                saveToPrefser();

            } else {
                mTotolNum.setSelection(mTotolNum.length());
            }

        });

        RxView.focusChanges(mMinNum).subscribe(r->{

            if (!mMinNum.isFocused())
            {
                if (mMinNum.getText().toString().isEmpty())
                    mMinNum.setText(mSettingDetail.minNum+"");

                int num = Integer.valueOf(mMinNum.getText().toString());
                if (num > mSettingDetail.maxNum)
                {
                    mSettingDetail.maxNum = num;
                    mMaxNum.setText(mSettingDetail.maxNum+"");
                }

                if (mSettingDetail.minNum != num)
                {
                    mSettingDetail.minNum = num;
                    mModified = true;
                }

                mMinNum.setText(num+"");
                saveToPrefser();
            }
            else {
                mMinNum.setSelection(mMinNum.length());
            }

        });

        RxView.focusChanges(mMaxNum).subscribe(r -> {

            if (!mMaxNum.isFocused()) {
                if (mMaxNum.getText().toString().isEmpty())
                    mMaxNum.setText(mSettingDetail.maxNum + "");

                int num = Integer.valueOf(mMaxNum.getText().toString());
                if (num < mSettingDetail.minNum) {
                    mSettingDetail.minNum = num;
                    mMinNum.setText(mSettingDetail.minNum + "");
                }

                if (mSettingDetail.maxNum != num)
                {
                    mModified = true;
                    mSettingDetail.maxNum = num;
                }

                mMaxNum.setText(num + "");
                saveToPrefser();
            } else {
                mMaxNum.setSelection(mMaxNum.length());
            }

        });



    }

    void saveToPrefser()
    {
        mPrefser.put(G.NumberSettingDetail,mSettingDetail);
    }

    @Override
    public void onBackPressed() {

        getCurrentFocus().clearFocus();

        if (mModified)
        {
            DialogHelper.popupAlertDialog(this,null,DialogHelper.Message.applyChanges,"yes",()->{
                NumbersPage.restart = true;
                finish();
            }, "No", ()->{
                finish();
            });
        }
        else
        {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                getCurrentFocus().clearFocus();

                if (mModified)
                {
                    DialogHelper.popupAlertDialog(this,null,DialogHelper.Message.applyChanges,"yes",()->{
                        NumbersPage.restart = true;
                        finish();
                    }, "No", ()->{
                        finish();
                    });
                }
                else
                    finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

