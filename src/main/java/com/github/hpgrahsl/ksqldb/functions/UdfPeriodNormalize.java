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
    name = "dt_period_normalize",
    description = "Normalize a period",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriodNormalize {

  @Udf(description = "Normalize a period which might affect years and/or months but leaves days unchanged",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct normalize(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to normalize",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod) {
    return basePeriod != null ? StructsConverter.toPeriodStruct(
        StructsConverter.fromPeriodStruct(basePeriod).normalized()
    ) : null;
  }

}
