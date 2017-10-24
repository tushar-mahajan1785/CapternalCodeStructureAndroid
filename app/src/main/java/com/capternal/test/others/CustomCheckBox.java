package com.capternal.test.others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.capternal.test.R;


/**
 * Created by student on 23/11/16.
 */

@SuppressLint("AppCompatCustomView")
public class CustomCheckBox extends CheckBox {

    // DEFAULT FONT NAME
    private String fontType = "";

    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
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

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }
}
