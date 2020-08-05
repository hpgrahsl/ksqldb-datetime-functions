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

public class UdfLocalDateTimeTests {

  @DisplayName("applying UDF dt_localdatetime with localdate and localtime")
  @ParameterizedTest(name = "dt_localdatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#createLocalDateTimeSamplesLocalDateLocalTime")
  void applyUdfLocalDateTimeLocalDateLocalTime(Struct localDate,Struct localTime,Struct result) {
    assertEquals(result, new UdfLocalDateTime().createLocalDateTime(localDate,localTime));
  }

  @DisplayName("applying UDF dt_localdatetime with year,month,day,hour,minute,second,nano")
  @ParameterizedTest(name = "dt_localdatetime({0},{1},{2},{3},{4},{5},{6}) = {7}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#createLocalDateTimeSamplesAllParts")
  void applyUdfLocalDateTimeAllParts(Integer year,Integer month,Integer day,Integer hour,Integer minute,Integer second,Integer nano,Struct result) {
    assertEquals(result, new UdfLocalDateTime().createLocalDateTime(year,month,day,hour,minute,second,nano));
  }

  @DisplayName("applying UDF dt_localdatetime with epochmillis")
  @ParameterizedTest(name = "dt_localdatetime({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#createLocalDateTimeSamplesEpochMillis")
  void applyUdfLocalDateTimeEpochMillis(Long epochMillis,Struct result) {
    assertEquals(result, new UdfLocalDateTime().createLocalDateTime(epochMillis));
  }

  @DisplayName("applying UDF dt_localdatetime with text")
  @ParameterizedTest(name = "dt_localdatetime({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#createLocalDateTimeSamplesText")
  void applyUdfLocalDateTimeText(String text,Struct result) {
    assertEquals(result, new UdfLocalDateTime().createLocalDateTime(text));
  }

  @DisplayName("applying UDF dt_localdatetime with text and pattern")
  @ParameterizedTest(name = "dt_localdatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#createLocalDateTimeSamplesTextPattern")
  void applyUdfLocalDateTimeTextPattern(String text,String pattern,Struct result) {
    assertEquals(result, new UdfLocalDateTime().createLocalDateTime(text,pattern));
  }

  @DisplayName("applying UDF dt_localdatetime_format with localDateTime")
  @ParameterizedTest(name = "dt_localdatetime_format({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#formatSamples")
  void applyUdfLocalDateTimeFormat(Struct localDateTime,String result) {
    assertEquals(result, new UdfLocalDateTimeFormat().format(localDateTime));
  }

  @DisplayName("applying UDF dt_localdatetime_format with localDateTime and pattern")
  @ParameterizedTest(name = "dt_localdatetime_format({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#formatPatternSamples")
  void applyUdfLocalDateTimeFormatPattern(Struct localDateTime,String pattern,String result) {
    assertEquals(result, new UdfLocalDateTimeFormat().format(localDateTime,pattern));
  }

  @DisplayName("applying UDF dt_localdatetime_chronology with baseLocalDateTime, localDateTime, chronologyMode")
  @ParameterizedTest(name = "dt_localdatetime_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#chronologySamples")
  void applyUdfLocalDateTimeChronology(Struct baseLocalDateTime,Struct localDateTime,String chronologyMode,Boolean result) {
    assertEquals(result, new UdfLocalDateTimeChronology().check(baseLocalDateTime, localDateTime, chronologyMode));
  }

  @DisplayName("applying UDF dt_localdatetime_minus with baseLocalDateTime, period, duration")
  @ParameterizedTest(name = "dt_localdatetime_minus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#minusSamplesBaseLocalDateTimePeriodDuration")
  void applyUdfLocalDateTimeMinusBaseLocalDateTimePeriodDuration(Struct baseLocalDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfLocalDateTimeMinus().minus(baseLocalDateTime, period, duration));
  }

  @DisplayName("applying UDF dt_localdatetime_plus with baseLocalDateTime, period, duration")
  @ParameterizedTest(name = "dt_localdatetime_plus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateTimeUdfsArgumentsProvider#plusSamplesBaseLocalDateTimePeriodDuration")
  void applyUdfLocalDateTimePlusBaseLocalDateTimePeriodDuration(Struct baseLocalDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfLocalDateTimePlus().plus(baseLocalDateTime, period, duration));
  }

}
