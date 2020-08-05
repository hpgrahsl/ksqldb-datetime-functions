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

public class LocalTimeUdfsArgumentsProvider {

  private static final String SAMPLES_DT_LOCALTIME_CHRONOLOGY = "localtime/udf_dt_localtime_chronology_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_HOURS_MINUTES_SECONDS = "localtime/udf_dt_localtime_hours_minutes_seconds_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_HOURS_MINUTES_SECONDS_NANOS = "localtime/udf_dt_localtime_hours_minutes_seconds_nanos_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_FORMAT = "localtime/udf_dt_localtime_format_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_FORMAT_PATTERN = "localtime/udf_dt_localtime_format_pattern_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_MINUS_BASELOCALTIME_DURATION = "localtime/udf_dt_localtime_minus_localtime_duration_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_PLUS_BASELOCALTIME_DURATION = "localtime/udf_dt_localtime_plus_localtime_duration_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_TEXT = "localtime/udf_dt_localtime_text_samples.json";
  private static final String SAMPLES_DT_LOCALTIME_TEXT_PATTERN = "localtime/udf_dt_localtime_text_pattern_samples.json";

  public static Stream<Arguments> createLocalTimeSamplesHoursMinutesSeconds() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_HOURS_MINUTES_SECONDS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInteger(jo,"param_hour"),
            JsonExtractor.toInteger(jo,"param_minute"),
            JsonExtractor.toInteger(jo,"param_second"),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalTimeSamplesHoursMinutesSecondsNanos() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_HOURS_MINUTES_SECONDS_NANOS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInteger(jo,"param_hour"),
            JsonExtractor.toInteger(jo,"param_minute"),
            JsonExtractor.toInteger(jo,"param_second"),
            JsonExtractor.toInteger(jo,"param_nano"),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalTimeSamplesText() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createLocalTimeSamplesTextPattern() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_TEXT_PATTERN)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            jo.getString("param_pattern",null),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> formatSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_FORMAT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_localTime"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> formatPatternSamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_FORMAT_PATTERN)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_localTime"),
            jo.getString("param_pattern",null),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> chronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_baseLocalTime"),
            JsonExtractor.toLocalTimeStruct(jo,"param_localTime"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesBaseLocalTimeDuration() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_MINUS_BASELOCALTIME_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_baseLocalTime"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesBaseLocalTimeDuration() {
    return parseJsonSampleFile(SAMPLES_DT_LOCALTIME_PLUS_BASELOCALTIME_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_baseLocalTime"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toLocalTimeStruct(jo,"result")
            )
        );
  }

}
