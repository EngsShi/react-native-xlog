//
//  XLogCrashHandler.h
//  XLogForRN
//
//  Created by shihui on 2017/1/16.
//  Copyright © 2017年 EngsSH. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface XLogCrashHandler : NSObject

void InstallUncaughtExceptionHandler(NSUncaughtExceptionHandler * _Nullable handler);
void UninstallUncaughtExceptionHandler(void);

void InstallUncaughtSignalHandler(void);
void UninstallUncaughtSignalHandler(void);

@end
