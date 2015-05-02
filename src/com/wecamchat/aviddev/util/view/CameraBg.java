package com.wecamchat.aviddev.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;

public class CameraBg extends View {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Path path = new Path();
    private int height, width;

    public CameraBg(Context context, AttributeSet arg1) {
        super(context, arg1);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        bitmap = Bitmap.createBitmap(width, height, Config.ALPHA_8);
        paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        path.addCircle(width / 2, height / 2, density
                * AppConstant.CAMERA_IMAGE_SIZE, Direction.CCW);
        initDraw();
    }

    private void initDraw() {
        canvas = new Canvas(bitmap);
    }

    private void refershColorOrPosition() {
        canvas.drawColor(R.color.translucent);
        canvas.save();
        canvas.clipPath(path);
        canvas.drawPaint(paint);
        canvas.restore();
    }

    // public void setRadius(int radius) {
    // path.addCircle(width / 2, height / 2, radius, Direction.CCW);
    // refershColorOrPosition();
    // postInvalidate();
    // }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        refershColorOrPosition();
    }

}
