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
import java.time.Instant;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_instant",
    description = "Factory functions for Instant struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfInstant {

  @Udf(description = "Create an Instant struct at the current local date time (default system UTC clock)",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct createInstant() {
    return StructsConverter.toInstantStruct(Instant.now());
  }

  @Udf(description = "Create an Instant struct based on millis since the epoch",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct createInstant(
      @UdfParameter(
          value = "millis",
          description = "the millis since the epoch")
      final Long millis) {
    return millis != null ? StructsConverter.toInstantStruct(Instant.ofEpochMilli(millis)) : null;
  }

  @Udf(description = "Create an Instant struct based on seconds since the epoch with(out) nano seconds adjustment",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct createInstant(
      @UdfParameter(
          value = "seconds",
          description = "the seconds since the epoch")
      final Long seconds,
      @UdfParameter(
          value = "nanoAdjustment",
          description = "the nano seconds part")
      final Long nanoAdjustment) {
    if (seconds == null || nanoAdjustment == null)
      return null;
    return StructsConverter.toInstantStruct(Instant.ofEpochSecond(seconds,nanoAdjustment));
  }

  @Udf(description = "Create an Instant struct from its string representation using the java.time.format.DateTimeFormatter#ISO_INSTANT format",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct createInstant(
      @UdfParameter(
          value = "text",
          description = "the string representation of the Instant following java.time.format.DateTimeFormatter#ISO_INSTANT e.g. 2020-07-24T20:07:24.00Z")
      final String text) {
    return text != null ? StructsConverter.toInstantStruct(Instant.parse(text)) : null;
  }

}
