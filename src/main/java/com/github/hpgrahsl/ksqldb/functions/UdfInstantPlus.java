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
    name = "dt_instant_plus",
    description = "Add to instants",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfInstantPlus {

  @Udf(description = "Add a duration to an instant",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseInstant",
          description = "the instant to add to",
          schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
      final Struct baseInstant,
      @UdfParameter(
          value = "duration",
          description = "the duration to add",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct duration
      ) {
    if (baseInstant == null || duration == null)
      return null;
    return StructsConverter.toInstantStruct(
        StructsConverter.fromInstantStruct(baseInstant)
            .plus(StructsConverter.fromDurationStruct(duration))
    );
  }

  @Udf(description = "Add seconds and nanos to an instant",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseInstant",
          description = "the instant to add to",
          schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
      final Struct baseInstant,
      @UdfParameter(
          value = "seconds",
          description = "the second part to add")
      final Long seconds,
      @UdfParameter(
          value = "nanos",
          description = "the nano part to add")
      final Long nanos) {
    if (baseInstant == null || seconds == null || nanos == null)
      return null;
    return StructsConverter.toInstantStruct(
        StructsConverter.fromInstantStruct(baseInstant)
          .plusSeconds(seconds)
          .plusNanos(nanos)
    );
  }

}
