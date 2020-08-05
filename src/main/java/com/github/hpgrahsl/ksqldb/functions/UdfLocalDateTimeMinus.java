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
    name = "dt_localdatetime_minus",
    description = "Subtract from local datetimes",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateTimeMinus {

  @Udf(description = "Subtract a period and/or duration from a local datetime",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseLocalDateTime",
          description = "the local datetime to subtract from",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDateTime,
      @UdfParameter(
          value = "period",
          description = "the period to subtract (use the empty/zero Period in case only a duration should be subtracted)",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct period,
      @UdfParameter(
          value = "duration",
          description = "the duration to subtract (use the empty/zero Duration in case only a period should be subtracted)",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct duration
      ) {
    if (baseLocalDateTime == null || period == null || duration == null)
      return null;
    return StructsConverter.toLocalDateTimeStruct(
        StructsConverter.fromLocalDateTimeStruct(baseLocalDateTime)
            .minus(StructsConverter.fromPeriodStruct(period))
            .minus(StructsConverter.fromDurationStruct(duration))
    );
  }

  @Udf(description = "Subtract years, months, days, hours, minutes, seconds and nanos from a local datetime",
      schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
  public Struct minus(
      @UdfParameter(
          value = "baseLocalDateTime",
          description = "the local datetime to subtract from",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDateTime,
      @UdfParameter(
          value = "subtractYears",
          description = "the year part to subtract")
      final Integer subtractYears,
      @UdfParameter(
          value = "subtractMonths",
          description = "the month part to subtract")
      final Integer subtractMonths,
      @UdfParameter(
          value = "subtractDays",
          description = "the day part to subtract")
      final Integer subtractDays,
      @UdfParameter(
          value = "subtractHours",
          description = "the hour part to subtract")
      final Integer subtractHours,
      @UdfParameter(
          value = "subtractMinutes",
          description = "the minute part to subtract")
      final Integer subtractMinutes,
      @UdfParameter(
          value = "subtractSeconds",
          description = "the second part to subtract")
      final Integer subtractSeconds,
      @UdfParameter(
          value = "subtractNanos",
          description = "the nano part to subtract")
      final Integer subtractNanos) {
    if (baseLocalDateTime == null || subtractYears == null || subtractMonths == null || subtractDays == null
        || subtractHours == null || subtractMinutes == null || subtractSeconds == null || subtractNanos == null)
      return null;
    return StructsConverter.toLocalDateTimeStruct(
        StructsConverter.fromLocalDateTimeStruct(baseLocalDateTime)
          .minusYears(subtractYears)
          .minusMonths(subtractMonths)
          .minusDays(subtractDays)
          .minusHours(subtractHours)
          .minusMinutes(subtractMinutes)
          .minusSeconds(subtractSeconds)
          .minusNanos(subtractNanos)
    );
  }

}
