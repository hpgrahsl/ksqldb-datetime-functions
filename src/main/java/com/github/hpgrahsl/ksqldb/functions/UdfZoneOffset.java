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
import java.time.ZoneOffset;
import org.apache.kafka.connect.data.Struct;

@UdfDescription(
    name = "dt_zoneoffset",
    description = "Factory functions for ZoneOffset struct creation",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfZoneOffset {

  @Udf(description = "Create a ZoneOffset struct from its components (hours, minutes, seconds)",
      schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
  public Struct createZoneOffset(
      @UdfParameter(
          value = "hours",
          description = "the hours part of the ZoneOffset")
      final Integer hours,
      @UdfParameter(
          value = "minutes",
          description = "the minutes part of the ZoneOffset")
      final Integer minutes,
      @UdfParameter(
          value = "seconds",
          description = "the seconds part of the ZoneOffset")
      final Integer seconds) {
    if (hours == null || minutes == null || seconds == null)
      return null;
    return StructsConverter.toZoneOffsetStruct(ZoneOffset.ofHoursMinutesSeconds(hours,minutes,seconds));
  }

  @Udf(description = "Create a ZoneOffset struct based on total seconds",
      schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
  public Struct createZoneOffset(
      @UdfParameter(
          value = "totalSeconds",
          description = "the totalSeconds defining the ZoneOffset")
      final Integer totalSeconds) {
    return totalSeconds != null ? StructsConverter.toZoneOffsetStruct(ZoneOffset.ofTotalSeconds(totalSeconds)) : null;
  }

  @Udf(description = "Create a ZoneOffset struct from its ID string",
      schema = DateTimeSchemas.ZONEOFFSET_SCHEMA_DESCRIPTOR)
  public Struct createZoneOffset(
      @UdfParameter(
          value = "text",
          description = "the ID string of the ZoneOffset")
      final String text) {
    return text != null ? StructsConverter.toZoneOffsetStruct(ZoneOffset.of(text)) : null;
  }

}
