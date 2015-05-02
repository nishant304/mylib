package com.wecamchat.aviddev.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.intrface.RoundBitMapListener;

public class RoundedImage {

    private static int rectWH = 20;
    private static float minusOfBorder = 12;
    // class RoundImage Asyn

    private volatile static RoundedImage instance;

    /** Returns singleton class instance */
    public static RoundedImage getInstance() {
        if (instance == null) {
            synchronized (RoundedImage.class) {
                if (instance == null) {
                    instance = new RoundedImage();
                }
            }
        }
        return instance;
    }

    protected RoundedImage() {
    }

    public void getFinalBitmap(Context ctx, Bitmap largeImage,
            Bitmap smallImage, int setCircleColor, Object object,
            RoundBitMapListener listener) {

        new RoundImageAsync(ctx, largeImage, smallImage, setCircleColor,
                object, listener).execute();

    }

    private class RoundImageAsync extends AsyncTask<Void, Void, Bitmap> {
        Context ctx;
        Bitmap largeImage;
        Bitmap smallImage;
        int setCircleColor;
        RoundBitMapListener listener;
        Object object;

        public RoundImageAsync(Context ctx, Bitmap largeImage,
                Bitmap smallImage, int setCircleColor, Object object,
                RoundBitMapListener listener) {
            this.ctx = ctx;
            this.largeImage = largeImage;
            this.smallImage = smallImage;
            this.setCircleColor = setCircleColor;
            this.listener = listener;
            this.object = object;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                if (smallImage != null) {
                    return getRoundedShape(ctx, largeImage, smallImage,
                            setCircleColor);

                }
                return getRoundedShape(ctx, largeImage);

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                listener.LoadingComplete(object, bitmap);
            } else {
                listener.LoadingFailed(object);
            }

            // if (largeImage != null) {
            // largeImage.recycle();
            // largeImage = null;
            // }

            // if (smallImage != null) {
            // smallImage.recycle();
            // smallImage = null;
            // }

        }

    }

    public Bitmap getRoundedShape(Context ctx, Bitmap largeImage,
            Bitmap smallImage, int setCircleColor) {
        float borderMinus = 0;
        try {

            float circleRadius = ctx.getResources().getDimension(
                    R.dimen.circleRadius);

            float greenGrayBorder = ctx.getResources().getDimension(
                    R.dimen.greenGrayBotder);

            float margin_small_icon = ctx.getResources().getDimension(
                    R.dimen.round_circle_small_icon_margin);

            float density = ctx.getResources().getDisplayMetrics().density;
            int targetWidth = (int) (circleRadius * density);
            int targetHeight = targetWidth;
            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth + rectWH,
                    targetHeight + rectWH, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);

            /*
             * set color of rounded border color
             */

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            switch (setCircleColor) {
            case 0: // GREEN
                paint.setColor(ctx.getResources().getColor(
                        R.color.map_circle_border_green));
                borderMinus = greenGrayBorder;
                break;

            case 1: // GREY
                paint.setColor(ctx.getResources().getColor(
                        R.color.map_circle_border_gray));
                borderMinus = greenGrayBorder;
                break;

            default: // BLUE

                paint.setColor(ctx.getResources().getColor(
                        R.color.map_circle_border_blue));
                break;
            }

            /*
             * set rounded border color
             */

            canvas.drawCircle(
                    ((float) targetWidth) / 2,
                    ((float) targetHeight) / 2,
                    (Math.min(((float) targetWidth), ((float) targetHeight)) / 2)
                            - borderMinus, paint);

            Path path = new Path();
            path.addCircle(
                    ((float) targetWidth - 1) / 2,
                    ((float) targetHeight - 1) / 2,
                    (Math.min(((float) targetWidth), ((float) targetHeight)) / 2)
                            - minusOfBorder, Path.Direction.CCW);

            /*
             * set small image on large image of half part
             */

            if (smallImage != null)
                canvas.drawBitmap(smallImage, margin_small_icon, 0, null);

            /*
             * add to circle clip part in path
             */
            canvas.clipPath(path);
            /*
             * draw large image
             */
            canvas.drawBitmap(largeImage, new Rect(0, 0, largeImage.getWidth(),
                    largeImage.getHeight()), new Rect(0, 0, targetWidth,
                    targetHeight), null);

            /*
             * set small image on large image of half part
             */
            if (smallImage != null)
                canvas.drawBitmap(smallImage, margin_small_icon, 0, null);

            return targetBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getRoundedShape(Context ctx, Bitmap scaleBitmapImage) {
        // int targetWidth = 120;
        // int targetHeight = 120;

        try {
            // Calculation for map markers
            float density = ctx.getResources().getDisplayMetrics().density;
            int targetWidth = (int) (60 * density);
            int targetHeight = (int) (60 * density);
            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                    targetHeight, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();
            path.addCircle(
                    ((float) targetWidth - 1) / 2,
                    ((float) targetHeight - 1) / 2,
                    (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                    Path.Direction.CCW);

            canvas.clipPath(path);
            Bitmap sourceBitmap = scaleBitmapImage;
            canvas.drawBitmap(
                    sourceBitmap,
                    new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
                            .getHeight()), new Rect(0, 0, targetWidth,
                            targetHeight), null);
            return targetBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
