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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_localdate",
    description = "Factory functions for LocalDate struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDate {

  @Udf(description = "Create a LocalDate struct based on the current date (system clock in the default time-zone)",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct createLocalDate() {
    return StructsConverter.toLocalDateStruct(LocalDate.now());
  }

  @Udf(description = "Create a LocalDate struct from its components (year, month, day)",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct createLocalDate(
      @UdfParameter(
          value = "year",
          description = "the year part of the LocalDate")
      final Integer year,
      @UdfParameter(
          value = "month",
          description = "the month part of the LocalDate")
      final Integer month,
      @UdfParameter(
          value = "day",
          description = "the day part of the LocalDate")
      final Integer day) {
    if (year == null || month == null || day == null)
      return null;
    return StructsConverter.toLocalDateStruct(LocalDate.of(year, month, day));
  }

  @Udf(description = "Create a LocalDate struct from a UNIX date given as the number of days since the epoch",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct createLocalDate(
      @UdfParameter(
          value = "epochDays",
          description = "the number of days since the epoch")
      final Long epochDays) {
    return epochDays != null ? StructsConverter.toLocalDateStruct(LocalDate.ofEpochDay(epochDays)) : null;
  }

  @Udf(description = "Create a LocalDate struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_DATE",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct createLocalDate(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalDate")
      final String text) {
    return text != null ? StructsConverter.toLocalDateStruct(LocalDate.parse(text)) : null;
  }

  @Udf(description = "Create a LocalDate struct from its string representation using the specified java.time.format.DateTimeFormatter format string",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct createLocalDate(
      @UdfParameter(
          value = "text",
          description = "the string representation of the LocalDate")
      final String text,
      @UdfParameter(
          value = "format",
          description = "the specified java.time.format.DateTimeFormatter format string")
      final String format) {
    if (text == null || format == null)
      return null;
    return StructsConverter.toLocalDateStruct(LocalDate.parse(text, DateTimeFormatter.ofPattern(format)));
  }

}
