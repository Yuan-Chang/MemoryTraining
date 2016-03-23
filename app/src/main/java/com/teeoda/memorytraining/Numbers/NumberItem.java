package com.teeoda.memorytraining.Numbers;

import android.widget.EditText;

/**
 * Created by home on 3/5/16.
 */
public class NumberItem {
    public int num = -1;
    public boolean showHint = false;
    public boolean isSelected = false;
    public boolean showAnswer = false;
    public EditText myEditText;

    public NumberItem(int num) {
        this.num = num;
    }
}
