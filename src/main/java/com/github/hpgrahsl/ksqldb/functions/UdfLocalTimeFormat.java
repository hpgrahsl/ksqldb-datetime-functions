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
import java.util.Locale;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_localtime_format",
    description = "Create a string representation of the LocalTime struct",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalTimeFormat {

  @Udf(description = "Create a string representation of the LocalTime struct using the java.time.format.DateTimeFormatter#ISO_LOCAL_TIME format")
  public String format(
      @UdfParameter(
          value = "localTime",
          description = "the LocalTime struct to create a string representation for",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTime
  ) {
    return localTime != null ? StructsConverter.fromLocalTimeStruct(localTime).format(DateTimeFormatter.ISO_LOCAL_TIME) : null;
  }

  @Udf(description = "Create a string representation of the LocalTime struct using the specified java.time.format.DateTimeFormatter format string")
  public String format(
      @UdfParameter(
          value = "localTime",
          description = "the LocalTime struct to create a string representation for",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTime,
      @UdfParameter(
          value = "format",
          description = "the java.time.format.DateTimeFormatter format string")
      final String format
      ) {
    if(localTime == null || format == null )
      return null;
    return StructsConverter.fromLocalTimeStruct(localTime).format(DateTimeFormatter.ofPattern(format, Locale.ENGLISH));
  }

}
