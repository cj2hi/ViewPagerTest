package com.killer.viewpagertest;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/9/7.
 */
public class CicrleDrawable extends Drawable {

    private Paint paint;
    private int width;
    private Bitmap bitmap;

    public CicrleDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        width = Math.min(bitmap.getHeight(), bitmap.getWidth());
    }

    @Override
    public void draw(Canvas canvas) {
        // 指定位置点和半径画圆
        canvas.drawCircle(width/2,width/2,width/2,paint);


    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return width;
    }
}
