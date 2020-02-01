package com.example.customdatelibrary.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PagerViewAdapter extends PagerAdapter {
    private List<View> mList;

    public PagerViewAdapter(List list) {
        this.mList = list;
    }

    /**
     * 返回当前有效视图的个数
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 该函数用来判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
     * @return 如果对应的是同一个View，返回True，否则返回False。
     */

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 这个函数的实现的功能是创建指定位置的页面视图。适配器有责任增加即将创建的View视图到这里给定的
     * container中，这是为了确保在finishUpdate(viewGroup)返回时这已经被完成
     * @return 返回一个代表新增视图页面的Object（Key），这里没必要非要返回视图本身，也可以这个页面的
     * 其它容器（只要可以与新增加的view一一对应即可）
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    /**
     * 该方法实现的功能是移除一个给定位置的页面。适配器有责任从容器中删除这个视图。这是为了确保
     * 在finishUpdate(viewGroup)返回时视图能够被移除
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

}