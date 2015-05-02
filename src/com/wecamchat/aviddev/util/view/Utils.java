package com.wecamchat.aviddev.util.view;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wecamchat.aviddev.constant.AppConstant;

public class Utils {

    public static int mDeviceWidth = 0, mDeviceHight;

    public static Typeface findTypeface(Context context, String initPath,
            String typeface) {
        AssetManager assets = context.getAssets();
        return Typeface.createFromAsset(assets, (initPath + File.separator)
                + typeface);
    }

    public static float getScreenDensity(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.density;
    }

    @SuppressLint("NewApi")
    public static void getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (AppConstant.SDK_VERSION >= 13) {
            Point outSize = new Point();
            display.getSize(outSize);
            mDeviceWidth = outSize.x;
        } else {
            mDeviceWidth = display.getWidth();
        }

    }

    @SuppressLint("NewApi")
    public static void getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (AppConstant.SDK_VERSION >= 13) {
            Point outSize = new Point();
            display.getSize(outSize);
            mDeviceHight = outSize.y;
        } else {
            mDeviceHight = display.getHeight();
        }

    }

    public static int getListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = listView.getPaddingTop()
                + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // ViewGroup.LayoutParams params = listView.getLayoutParams();
        // params.height = totalHeight
        // + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.setLayoutParams(params);

        return (totalHeight + (listView.getDividerHeight() * (listAdapter
                .getCount() - 1)));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFullDate(long time) {

        try {
            if (time == 0) {
                return "";
            } else {

                java.util.Date dt = new java.util.Date(time);
                java.text.DateFormat df = new java.text.SimpleDateFormat(
                        "MMMM dd, yyyy");
                df.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                return df.format(dt);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return "";
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(),
                    bitmap.getHeight(), Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(),
                    Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static boolean isImageFile(String filePath) {
        int index = filePath.lastIndexOf(".");
        String fileType = filePath.substring(index + 1);
        if (fileType.equals("jpg") || fileType.equals("jpeg")
                || fileType.equals("png")) {
            return true;
        } else
            return false;
    }

}
