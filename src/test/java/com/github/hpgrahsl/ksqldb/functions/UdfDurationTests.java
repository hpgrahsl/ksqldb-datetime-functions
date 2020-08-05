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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UdfDurationTests {

  @DisplayName("applying UDF dt_duration with seconds")
  @ParameterizedTest(name = "dt_duration({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#createDurationSamplesSeconds")
  void applyUdfDurationSeconds(Long seconds, Struct result) {
    assertEquals(result, new UdfDuration().createDuration(seconds));
  }

  @DisplayName("applying UDF dt_duration with seconds and nanos")
  @ParameterizedTest(name = "dt_duration({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#createDurationSamplesSecondsNanos")
  void applyUdfDurationSecondsNanos(Long seconds, Long nanos, Struct result) {
    assertEquals(result, new UdfDuration().createDuration(seconds,nanos));
  }

  @DisplayName("applying UDF dt_duration with strings")
  @ParameterizedTest(name = "dt_duration({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#createDurationSamplesString")
  void applyUdfDurationStrings(String text, Struct result) {
    assertEquals(result, new UdfDuration().createDuration(text));
  }

  @DisplayName("applying UDF dt_duration_between with struct and struct")
  @ParameterizedTest(name = "dt_duration_between({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#betweenSamplesStructStruct")
  void applyUdfDurationBetweenStructStruct(Struct localTimeFrom, Struct localTimeTo, Struct result) {
    assertEquals(result, new UdfDurationBetween().between(localTimeFrom,localTimeTo));
  }

  @DisplayName("applying UDF dt_duration_divide with struct and long")
  @ParameterizedTest(name = "dt_duration_divide({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#divideSamplesStructLong")
  void applyUdfDurationDivideStructLong(Struct baseDuration, Long divisor, Struct result) {
    if (divisor == null)
      assertNull(result, "error: UDF result should have been null");
    else if (divisor != 0)
      assertEquals(result, new UdfDurationDivide().divide(baseDuration,divisor));
    else
      assertThrows(ArithmeticException.class,
          () -> new UdfDurationDivide().divide(baseDuration,divisor),
          "error: UDF should have thrown an ArithmeticException due to div by zero");
  }

  @DisplayName("applying UDF dt_duration_divide with struct and struct")
  @ParameterizedTest(name = "dt_duration_divide({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#divideSamplesStructStruct")
  void applyUdfDurationDivideStructStruct(Struct baseDuration, Struct divisorDuration, Long result) {
    if(divisorDuration == null)
      assertNull(result, "error: UDF result should have been null");
    else if(divisorDuration.getInt64("SECONDS_FIELD") == 0
        && divisorDuration.getInt32("NANOS_FIELD") == 0)
      assertThrows(ArithmeticException.class,
          () -> new UdfDurationDivide().divide(baseDuration,divisorDuration),
          "error: UDF should have thrown an ArithmeticException due to div by zero");
    else
      assertEquals(result, new UdfDurationDivide().divide(baseDuration,divisorDuration));
  }

  @DisplayName("applying UDF dt_duration_minus with struct and struct")
  @ParameterizedTest(name = "dt_duration_minus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#minusSamplesStructStruct")
  void applyUdfDurationMinusStructStruct(Struct baseDuration, Struct subtractDuration, Struct result) {
    assertEquals(result, new UdfDurationMinus().minus(baseDuration,subtractDuration));
  }

  @DisplayName("applying UDF dt_duration_multiply with struct and long")
  @ParameterizedTest(name = "dt_duration_multiply({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#multiplySamplesStructLong")
  void applyUdfDurationMultiply(Struct baseDuration, Long scalar, Struct result) {
    assertEquals(result, new UdfDurationMultiply().multiply(baseDuration,scalar));
  }

  @DisplayName("applying UDF dt_duration_plus with struct and struct")
  @ParameterizedTest(name = "dt_duration_plus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#plusSamplesStructStruct")
  void applyUdfDurationPlusStructStruct(Struct baseDuration, Struct addDuration, Struct result) {
    assertEquals(result, new UdfDurationPlus().plus(baseDuration,addDuration));
  }

  @DisplayName("applying UDF dt_duration_stringify with struct")
  @ParameterizedTest(name = "dt_duration_stringify({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.DurationUdfsArgumentsProvider#stringifySamples")
  void applyUdfDurationStringify(Struct duration, String result) {
    assertEquals(result, new UdfDurationStringify().stringify(duration));
  }

}
