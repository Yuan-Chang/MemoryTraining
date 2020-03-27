package com.teeoda.memorytraining.RecordBoard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.teeoda.memorytraining.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicRecordFrag extends Fragment {


    public PublicRecordFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_record, container, false);
    }

}
