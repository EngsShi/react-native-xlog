//
//  XLogForRN.m
//  XLogForRN
//
//  Created by shihui on 2017/1/6.
//  Copyright © 2017年 EngsSH. All rights reserved.
//

#import "XLogBridge.h"

#import "RCTBridge.h"


#import <sys/xattr.h>

#include <mars/xlog/xlogger.h>
#include <mars/xlog/appender.h>

#import "LogUtil.h"


@implementation XLogBridge

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

static __strong NSString *logPath = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingString:@"/log"];
static TLogLevel level = kLevelAll;
static BOOL showConsoleLog = YES;
static TAppenderMode mode = kAppednerAsync;
static __strong NSString *nameprefix = @"Test";

#pragma mark - register xlog setting

+ (void)registerWithLogPath:(NSString *)_logPath level:(TLogLevel)_level showConsoleLog:(BOOL)_showConsoleLog appenderMode:(TAppenderMode)_mode nameprefix:(NSString *)_nameprefix {
  
  logPath = _logPath;
  level = _level;
  showConsoleLog = _showConsoleLog;
  mode = _mode;
  nameprefix = _nameprefix;
}

+ (void)open {
  // set do not backup for logpath
  const char* attrName = "com.apple.MobileBackup";
  u_int8_t attrValue = 1;
  setxattr([logPath UTF8String], attrName, &attrValue, sizeof(attrValue), 0, 0);

  // init xlog
  xlogger_SetLevel(level);
  appender_set_console_log(showConsoleLog);
  
  appender_open(mode, [logPath UTF8String], [nameprefix UTF8String]);
}

+ (void)close {
  appender_close();
}


#pragma mark - open & close

RCT_EXPORT_METHOD(open
                  :(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
  [XLogBridge open];
  
  if (resolve) {
    resolve(nil);
  }
}

RCT_EXPORT_METHOD(close
                  :(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
  [XLogBridge close];
  
  if (resolve) {
    resolve(nil);
  }
}


#pragma mark - log

RCT_EXPORT_METHOD(log:(int)level
                  :(NSString *)tag
                  :(NSString *)log
                  :(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
  
  LOG_MESSAGE((TLogLevel)level, [tag UTF8String], log);
  
  
  if (resolve) {
    resolve(nil);
  }
}


@end
