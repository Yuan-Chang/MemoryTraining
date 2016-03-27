package com.teeoda.memorytraining.RecordBoard;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.G;
import com.teeoda.memorytraining.global.GreenDAO.DBHelper;
import com.teeoda.memorytraining.global.GreenDAO.TrainingHistory;
import com.teeoda.memorytraining.global.GreenDAO.TrainingHistoryDao;
import com.teeoda.memorytraining.global.InteractiveScrollView;
import com.teeoda.memorytraining.global.TimerTime;

import java.text.SimpleDateFormat;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestRecordFrag extends Fragment {

    TableLayout mTableLayout;
    TableLayout mTableColumns;
    InteractiveScrollView mScrollView;

    public BestRecordFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_best_record, container, false);
        mTableLayout = (TableLayout)view.findViewById(R.id.tableLayout);
        mTableColumns = (TableLayout)view.findViewById(R.id.tableColumns);
        mScrollView = (InteractiveScrollView)view.findViewById(R.id.scrollView);

        TableRow firstRow = (TableRow)inflater.inflate(R.layout.best_record_table_row,container,false);
        TextView categoryTitle = (TextView)firstRow.findViewById(R.id.category);
        TextView correctnessTitle = (TextView)firstRow.findViewById(R.id.correctness);
        TextView timeSpentTitle = (TextView)firstRow.findViewById(R.id.timeSpent);
        TextView dateTitle = (TextView)firstRow.findViewById(R.id.date);

        categoryTitle.setTypeface(null, Typeface.BOLD);
        categoryTitle.setTextSize(16);
        correctnessTitle.setTypeface(null, Typeface.BOLD);
        correctnessTitle.setTextSize(16);
        timeSpentTitle.setTypeface(null, Typeface.BOLD);
        timeSpentTitle.setTextSize(16);
        dateTitle.setTypeface(null, Typeface.BOLD);
        dateTitle.setTextSize(16);
        mTableColumns.addView(firstRow);

        loadData(inflater, container, 0);

        mScrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                loadData(inflater, container, mTableLayout.getChildCount());
            }
        });

        return view;
    }

    void loadData(LayoutInflater inflater, ViewGroup container, int offset)
    {

        QueryBuilder builder = DBHelper.getInstance().getTrainingHitoryDAO().queryBuilder();
        builder.where(TrainingHistoryDao.Properties.IsBest.eq(true));
        builder.orderDesc(TrainingHistoryDao.Properties.Date).offset(offset).limit(20).build();

        List<TrainingHistory> list = builder.list();
        for (TrainingHistory entry : list)
        {
            TableRow row = (TableRow)inflater.inflate(R.layout.best_record_table_row, container, false);
            TextView category = (TextView)row.findViewById(R.id.category);
            TextView correctness = (TextView)row.findViewById(R.id.correctness);
            TextView timeSpent = (TextView)row.findViewById(R.id.timeSpent);
            TextView date = (TextView)row.findViewById(R.id.date);

            category.setText(entry.getType());
            correctness.setText(entry.getCorrectNum()+"/"+entry.getTotal());
            timeSpent.setText(TimerTime.secondsToString(entry.getTimeSpent()));

            String dateString = G.getInstance().getDateFormattedString(entry.getDate());
            date.setText(dateString);

            mTableLayout.addView(row);
        }
    }
}
