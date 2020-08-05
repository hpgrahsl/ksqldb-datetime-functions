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
    name = "dt_localdate_plus",
    description = "Add to local dates",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDatePlus {

  @Udf(description = "Add a period to a local date",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseLocalDate",
          description = "the local date to add to",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDate,
      @UdfParameter(
          value = "period",
          description = "the period to add",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct period
      ) {
    if (baseLocalDate == null || period == null)
      return null;
    return StructsConverter.toLocalDateStruct(
        StructsConverter.fromLocalDateStruct(baseLocalDate)
            .plus(StructsConverter.fromPeriodStruct(period))
    );
  }

  @Udf(description = "Add years, months and days to a local date",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseLocalDate",
          description = "the local date to add to",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDate,
      @UdfParameter(
          value = "addYears",
          description = "the year part to add")
      final Integer addYears,
      @UdfParameter(
          value = "addMonths",
          description = "the month part to add")
      final Integer addMonths,
      @UdfParameter(
          value = "addDays",
          description = "the day part to add")
      final Integer addDays) {
    if (baseLocalDate == null || addYears == null || addMonths == null || addDays == null)
      return null;
    return StructsConverter.toLocalDateStruct(
        StructsConverter.fromLocalDateStruct(baseLocalDate)
          .plusYears(addYears)
          .plusMonths(addMonths)
          .plusDays(addDays)
    );
  }

}
