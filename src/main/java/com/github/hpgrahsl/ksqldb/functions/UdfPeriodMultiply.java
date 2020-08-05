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
    name = "dt_period_multiply",
    description = "Multiply a period by a scalar",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriodMultiply {

  @Udf(description = "Multiply a period by a scalar (i.e. all its components are multiplied by that scalar)",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct multiply(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to multiply",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod,
      @UdfParameter(value = "scalar", description = "the scalar to multiply by")
      final Integer scalar) {

    if (basePeriod == null || scalar == null)
      return null;

    return StructsConverter.toPeriodStruct(
        StructsConverter.fromPeriodStruct(basePeriod).multipliedBy(scalar)
    );

  }

}
