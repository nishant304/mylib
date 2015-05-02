package com.wecamchat.aviddev.util.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

public class FontManager {

    private static FontManager manager;
    private Context context;
    private ArrayList<String> fontNames = new ArrayList<String>();
    private HashMap<String, Typeface> typeFaceStore = new HashMap<String, Typeface>();

    private FontManager(Context context) {
        this.context = context;
    }

    public static FontManager getInstance(Context context) {
        if (manager == null) {
            manager = new FontManager(context);
        }
        return manager;
    }

    public void setTypeFace(View view, String fontName) {
        if (!(view instanceof android.widget.TextView)) {
            return;
        }
        int index = fontNames.indexOf(fontName);
        if (index == -1) {
            fontNames.add(fontName);
        } else {
            fontName = fontNames.get(index);
        }
        Typeface typeface = typeFaceStore.get(fontName);
        if (typeface == null) {
            typeface = Utils.findTypeface(context, "fonts", fontName);
            typeFaceStore.put(fontName, typeface);
        }
        if (typeface != null) {
            ((android.widget.TextView) view).setTypeface(typeface);
        } else {
            ((android.widget.TextView) view).setTypeface(Typeface.DEFAULT);
        }

    }

    public Typeface getTypeFace(String fontName) {
        int index = fontNames.indexOf(fontName);
        if (index == -1) {
            fontNames.add(fontName);
        } else {
            fontName = fontNames.get(index);
        }
        Typeface typeface = typeFaceStore.get(fontName);
        if (typeface == null) {
            typeface = Utils.findTypeface(context, "fonts", fontName);
            typeFaceStore.put(fontName, typeface);
        }
        if (typeface != null) {
            return typeface;
        } else {
            return Typeface.DEFAULT;
        }
    }
}
