//
//  XLogCrashHandler.m
//  XLogForRN
//
//  Created by shihui on 2017/1/16.
//  Copyright © 2017年 EngsSH. All rights reserved.
//


#import "XLogCrashHandler.h"

#include <libkern/OSAtomic.h>
#include <execinfo.h>

#import "LogUtil.h"


volatile int32_t UncaughtExceptionCount = 0;
const int32_t UncaughtExceptionMaximum = 10;

@implementation XLogCrashHandler

#pragma mark - exception

void InstallUncaughtExceptionHandler(NSUncaughtExceptionHandler * _Nullable handler) {
  if (!handler) {
    handler = &UncaughtExceptionHandler;
  }
  NSSetUncaughtExceptionHandler(handler);
}

void UninstallUncaughtExceptionHandler(void) {
  NSSetUncaughtExceptionHandler(NULL);
}

void UncaughtExceptionHandler(NSException *exception) {
  NSArray *arr = [exception callStackSymbols];//得到当前调用栈信息
  NSString *reason = [exception reason];//非常重要，就是崩溃的原因
  NSString *name = [exception name];//异常类型
  
  NSString *log = [NSString stringWithFormat:@"exception type : %@ \n crash reason : %@ \n call stack info : %@", name, reason, arr];
  
  LOG_MESSAGE(kLevelFatal, "ios crash", log);
}

#pragma mark - memory crash

void InstallUncaughtSignalHandler(void) {
  signal(SIGABRT, SignalHandler);
  signal(SIGILL, SignalHandler);
  signal(SIGSEGV, SignalHandler);
  signal(SIGFPE, SignalHandler);
  signal(SIGBUS, SignalHandler);
  signal(SIGPIPE, SignalHandler);
}

void UninstallUncaughtSignalHandler(void) {
  signal(SIGABRT, SIG_DFL);
  signal(SIGILL, SIG_DFL);
  signal(SIGSEGV, SIG_DFL);
  signal(SIGFPE, SIG_DFL);
  signal(SIGBUS, SIG_DFL);
  signal(SIGPIPE, SIG_DFL);
}


+ (NSArray *)backtrace {
  void* callstack[128];
  int frames = backtrace(callstack, 128);
  char **strs = backtrace_symbols(callstack, frames);
  
  int i;
  NSMutableArray *backtrace = [NSMutableArray arrayWithCapacity:frames];
  for ( i = 0; i < frames;  i++) {
    [backtrace addObject:[NSString stringWithUTF8String:strs[i]]];
  }
  free(strs);
  
  return backtrace;
}


void SignalHandler(int _signal) {
  int32_t exceptionCount = OSAtomicIncrement32(&UncaughtExceptionCount);
  if (exceptionCount > UncaughtExceptionMaximum) {
    return;
  }
  
  NSArray *arr = [XLogCrashHandler backtrace]; //得到当前调用栈信息
  NSString *reason = [NSString stringWithFormat:@"Signal %d was raised.", _signal]; //非常重要，就是崩溃的原因
  
  NSString *log = [NSString stringWithFormat:@"crash reason : %@ \n call stack info : %@", reason, arr];
  
  LOG_MESSAGE(kLevelFatal, "ios crash", log);
  
  UninstallUncaughtSignalHandler();
}

@end
