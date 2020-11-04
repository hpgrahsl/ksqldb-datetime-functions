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

public class OffsetDateTimeUdfsArgumentsProvider {

  private static final String SAMPLES_DT_OFFSETDATETIME_LOCALDATETIME_ZONEOFFSET = "offsetdatetime/udf_dt_offsetdatetime_localdatetime_zoneoffset_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_LOCALDATE_LOCALTIME_ZONEOFFSET = "offsetdatetime/udf_dt_offsetdatetime_localdate_localtime_zoneoffset_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_TEXT = "offsetdatetime/udf_dt_offsetdatetime_text_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_TEXT_PATTERN = "offsetdatetime/udf_dt_offsetdatetime_text_pattern_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_FORMAT = "offsetdatetime/udf_dt_offsetdatetime_format_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_FORMAT_PATTERN = "offsetdatetime/udf_dt_offsetdatetime_format_pattern_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_CHRONOLOGY = "offsetdatetime/udf_dt_offsetdatetime_chronology_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_MINUS_BASEOFFSETDATETIME_PERIOD_DURATION = "offsetdatetime/udf_dt_offsetdatetime_minus_offsetdatetime_period_duration_samples.json";
  private static final String SAMPLES_DT_OFFSETDATETIME_PLUS_BASEOFFSETDATETIME_PERIOD_DURATION = "offsetdatetime/udf_dt_offsetdatetime_plus_offsetdatetime_period_duration_samples.json";

  public static Stream<Arguments> createOffsetDateTimeSamplesLocalDateTimeZoneOffset() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_LOCALDATETIME_ZONEOFFSET)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            JsonExtractor.toZoneOffsetStruct(jo, "param_zoneOffset"),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createOffsetDateTimeSamplesLocalDateLocalTimeZoneOffset() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_LOCALDATE_LOCALTIME_ZONEOFFSET)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateStruct(jo,"param_localDate"),
            JsonExtractor.toLocalTimeStruct(jo,"param_localTime"),
            JsonExtractor.toZoneOffsetStruct(jo, "param_zoneOffset"),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createOffsetDateTimeSamplesText() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createOffsetDateTimeSamplesTextPattern() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_TEXT_PATTERN)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            jo.getString("param_pattern",null),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> formatSamples() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_FORMAT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_offsetDateTime"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> formatPatternSamples() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_FORMAT_PATTERN)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_offsetDateTime"),
            jo.getString("param_pattern",null),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> chronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_baseOffsetDateTime"),
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_offsetDateTime"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesBaseOffsetDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_MINUS_BASEOFFSETDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_baseOffsetDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesBaseOffsetDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_OFFSETDATETIME_PLUS_BASEOFFSETDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toOffsetDateTimeStruct(jo,"param_baseOffsetDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toOffsetDateTimeStruct(jo,"result")
            )
        );
  }

}
