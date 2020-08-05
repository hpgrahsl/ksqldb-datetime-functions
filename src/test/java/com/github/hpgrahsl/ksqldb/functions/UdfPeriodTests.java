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

public class UdfPeriodTests {

  @DisplayName("applying UDF dt_period with years, months, days")
  @ParameterizedTest(name = "dt_period({0},{1},{2}) = {3}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#createPeriodSamplesYearsMonthsDays")
  void applyUdfPeriodYearsMonthsDays(Integer years,Integer months,Integer days,Struct result) {
    assertEquals(result, new UdfPeriod().createPeriod(years,months,days));
  }

  @DisplayName("applying UDF dt_period with text")
  @ParameterizedTest(name = "dt_period({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#createPeriodSamplesText")
  void applyUdfPeriodText(String text,Struct result) {
    assertEquals(result, new UdfPeriod().createPeriod(text));
  }

  @DisplayName("applying UDF dt_period_between with localDateFrom and localDateTo")
  @ParameterizedTest(name = "dt_period_between({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#betweenSamplesLocalDateFromLocalDateTo")
  void applyUdfPeriodBetweenLocalDateFromLocalDateTo(Struct localDateFrom,Struct localDateTo,Struct result) {
    assertEquals(result, new UdfPeriodBetween().between(localDateFrom,localDateTo));
  }

  @DisplayName("applying UDF dt_period_minus with basePeriod and subtractPeriod")
  @ParameterizedTest(name = "dt_period_minus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#minusSamplesBasePeriodSubtractPeriod")
  void applyUdfPeriodMinusBasePeriodSubtractPeriod(Struct basePeriod,Struct subtractPeriod,Struct result) {
    assertEquals(result, new UdfPeriodMinus().minus(basePeriod,subtractPeriod));
  }

  @DisplayName("applying UDF dt_period_multiply with basePeriod and scalar")
  @ParameterizedTest(name = "dt_period_multiply({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#multiplySamplesBasePeriodScalar")
  void applyUdfPeriodMultiplyBasePeriodScalar(Struct basePeriod,Integer scalar,Struct result) {
    assertEquals(result, new UdfPeriodMultiply().multiply(basePeriod,scalar));
  }

  @DisplayName("applying UDF dt_period_normalize with period")
  @ParameterizedTest(name = "dt_period_multiply({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#normalizeSamplesPeriod")
  void applyUdfPeriodNormalizePeriod(Struct period,Struct result) {
    assertEquals(result, new UdfPeriodNormalize().normalize(period));
  }

  @DisplayName("applying UDF dt_period_plus with basePeriod and addPeriod")
  @ParameterizedTest(name = "dt_period_plus({0},{1}) = {2}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#plusSamplesBasePeriodAddPeriod")
  void applyUdfPeriodPlusBasePeriodAddPeriod(Struct basePeriod,Struct addPeriod,Struct result) {
    assertEquals(result, new UdfPeriodPlus().plus(basePeriod,addPeriod));
  }

  @DisplayName("applying UDF dt_period_stringify with period")
  @ParameterizedTest(name = "dt_period_stringify({0}) = {1}")
  @MethodSource("com.github.hpgrahsl.ksqldb.functions.PeriodUdfsArgumentsProvider#stringifySamples")
  void applyUdfDurationStringify(Struct period, String result) {
    assertEquals(result, new UdfPeriodStringify().stringify(period));
  }

}
