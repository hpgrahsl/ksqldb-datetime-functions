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

public class UdfInstantTests {

  @DisplayName("applying UDF dt_instant with millis")
  @ParameterizedTest(name = "dt_instant({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#createInstantSamplesMillis")
  void applyUdfInstantMillis(Long millis, Struct result) {
    assertEquals(result, new UdfInstant().createInstant(millis));
  }

  @DisplayName("applying UDF dt_instant with seconds and nanos")
  @ParameterizedTest(name = "dt_instant({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#createInstantSamplesSecondsNanos")
  void applyUdfInstantSecondsNanos(Long seconds, Long nanos, Struct result) {
    assertEquals(result, new UdfInstant().createInstant(seconds,nanos));
  }

  @DisplayName("applying UDF dt_instant with strings")
  @ParameterizedTest(name = "dt_instant({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#createInstantSamplesString")
  void applyUdfInstantStrings(String text, Struct result) {
    assertEquals(result, new UdfInstant().createInstant(text));
  }

  @DisplayName("applying UDF dt_instant_chronology with Struct, Struct, String")
  @ParameterizedTest(name = "dt_instant_chronology({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#checkChronologySamples")
  void applyUdfInstantChronology(Struct baseInstant, Struct instant, String mode, Boolean result) {
    assertEquals(result, new UdfInstantChronology().checkChronology(baseInstant, instant, mode));
  }

  @DisplayName("applying UDF dt_instant_minus with instant Struct and duration Struct")
  @ParameterizedTest(name = "dt_instant_minus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#minusSamplesDuration")
  void applyUdfInstantMinus(Struct instant, Struct duration, Struct result) {
    assertEquals(result, new UdfInstantMinus().minus(instant, duration));
  }

  @DisplayName("applying UDF dt_instant_minus with instant Struct, seconds and nanos")
  @ParameterizedTest(name = "dt_instant_minus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#minusSamplesSecondsNanos")
  void applyUdfInstantMinus(Struct instant, Long seconds, Long nanos, Struct result) {
    assertEquals(result, new UdfInstantMinus().minus(instant, seconds, nanos));
  }

  @DisplayName("applying UDF dt_instant_plus with instant Struct and duration Struct")
  @ParameterizedTest(name = "dt_instant_plus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#plusSamplesDuration")
  void applyUdfInstantPlus(Struct instant, Struct duration, Struct result) {
    assertEquals(result, new UdfInstantPlus().plus(instant, duration));
  }

  @DisplayName("applying UDF dt_instant_plus with instant Struct, seconds and nanos")
  @ParameterizedTest(name = "dt_instant_plus({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#plusSamplesSecondsNanos")
  void applyUdfInstantPlus(Struct instant, Long seconds, Long nanos, Struct result) {
    assertEquals(result, new UdfInstantPlus().plus(instant, seconds, nanos));
  }

  @DisplayName("applying UDF dt_instant_stringify with Struct")
  @ParameterizedTest(name = "dt_instant_stringify({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.InstantUdfsArgumentsProvider#stringifySamples")
  void applyUdfInstantStringify(Struct instant, String result) {
    assertEquals(result, new UdfInstantStringify().stringify(instant));
  }

}
