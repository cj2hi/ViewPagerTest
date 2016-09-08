package com.killer.viewpagertest;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<View> viewList;
    private List<String> titleList;

    public MyPagerAdapter(List<String> titleList, List<View> viewList) {
        this.titleList = titleList;
        this.viewList = viewList;
    }


    /**
     *
     * @return 页卡的数量
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     *
     * @param view
     * @param object
     * @return 判断view是否来自于object
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /**
     * 实例化一个页卡
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /**
     * 销毁一个页卡
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    /*
    返回标题内容
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
