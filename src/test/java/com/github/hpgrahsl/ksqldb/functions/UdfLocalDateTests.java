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

public class UdfLocalDateTests {

  @DisplayName("applying UDF dt_localdate with year,month,day")
  @ParameterizedTest(name = "dt_localdate({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#createLocalDateSamplesYearsMonthsDays")
  void applyUdfLocalDateYearMonthDay(Integer year,Integer month,Integer day,Struct result) {
    assertEquals(result, new UdfLocalDate().createLocalDate(year,month,day));
  }

  @DisplayName("applying UDF dt_localdate with epoch days")
  @ParameterizedTest(name = "dt_localdate({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#createLocalDateSamplesEpochDays")
  void applyUdfLocalDateEpochDays(Long epochDays,Struct result) {
    assertEquals(result, new UdfLocalDate().createLocalDate(epochDays));
  }

  @DisplayName("applying UDF dt_localdate with text")
  @ParameterizedTest(name = "dt_localdate({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#createLocalDateSamplesText")
  void applyUdfLocalDateText(String text,Struct result) {
    assertEquals(result, new UdfLocalDate().createLocalDate(text));
  }

  @DisplayName("applying UDF dt_localdate with text and pattern")
  @ParameterizedTest(name = "dt_localdate({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#createLocalDateSamplesTextPattern")
  void applyUdfLocalDateTextPattern(String text,String pattern,Struct result) {
    assertEquals(result, new UdfLocalDate().createLocalDate(text,pattern));
  }

  @DisplayName("applying UDF dt_localdate_format with localDate")
  @ParameterizedTest(name = "dt_localdate_format({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#formatSamples")
  void applyUdfLocalDateFormat(Struct localDate,String result) {
    assertEquals(result, new UdfLocalDateFormat().format(localDate));
  }

  @DisplayName("applying UDF dt_localdate_format with localDate and pattern")
  @ParameterizedTest(name = "dt_localdate_format({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#formatPatternSamples")
  void applyUdfLocalDateFormatPattern(Struct localDate,String pattern,String result) {
    assertEquals(result, new UdfLocalDateFormat().format(localDate,pattern));
  }

  @DisplayName("applying UDF dt_localdate_chronology with baseLocalDate,localDate,chronologyMode")
  @ParameterizedTest(name = "dt_localdate_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#chronologySamples")
  void applyUdfLocalDateChronology(Struct baseLocalDate,Struct localDate,String chronologyMode, Boolean result) {
    assertEquals(result, new UdfLocalDateChronology().check(baseLocalDate,localDate,chronologyMode));
  }

  @DisplayName("applying UDF dt_localdate_minus with baseLocalDate and duration")
  @ParameterizedTest(name = "dt_localdate_minus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#minusSamplesBaseLocalDatePeriod")
  void applyUdfLocalDateMinusBaseLocalDateDuration(Struct baseLocalDate,Struct period,Struct result) {
    assertEquals(result, new UdfLocalDateMinus().minus(baseLocalDate,period));
  }

  @DisplayName("applying UDF dt_localdate_plus with baseLocalDate and duration")
  @ParameterizedTest(name = "dt_localdate_plus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.LocalDateUdfsArgumentsProvider#plusSamplesBaseLocalDatePeriod")
  void applyUdfLocalDatePlusBaseLocalDateDuration(Struct baseLocalDate,Struct period,Struct result) {
    assertEquals(result, new UdfLocalDatePlus().plus(baseLocalDate,period));
  }

}
