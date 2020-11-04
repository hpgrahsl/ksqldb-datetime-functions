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
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_zoneoffset_stringify",
    description = "Create a string representation of a ZoneOffset",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfZoneOffsetStringify {

  @Udf(description = "Create a string representation of a ZoneOffset (e.g. 'Z', '+/-hh:mm', '+/-hh:mm:ss'")
  public String stringify(
      @UdfParameter(
          value = "zoneOffset", description = "the ZoneOffset struct to stringify",
          schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
      final Struct zoneOffset) {
    return zoneOffset != null ? StructsConverter.fromZoneOffsetStruct(zoneOffset).toString() : null;
  }

}
