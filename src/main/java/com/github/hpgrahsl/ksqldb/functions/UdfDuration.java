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
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_duration",
    description = "Factory functions for Duration struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
)
public class UdfDuration {

  @Udf(description = "Create the empty/zero Duration struct",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct createDuration() {
    return StructsConverter.toDurationStruct(Duration.ZERO);
  }

  @Udf(description = "Create a Duration struct based on seconds without nano seconds adjustment",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct createDuration(
      @UdfParameter(
          value = "seconds",
          description = "the seconds part of the Duration")
      final Long seconds) {
    return seconds != null ? StructsConverter.toDurationStruct(Duration.ofSeconds(seconds)) : null;
  }

  @Udf(description = "Create a Duration struct based on seconds with nano seconds adjustment",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct createDuration(
      @UdfParameter(
          value = "seconds",
          description = "the seconds part of the Duration")
      final Long seconds,
      @UdfParameter(
          value = "nanoAdjustment",
          description = "the nano seconds part of the Duration")
      final Long nanoAdjustment) {
    if (seconds == null || nanoAdjustment == null)
      return null;
    return StructsConverter.toDurationStruct(Duration.ofSeconds(seconds,nanoAdjustment));
  }

  @Udf(description = "Create a Duration struct from its string representation using the ISO-8601 duration format {PnDTnHnMn.nS}",
      schema = DateTimeSchemas.DURATION_SCHEMA_DESCRIPTOR)
  public Struct createDuration(
      @UdfParameter(
          value = "text",
          description = "the string representation of the Duration")
      final String text) {
    return text != null ? StructsConverter.toDurationStruct(Duration.parse(text)) : null;
  }

}
