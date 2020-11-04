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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_zoneddatetime",
    description = "Factory functions for ZonedDateTime struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfZonedDateTime {

  @Udf(description = "Create a ZonedDateTime struct based on the system clock's current date-time and its zone and offset according to the default time-zone.",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime() {
    return StructsConverter.toZonedDateTimeStruct(ZonedDateTime.now());
  }

  @Udf(description = "Create a ZonedDateTime struct based on the system clock's current date-time where zone and offset are based on the specified time-zone.",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime(
      @UdfParameter(
          value = "zoneId",
          description = "the ZoneId struct for the ZonedDateTime",
          schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
      final Struct zoneId) {
    return zoneId != null ? StructsConverter.toZonedDateTimeStruct(ZonedDateTime.now(StructsConverter.fromZoneIdStruct(zoneId))) : null;
  }

  @Udf(description = "Create a ZonedDateTime struct based on given LocalDateTime and ZoneId structs.",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime(
      @UdfParameter(
          value = "localDatetime",
          description = "the LocalDateTime struct for the ZonedDateTime",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct locaDateTime,
      @UdfParameter(
          value = "zoneId",
          description = "the ZoneId struct for the ZonedDateTime",
          schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
      final Struct zoneId) {
    if (locaDateTime == null || zoneId == null)
      return null;
    return StructsConverter.toZonedDateTimeStruct(ZonedDateTime.of(
            StructsConverter.fromLocalDateTimeStruct(locaDateTime),
            StructsConverter.fromZoneIdStruct(zoneId)
    ));
  }

  @Udf(description = "Create a ZonedDateTime struct based on given LocalDateTime, ZoneId and ZoneOffset structs.",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime(
      @UdfParameter(
          value = "localDatetime",
          description = "the LocalDateTime struct for the ZonedDateTime",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct locaDateTime,
      @UdfParameter(
          value = "zoneId",
          description = "the ZoneId struct for the ZonedDateTime",
          schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
      final Struct zoneId,
      @UdfParameter(
          value = "zoneOffset",
          description = "the ZoneOffset struct for the ZonedDateTime",
          schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
      final Struct zoneOffset) {
    if (locaDateTime == null || zoneId == null || zoneOffset == null)
      return null;
    return StructsConverter.toZonedDateTimeStruct(ZonedDateTime.ofLocal(
        StructsConverter.fromLocalDateTimeStruct(locaDateTime),
        StructsConverter.fromZoneIdStruct(zoneId),
        StructsConverter.fromZoneOffsetStruct(zoneOffset)
    ));
  }

  @Udf(description = "Create a ZonedDateTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the ZonedDateTime")
      final String text) {
    return text != null ? StructsConverter.toZonedDateTimeStruct(ZonedDateTime.parse(text)) : null;
  }

  @Udf(description = "Create an ZonedDateTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string",
      schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
  public Struct createZonedDateTime(
      @UdfParameter(
          value = "text",
          description = "the string representation of the ZonedDateTime")
      final String text,
      @UdfParameter(
          value = "format",
          description = "the specified java.time.format.DateTimeFormatter format string")
      final String format) {
    if (text == null || format == null)
      return null;
    return StructsConverter.toZonedDateTimeStruct(ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format,
        Locale.ENGLISH)));
  }

}
