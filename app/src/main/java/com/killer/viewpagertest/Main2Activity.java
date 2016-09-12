package com.killer.viewpagertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * viewFlipper演示
 */
public class Main2Activity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private int[] resId = {R.drawable.car2,R.drawable.car3,R.drawable.car4};
    private float startX;
    private boolean isDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        // 添加imageView
        for (int i = 0; i < resId.length; i++) {
            viewFlipper.addView(getImageView(resId[i]));
        }


        // 设置切换的时间
//        viewFlipper.setFlipInterval(2000);
        // 开始播放
//        viewFlipper.startFlipping();

    }

    /*
    手指划动的处理事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            // 手指落下
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                startX = event.getX();
                break;
            // 手指滑动
            case MotionEvent.ACTION_MOVE:
                if(isDown) { // 一次按下只允许更新一张图片
                    // 向左滑
                    if (startX - event.getX() > 200) {
                        viewFlipper.showNext();
                        isDown = false;
                    }
                    // 向右滑
                    if (event.getX() - startX > 200) {
                        viewFlipper.showPrevious();
                        isDown = false;
                    }
                }
                break;

            // 手指抬起
            case MotionEvent.ACTION_UP:

                break;

        }

        return super.onTouchEvent(event);
    }

    private ImageView getImageView(int resId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        return imageView;
    }
}
