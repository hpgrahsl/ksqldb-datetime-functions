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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_localdatetime",
    description = "Factory functions for LocalDateTime struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateTime {

  @Udf(description = "Create a LocalDateTime struct based on the current datetime (system clock in the default time-zone)",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime() {
    return StructsConverter.toLocalDateTimeStruct(LocalDateTime.now());
  }

  @Udf(description = "Create a LocalDateTime struct from its components (year, month, day, hour, minute, second, nano)",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime(
      @UdfParameter(
          value = "year",
          description = "the year part of the LocalDateTime")
      final Integer year,
      @UdfParameter(
          value = "month",
          description = "the month part of the LocalDateTime")
      final Integer month,
      @UdfParameter(
          value = "day",
          description = "the day part of the LocalDateTime")
      final Integer day,
      @UdfParameter(
          value = "hour",
          description = "the hour part of the LocalDateTime")
      final Integer hour,
      @UdfParameter(
          value = "month",
          description = "the minute part of the LocalDateTime")
      final Integer minute,
      @UdfParameter(
          value = "second",
          description = "the second part of the LocalDateTime")
      final Integer second,
      @UdfParameter(
          value = "nano",
          description = "the nano part of the LocalDateTime")
      final Integer nano) {
    if (year == null || month == null || day == null || hour == null || minute == null || second == null || nano == null)
      return null;
    return StructsConverter.toLocalDateTimeStruct(LocalDateTime.of(
        year,month,day,hour,minute,second,nano
    ));
  }

  @Udf(description = "Create a LocalDateTime from a UNIX timestamp given as the number of millis since the epoch (zone offset +00:00 i.e. UTC)",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime(
      @UdfParameter(
          value = "epochMillis",
          description = "the number of millis since the epoch")
      final Long epochMillis) {
    if (epochMillis == null)
      return null;
    final long epochSecond = TimeUnit.MILLISECONDS.toSeconds(epochMillis);
    final long nanos = TimeUnit.MILLISECONDS.toNanos(epochMillis % 1_000);
    return StructsConverter.toLocalDateTimeStruct(LocalDateTime.ofEpochSecond(epochSecond,(int)nanos,ZoneOffset.UTC));
  }

  @Udf(description = "Create a LocalDateTime struct based on a LocalDate struct and LocalTime struct",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime(
      @UdfParameter(
          value = "localDate",
          description = "the local date part",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate,
      @UdfParameter(
          value = "localTime",
          description = "the local time part",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTime
  ) {
    if (localDate == null || localTime == null)
      return null;
    return StructsConverter.toLocalDateTimeStruct(
        LocalDateTime.of(
          StructsConverter.fromLocalDateStruct(localDate),
          StructsConverter.fromLocalTimeStruct(localTime)
        )
    );
  }

  @Udf(description = "Create a LocalDateTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalDateTime")
      final String text) {
    return text != null ? StructsConverter.toLocalDateTimeStruct(LocalDateTime.parse(text)) : null;
  }

  @Udf(description = "Create a LocalDateTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createLocalDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalDateTime")
      final String text,
      @UdfParameter(
          value = "format",
          description = "the specified java.time.format.DateTimeFormatter format string")
      final String format) {
    if (text == null || format == null)
      return null;
    return StructsConverter.toLocalDateTimeStruct(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format,
        Locale.ENGLISH)));
  }

}
