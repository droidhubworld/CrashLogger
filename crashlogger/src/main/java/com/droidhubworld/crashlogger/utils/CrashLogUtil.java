package com.droidhubworld.crashlogger.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.droidhubworld.crashlogger.CrashLogReporter;
import com.droidhubworld.crashlogger.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.droidhubworld.crashlogger.utils.Constants.CHANNEL_NOTIFICATION_ID;

public class CrashLogUtil {
    private static final String tag = CrashLogUtil.class.getSimpleName();

    private CrashLogUtil() {
        //this class is not publicly instantiable
    }

    private static String getCrashLogTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static void saveCrashReport(final Throwable throwable) {

        String crashReportPath = CrashLogReporter.getCrashReportPath();
        String filename = getCrashLogTime() + Constants.CRASH_SUFFIX + Constants.FILE_EXTENSION;
        writeToFile(crashReportPath, filename, getStackTrace(null, null, throwable), true);

        showNotification(throwable.getLocalizedMessage(), true);
    }

    public static void takeScreenshot(View view) {

        try {
            String screenShortPath = CrashLogReporter.getCrashReportPath();
            if (TextUtils.isEmpty(screenShortPath)) {
                screenShortPath = getDefaultScreenShortPath();
            }
            String mFileName = getCrashLogTime() + "_" + CrashLogReporter.getCrashReportFileName() + Constants.SCREEN_SHORT_SUFFIX + Constants.SCREEN_SHORT_FILE_EXTENSION;

            File crashDir = new File(screenShortPath);
            if (!crashDir.exists() || !crashDir.isDirectory()) {
                screenShortPath = getDefaultScreenShortPath();
                Logger.e(tag, "Path provided doesn't exists : " + crashDir + "\nSaving crash report at : " + getDefaultPath());
            }
            String mPath = screenShortPath + File.separator + mFileName;
            // create bitmap screen capture
            //View v1 = ((AppCompatActivity) CrashLogReporter.getContext()).getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Logger.e(tag, "SCREEN SHORT PATH : " + imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public static void logException(final Exception exception) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String crashReportPath = CrashLogReporter.getCrashReportPath();
                final String filename = getCrashLogTime() + Constants.EXCEPTION_SUFFIX + Constants.FILE_EXTENSION;
                writeToFile(crashReportPath, filename, getStackTrace(null, null, exception), true);

                showNotification(exception.getLocalizedMessage(), false);
            }
        }).start();
    }

    public static void readAndWrite(Exception exception) {

        new Thread(() -> {
            String crashReportPath = CrashLogReporter.getCrashReportPath();
            String filename = CrashLogReporter.getCrashReportFileName() + Constants.EXCEPTION_SUFFIX + Constants.FILE_EXTENSION;

            String dirPath = crashReportPath + File.separator + filename;
            File file = new File(dirPath);
            if (!file.exists()) {
                writeToFile(crashReportPath, filename, getStackTrace(null, null, exception), true);
            } else {
                String crashLog = FileUtils.readFromFile(file);
                try {
                    JSONObject data = new JSONObject(crashLog);
                    JSONArray oldData = data.optJSONArray(Constants.EXCEPTION_SUFFIX);
                    writeToFile(crashReportPath, filename, getStackTrace(oldData, null, exception), true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            showNotification(exception.getLocalizedMessage(), false);
        }).start();
    }

    public static void readAndWrite(Throwable exception) {

        new Thread(() -> {
            String crashReportPath = CrashLogReporter.getCrashReportPath();
            String filename = CrashLogReporter.getCrashReportFileName() + Constants.EXCEPTION_SUFFIX + Constants.FILE_EXTENSION;

            String dirPath = crashReportPath + File.separator + filename;
            File file = new File(dirPath);
            if (!file.exists()) {
                writeToFile(crashReportPath, filename, getStackTrace(null, null, exception), true);
            } else {
                String crashLog = FileUtils.readFromFile(file);
                try {
                    JSONObject data = new JSONObject(crashLog);
                    JSONArray oldData = data.optJSONArray(Constants.EXCEPTION_SUFFIX);
                    writeToFile(crashReportPath, filename, getStackTrace(oldData, null, exception), true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            showNotification(exception.getLocalizedMessage(), false);
        }).start();
    }

    public static void readAndWrite(String tag, Throwable exception) {

        new Thread(() -> {
            String crashReportPath = CrashLogReporter.getCrashReportPath();
            String filename = CrashLogReporter.getCrashReportFileName() + Constants.EXCEPTION_SUFFIX + Constants.FILE_EXTENSION;

            String dirPath = crashReportPath + File.separator + filename;
            File file = new File(dirPath);
            if (!file.exists()) {
                writeToFile(crashReportPath, filename, getStackTrace(null, tag, exception), true);
            } else {
                String crashLog = FileUtils.readFromFile(file);
                try {
                    JSONObject data = new JSONObject(crashLog);
                    JSONArray oldData = data.optJSONArray(Constants.EXCEPTION_SUFFIX);
                    writeToFile(crashReportPath, filename, getStackTrace(oldData, tag, exception), true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            showNotification(exception.getLocalizedMessage(), false);
        }).start();
    }

    public static void readAndWrite(Throwable exception, String tag, String crashReportPath, String fileName) {

        new Thread(() -> {
            String _fileName = fileName + "_" + Constants.DATA_SUFFIX + Constants.FILE_EXTENSION;

            String dirPath = crashReportPath + File.separator + _fileName;
            File file = new File(dirPath);
            if (!file.exists()) {
                writeToFile(crashReportPath, _fileName, getStackTrace(null, tag, exception), true);
            } else {
                String crashLog = FileUtils.readFromFile(file);
                try {
                    JSONObject data = new JSONObject(crashLog);
                    JSONArray oldData = data.optJSONArray(Constants.DATA_SUFFIX);
                    writeToFile(crashReportPath, _fileName, getStackTrace(oldData, tag, exception), true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            showNotification(exception.getLocalizedMessage(), false);
        }).start();
    }

    public static void readAndWrite(String text, String tag, String crashReportPath, String fileName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String _fileName = fileName + "_" + Constants.DATA_SUFFIX + Constants.FILE_EXTENSION;

                String dirPath = crashReportPath + File.separator + _fileName;
                File file = new File(dirPath);
                if (!file.exists()) {
                    writeToFile(crashReportPath, _fileName, getStackTrace(null, tag, text), false);
                } else {
                    String crashLog = FileUtils.readFromFile(file);
                    try {
                        JSONObject data = new JSONObject(crashLog);
                        JSONArray oldData = data.optJSONArray(Constants.DATA_SUFFIX);
                        writeToFile(crashReportPath, _fileName, getStackTrace(oldData, tag, text), false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                showNotification(text, false);
            }
        }).start();
    }

    private static void writeToFile(String crashReportPath, String filename, JSONArray crashLog, boolean isException) {

        if (TextUtils.isEmpty(crashReportPath)) {
            crashReportPath = getDefaultPath();
        }

        File crashDir = new File(crashReportPath);
        if (!crashDir.exists() || !crashDir.isDirectory()) {
            if (crashDir.mkdirs()) {
                crashReportPath = crashDir.getAbsolutePath();
            } else {
                crashReportPath = getDefaultPath();
            }
            Logger.e(tag, "Path provided doesn't exists : " + crashDir + "\nSaving crash report at : " + getDefaultPath());
        }

        BufferedWriter bufferedWriter;
        try {
            JSONObject object = new JSONObject();

            object.put((isException) ? Constants.EXCEPTION_SUFFIX : Constants.DATA_SUFFIX, crashLog);

            String systemInfo = AppUtils.getDeviceDetails(CrashLogReporter.getContext());
            object.put(Constants.DEVISE_INFO, systemInfo);

            bufferedWriter = new BufferedWriter(new FileWriter(
                    crashReportPath + File.separator + filename));

            bufferedWriter.write(object.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            Logger.d(tag, "crash report saved in : " + crashReportPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showNotification(String localisedMsg, boolean isCrash) {

        if (CrashLogReporter.isNotificationEnabled()) {
            Context context = CrashLogReporter.getContext();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            createNotificationChannel(notificationManager, context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NOTIFICATION_ID);
            builder.setSmallIcon(R.drawable.ic_bug_24dp);

            Intent intent = CrashLogReporter.getLaunchIntent();
            intent.putExtra(Constants.LANDING, isCrash);
            intent.setAction(Long.toString(System.currentTimeMillis()));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);

            builder.setContentTitle(context.getString(R.string.view_crash_report));

            if (TextUtils.isEmpty(localisedMsg)) {
                builder.setContentText(context.getString(R.string.check_your_message_here));
            } else {
                builder.setContentText(localisedMsg);
            }

            builder.setAutoCancel(true);
            builder.setColor(ContextCompat.getColor(context, R.color.colorAccent_CrashLogger));

            notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
        }

    }

    private static void createNotificationChannel(NotificationManager notificationManager, Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            CharSequence name = context.getString(R.string.notification_crash_report_title);
            String description = "";
            NotificationChannel channel = new NotificationChannel(CHANNEL_NOTIFICATION_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static JSONArray getStackTrace(JSONArray oldData, String tag, Throwable e) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        try {

            e.printStackTrace(printWriter);

            JSONArray jsonArray = oldData;

            if (jsonArray == null) {
                jsonArray = new JSONArray();
            }

            JSONObject object1 = new JSONObject();
            object1.put(Constants.EXCEPTION_DATE_SUFFIX, getCrashLogTime());
            if (tag != null)
                object1.put(Constants.TAG_SUFFIX, tag);



            /*if (oldData != null) {
                jsonArray.put(oldData);
            }*/
//        crashLog = result.toString();

            object1.put(Constants.EXCEPTION_SUFFIX, result.toString());

            jsonArray.put(object1);

            printWriter.close();
            return jsonArray;
        } catch (Exception _e) {
            _e.printStackTrace();
            return oldData;
        }
    }

    private static JSONArray getStackTrace(JSONArray oldData, String tag, String text) {

        try {
            JSONArray jsonArray = oldData;

            if (jsonArray == null) {
                jsonArray = new JSONArray();
            }

            JSONObject object1 = new JSONObject();
            object1.put(Constants.DATE_SUFFIX, getCrashLogTime());
            if (tag != null)
                object1.put(Constants.TAG_SUFFIX, tag);
            object1.put(Constants.DATA_SUFFIX, text);

            jsonArray.put(object1);

            return jsonArray;
        } catch (Exception _e) {
            _e.printStackTrace();
            return oldData;
        }
    }

    public static String getDefaultPath() {
        String defaultPath = CrashLogReporter.getContext().getExternalFilesDir(null).getAbsolutePath()
                + File.separator + Constants.CRASH_REPORT_DIR;

        File file = new File(defaultPath);
        file.mkdirs();
        return defaultPath;
    }

    public static String getDefaultScreenShortPath() {
        String defaultPath = CrashLogReporter.getContext().getExternalFilesDir(null).getAbsolutePath()
                + File.separator + Constants.SCREEN_SHORT_DIR;

        File file = new File(defaultPath);
        file.mkdirs();
        return defaultPath;
    }

}
