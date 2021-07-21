package com.droidhubworld.crashlogger.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.droidhubworld.crashlogger.fragment.CrashLoggerFragment;
import com.droidhubworld.crashlogger.fragment.ExceptionLoggerFragment;

public class LogPagerAdapterTest extends FragmentStateAdapter {
    private CrashLoggerFragment crashLoggerFragment;
    private ExceptionLoggerFragment exceptionLoggerFragment;
    private String[] titles;

    public LogPagerAdapterTest(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    public void clearLogs() {
        crashLoggerFragment.clearLog();
        exceptionLoggerFragment.clearLog();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return crashLoggerFragment = new CrashLoggerFragment();
        } else if (position == 1) {
            return exceptionLoggerFragment = new ExceptionLoggerFragment();
        } else {
            return new CrashLoggerFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
