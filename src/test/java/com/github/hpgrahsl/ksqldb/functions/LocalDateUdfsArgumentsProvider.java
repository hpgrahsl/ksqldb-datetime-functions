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

public class LocalDateUdfsArgumentsProvider {

  private static final String SAMPLES_DT_LOCALDATE_CHRONOLOGY = "localdate/udf_dt_localdate_chronology_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_YEARS_MONTHS_DAYS = "localdate/udf_dt_localdate_years_months_days_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_EPOCHDAYS = "localdate/udf_dt_localdate_epochdays_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_FORMAT = "localdate/udf_dt_localdate_format_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_FORMAT_PATTERN = "localdate/udf_dt_localdate_format_pattern_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_MINUS_BASELOCALDATE_PERIOD = "localdate/udf_dt_localdate_minus_localdate_period_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_PLUS_BASELOCALDATE_PERIOD = "localdate/udf_dt_localdate_plus_localdate_period_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_TEXT = "localdate/udf_dt_localdate_text_samples.json";
  private static final String SAMPLES_DT_LOCALDATE_TEXT_PATTERN = "localdate/udf_dt_localdate_text_pattern_samples.json";

  public static Stream<Arguments> createLocalDateSamplesYearsMonthsDays() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_YEARS_MONTHS_DAYS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInteger(jo,"param_year"),
            JsonExtractor.toInteger(jo,"param_month"),
            JsonExtractor.toInteger(jo,"param_day"),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateSamplesEpochDays() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_EPOCHDAYS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo,"param_epochdays"),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateSamplesText() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateSamplesTextPattern() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_TEXT_PATTERN)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            jo.getString("param_pattern",null),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> formatSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_FORMAT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_localDate"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> formatPatternSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_FORMAT_PATTERN)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_localDate"),
            jo.getString("param_pattern",null),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> chronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_baseLocalDate"),
            JsonExtractor.toLocalDateStruct(jo,"param_localDate"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesBaseLocalDatePeriod() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_MINUS_BASELOCALDATE_PERIOD)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_baseLocalDate"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesBaseLocalDatePeriod() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATE_PLUS_BASELOCALDATE_PERIOD)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_baseLocalDate"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toLocalDateStruct(jo,"result")
            )
        );
  }

}
