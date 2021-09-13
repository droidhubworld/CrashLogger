# CrashLogger
In App Crash Logger easily use

###### Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
   repositories {
     ...
     maven { url 'https://jitpack.io' }
    }
   }
]
```
 ###### Step 2. Add the dependency
 ```
dependencies {
         implementation 'com.github.droidhubworld:CrashLogger:v0.0.2'
}

```
###### Usage
```

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            //initialise reporter with external path
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CrashLogger";

            new CrashLogReporter.Builder(this)
                    .crashReportPath(path)
                    .crashReportFileName("crash_log")
                    .isNotificationEnabled(false)
                    .build();
        }
    }
}
```
###### Check Creah on click
```
findViewById(R.id.exceptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    context = null;
                    context.getResources();
                } catch (Exception e) {
                    //log caught Exception
                    CrashLogReporter.logReadAndWriteException(getWindow().getDecorView().getRootView(), e);
                }
            }
        });
findViewById(R.id.writeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CrashLogger";

                CrashLogReporter.logReadAndWriteException(null, path, "Text_Write", "Test Is Text Message for save");
            }
        });

```
#### For more exception type please check MainActivity.java class

## Thanks

