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
    name = "dt_duration_multiply",
    description = "Multiply a duration by a scalar",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDurationMultiply {

  @Udf(description = "Multiply a duration by a scalar (i.e. all its components are multiplied by that scalar)",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct multiply(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct to multiply",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "scalar",
          description = "the scalar to multiply by")
      final Long scalar) {

    if (baseDuration == null || scalar == null)
      return null;

    return StructsConverter.toDurationStruct(
        StructsConverter.fromDurationStruct(baseDuration).multipliedBy(scalar)
    );

  }

}
