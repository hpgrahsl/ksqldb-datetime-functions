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

import com.github.hpgrahsl.ksqldb.functions.schemas.DateTimeSchemas;
import com.github.hpgrahsl.ksqldb.functions.structs.StructsConverter;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_localtime",
    description = "Factory functions for LocalTime struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalTime {

  @Udf(description = "Create a LocalTime struct based on the current time (system clock in the default time-zone)",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalTime() {
    return StructsConverter.toLocalTimeStruct(LocalTime.now());
  }

  @Udf(description = "Create a LocalTime struct from its components (hour, minute, second, nano=0)",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalTime(
      @UdfParameter(
          value = "hour",
          description = "the hour part of the LocalTime")
      final Integer hour,
      @UdfParameter(
          value = "month",
          description = "the minute part of the LocalTime")
      final Integer minute,
      @UdfParameter(
          value = "second",
          description = "the second part of the LocalTime")
      final Integer second) {
    if (hour == null || minute == null || second == null)
      return null;
    return StructsConverter.toLocalTimeStruct(LocalTime.of(hour,minute,second,0));
  }

  @Udf(description = "Create a LocalTime struct from its components (hour, minute, second, nano)",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalTime(
      @UdfParameter(
          value = "hour",
          description = "the hour part of the LocalTime")
      final Integer hour,
      @UdfParameter(
          value = "month",
          description = "the minute part of the LocalTime")
      final Integer minute,
      @UdfParameter(
          value = "second",
          description = "the second part of the LocalTime")
      final Integer second,
      @UdfParameter(
          value = "nano",
          description = "the nano part of the LocalTime")
      final Integer nano) {
    if (hour == null || minute == null || second == null || nano == null)
      return null;
    return StructsConverter.toLocalTimeStruct(LocalTime.of(hour,minute,second,nano));
  }

  @Udf(description = "Create a LocalTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_TIME",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalTime")
      final String text) {
    return text != null ? StructsConverter.toLocalTimeStruct(LocalTime.parse(text)) : null;
  }

  @Udf(description = "Create a LocalTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalTime")
      final String text,
      @UdfParameter(
          value = "format",
          description = "the specified java.time.format.DateTimeFormatter format string")
      final String format) {
    if (text == null || format == null)
      return null;
    return StructsConverter.toLocalTimeStruct(LocalTime.parse(text, DateTimeFormatter.ofPattern(format,
        Locale.ENGLISH)));
  }

}
