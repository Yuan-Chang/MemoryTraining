package com.teeoda.memorytraining.RecordBoard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.teeoda.memorytraining.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingHistoryFrag extends Fragment {

    TableLayout mTableLayout;

    public TrainingHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_history, container, false);
        mTableLayout = (TableLayout)view.findViewById(R.id.tableLayout);
        TableRow firstRow = (TableRow)inflater.inflate(R.layout.personal_record_table_row,container,false);
        mTableLayout.addView(firstRow);
        return view;
    }

}
