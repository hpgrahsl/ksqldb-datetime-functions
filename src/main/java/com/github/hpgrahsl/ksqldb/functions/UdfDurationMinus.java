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
import java.time.Duration;
import java.util.List;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_duration_minus",
    description = "Subtract durations",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDurationMinus {

  @Udf(description = "Subtract a duration from another duration",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct to subtract from",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "subtractDuration",
          description = "the duration struct to subtract with",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct subtractDuration) {

    if (baseDuration == null || subtractDuration == null)
      return null;

    return
        StructsConverter.toDurationStruct(
            StructsConverter.fromDurationStruct(baseDuration)
                .minus(StructsConverter.fromDurationStruct(subtractDuration))
        );

  }

  @Udf(description = "Subtract multiple durations to from another duration",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct to subtract from",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "subtractDurations",
          description = "multiple duration structs to subtract with",
          schema = DateTimeSchemas.DURATION_ARRAY_SCHEMA_DESCRIPTOR)
      final List<Struct> subtractDurations) {

    if (baseDuration == null || subtractDurations == null)
      return null;

    Duration d = StructsConverter.fromDurationStruct(baseDuration);
    for(Struct s : subtractDurations) {
      d = d.minus(StructsConverter.fromDurationStruct(s));
    }
    return StructsConverter.toDurationStruct(d);

  }

}
