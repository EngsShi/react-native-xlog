/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>

#import <XLogBridge.h>

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
//  
//  
//  xinfo_function();
//  xinfo2(TSF"%_ %_", "123", 123);
//  
//  xinfo2(TSF"%0 %1 %0", "123", 345);
//  xinfo2("%s %d", "232", 123);
//
  
  
  
  
  
  NSURL *jsCodeLocation;

  jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index.ios" fallbackResource:nil];

  RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                      moduleName:@"example"
                                               initialProperties:nil
                                                   launchOptions:launchOptions];
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];

  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];
  
  
  
  
  
  
  
  // add native crash btn, test native crash log output xlog
  [self addCrashBtn: rootViewController];
  
  
  
  
  return YES;
}

- (void)applicationWillTerminate:(UIApplication *)application {
  // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
  
//  [XLogBridge uninstallUncaughtCrashHandler];
//  [XLogBridge close];
}

#pragma mark - crash btn

- (void)addCrashBtn:(UIViewController *)vc {
  CGRect bounds = self.window.bounds;
  
  UIButton *arrayCrashBtn = [UIButton buttonWithType:UIButtonTypeCustom];
  arrayCrashBtn.frame = CGRectMake(10, bounds.size.height - 50 , 140, 40);
  arrayCrashBtn.backgroundColor = [UIColor yellowColor];
  [arrayCrashBtn setTitleColor:[UIColor blackColor] forState:UIButtonTypeCustom];
  arrayCrashBtn.titleLabel.font = [UIFont systemFontOfSize:14];
  [arrayCrashBtn setTitle:@"native crash: Array" forState:UIButtonTypeCustom];
  [arrayCrashBtn addTarget:self action:@selector(arrayCrashOnClick:) forControlEvents:UIControlEventTouchUpInside];
  [vc.view addSubview:arrayCrashBtn];
  
  
  UIButton *memoryCrashBtn = [UIButton buttonWithType:UIButtonTypeCustom];
  memoryCrashBtn.frame = CGRectMake(bounds.size.width - 150, bounds.size.height - 50 , 140, 40);
  memoryCrashBtn.backgroundColor = [UIColor yellowColor];
  [memoryCrashBtn setTitleColor:[UIColor blackColor] forState:UIButtonTypeCustom];
  memoryCrashBtn.titleLabel.font = [UIFont systemFontOfSize:14];
  [memoryCrashBtn setTitle:@"native crash: memory" forState:UIButtonTypeCustom];
  [memoryCrashBtn addTarget:self action:@selector(memoryCrashOnClick:) forControlEvents:UIControlEventTouchUpInside];
  [vc.view addSubview:memoryCrashBtn];
}

- (void)arrayCrashOnClick:(UIButton *)btn {
  NSArray *array = @[];
  array[1];
}

- (void)memoryCrashOnClick:(UIButton *)btn {
  NSURL *url = [[NSURL alloc] initWithString:@"http://www.baidu.com"];
  CFURLRef ref = (__bridge CFURLRef)url;
  CFRelease(ref);
  NSLog(@"Here will crash. %ld", CFGetRetainCount(ref));
}

@end
