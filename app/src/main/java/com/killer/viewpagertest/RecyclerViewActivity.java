package com.killer.viewpagertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class RecyclerViewActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageSwitcher imageSwitcher;


    private int[] resId = {R.drawable.car2,R.drawable.car3,R.drawable.car4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        // 实现ViewFactory接口，并设置此工厂接口加载
        imageSwitcher.setFactory(this);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));


        layoutManager = new LinearLayoutManager(this);
        // 设置排列方向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        PictureAdapter pictureAdapter = new PictureAdapter(resId,this);
        recyclerView.setAdapter(pictureAdapter);

        pictureAdapter.setOnItemClickLitener(new PictureAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                // 设置自定义toast显示
                /*LayoutInflater layoutInflater = LayoutInflater.from(RecyclerViewActivity.this);
                View view1 = layoutInflater.inflate(R.layout.toast_layout, null);
                view1.findViewById(R.id.imageView_toast).setBackgroundResource(resId[position%resId.length]);
                Toast toast = new Toast(RecyclerViewActivity.this);
                toast.setView(view1);
                toast.show();*/

                imageSwitcher.setImageResource(resId[position%resId.length]);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }


    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        // 设置图片填充类型
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        return imageView;
    }
}
