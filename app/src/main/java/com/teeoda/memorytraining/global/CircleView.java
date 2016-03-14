package com.teeoda.memorytraining.global;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.teeoda.memorytraining.R;

/**
 * Created by developer3 on 2/18/16.
 */
public class CircleView extends View {

    int color = Color.RED;
    Paint paint;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);
        color = a.getColor(R.styleable.CircleView_fill_color,Color.RED);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        a.recycle();
    }

    public void setColor(int colorCode)
    {
        color = colorCode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int radius;
        radius = getWidth()/2;
        paint.setColor(color);

        canvas.drawCircle(x / 2, y / 2, radius, paint);
    }
}
