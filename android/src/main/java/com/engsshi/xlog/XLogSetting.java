package com.engsshi.xlog;

import com.facebook.infer.annotation.Assertions;

/**
 * Created by Sean.Ye on 2017/1/17.
 */

public final class XLogSetting {
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;

    public static final int APPENDER_MODE_ASYNC = 0;
    public static final int APPENDER_MODE_SYNC = 1;

    private int level;
    private int appenderMode;
    private String cacheDir;
    private String path;
    private String namePrefix;
    private boolean openConsoleLog;

    public XLogSetting(int level,
                       int appenderMode,
                       String cacheDir,
                       String path,
                       String namePrefix,
                       boolean openConsoleLog) {
        this.level = level;
        this.appenderMode = appenderMode;
        this.cacheDir = cacheDir;
        this.path = path;
        this.namePrefix = namePrefix;
        this.openConsoleLog = openConsoleLog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getLevel() {
        return level;
    }

    public int getAppenderMode() {
        return appenderMode;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public String getPath() {
        return path;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public boolean isOpenConsoleLog() {
        return openConsoleLog;
    }

    public static class Builder {
        /**
         * log level
         * <p>
         * default LEVEL_NONE
         */
        private int level = LEVEL_NONE;
        /**
         * appender mode
         * <p>
         * default APPENDER_MODE_ASYNC
         */
        private int appenderMode = APPENDER_MODE_ASYNC;
        /**
         * cache dir,default ""
         * 缓存目录，当 logDir 不可写时候会写进这个目录，可选项，不选用请给 ""， 如若要给，建议给应用的 /data/data/packname/files/log 目录。
         * https://github.com/Tencent/mars/wiki/Mars-Android-%E6%8E%A5%E5%8F%A3%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E
         */
        private String cacheDir = "";
        /**
         * log path
         */
        private String path;

        /**
         * name prefix
         */
        private String namePrefix;

        /**
         * open console log, default false
         */
        private boolean openConsoleLog = false;

        public Builder setLevel(int level) {
            this.level = level;
            return this;
        }

        public Builder setAppenderMode(int appenderMode) {
            this.appenderMode = appenderMode;
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
            return this;
        }

        public Builder setOpenConsoleLog(boolean openConsoleLog) {
            this.openConsoleLog = openConsoleLog;
            return this;
        }

        public XLogSetting build() {
            return new XLogSetting(
                    level,
                    appenderMode,
                    cacheDir,
                    Assertions.assertNotNull(path),
                    Assertions.assertNotNull(namePrefix),
                    openConsoleLog);
        }
    }
}
