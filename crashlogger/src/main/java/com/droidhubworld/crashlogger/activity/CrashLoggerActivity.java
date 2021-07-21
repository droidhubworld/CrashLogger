package com.droidhubworld.crashlogger.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.droidhubworld.crashlogger.CrashLogReporter;
import com.droidhubworld.crashlogger.R;
import com.droidhubworld.crashlogger.adapter.LogPagerAdapterTest;
import com.droidhubworld.crashlogger.utils.Constants;
import com.droidhubworld.crashlogger.utils.CrashLogUtil;
import com.droidhubworld.crashlogger.utils.FileUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class CrashLoggerActivity extends AppCompatActivity {
    private LogPagerAdapterTest logPagerAdapter;
    private int selectedTabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_logger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.crash_reporter));
        toolbar.setSubtitle(getApplicationName());
        setSupportActionBar(toolbar);

        ViewPager2 viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.crashes));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.exceptions));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabPosition = tab.getPosition();
                if (viewPager != null) {
                    viewPager.setCurrentItem(selectedTabPosition);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String tabText = "";
                switch (position) {
                    case 0:
                        tabText = getString(R.string.crashes);
                        break;
                    case 1:
                        tabText = getString(R.string.exceptions);
                        break;
                }
                tab.setText(tabText);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logger_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_crash_logs) {
            clearCrashLog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearCrashLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String crashReportPath = TextUtils.isEmpty(CrashLogReporter.getCrashReportPath()) ?
                        CrashLogUtil.getDefaultPath() : CrashLogReporter.getCrashReportPath();

                File[] logs = new File(crashReportPath).listFiles();
                for (File file : logs) {
                    FileUtils.delete(file);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logPagerAdapter.clearLogs();
                    }
                });
            }
        }).start();
    }

    private void setupViewPager(ViewPager2 viewPager) {
        String[] titles = {getString(R.string.crashes), getString(R.string.exceptions)};
//        logPagerAdapter = new LogPagerAdapterTest(getSupportFragmentManager(), titles);
        logPagerAdapter = new LogPagerAdapterTest(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(logPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                selectedTabPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        Intent intent = getIntent();
        if (intent != null && !intent.getBooleanExtra(Constants.LANDING, false)) {
            selectedTabPosition = 1;
        }
        viewPager.setCurrentItem(selectedTabPosition);
    }

    private String getApplicationName() {
        ApplicationInfo applicationInfo = getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getString(stringId);
    }
}