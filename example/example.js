/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  Dimensions,
  Linking,
  StyleSheet,
  Text,
  TouchableOpacity,
  ScrollView,
  View,
} from 'react-native';

import XLog from 'react-native-xlog';



type Props = {
  
}

type State = {
  msgArray: Array<string>,
}

export default class example extends Component {
  state: State;
  props: Props;

  constructor(props: Props) {
    super(props);

    this.state = {
      msgArray: [],
    },

    XLog.open();
  }

  componentWillUnmount() {
    XLog.close();
  }

  ////////////

  render() {

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          XLog Example
        </Text>

        <View style={styles.btnView}>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.verbose('tag verbose', 'verbose message is here')}}><Text>verbose</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.debug('tag debug', 'debug message is here')}}><Text>debug</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.info('tag info', 'info message is here')}}><Text>info</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.warn('tag warn', 'warn message is here')}}><Text>warn</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.error('tag error', 'error message is here')}}><Text>error</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {XLog.fatal('tag fatal', 'fatal message is here')}}><Text>fatal</Text></TouchableOpacity>
        </View>

        <Text style={styles.chapterText}>
          send console log
        </Text>
        <View style={styles.consoleLogView}>
          <TouchableOpacity style={styles.btn} onPress={()=> {console.log('console log is here')}}><Text>log</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {console.warn('console warn is here')}}><Text>warn</Text></TouchableOpacity>
          <TouchableOpacity style={styles.btn} onPress={()=> {console.error('console error is here')}}><Text>error</Text></TouchableOpacity>
        </View>

        <Text style={styles.chapterText}>
          crash test
        </Text>
        <View style={styles.consoleLogView}>
          <TouchableOpacity style={styles.btn} onPress={()=> {throw new Error('crash test is here')}}><Text>crash</Text></TouchableOpacity>
        </View>
      </View>
    );
  }
}

const window = Dimensions.get('window');

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    paddingTop: 66,
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },

  btnView: {
    width: window.width,
    flexWrap: 'wrap',
    flexDirection: 'row',
  },
  btn: {
    width: 80,
    height: 80,
    margin: 10,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#9ff',
  },

  consoleLogView: {
    width: window.width,
    flexWrap: 'wrap',
    flexDirection: 'row',
  },
  chapterText: {
    marginTop: 30,
    width: window.width,
    textAlign: 'center',
  },
});