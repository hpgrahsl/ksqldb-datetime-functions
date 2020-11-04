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

public class ZonedDateTimeUdfsArgumentsProvider {

  private static final String SAMPLES_DT_ZONEDDATETIME_LOCALDATETIME_ZONEID = "zoneddatetime/udf_dt_zoneddatetime_localdatetime_zoneid_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_LOCALDATETIME_ZONEID_ZONEOFFSET = "zoneddatetime/udf_dt_zoneddatetime_localdatetime_zoneid_zoneoffset_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_TEXT = "zoneddatetime/udf_dt_zoneddatetime_text_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_TEXT_PATTERN = "zoneddatetime/udf_dt_zoneddatetime_text_pattern_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_FORMAT = "zoneddatetime/udf_dt_zoneddatetime_format_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_FORMAT_PATTERN = "zoneddatetime/udf_dt_zoneddatetime_format_pattern_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_CHRONOLOGY = "zoneddatetime/udf_dt_zoneddatetime_chronology_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_MINUS_BASEZONEDDATETIME_PERIOD_DURATION = "zoneddatetime/udf_dt_zoneddatetime_minus_zoneddatetime_period_duration_samples.json";
  private static final String SAMPLES_DT_ZONEDDATETIME_PLUS_BASEZONEDDATETIME_PERIOD_DURATION = "zoneddatetime/udf_dt_zoneddatetime_plus_zoneddatetime_period_duration_samples.json";

  public static Stream<Arguments> createZonedDateTimeSamplesLocalDateTimeZoneId() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_LOCALDATETIME_ZONEID)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            JsonExtractor.toZoneIdStruct(jo, "param_zoneId"),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createZonedDateTimeSamplesLocalDateTimeZoneIdZoneOffset() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_LOCALDATETIME_ZONEID_ZONEOFFSET)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalDateTimeStruct(jo,"param_localDateTime"),
            JsonExtractor.toZoneIdStruct(jo, "param_zoneId"),
            JsonExtractor.toZoneOffsetStruct(jo, "param_zoneOffset"),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createZonedDateTimeSamplesText() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_TEXT)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createZonedDateTimeSamplesTextPattern() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_TEXT_PATTERN)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            jo.getString("param_pattern",null),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> formatSamples() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_FORMAT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toZonedDateTimeStruct(jo,"param_zonedDateTime"),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> formatPatternSamples() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_FORMAT_PATTERN)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toZonedDateTimeStruct(jo,"param_zonedDateTime"),
            jo.getString("param_pattern",null),
            jo.getString("result",null)
            )
        );
  }

  public static Stream<Arguments> chronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toZonedDateTimeStruct(jo,"param_baseZonedDateTime"),
            JsonExtractor.toZonedDateTimeStruct(jo,"param_zonedDateTime"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesBaseZonedDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_MINUS_BASEZONEDDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toZonedDateTimeStruct(jo,"param_baseZonedDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesBaseZonedDateTimePeriodDuration() {
    return parseJsonSampleFile(SAMPLES_DT_ZONEDDATETIME_PLUS_BASEZONEDDATETIME_PERIOD_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toZonedDateTimeStruct(jo,"param_baseZonedDateTime"),
            JsonExtractor.toPeriodStruct(jo,"param_period"),
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            JsonExtractor.toZonedDateTimeStruct(jo,"result")
            )
        );
  }

}
