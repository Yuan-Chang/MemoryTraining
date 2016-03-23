package com.teeoda.memorytraining.RecordBoard;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;

public class RecordPage extends BaseActivity {

    public TrainingHistoryFrag mTrainingHistoryFrag;
    public PublicRecordFrag mPublicRecordFrag;
    public BestRecordFrag mBestRecordFrag;


    private TabLayout mTabs;
    private ViewPager mViewPager;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-19 21:56:45 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mTabs = (TabLayout)findViewById( R.id.tabs );
        mViewPager = (ViewPager)findViewById( R.id.viewPager );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);

        getSupportActionBar().setTitle("Record board");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViews();

        mTrainingHistoryFrag = new TrainingHistoryFrag();
        mPublicRecordFrag = new PublicRecordFrag();
        mBestRecordFrag = new BestRecordFrag();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mTrainingHistoryFrag, "Training history");
        adapter.addFragment(mBestRecordFrag,"Best record");
        adapter.addFragment(mPublicRecordFrag, "Others' record");
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mTabs.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(5);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
