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
    name = "dt_localtime_plus",
    description = "Add to local times",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalTimePlus {

  @Udf(description = "Add a duration to a local time",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseLocalTime",
          description = "the local time to add to",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct baseLocalTime,
      @UdfParameter(
          value = "duration",
          description = "the duration to add",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct duration
      ) {
    if (baseLocalTime == null || duration == null)
      return null;
    return StructsConverter.toLocalTimeStruct(
        StructsConverter.fromLocalTimeStruct(baseLocalTime)
            .plus(StructsConverter.fromDurationStruct(duration))
    );
  }

  @Udf(description = "Add hours, minutes, seconds and nanos to a local time",
      schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseLocalTime",
          description = "the local time to add to",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct baseLocalTime,
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
    if (baseLocalTime == null || addHours == null || addMinutes == null || addSeconds == null || addNanos == null)
      return null;
    return StructsConverter.toLocalTimeStruct(
        StructsConverter.fromLocalTimeStruct(baseLocalTime)
          .plusHours(addHours)
          .plusMinutes(addMinutes)
          .plusSeconds(addSeconds)
          .plusNanos(addNanos)
    );
  }

}
