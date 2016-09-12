package com.killer.viewpagertest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<View> viewLIst;
    private ViewPager viewPager;
    private PagerTabStrip tab;
    private List<String> titleList;
    private List<Fragment> fragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewLIst = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        View view1 = View.inflate(this,R.layout.view1,null);
        View view2 = View.inflate(this,R.layout.view2,null);
        View view3 = View.inflate(this,R.layout.view3,null);
        View view4 = View.inflate(this,R.layout.view4,null);
        View view5 = View.inflate(this,R.layout.view5,null);

        viewLIst.add(view1);
        viewLIst.add(view2);
        viewLIst.add(view3);
        viewLIst.add(view4);
        viewLIst.add(view5);

        titleList = new ArrayList<>();
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");
        titleList.add("第四页");
        titleList.add("第五页");

        fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());
        fragmentList.add(new Fragment5());



        tab = (PagerTabStrip) findViewById(R.id.tab);
        tab.setBackgroundColor(Color.BLUE);
        tab.setTextColor(Color.RED);
        tab.setDrawFullUnderline(false);
        tab.setTabIndicatorColor(Color.GREEN);



        MyPagerAdapter adapter = new MyPagerAdapter(titleList,viewLIst);

//        viewPager.setAdapter(adapter);


        MyFragmentAdapter adapter1 = new MyFragmentAdapter(getSupportFragmentManager(),titleList,fragmentList);
        MyFragmentAdapter2 adapter2 = new MyFragmentAdapter2(getSupportFragmentManager(),titleList,fragmentList);

        viewPager.setAdapter(adapter2);

        viewPager.setOnPageChangeListener(this);




    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this,"当前是第" + (position + 1) + "个页面",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 创建菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    // 选择菜单项后运行操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item1:
                // 自定义toast菜单
                Toast toast =Toast.makeText(this, "选择了菜单1", Toast.LENGTH_SHORT);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.dialog);
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                // 设置图片排列位置
                linearLayout.addView(imageView,0);
                toast.setView(linearLayout);

                toast.show();
                break;
            case R.id.menu_item2:
                Intent intent = new Intent(this, Main2Activity.class);
                // 跳转到另一界面
                item.setIntent(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
