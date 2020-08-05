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

public class InstantUdfsArgumentsProvider {

  private static final String SAMPLES_DT_INSTANT_MILLIS = "instant/udf_dt_instant_millis_samples.json";
  private static final String SAMPLES_DT_INSTANT_SECONDS_NANOS = "instant/udf_dt_instant_seconds_nanos_samples.json";
  private static final String SAMPLES_DT_INSTANT_STRING = "instant/udf_dt_instant_text_samples.json";
  private static final String SAMPLES_DT_INSTANT_CHRONOLOGY = "instant/udf_dt_instant_chronology_samples.json";
  private static final String SAMPLES_DT_INSTANT_MINUS_DURATION= "instant/udf_dt_instant_minus_duration_samples.json";
  private static final String SAMPLES_DT_INSTANT_PLUS_DURATION= "instant/udf_dt_instant_plus_duration_samples.json";
  private static final String SAMPLES_DT_INSTANT_MINUS_SECONDS_NANOS= "instant/udf_dt_instant_minus_seconds_nanos_samples.json";
  private static final String SAMPLES_DT_INSTANT_PLUS_SECONDS_NANOS= "instant/udf_dt_instant_plus_seconds_nanos_samples.json";
  private static final String SAMPLES_DT_INSTANT_STRINGIFY= "instant/udf_dt_instant_stringify_samples.json";

  public static Stream<Arguments> createInstantSamplesMillis() {

    return parseJsonSampleFile(SAMPLES_DT_INSTANT_MILLIS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo,"param_millis"),
            JsonExtractor.toInstantStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createInstantSamplesSecondsNanos() {
    return parseJsonSampleFile(SAMPLES_DT_INSTANT_SECONDS_NANOS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toLong(jo,"param_seconds"),
            JsonExtractor.toLong(jo,"param_nanos"),
            JsonExtractor.toInstantStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> createInstantSamplesString() {
    return parseJsonSampleFile(SAMPLES_DT_INSTANT_STRING)
        .stream()
        .map(jo -> arguments(
            jo.getString("param_text",null),
            JsonExtractor.toInstantStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> checkChronologySamples() {
    return parseJsonSampleFile(SAMPLES_DT_INSTANT_CHRONOLOGY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInstantStruct(jo,"param_baseInstant"),
            JsonExtractor.toInstantStruct(jo,"param_instant"),
            jo.getString("param_chronologyMode",null),
            jo.isNull("result") ? null : jo.getBoolean("result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesDuration() {
    return calcSamplesDuration("minus");
  }

  public static Stream<Arguments> plusSamplesDuration() {
    return calcSamplesDuration("plus");
  }

  private static Stream<Arguments> calcSamplesDuration(String mode) {
    return parseJsonSampleFile(mode.equalsIgnoreCase("minus") ?
        SAMPLES_DT_INSTANT_MINUS_DURATION : SAMPLES_DT_INSTANT_PLUS_DURATION)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInstantStruct(jo,"param_instant"),
            JsonExtractor.toDurationStruct(jo, "param_duration"),
            JsonExtractor.toInstantStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> minusSamplesSecondsNanos() {
    return calcSamplesSecondsNanos("minus");
  }

  public static Stream<Arguments> plusSamplesSecondsNanos() {
    return calcSamplesSecondsNanos("plus");
  }

  public static Stream<Arguments> calcSamplesSecondsNanos(String mode) {
    return parseJsonSampleFile(mode.equalsIgnoreCase("minus") ?
        SAMPLES_DT_INSTANT_MINUS_SECONDS_NANOS : SAMPLES_DT_INSTANT_PLUS_SECONDS_NANOS)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInstantStruct(jo,"param_instant"),
            JsonExtractor.toLong(jo,"param_seconds"),
            JsonExtractor.toLong(jo,"param_nanos"),
            JsonExtractor.toInstantStruct(jo,"result")
            )
        );
  }

  public static Stream<Arguments> stringifySamples() {
    return parseJsonSampleFile(SAMPLES_DT_INSTANT_STRINGIFY)
        .stream()
        .map(jo -> arguments(
            JsonExtractor.toInstantStruct(jo,"param_instant"),
            jo.getString("result",null)
            )
        );
  }

}
