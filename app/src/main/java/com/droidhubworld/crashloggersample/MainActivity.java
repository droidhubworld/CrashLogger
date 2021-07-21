package com.droidhubworld.crashloggersample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.droidhubworld.crashlogger.CrashLogReporter;
import com.droidhubworld.crashlogger.activity.CrashLoggerActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.nullPointer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = null;
                context.getResources();
            }
        });

        findViewById(R.id.indexOutOfBound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList();
                list.add("hello");
                String crashMe = list.get(2);
            }
        });

        findViewById(R.id.classCastExeption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object x = new Integer(0);
                System.out.println((String) x);

            }
        });

        findViewById(R.id.arrayStoreException).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object x[] = new String[3];
                x[0] = new Integer(0);

            }
        });


        //Crashes and exceptions are also captured from other threads
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context = null;
                    context.getResources();
                } catch (Exception e) {
                    //log caught Exception
                    CrashLogReporter.logException(e);
                }

            }
        }).start();*/
        mContext = this;
        findViewById(R.id.crashLogActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CrashLoggerActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.exceptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    context = null;
                    context.getResources();
                } catch (Exception e) {
                    //log caught Exception
                    CrashLogReporter.logException(getWindow().getDecorView().getRootView(), e);
                }
            }
        });

    }
}