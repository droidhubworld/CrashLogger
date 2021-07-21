package com.droidhubworld.crashlogger.utils;

import androidx.viewpager.widget.ViewPager;

public abstract class ViewPagerPageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public abstract void onPageSelected(int position);

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
