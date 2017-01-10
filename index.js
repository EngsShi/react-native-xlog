/*
  * @flow
 */

'use strict';

import { NativeModules, NativeAppEventEmitter, DeviceEventEmitter, Platform } from 'react-native';
const XLogBridge = NativeModules.XLogBridge;

const emitter = (Platform.OS === 'android' ? DeviceEventEmitter : NativeAppEventEmitter)

// 以下参数将来可能改为原生穿透过来，待定
const kLevelAll = 0;
const kLevelVerbose = 0;
const kLevelDebug = 1;
const kLevelInfo = 2;
const kLevelWarn = 3;
const kLevelError = 4;
const kLevelFatal = 5;
const kLevelNone = 6;

// 开启xlog, logLevel为上方8种模式
const open = async (): void => {
  return await XLogBridge.open();
}

// 关闭xlog
const close = async (): void => {
  return await XLogBridge.close();
}

// 存储用户日志接口

const verbose = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelVerbose, tag, log);
}

const debug = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelDebug, tag, log);
}

const info = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelInfo, tag, log);
}

const warn = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelWarn, tag, log);
}

const error = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelError, tag, log);
}

const fatal = async (tag: string, log: string): void => {
  return await XLogBridge.log(kLevelFatal, tag, log);
}

module.exports = {
  open,
  close,
  
  verbose,
  debug,
  info,
  warn,
  error,
  fatal,
}
