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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UdfZonedDateTimeTests {

  @DisplayName("applying UDF dt_zoneddatetime with localdatetime and zoneid")
  @ParameterizedTest(name = "dt_zoneddatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#createZonedDateTimeSamplesLocalDateTimeZoneId")
  void applyUdfZonedDateTimeLocalDateTimeZoneId(Struct localDateTime,Struct zoneId,Struct result) {
    assertEquals(result, new UdfZonedDateTime().createZonedDateTime(localDateTime,zoneId));
  }

  @DisplayName("applying UDF dt_zoneddatetime with localdate,localtime and zoneid")
  @ParameterizedTest(name = "dt_zoneddatetime({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#createZonedDateTimeSamplesLocalDateTimeZoneIdZoneOffset")
  void applyUdfZonedDateTimeLocalDateLocalTimeZoneId(Struct localDateTime,Struct zoneId,Struct zoneOffset,Struct result) {
    assertEquals(result, new UdfZonedDateTime().createZonedDateTime(localDateTime,zoneId,zoneOffset));
  }

  @DisplayName("applying UDF dt_zoneddatetime with text")
  @ParameterizedTest(name = "dt_zoneddatetime({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#createZonedDateTimeSamplesText")
  void applyUdfZonedDateTimeText(String text,Struct result) {
    assertEquals(result, new UdfZonedDateTime().createZonedDateTime(text));
  }

  @DisplayName("applying UDF dt_zoneddatetime with text and pattern")
  @ParameterizedTest(name = "dt_zoneddatetime({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#createZonedDateTimeSamplesTextPattern")
  void applyUdfZonedDateTimeTextPattern(String text,String pattern,Struct result) {
    assertEquals(result, new UdfZonedDateTime().createZonedDateTime(text,pattern));
  }

  @DisplayName("applying UDF dt_zoneddatetime_format with zonedDateTime")
  @ParameterizedTest(name = "dt_zoneddatetime_format({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#formatSamples")
  void applyUdfZonedDateTimeFormat(Struct zonedDateTime,String result) {
    assertEquals(result, new UdfZonedDateTimeFormat().format(zonedDateTime));
  }

  @DisplayName("applying UDF dt_zoneddatetime_format with zonedDateTime and pattern")
  @ParameterizedTest(name = "dt_zoneddatetime_format({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#formatPatternSamples")
  void applyUdfZonedDateTimeFormatPattern(Struct zonedDateTime,String pattern,String result) {
    assertEquals(result, new UdfZonedDateTimeFormat().format(zonedDateTime,pattern));
  }

  @DisplayName("applying UDF dt_zoneddatetime_chronology with baseZonedDateTime, zonedDateTime, chronologyMode")
  @ParameterizedTest(name = "dt_zoneddatetime_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#chronologySamples")
  void applyUdfZonedDateTimeChronology(Struct baseZonedDateTime,Struct zonedDateTime,String chronologyMode,Boolean result) {
    assertEquals(result, new UdfZonedDateTimeChronology().check(baseZonedDateTime, zonedDateTime, chronologyMode));
  }

  @DisplayName("applying UDF dt_zoneddatetime_minus with baseZonedDateTime, period, duration")
  @ParameterizedTest(name = "dt_zoneddatetime_minus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#minusSamplesBaseZonedDateTimePeriodDuration")
  void applyUdfZonedDateTimeMinusBaseLocalDateTimePeriodDuration(Struct baseZonedDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfZonedDateTimeMinus().minus(baseZonedDateTime, period, duration));
  }

  @DisplayName("applying UDF dt_zoneddatetime_plus with baseZonedDateTime, period, duration")
  @ParameterizedTest(name = "dt_zoneddatetime_plus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.ZonedDateTimeUdfsArgumentsProvider#plusSamplesBaseZonedDateTimePeriodDuration")
  void applyUdfZonedDateTimePlusBaseLocalDateTimePeriodDuration(Struct baseZonedDateTime,Struct period,Struct duration,Struct result) {
    assertEquals(result, new UdfZonedDateTimePlus().plus(baseZonedDateTime, period, duration));
  }

}
