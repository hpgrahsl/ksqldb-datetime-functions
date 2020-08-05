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
    name = "dt_instant_minus",
    description = "Subtract from instants",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfInstantMinus {

  @Udf(description = "Subtract a duration from an instant",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "instant",
          description = "the instant to subtract from",
          schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
      final Struct instant,
      @UdfParameter(
          value = "duration",
          description = "the duration to subtract",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct duration
      ) {
    if (instant == null || duration == null)
      return null;
    return StructsConverter.toInstantStruct(
        StructsConverter.fromInstantStruct(instant)
            .minus(StructsConverter.fromDurationStruct(duration))
    );
  }

  @Udf(description = "Subtract seconds and nanos from an instant",
      schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "instant",
          description = "the instant to subtract from",
          schema = DateTimeSchemas.INSTANT_SCHEMA_DESCRIPTOR)
      final Struct instant,
      @UdfParameter(
          value = "seconds",
          description = "the second part to subtract")
      final Long seconds,
      @UdfParameter(
          value = "nanos",
          description = "the nano part to subtract")
      final Long nanos) {
    if (instant == null || seconds == null || nanos == null)
      return null;
    return StructsConverter.toInstantStruct(
        StructsConverter.fromInstantStruct(instant)
          .minusSeconds(seconds)
          .minusNanos(nanos)
    );
  }

}
