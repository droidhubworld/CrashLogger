package com.droidhubworld.crashlogger.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils() {
        //this class is not publicly instantiable
    }

    public static String createFolder(Context context, String directoryName) {
        File externalStorageDir;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            File path = Environment.getExternalStorageDirectory();
            String path = context.getExternalFilesDir(null).getAbsolutePath();

            externalStorageDir = new File(path, directoryName);
            if (!externalStorageDir.exists()) {
                externalStorageDir.mkdirs();
            }
        } else {
            externalStorageDir = new File(Environment.getExternalStorageDirectory(), directoryName);
            if (!externalStorageDir.exists()) {
                externalStorageDir.mkdirs();
            }
        }*/
        externalStorageDir = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!externalStorageDir.exists()) {
            externalStorageDir.mkdirs();
        }
        return externalStorageDir.getAbsolutePath();
    }

    public static boolean isDirExit(Context context, String directoryName){
        File externalStorageDir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String path = context.getExternalFilesDir(null).getAbsolutePath();

            externalStorageDir = new File(path, directoryName);

        } else {
            externalStorageDir = new File(Environment.getExternalStorageDirectory(), directoryName);
        }
        return externalStorageDir.exists();
    }

    public static boolean delete(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return false;
        }

        File file = new File(absPath);
        return delete(file);
    }

    public static boolean delete(File file) {
        if (!exists(file)) {
            return true;
        }

        if (file.isFile()) {
            return file.delete();
        }

        boolean result = true;
        File files[] = file.listFiles();
        if (files == null) return false;
        for (int index = 0; index < files.length; index++) {
            result |= delete(files[index]);
        }
        result |= file.delete();

        return result;
    }

    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    public static String cleanPath(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return absPath;
        }
        try {
            File file = new File(absPath);
            absPath = file.getCanonicalPath();
        } catch (Exception e) {

        }
        return absPath;
    }

    public final static String getParent(File file) {
        return file == null ? null : file.getParent();
    }

    public final static String getParent(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        absPath = cleanPath(absPath);
        File file = new File(absPath);
        return getParent(file);
    }

    public static boolean deleteFiles(String directoryPath) {
        String directoryToDelete;
        if (!TextUtils.isEmpty(directoryPath)) {
            directoryToDelete = directoryPath;
        } else {
            directoryToDelete = CrashLogUtil.getDefaultPath();
        }

        return delete(directoryToDelete);
    }

    public static String readFirstLineFromFile(File file) {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static String readFromFile(File file) {
        StringBuilder crash = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                crash.append(line);
                crash.append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crash.toString();
    }
}
