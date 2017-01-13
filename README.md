# react-native-xlog

Wecha's Xlog framework for RN.

You can send log to Xlog, and manager Xlog lifecycle. Handle js/native crash log in production builds.



## Feature

- [x] iOS: send log to xlog for js function
- [x] iOS: RCTLog output to xlog
- [x] iOS: crash message output to xlog
- [ ] android: send log to xlog for js function
- [ ] android: RCTLog output to xlog
- [ ] android: crash message output in the xlog



## Get Start

### Add it to your project

1. Run `npm i -S react-native-xlog`
2. Add [Xlog](https://github.com/Tencent/mars/) to you project.

### Linking iOS

run `react-native link react-native-xlog` to link the library.

> if `Header Search Paths` does not have the `$(SRCROOT)/../node_modules/react-native/React` + `recursive`, linking may fail.



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

