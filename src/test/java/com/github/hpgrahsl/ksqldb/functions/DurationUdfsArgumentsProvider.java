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

public class DurationUdfsArgumentsProvider {

  private static final String SAMPLES_DT_DURATION_SECONDS = "duration/udf_dt_duration_seconds_samples.json";
  private static final String SAMPLES_DT_DURATION_SECONDS_NANOS = "duration/udf_dt_duration_seconds_nanos_samples.json";
  private static final String SAMPLES_DT_DURATION_STRING = "duration/udf_dt_duration_text_samples.json";
  private static final String SAMPLES_DT_DURATION_BETWEEN_STRUCT_STRUCT = "duration/udf_dt_duration_between_localtimefrom_localtimeto_samples.json";
  private static final String SAMPLES_DT_DURATION_DIVIDE_STRUCT_LONG = "duration/udf_dt_duration_divide_baseduration_divisor_samples.json";
  private static final String SAMPLES_DT_DURATION_DIVIDE_STRUCT_STRUCT = "duration/udf_dt_duration_divide_baseduration_divisorduration_samples.json";
  private static final String SAMPLES_DT_DURATION_MINUS_STRUCT_STRUCT = "duration/udf_dt_duration_minus_baseduration_subtractduration_samples.json";
  private static final String SAMPLES_DT_DURATION_MULTIPLY_STRUCT_LONG = "duration/udf_dt_duration_multiply_baseduration_scalar_samples.json";
  private static final String SAMPLES_DT_DURATION_PLUS_STRUCT_STRUCT = "duration/udf_dt_duration_plus_baseduration_addduration_samples.json";
  private static final String SAMPLES_DT_DURATION_STRINGIFY = "duration/udf_dt_duration_stringify_samples.json";

  public static Stream<Arguments> createDurationSamplesSeconds() {

    return parseJsonSampleFile(SAMPLES_DT_DURATION_SECONDS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo,"param_seconds"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createDurationSamplesSecondsNanos() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_SECONDS_NANOS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo,"param_seconds"),
            JsonExtractor.toLong(jo,"param_nanos"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createDurationSamplesString() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_STRING)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> betweenSamplesStructStruct() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_BETWEEN_STRUCT_STRUCT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLocalTimeStruct(jo,"param_localTimeFrom"),
            JsonExtractor.toLocalTimeStruct(jo,"param_localTimeTo"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> divideSamplesStructLong() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_DIVIDE_STRUCT_LONG)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_baseDuration"),
            JsonExtractor.toLong(jo,"param_divisor"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> divideSamplesStructStruct() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_DIVIDE_STRUCT_STRUCT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_baseDuration"),
            JsonExtractor.toDurationStruct(jo,"param_divisorDuration"),
            JsonExtractor.toLong(jo,"result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesStructStruct() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_MINUS_STRUCT_STRUCT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_baseDuration"),
            JsonExtractor.toDurationStruct(jo,"param_subtractDuration"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> multiplySamplesStructLong() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_MULTIPLY_STRUCT_LONG)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_baseDuration"),
            JsonExtractor.toLong(jo,"param_scalar"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> plusSamplesStructStruct() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_PLUS_STRUCT_STRUCT)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_baseDuration"),
            JsonExtractor.toDurationStruct(jo,"param_addDuration"),
            JsonExtractor.toDurationStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> stringifySamples() {
    return parseJsonSampleFile(SAMPLES_DT_DURATION_STRINGIFY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toDurationStruct(jo,"param_duration"),
            jo.getString("result",null)
            )
        );
  }


}
