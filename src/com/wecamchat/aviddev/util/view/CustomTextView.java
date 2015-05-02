package com.wecamchat.aviddev.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.wecamchat.aviddev.R;

public class CustomTextView extends android.widget.TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextView);

        String typeface = a.getString(R.styleable.TextView_ctypeface);
        if (typeface != null) {
            FontManager.getInstance(context).setTypeFace(this, typeface);
        }

    }

}
