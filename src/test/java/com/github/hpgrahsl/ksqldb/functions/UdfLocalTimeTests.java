/*
 * Copyright (c) 2020. Hans-Peter Grahsl (grahslhp@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.hpgrahsl.ksqldb.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UdfLocalTimeTests {

  @DisplayName("applying UDF dt_localtime with hour,minute,second")
  @ParameterizedTest(name = "dt_localtime({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#createLocalTimeSamplesHoursMinutesSeconds")
  void applyUdfLocalTimeHourMinuteSecond(Integer hour,Integer minute,Integer second,Struct result) {
    assertEquals(result, new UdfLocalTime().createLocalTime(hour,minute,second));
  }

  @DisplayName("applying UDF dt_localtime with hour,minute,second,nano")
  @ParameterizedTest(name = "dt_localtime({0},{1},{2},{3}) = {4}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#createLocalTimeSamplesHoursMinutesSecondsNanos")
  void applyUdfLocalTimeHourMinuteSecondNano(Integer hour,Integer minute,Integer second,Integer nano,Struct result) {
    assertEquals(result, new UdfLocalTime().createLocalTime(hour,minute,second,nano));
  }

  @DisplayName("applying UDF dt_localtime with text")
  @ParameterizedTest(name = "dt_localtime({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#createLocalTimeSamplesText")
  void applyUdfLocalTimeText(String text,Struct result) {
    assertEquals(result, new UdfLocalTime().createLocalTime(text));
  }

  @DisplayName("applying UDF dt_localtime with text and pattern")
  @ParameterizedTest(name = "dt_localtime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#createLocalTimeSamplesTextPattern")
  void applyUdfLocalTimeTextPattern(String text,String pattern,Struct result) {
    assertEquals(result, new UdfLocalTime().createLocalTime(text,pattern));
  }

  @DisplayName("applying UDF dt_localtime_format")
  @ParameterizedTest(name = "dt_localtime_format({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#formatSamples")
  void applyUdfLocalTimeFormat(Struct localTime,String result) {
    assertEquals(result, new UdfLocalTimeFormat().format(localTime));
  }

  @DisplayName("applying UDF dt_localtime_format with pattern")
  @ParameterizedTest(name = "dt_localtime_format({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#formatPatternSamples")
  void applyUdfLocalTimeFormatPattern(Struct localTime,String pattern,String result) {
    assertEquals(result, new UdfLocalTimeFormat().format(localTime,pattern));
  }

  @DisplayName("applying UDF dt_localtime_chronology with baseLocalTime,localTime,chronologyMode")
  @ParameterizedTest(name = "dt_localtime_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#chronologySamples")
  void applyUdfLocalTimeChronology(Struct baseLocalTime,Struct localTime,String chronologyMode,Boolean result) {
    assertEquals(result, new UdfLocalTimeChronology().check(baseLocalTime,localTime,chronologyMode));
  }

  @DisplayName("applying UDF dt_localtime_minus with baseLocalTime and duration")
  @ParameterizedTest(name = "dt_localtime_minus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#minusSamplesBaseLocalTimeDuration")
  void applyUdfLocalTimeMinusBaseLocalTimeDuration(Struct baseLocalTime,Struct duration,Struct result) {
    assertEquals(result, new UdfLocalTimeMinus().minus(baseLocalTime,duration));
  }

  @DisplayName("applying UDF dt_localtime_plus with baseLocalTime and duration")
  @ParameterizedTest(name = "dt_localtime_plus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalTimeUdfsArgumentsProvider#plusSamplesBaseLocalTimeDuration")
  void applyUdfLocalTimePlusBaseLocalTimeDuration(Struct baseLocalTime,Struct duration,Struct result) {
    assertEquals(result, new UdfLocalTimePlus().plus(baseLocalTime,duration));
  }

}
