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
import java.time.format.DateTimeFormatter;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_localdate_format",
    description = "Create a string representation of the LocalDate struct",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateFormat {

  @Udf(description = "Create a string representation of the LocalDate struct using the java.time.format.DateTimeFormatter#ISO_LOCAL_DATE format")
  public String format(
      @UdfParameter(
          value = "localDate",
          description = "the LocalDate struct to create a string representation for",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate
  ) {
    return localDate != null ? StructsConverter.fromLocalDateStruct(localDate).format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
  }

  @Udf(description = "Create a string representation of the LocalDate struct using the specified java.time.format.DateTimeFormatter format string")
  public String format(
      @UdfParameter(
          value = "localDate",
          description = "the LocalDate struct to create a string representation for",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate,
      @UdfParameter(
          value = "format",
          description = "the java.time.format.DateTimeFormatter format string")
      final String format
      ) {
    if (localDate == null || format == null)
      return null;
    return StructsConverter.fromLocalDateStruct(localDate).format(DateTimeFormatter.ofPattern(format));
  }

}
