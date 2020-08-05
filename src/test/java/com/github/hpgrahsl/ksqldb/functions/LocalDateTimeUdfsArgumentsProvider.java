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

public class LocalDateTimeUdfsArgumentsProvider {

  private static final String SAMPLES_DT_LOCALDATETIME_ALL_PARTS = "localdatetime/udf_dt_localdatetime_all_parts_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_CHRONOLOGY = "localdatetime/udf_dt_localdatetime_chronology_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_EPOCHMILLIS = "localdatetime/udf_dt_localdatetime_epochmillis_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_FORMAT = "localdatetime/udf_dt_localdatetime_format_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_FORMAT_PATTERN = "localdatetime/udf_dt_localdatetime_format_pattern_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_LOCALDATE_LOCALTIME = "localdatetime/udf_dt_localdatetime_localdate_localtime_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_MINUS_BASELOCALDATETIME_PERIOD_DURATION = "localdatetime/udf_dt_localdatetime_minus_localdatetime_period_duration_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_PLUS_BASELOCALDATETIME_PERIOD_DURATION = "localdatetime/udf_dt_localdatetime_plus_localdatetime_period_duration_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_TEXT = "localdatetime/udf_dt_localdatetime_text_samples.json";
  private static final String SAMPLES_DT_LOCALDATETIME_TEXT_PATTERN = "localdatetime/udf_dt_localdatetime_text_pattern_samples.json";


  public static Stream<Arguments> createLocalDateTimeSamplesLocalDateLocalTime() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_LOCALDATE_LOCALTIME)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_localDate"),
            JsonExtractor.toLocalTimeStruct(jo,"param_localTime"),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateTimeSamplesAllParts() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_ALL_PARTS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInteger(jo, "param_year"),
            JsonExtractor.toInteger(jo, "param_month"),
            JsonExtractor.toInteger(jo, "param_day"),
            JsonExtractor.toInteger(jo, "param_hour"),
            JsonExtractor.toInteger(jo, "param_minute"),
            JsonExtractor.toInteger(jo, "param_second"),
            JsonExtractor.toInteger(jo, "param_nano"),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateTimeSamplesEpochMillis() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_EPOCHMILLIS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo, "param_epochmillis"),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateTimeSamplesText() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalDateTimeSamplesTextPattern() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_TEXT_PATTERN)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            jo.getString("param_pattern",null),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> formatSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_FORMAT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> formatPatternSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_FORMAT_PATTERN)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            jo.getString("param_pattern",null),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> chronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_baseLocalDateTime"),
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesBaseLocalDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_MINUS_BASELOCALDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_baseLocalDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesBaseLocalDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALDATETIME_PLUS_BASELOCALDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_baseLocalDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toLocalDateTimeStruct(jo,"result")
            )
        );
  }

}
