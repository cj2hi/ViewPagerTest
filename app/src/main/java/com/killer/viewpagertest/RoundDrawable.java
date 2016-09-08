package com.killer.viewpagertest;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**圆角矩形
 * Created by Administrator on 2016/9/7.
 */
public class RoundDrawable extends Drawable {

    private Paint paint;
    private RectF rectF;
    private Bitmap bitmap;

    public RoundDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
    }

    @Override
    public void draw(Canvas canvas) {
        // 绘制有圆角的矩形
        canvas.drawRoundRect(rectF,50,50,paint);

    }

    // 通过setBounds得到图片的四个角
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        rectF = new RectF(left, top, right, bottom);
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
        return bitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();
    }
}
