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
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UdfDescription(
    name = "dt_zoneid",
    description = "Factory functions for ZoneId struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfZoneId {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfZoneId.class);

  @Udf(description = "Create a ZoneId struct from the system default time-zone",
      schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
  public Struct createZoneId() {
    return StructsConverter.toZoneIdStruct(ZoneId.systemDefault());
  }

  @Udf(description = "Create a ZoneId struct from a region ID string which is typically of the form '{area}/{city}'. For ID strings which represent an offset use ZoneOffset-related UDFs instead.",
      schema = DateTimeSchemas.ZONEID_SCHEMA_DESCRIPTOR)
  public Struct createZoneId(
      @UdfParameter(
          value = "text",
          description = "the region ID string")
      final String text) {
    if(text == null)
      return null;
    ZoneId parsed = ZoneId.of(text);
    if(parsed instanceof ZoneOffset) {
      LOGGER.error("only strings which are resolvable to regions are supported but the specified text '"+text+"' represents an offset");
      return null;
    }
    return StructsConverter.toZoneIdStruct(ZoneId.of(text));
  }

}
