package com.teeoda.memorytraining.RecordBoard;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingHistoryFrag extends Fragment {

    TableLayout mTableLayout;
    TableLayout mTableColumns;
    InteractiveScrollView mScrollView;

    public TrainingHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_history, container, false);
        mTableLayout = (TableLayout)view.findViewById(R.id.tableLayout);
        mTableColumns = (TableLayout)view.findViewById(R.id.tableColumns);
        mScrollView = (InteractiveScrollView)view.findViewById(R.id.scrollView);

        TableRow firstRow = (TableRow)inflater.inflate(R.layout.personal_record_table_row,container,false);
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

        //DBHelper.getInstance().getTrainingHitoryDAO().deleteAll();

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

        List<TrainingHistory> list = DBHelper.getInstance().getTrainingHitoryDAO().queryBuilder().orderDesc(TrainingHistoryDao.Properties.Date).offset(offset).limit(20).list();
        for (TrainingHistory entry : list)
        {
            TableRow row = (TableRow)inflater.inflate(R.layout.personal_record_table_row, container, false);
            TextView category = (TextView)row.findViewById(R.id.category);
            TextView correctness = (TextView)row.findViewById(R.id.correctness);
            TextView timeSpent = (TextView)row.findViewById(R.id.timeSpent);
            TextView date = (TextView)row.findViewById(R.id.date);

            if (entry.getIsBest())
            {
                category.setTextColor(Color.RED);
                correctness.setTextColor(Color.RED);
                timeSpent.setTextColor(Color.RED);
                date.setTextColor(Color.RED);
            }

            category.setText(entry.getType());
            correctness.setText(entry.getCorrectNum()+"/"+entry.getTotal());
            timeSpent.setText(TimerTime.secondsToString(entry.getTimeSpent()));

            //String dateString = new SimpleDateFormat("MMM d, yyyy, h:mm a").format(entry.getDate());
            String dateString = G.getInstance().getDateFormattedString(entry.getDate());
            date.setText(dateString);

            mTableLayout.addView(row);
        }
    }

}
