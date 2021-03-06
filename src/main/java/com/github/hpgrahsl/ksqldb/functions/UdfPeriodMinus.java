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
import java.time.Period;
import java.util.List;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_period_minus",
    description = "Subtract periods",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriodMinus {

  @Udf(description = "Subtract a period from another period",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to subtract from",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod,
      @UdfParameter(
          value = "subtractPeriod",
          description = "the period struct to subtract with",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct subtractPeriod) {

    if (basePeriod == null || subtractPeriod == null)
      return null;

    return
        StructsConverter.toPeriodStruct(
            StructsConverter.fromPeriodStruct(basePeriod)
                .minus(StructsConverter.fromPeriodStruct(subtractPeriod))
        );

  }

  @Udf(description = "Subtract multiple periods to from another period",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to subtract from",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod,
      @UdfParameter(
          value = "subtractPeriods",
          description = "multiple period structs to subtract with",
          schema = DateTimeSchemas.PERIOD_ARRAY_SCHEMA_DESCRIPTOR)
      final List<Struct> subtractPeriods) {

    if (basePeriod == null || subtractPeriods == null)
      return null;

    Period p = StructsConverter.fromPeriodStruct(basePeriod);
    for(Struct s : subtractPeriods) {
      p = p.minus(StructsConverter.fromPeriodStruct(s));
    }
    return StructsConverter.toPeriodStruct(p);

  }

}
