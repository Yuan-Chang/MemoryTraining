package com.teeoda.memorytraining.Numbers;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.RxViewGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;
import com.teeoda.memorytraining.global.CircleView;
import com.teeoda.memorytraining.global.G;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Subscription;

/**
 * Created by home on 3/5/16.
 */
public class NumberAdapter extends BaseAdapter {

    ArrayList<NumberItem> numbers;
    ArrayList<NumberItem> answers;

    public NumberAdapter(ArrayList<NumberItem> data) {
        numbers = data;
    }
    public NumberAdapter(ArrayList<NumberItem> data, ArrayList<NumberItem> answers) {
        numbers = data;
        this.answers = answers;
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public NumberItem getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = BaseActivity.getCurrent().getLayoutInflater();
            convertView = inflater.inflate(R.layout.number_gridview_item, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(viewHolder.textChangedSubscription != null)
            viewHolder.textChangedSubscription.unsubscribe();
        viewHolder.mEditText.setOnKeyListener(null);

        if (getItem(position).showAnswer && answers != null)
        {
            viewHolder.mCorrectNum.setText(answers.get(position).num+"");
            viewHolder.mCorrectNum.setVisibility(View.VISIBLE);
            viewHolder.mCossLine.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.mCorrectNum.setVisibility(View.GONE);
            viewHolder.mCossLine.setVisibility(View.GONE);
        }

        int num = getItem(position).num;
        if (num > -1) {
            viewHolder.mTextView.setText(num + "");
            viewHolder.mEditText.setText(num + "");
        }
        else {
            viewHolder.mTextView.setText("");
            viewHolder.mEditText.setText("");
        }

        if (NumbersPage.state != NumbersPage.State.FILLING || !getItem(position).isSelected) {

            viewHolder.mEditText.setVisibility(View.GONE);
            viewHolder.mTextView.setVisibility(View.VISIBLE);
            viewHolder.mCircleView.setColor(Color.LTGRAY);
            viewHolder.mCircleView.invalidate();
            Log.d("yuan", "in memory page");

        } else {
            viewHolder.mEditText.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setVisibility(View.GONE);
            viewHolder.mEditText.requestFocus();
            viewHolder.mEditText.setSelected(true);
            viewHolder.mEditText.setTextIsSelectable(true);
            viewHolder.mEditText.setCursorVisible(true);

            viewHolder.mCircleView.setColor(Color.RED);
            viewHolder.mCircleView.invalidate();

            G.showKeyboard(viewHolder.mEditText);

            viewHolder.textChangedSubscription = RxTextView.textChanges(viewHolder.mEditText)
                    .subscribe(r -> {
                        if (!r.toString().isEmpty())
                            getItem(position).num = Integer.valueOf(r.toString());
                        else
                            getItem(position).num = -1;
                        Log.d("yuan", "number : " + r.toString());
                    });

            final ViewHolder vh = viewHolder;
            viewHolder.mEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        vh.mEditText.setText("");
                        getItem(position).num = -1;
                    }
                    return false;
                }
            });
        }

        return convertView;
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2016-03-13 22:54:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public Subscription textChangedSubscription;
        public final RelativeLayout mRoot;
        public final CircleView mCircleView;
        public final EditText mEditText;
        public final TextView mTextView;
        public final RelativeLayout mCossLine;
        public final TextView mCorrectNum;

        private ViewHolder(RelativeLayout mRoot, CircleView mCircleView, EditText mEditText, TextView mTextView, RelativeLayout mCossLine, TextView mCorrectNum) {
            this.mRoot = mRoot;
            this.mCircleView = mCircleView;
            this.mEditText = mEditText;
            this.mTextView = mTextView;
            this.mCossLine = mCossLine;
            this.mCorrectNum = mCorrectNum;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            RelativeLayout mRoot = (RelativeLayout)rootView.findViewById( R.id.root );
            CircleView mCircleView = (CircleView)rootView.findViewById( R.id.circleView );
            EditText mEditText = (EditText)rootView.findViewById( R.id.editText );
            TextView mTextView = (TextView)rootView.findViewById( R.id.textView );
            RelativeLayout mCossLine = (RelativeLayout)rootView.findViewById( R.id.cossLine );
            TextView mCorrectNum = (TextView)rootView.findViewById( R.id.correctNum );
            return new ViewHolder( mRoot, mCircleView, mEditText, mTextView, mCossLine, mCorrectNum );
        }
    }



}
