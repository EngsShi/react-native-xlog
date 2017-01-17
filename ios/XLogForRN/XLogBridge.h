//
//  XLogForRN.h
//  XLogForRN
//
//  Created by shihui on 2017/1/6.
//  Copyright © 2017年 EngsSH. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RCTBridgeModule.h"

#include <mars/xlog/xlogger.h>
#include <mars/xlog/appender.h>

@interface XLogBridge : NSObject <RCTBridgeModule>

/**
 if you want to manager xlog lifecycle in the RN, or call the XLogBridge method in the native, you need set xlog open parameters.
 if you want to use native manager, you can ignore this function.
 
 @param logPath log file path
 @param level log level
 @param showConsoleLog log can be output in the console
 @param mode write file mode
 @param nameprefix log file prefix
 */
+ (void)registerWithLogPath:(NSString *)logPath level:(TLogLevel)level showConsoleLog:(BOOL)showConsoleLog appenderMode:(TAppenderMode)mode nameprefix:(NSString *)nameprefix;

/**
 open xlog use XLogBridge
 */
+ (void)open;

/**
 close xlog use XLogBridge
 */
+ (void)close;


#pragma mark - handle crash log

/**
 handle crash, and send crash message to xlog

 @param handler crash message handler, send message to xlog. If you set nil, use default handler.
 */
+ (void)installUncaughtCrashHandler:(NSUncaughtExceptionHandler * _Nullable)handler;

/**
 clear crash handler
 */
+ (void)uninstallUncaughtCrashHandler;

@end
