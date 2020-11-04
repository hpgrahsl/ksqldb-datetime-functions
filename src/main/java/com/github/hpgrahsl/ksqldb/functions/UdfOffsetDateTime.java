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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_offsetdatetime",
    description = "Factory functions for OffsetDateTime struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfOffsetDateTime {

  @Udf(description = "Create an OffsetDateTime struct based on the system clock's current date-time and default time-zone",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime() {
    return StructsConverter.toOffsetDateTimeStruct(OffsetDateTime.now());
  }

  @Udf(description = "Create an OffsetDateTime struct based on the system clock's date-time in the specified time-zone",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime(
      @UdfParameter(
          value = "zoneId",
          description = "the ZoneId struct for the OffsetDateTime",
          schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
      final Struct zoneId) {
    return zoneId != null ? StructsConverter.toOffsetDateTimeStruct(OffsetDateTime.now(StructsConverter.fromZoneIdStruct(zoneId))) : null;
  }

  @Udf(description = "Create an OffsetDateTime struct based on given LocalDateTime and ZoneOffset structs",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime(
      @UdfParameter(
          value = "localDateTime",
          description = "the LocalDateTime struct for the OffsetDateTime",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct localDateTime,
      @UdfParameter(
          value = "zoneOffset",
          description = "the ZoneOffset struct for the OffsetDateTime",
          schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
      final Struct zoneOffset) {
    if (localDateTime == null || zoneOffset == null)
      return null;
    return StructsConverter.toOffsetDateTimeStruct(OffsetDateTime.of(
        StructsConverter.fromLocalDateTimeStruct(localDateTime),
        StructsConverter.fromZoneOffsetStruct(zoneOffset)
    ));
  }

  @Udf(description = "Create an OffsetDateTime struct based on given LocalDate, LocalTime and ZoneOffset structs",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime(
      @UdfParameter(
          value = "localDate",
          description = "the LocalDate struct for the OffsetDateTime",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate,
      @UdfParameter(
          value = "localTime",
          description = "the LocalTime struct for the OffsetDateTime",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTime,
      @UdfParameter(
          value = "zoneOffset",
          description = "the ZoneOffset struct for the OffsetDateTime",
          schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
      final Struct zoneOffset) {
    if (localDate == null || localTime == null || zoneOffset == null)
      return null;
    return StructsConverter.toOffsetDateTimeStruct(
        OffsetDateTime.of(
            StructsConverter.fromLocalDateStruct(localDate),
            StructsConverter.fromLocalTimeStruct(localTime),
            StructsConverter.fromZoneOffsetStruct(zoneOffset))
    );
  }

  @Udf(description = "Create an OffsetDateTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the OffsetDateTime")
      final String text) {
    return text != null ? StructsConverter.toOffsetDateTimeStruct(OffsetDateTime.parse(text)) : null;
  }

  @Udf(description = "Create an OffsetDateTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createOffsetDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the OffsetDateTime")
      final String text,
      @UdfParameter(
          value = "format",
          description = "the specified java.time.format.DateTimeFormatter format string")
      final String format) {
    if (text == null || format == null)
      return null;
    return StructsConverter.toOffsetDateTimeStruct(OffsetDateTime.parse(text, DateTimeFormatter.ofPattern(format,
        Locale.ENGLISH)));
  }

}
