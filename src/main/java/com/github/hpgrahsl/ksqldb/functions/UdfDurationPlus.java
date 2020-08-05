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
import java.util.List;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_duration_plus",
    description = "Add durations",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDurationPlus {

  @Udf(description = "Add a duration to another duration",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct which to add to",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "addDuration",
          description = "the duration struct to add",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct addDuration) {

    if (baseDuration == null || addDuration == null)
      return null;

    return
        StructsConverter.toDurationStruct(
            StructsConverter.fromDurationStruct(baseDuration)
                .plus(StructsConverter.fromDurationStruct(addDuration))
        );

  }

  @Udf(description = "Add multiple durations to another duration",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct plus(
      @UdfParameter(
          value = "baseDuration",
          description = "the duration struct which to add to",
          schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
      final Struct baseDuration,
      @UdfParameter(
          value = "addDurations",
          description = "multiple duration structs to add",
          schema = DateTimeSchemas.DURATION_ARRAY_SCHEMA_DESCRIPTOR)
      final List<Struct> addDurations) {

    if (baseDuration == null || addDurations == null)
      return null;

    Duration d = StructsConverter.fromDurationStruct(baseDuration);
    for(Struct s : addDurations) {
      d = d.plus(StructsConverter.fromDurationStruct(s));
    }
    return StructsConverter.toDurationStruct(d);

  }

}
