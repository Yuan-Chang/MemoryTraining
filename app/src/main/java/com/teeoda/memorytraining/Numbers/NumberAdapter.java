package com.teeoda.memorytraining.Numbers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.teeoda.memorytraining.R;
import com.teeoda.memorytraining.global.BaseActivity;

import java.util.ArrayList;

/**
 * Created by home on 3/5/16.
 */
public class NumberAdapter extends BaseAdapter {

    ArrayList<NumberItem> numbers;

    public NumberAdapter(ArrayList<NumberItem> data) {
        numbers = data;
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
            viewHolder = ViewHolder.create((RelativeLayout)convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (getItem(position).num >= 0)
            viewHolder.mEditText.setText(getItem(position).num+"");
        else
            viewHolder.mEditText.setText("");

        return convertView;
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2016-03-05 09:21:19 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final EditText mEditText;

        private ViewHolder(EditText mEditText) {
            this.mEditText = mEditText;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            EditText mEditText = (EditText)rootView.findViewById( R.id.editText );
            return new ViewHolder( mEditText );
        }
    }

}
