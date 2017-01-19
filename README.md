# react-native-xlog

Wechat's Xlog framework for RN.

You can send log to Xlog, and manager Xlog lifecycle. Handle js/native crash log in production builds.

*attention*: you will see the crash log only when you restart your app

## Feature

- [x] iOS: send log to xlog for js function
- [x] iOS: RCTLog output to xlog
- [x] iOS: crash message output to xlog
- [x] android: send log to xlog for js function
- [x] android: RCTLog output to xlog
- [x] android: crash message output in the xlog



## Get Start

### Add it to your project

1. Run `npm i -S react-native-xlog`
2. Add [Xlog](https://github.com/Tencent/mars/) to you project.

### Linking iOS

run `react-native link react-native-xlog` to link the library.

> if `Header Search Paths` does not have the `$(SRCROOT)/../node_modules/react-native/React` + `recursive`, linking may fail.


## Android 
1. config gradle script
 - add dependency in android/app/build.gradle
```gradle
dependencies {
    compile fileTree(dir: "libs", include: ["*.jar"])
    compile "com.android.support:appcompat-v7:23.0.1"
    compile "com.facebook.react:react-native:+"  // From node_modules
    compile 'com.tencent.mars:mars-xlog:1.0.1' //<--add here
}
```

- add react-native-xlog project in root project's settings.gradle
```gradle
include ':react-native-xlog'
project(':react-native-xlog').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-xlog/android')
```

2. add XLogPackage 
```java
public class MainApplication extends Application implements ReactApplication {
    ...
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.asList(
                    new MainReactPackage(),
                    new XLogPackage()  // <--add here
            );
        }
    };
   ...
}   
```

2. init xlog settings.   
JS has to exception type: fatal exception(just throw JavascriptException in native) and soft exception(use FLog.e in native). you can find this from RN source file: ExceptionsManagerModule.java 
and support you two init way:

- XLogModule.init(xLogSetting): normal log, include FLog
- XLogModule.initWithNativeCrashInclude(xLogSetting, this): normal log and crash log (JS fatal exception and native uncaught exception)

```java
public class MainApplication extends Application implements ReactApplication {
  ...
   @Override
    public void onCreate() {
        super.onCreate();
        ...
        final String appName = this.getString(R.string.app_name);
        final String logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + '/' + appName + "/log";

        XLogSetting xLogSetting = XLogSetting.builder()
                .setLevel(XLogSetting.LEVEL_DEBUG)
                .setPath(logPath)
                .setCacheDir("")
                .setAppenderMode(XLogSetting.APPENDER_MODE_ASYNC)
                .setNamePrefix(appName)
                .setOpenConsoleLog(true)
                .build();
        XLogModule.init(xLogSetting)
        //XLogModule.initWithNativeCrashInclude(xLogSetting, this);
        ...
  }
  ...
}
```

3. if your want to log early, rather than delay to RNView render done, your can turn on xlog early, just add in Application's onCreate() after 
XLogModule init done. 
```java 
public class MainApplication extends Application implements ReactApplication {
  ...
   @Override
    public void onCreate() {
       // xlog init here ....
        ...
        XLogModule.open(); //optional, for this, you can log before into RNView 
        ...
  }
  ...
}
```

## Usage

```
import Xlog from 'react-native-xlog';

Xlog.verbose('tag', 'log');
Xlog.debug('tag', 'log');
Xlog.info('tag', 'log');
Xlog.warn('tag', 'log');
Xlog.error('tag', 'log');
Xlog.fatal('tag', 'log');
```

