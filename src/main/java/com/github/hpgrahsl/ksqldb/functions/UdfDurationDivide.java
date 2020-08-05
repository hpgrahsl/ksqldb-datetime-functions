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
    name = "dt_duration_divide",
    description = "Divide a duration",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDurationDivide {

  @Udf(description = "Divide a duration by a divisor",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct divide(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct to divide",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "divisor",
          description = "the divisor to divide by")
      final Long divisor) {

    if (baseDuration == null || divisor == null)
      return null;

    return StructsConverter.toDurationStruct(
        StructsConverter.fromDurationStruct(baseDuration).dividedBy(divisor)
    );

  }

  @Udf(description = "Divide a duration by another duration, thereby returning the number of whole times a divisor duration occurs within the base duration")
  public Long divide(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct to divide",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "divisorDuration",
          description = "the divisor duration to divide by",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct divisorDuration) {

    if (baseDuration == null || divisorDuration == null)
      return null;

    return StructsConverter.fromDurationStruct(baseDuration)
        .dividedBy(StructsConverter.fromDurationStruct(divisorDuration));

  }

}
