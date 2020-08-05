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
import java.time.Duration;
import java.time.LocalTime;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UdfDescription(
    name = "dt_duration_between",
    description = "Calculate durations between LocalTime structs",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDurationBetween {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfDurationBetween.class);

  @Udf(
      description = "Calculate the duration between the given local time and the current local time, producing a duration result composed of seconds and optional nano seconds adjustment. If the given local time is after the current local time the resulting duration is negative.",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct between(
      @UdfParameter(
          value = "localTime",
          description = "the given local time based on which the duration is calculated towards current local time",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTime
      ) {

    if (localTime == null) {
      return null;
    }

    return StructsConverter.toDurationStruct(
        Duration.between(
            StructsConverter.fromLocalTimeStruct(localTime),
            LocalTime.now()
        )
    );

  }

  @Udf(description = "Calculate the duration between localTimeFrom and localTimeTo, producing a duration result composed of seconds and optional nano seconds adjustment. If localTimeFrom is after localTimeTo the resulting duration is negative.",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct between(
      @UdfParameter(
          value = "localTimeFrom",
          description = "localTime marking the duration's start",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTimeFrom,
      @UdfParameter(
          value = "localTimeTo",
          description = "localTime marking the duration's end",
          schema = DateTimeSchemas.LOCALTIME_SCHEMA_DESCRIPTOR)
      final Struct localTimeTo
  ) {

    if (localTimeFrom == null || localTimeTo == null) {
      return null;
    }

    return StructsConverter.toDurationStruct(
        Duration.between(
            StructsConverter.fromLocalTimeStruct(localTimeFrom),
            StructsConverter.fromLocalTimeStruct(localTimeTo)
        )
    );

  }

}
