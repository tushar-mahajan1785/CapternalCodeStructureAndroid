package com.capternal.test.others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;

import com.capternal.test.R;


/**
 * Created by student on 23/11/16.
 */

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    // DEFAULT FONT NAME
    private String fontType = "";

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
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

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }
}
