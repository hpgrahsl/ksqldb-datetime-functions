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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UdfDescription(
    name = "dt_zoneddatetime_chronology",
    description = "Chronology check of zoned datetimes",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfZonedDateTimeChronology {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfZonedDateTimeChronology.class);

  @Udf(description = "Check if a zoned datetime is either before, after or equal to another zoned datetime")
  public Boolean check(
      @UdfParameter(
          value = "baseZonedDateTime",
          description = "the zoned datetime to check against",
          schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseZonedDateTime,
      @UdfParameter(
          value = "zonedDateTime",
          description = "the zoned datetime to check whether it's before, after or equal",
          schema = DateTimeSchemas.ZONEDDATETIME_SCHEMA_DESCRIPTOR)
      final Struct zonedDateTime,
      @UdfParameter(
          value = "chronologyMode",
          description = "the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'")
      final String chronologyMode
      ) {
    if (baseZonedDateTime == null || zonedDateTime == null || chronologyMode == null)
      return null;
    try {
      ChronologyMode cm = ChronologyMode.valueOf(chronologyMode);
      switch (cm) {
        case IS_BEFORE:
          return StructsConverter.fromZonedDateTimeStruct(zonedDateTime)
              .isBefore(StructsConverter.fromZonedDateTimeStruct(baseZonedDateTime));
        case IS_AFTER:
          return StructsConverter.fromZonedDateTimeStruct(zonedDateTime)
              .isAfter(StructsConverter.fromZonedDateTimeStruct(baseZonedDateTime));
        case IS_EQUAL:
          return StructsConverter.fromZonedDateTimeStruct(zonedDateTime)
              .isEqual(StructsConverter.fromZonedDateTimeStruct(baseZonedDateTime));
      }
    } catch(IllegalArgumentException e) {
      LOGGER.error("chronologyMode '" + chronologyMode +
          "' is invalid - must be one of: 'IS_BEFORE','IS_AFTER','IS_EQUAL'",e);
    }
    return null;
  }

}
