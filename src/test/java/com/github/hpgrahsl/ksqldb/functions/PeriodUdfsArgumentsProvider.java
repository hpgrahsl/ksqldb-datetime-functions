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

import static com.github.hpgrahsl.ksqldb.functions.util.JsonObjectFileArgumentsProvider.parseJsonSampleFile;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.hpgrahsl.ksqldb.functions.util.JsonExtractor;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class PeriodUdfsArgumentsProvider {

  private static final String SAMPLES_DT_PERIOD_YEARS_MONTHS_DAYS = "period/udf_dt_period_years_months_days_samples.json";
  private static final String SAMPLES_DT_PERIOD_TEXT = "period/udf_dt_period_text_samples.json";
  private static final String SAMPLES_DT_PERIOD_BETWEEN_LOCALDATEFROM_LOCALDATETO = "period/udf_dt_period_between_localdatefrom_localdateto_samples.json";
  private static final String SAMPLES_DT_PERIOD_MINUS_BASEPERIOD_SUBTRACTPERIOD = "period/udf_dt_period_minus_baseperiod_subtractperiod_samples.json";
  private static final String SAMPLES_DT_PERIOD_MULTIPLY_BASEPERIOD_SCALAR = "period/udf_dt_period_multiply_baseperiod_scalar_samples.json";
  private static final String SAMPLES_DT_PERIOD_NORMALIZE_PERIOD = "period/udf_dt_period_normalize_period_samples.json";
  private static final String SAMPLES_DT_PERIOD_PLUS_BASEPERIOD_ADDPERIOD = "period/udf_dt_period_plus_baseperiod_addperiod_samples.json";
  private static final String SAMPLES_DT_PERIOD_STRINGIFY = "period/udf_dt_period_stringify_samples.json";

  public static Stream<Arguments> createPeriodSamplesYearsMonthsDays() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_YEARS_MONTHS_DAYS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInteger(jo,"param_years"),
            JsonExtractor.toInteger(jo,"param_months"),
            JsonExtractor.toInteger(jo,"param_days"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createPeriodSamplesText() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> stringifySamples() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_STRINGIFY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> betweenSamplesLocalDateFromLocalDateTo() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_BETWEEN_LOCALDATEFROM_LOCALDATETO)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo, "param_localDateFrom"),
            JsonExtractor.toLocalDateStruct(jo, "param_localDateTo"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );

  }

  public static Stream<Arguments> minusSamplesBasePeriodSubtractPeriod() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_MINUS_BASEPERIOD_SUBTRACTPERIOD)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toPeriodStruct(jo,"param_basePeriod"),
            JsonExtractor.toPeriodStruct(jo,"param_subtractPeriod"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );

  }

  public static Stream<Arguments> plusSamplesBasePeriodAddPeriod() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_PLUS_BASEPERIOD_ADDPERIOD)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toPeriodStruct(jo,"param_basePeriod"),
            JsonExtractor.toPeriodStruct(jo,"param_addPeriod"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );

  }

  public static Stream<Arguments> multiplySamplesBasePeriodScalar() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_MULTIPLY_BASEPERIOD_SCALAR)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toPeriodStruct(jo,"param_basePeriod"),
            JsonExtractor.toInteger(jo, "param_scalar"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );

  }

  public static Stream<Arguments> normalizeSamplesPeriod() {

    return parseJsonSampleFile(SAMPLES_DT_PERIOD_NORMALIZE_PERIOD)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toPeriodStruct(jo,"result")
            )
        );

  }

}
