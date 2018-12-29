package com.example.user.androidx.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SqaureImageView extends ImageView
{


    public SqaureImageView(Context context) {
        super(context);
    }

    public SqaureImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SqaureImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = getMeasuredWidth();

        int height = getMeasuredHeight();


        int squareLen = width > height ? height : width;

        setMeasuredDimension(squareLen, squareLen);

    }

}