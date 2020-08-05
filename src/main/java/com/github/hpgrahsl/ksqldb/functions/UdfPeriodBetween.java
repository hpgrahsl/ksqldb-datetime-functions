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
import java.time.LocalDate;
import java.time.Period;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UdfDescription(
    name = "dt_period_between",
    description = "Calculate periods between LocalDate structs",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriodBetween {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfPeriodBetween.class);

  @Udf(description = "Calculate the period between the given local date and the current local date, producing a period result composed of years, months and days. If the given local date is in the future the period is negative.",
        schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct between(
      @UdfParameter(
          value = "localDate",
          description = "given local date based on which the period is calculated towards current date",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate) {

    if(localDate == null) {
      return null;
    }

    return StructsConverter.toPeriodStruct(Period.between(
        StructsConverter.fromLocalDateStruct(localDate),
        LocalDate.now()));

  }

  @Udf(description = "Calculate the period between localDateFrom and localDateTo, producing a period result composed of years, months and days.  If localDateFrom is after localDateTo the period is negative.",
      schema =  DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct between(
      @UdfParameter(
          value = "localDateFrom",
          description = "local date marking the period's start",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDateFrom,
      @UdfParameter(
          value = "localDateTo",
          description = "local date marking the period's end",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDateTo
      ) {

    if(localDateFrom == null || localDateTo == null) {
      return null;
    }

    return StructsConverter.toPeriodStruct(Period.between(
        StructsConverter.fromLocalDateStruct(localDateFrom),
        StructsConverter.fromLocalDateStruct(localDateTo))
    );

  }

}
