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
    name = "dt_period_plus",
    description = "Add periods",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriodPlus {

  @Udf(description = "Add a period to another period",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to add to",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod,
      @UdfParameter(
          value = "addPeriod",
          description = "the period struct to add",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct addPeriod) {

    if (basePeriod == null || addPeriod == null)
      return null;

    return
        StructsConverter.toPeriodStruct(
            StructsConverter.fromPeriodStruct(basePeriod)
                .plus(StructsConverter.fromPeriodStruct(addPeriod))
        );

  }

  @Udf(description = "Add multiple periods to another period",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "basePeriod",
          description = "the period struct to add to",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct basePeriod,
      @UdfParameter(
          value = "addPeriods",
          description = "multiple period structs to add",
          schema = DateTimeSchemas.PERIOD_ARRAY_SCHEMA_DESCRIPTOR)
      final List<Struct> addPeriods) {

    if (basePeriod == null || addPeriods == null)
      return null;

    Period p = StructsConverter.fromPeriodStruct(basePeriod);
    for(Struct s : addPeriods) {
      p = p.plus(StructsConverter.fromPeriodStruct(s));
    }
    return StructsConverter.toPeriodStruct(p);

  }

}
