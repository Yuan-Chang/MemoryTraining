package com.teeoda.memorytraining.RecordBoard;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.teeoda.memorytraining.Numbers.NumberSettingPage;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;

public class RecordPage extends BaseActivity {

    private TabLayout mTabs;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-18 19:45:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        mTabs = (TabLayout)findViewById( R.id.tabs );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);

        getSupportActionBar().setTitle("Record board");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
