package com.droidhubworld.crashlogger.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.droidhubworld.crashlogger.fragment.ExceptionLoggerFragment;
import com.droidhubworld.crashlogger.fragment.CrashLoggerFragment;


public class LogPagerAdapter extends FragmentPagerAdapter {
    private CrashLoggerFragment crashLoggerFragment;
    private ExceptionLoggerFragment exceptionLoggerFragment;
    private String[] titles;

    public LogPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return crashLoggerFragment = new CrashLoggerFragment();
        } else if (position == 1) {
            return exceptionLoggerFragment = new ExceptionLoggerFragment();
        } else {
            return new CrashLoggerFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void clearLogs() {
        crashLoggerFragment.clearLog();
        exceptionLoggerFragment.clearLog();
    }
}