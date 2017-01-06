/*
  * @flow
 */

'use strict';

import { NativeModules, NativeAppEventEmitter, DeviceEventEmitter, Platform } from 'react-native';
const XLogBridge = NativeModules.XLogBridge;

const emitter = (Platform.OS === 'android' ? DeviceEventEmitter : NativeAppEventEmitter)


module.exports = {
  
}
