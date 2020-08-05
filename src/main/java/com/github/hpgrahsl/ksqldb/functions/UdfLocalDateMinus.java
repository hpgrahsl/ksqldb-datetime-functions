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
    name = "dt_localdate_minus",
    description = "Subtract from local dates",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateMinus {

  @Udf(description = "Subtract a period from a local date",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseLocalDate",
          description = "the local date to subtract from",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDate,
      @UdfParameter(
          value = "period",
          description = "the period to subtract",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct period
      ) {
    if (baseLocalDate == null || period == null)
      return null;
    return StructsConverter.toLocalDateStruct(
        StructsConverter.fromLocalDateStruct(baseLocalDate)
            .minus(StructsConverter.fromPeriodStruct(period))
    );
  }

  @Udf(description = "Subtract years, months and days from a local date",
      schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseLocalDate",
          description = "the local date to subtract from",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDate,
      @UdfParameter(
          value = "minusYears",
          description = "the year part to subtract")
      final Integer minusYears,
      @UdfParameter(
          value = "minusMonths",
          description = "the month part to subtract")
      final Integer minusMonths,
      @UdfParameter(
          value = "minusDays",
          description = "the day part to subtract")
      final Integer minusDays) {
    if (baseLocalDate == null || minusYears == null || minusMonths == null || minusDays == null)
      return null;
    return StructsConverter.toLocalDateStruct(
        StructsConverter.fromLocalDateStruct(baseLocalDate)
          .minusYears(minusYears)
          .minusMonths(minusMonths)
          .minusDays(minusDays)
    );
  }

}
