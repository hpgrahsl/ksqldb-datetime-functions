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
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_period",
    description = "Factory functions for Period struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfPeriod {

  @Udf(description = "Create the empty/zero Period struct",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct createPeriod() {
    return StructsConverter.toPeriodStruct(Period.ZERO);
  }

  @Udf(description = "Create a Period struct from its components (years, months, days)",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct createPeriod(
      @UdfParameter(
          value = "years",
          description = "the years part of the Period")
      final Integer years,
      @UdfParameter(
          value = "months",
          description = "the months part of the Period")
      final Integer months,
      @UdfParameter(
          value = "days",
          description = "the days part of the Period")
      final Integer days) {
    if (years == null || months == null || days == null)
      return null;
    return StructsConverter.toPeriodStruct(Period.of(years,months,days));
  }

  @Udf(description = "Create a Period struct from its string representation using the ISO-8601 period formats {PnYnMnD} and {PnW}",
      schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
  public Struct createPeriod(
      @UdfParameter(
          value = "text",
          description = "the string representation of the Period")
      final String text) {
    return text != null ? StructsConverter.toPeriodStruct(Period.parse(text)) : null;
  }

}
