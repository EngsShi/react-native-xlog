# react-native-xlog

Wecha's Xlog framework for RN.

You can send log to Xlog, and manager Xlog lifecycel.





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



## TODO

- [ ] iOS: RN crash message output in the xlog
- [ ] android: RN crash message output in the xlog