package com.droidhubworld.crashlogger.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhubworld.crashlogger.CrashLogReporter;
import com.droidhubworld.crashlogger.R;
import com.droidhubworld.crashlogger.adapter.CrashLogAdapter;
import com.droidhubworld.crashlogger.utils.Constants;
import com.droidhubworld.crashlogger.utils.CrashLogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class CrashLoggerFragment extends Fragment {

    private CrashLogAdapter logAdapter;

    private RecyclerView crashRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crash_logger, container, false);
        crashRecyclerView = (RecyclerView) view.findViewById(R.id.crashRecyclerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAdapter(getActivity(), crashRecyclerView);
    }

    private void loadAdapter(Context context, RecyclerView crashRecyclerView) {

        logAdapter = new CrashLogAdapter(context, getAllCrashes());
        crashRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        crashRecyclerView.setAdapter(logAdapter);
    }

    public void clearLog() {
        if (logAdapter != null) {
            logAdapter.updateList(getAllCrashes());
        }
    }


    private ArrayList<File> getAllCrashes() {
        String directoryPath;
        String crashReportPath = CrashLogReporter.getCrashReportPath();

        if (TextUtils.isEmpty(crashReportPath)) {
            directoryPath = CrashLogUtil.getDefaultPath();
        } else {
            directoryPath = crashReportPath;
        }
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new RuntimeException("The path provided doesn't exists : " + directoryPath);
        }
        ArrayList<File> listOfFiles = new ArrayList<>(Arrays.asList(directory.listFiles()));
        for (Iterator<File> iterator = listOfFiles.iterator(); iterator.hasNext(); ) {
            if (iterator.next().getName().contains(Constants.EXCEPTION_SUFFIX)) {
                iterator.remove();
            }
        }
        Collections.sort(listOfFiles, Collections.reverseOrder());
        return listOfFiles;
    }

}