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
    name = "dt_offsetdatetime_plus",
    description = "Add to offset datetimes",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfOffsetDateTimePlus {

  @Udf(description = "Add a period and/or duration to an offset datetime",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseOffsetDateTime",
          description = "the offset datetime to add to",
          schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseOffsetDateTime,
      @UdfParameter(
          value = "period",
          description = "the period to add (use the empty/zero Period in case only a duration should be added)",
          schema = DateTimeSchemas.PERIOD_SCHEMA_DESCRIPTOR)
      final Struct period,
      @UdfParameter(
          value = "duration",
          description = "the duration to add (use the empty/zero Duration in case only a period should be added)",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct duration
      ) {
    if (baseOffsetDateTime == null || period == null || duration == null)
      return null;
    return StructsConverter.toOffsetDateTimeStruct(
        StructsConverter.fromOffsetDateTimeStruct(baseOffsetDateTime)
            .plus(StructsConverter.fromPeriodStruct(period))
            .plus(StructsConverter.fromDurationStruct(duration))
    );
  }

  @Udf(description = "Add years, months, days, hours, minutes, seconds and nanos to an offset datetime",
      schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseOffsetDateTime",
          description = "the offset datetime to add to",
          schema = DateTimeSchemas.OFFSETDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseOffsetDateTime,
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
      final Integer addDays,
      @UdfParameter(
          value = "addHours",
          description = "the hour part to add")
      final Integer addHours,
      @UdfParameter(
          value = "addMinutes",
          description = "the minute part to add")
      final Integer addMinutes,
      @UdfParameter(
          value = "addSeconds",
          description = "the second part to add")
      final Integer addSeconds,
      @UdfParameter(
          value = "addNanos",
          description = "the nano part to add")
      final Integer addNanos) {
    if (baseOffsetDateTime == null || addYears == null || addMonths == null || addDays == null
        || addHours == null || addMinutes == null || addSeconds == null || addNanos == null)
      return null;
    return StructsConverter.toOffsetDateTimeStruct(
        StructsConverter.fromOffsetDateTimeStruct(baseOffsetDateTime)
          .plusYears(addYears)
          .plusMonths(addMonths)
          .plusDays(addDays)
          .plusHours(addHours)
          .plusMinutes(addMinutes)
          .plusSeconds(addSeconds)
          .plusNanos(addNanos)
    );
  }

}
