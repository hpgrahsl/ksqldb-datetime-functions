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

public class UdfOffsetDateTimeTests {

  @DisplayName("applying UDF dt_offsetdatetime with localdatetime and zoneoffset")
  @ParameterizedTest(name = "dt_offsetdatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#createOffsetDateTimeSamplesLocalDateTimeZoneOffset")
  void applyUdfOffsetDateTimeLocalDateTimeZoneOffset(Struct localDateTime,Struct zoneOffset,Struct result) {
    assertEquals(result, new UdfOffsetDateTime().createOffsetDateTime(localDateTime,zoneOffset));
  }

  @DisplayName("applying UDF dt_offsetdatetime with localdate,localtime and zoneoffset")
  @ParameterizedTest(name = "dt_offsetdatetime({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#createOffsetDateTimeSamplesLocalDateLocalTimeZoneOffset")
  void applyUdfOffsetDateTimeLocalDateLocalTimeZoneOffset(Struct localDate,Struct localTime,Struct zoneOffset,Struct result) {
    assertEquals(result, new UdfOffsetDateTime().createOffsetDateTime(localDate,localTime,zoneOffset));
  }

  @DisplayName("applying UDF dt_offsetdatetime with text")
  @ParameterizedTest(name = "dt_offsetdatetime({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#createOffsetDateTimeSamplesText")
  void applyUdfOffsetDateTimeText(String text,Struct result) {
    assertEquals(result, new UdfOffsetDateTime().createOffsetDateTime(text));
  }

  @DisplayName("applying UDF dt_offsetdatetime with text and pattern")
  @ParameterizedTest(name = "dt_offsetdatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#createOffsetDateTimeSamplesTextPattern")
  void applyUdfOffsetDateTimeTextPattern(String text,String pattern,Struct result) {
    assertEquals(result, new UdfOffsetDateTime().createOffsetDateTime(text,pattern));
  }

  @DisplayName("applying UDF dt_offsetdatetime_format with offsetDateTime")
  @ParameterizedTest(name = "dt_offsetdatetime_format({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#formatSamples")
  void applyUdfOffsetDateTimeFormat(Struct offsetDateTime,String result) {
    assertEquals(result, new UdfOffsetDateTimeFormat().format(offsetDateTime));
  }

  @DisplayName("applying UDF dt_offsetdatetime_format with offsetDateTime and pattern")
  @ParameterizedTest(name = "dt_offsetdatetime_format({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#formatPatternSamples")
  void applyUdfOffsetDateTimeFormatPattern(Struct offsetDateTime,String pattern,String result) {
    assertEquals(result, new UdfOffsetDateTimeFormat().format(offsetDateTime,pattern));
  }

  @DisplayName("applying UDF dt_offsetdatetime_chronology with baseOffsetDateTime, offsetDateTime, chronologyMode")
  @ParameterizedTest(name = "dt_offsetdatetime_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#chronologySamples")
  void applyUdfOffsetDateTimeChronology(Struct baseOffsetDateTime,Struct offsetDateTime,String chronologyMode,Boolean result) {
    assertEquals(result, new UdfOffsetDateTimeChronology().check(baseOffsetDateTime, offsetDateTime, chronologyMode));
  }

  @DisplayName("applying UDF dt_offsetdatetime_minus with baseOffsetDateTime, period, duration")
  @ParameterizedTest(name = "dt_offsetdatetime_minus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#minusSamplesBaseOffsetDateTimePeriodDuration")
  void applyUdfOffsetDateTimeMinusBaseLocalDateTimePeriodDuration(Struct baseOffsetDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfOffsetDateTimeMinus().minus(baseOffsetDateTime, period, duration));
  }

  @DisplayName("applying UDF dt_offsetdatetime_plus with baseOffsetDateTime, period, duration")
  @ParameterizedTest(name = "dt_offsetdatetime_plus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.OffsetDateTimeUdfsArgumentsProvider#plusSamplesBaseOffsetDateTimePeriodDuration")
  void applyUdfOffsetDateTimePlusBaseLocalDateTimePeriodDuration(Struct baseOffsetDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfOffsetDateTimePlus().plus(baseOffsetDateTime, period, duration));
  }

}
