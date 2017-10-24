package com.capternal.test.others;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.capternal.test.R;


/**
 * Created by student on 23/11/16.
 */

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    // DEFAULT FONT NAME
    private String fontType = "";

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);
        fontType = a.getString(R.styleable.CustomFont_fontType);
        if (fontType != null) {
            if (!fontType.isEmpty()) {
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), fontType));
            } else {
                this.setTypeface(Typeface.DEFAULT);
            }
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }
}
