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
    name = "dt_localdatetime_chronology",
    description = "Chronology check of local datetimes",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateTimeChronology {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfLocalDateTimeChronology.class);

  @Udf(description = "Check if a local datetime is either before, after or equal to another local datetime")
  public Boolean check(
      @UdfParameter(
          value = "baseLocalDateTime",
          description = "the local datetime to check against",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDateTime,
      @UdfParameter(
          value = "localDateTime",
          description = "the local datetime to check whether it's before, after or equal",
          schema = DateTimeSchemas.LOCALDATETIME_SCHEMA_DESCRIPTOR)
      final Struct localDateTime,
      @UdfParameter(
          value = "chronologyMode",
          description = "the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'")
      final String chronologyMode
      ) {
    if (baseLocalDateTime == null || localDateTime == null || chronologyMode == null)
      return null;
    try {
      ChronologyMode cm = ChronologyMode.valueOf(chronologyMode);
      switch (cm) {
        case IS_BEFORE:
          return StructsConverter.fromLocalDateTimeStruct(localDateTime)
              .isBefore(StructsConverter.fromLocalDateTimeStruct(baseLocalDateTime));
        case IS_AFTER:
          return StructsConverter.fromLocalDateTimeStruct(localDateTime)
              .isAfter(StructsConverter.fromLocalDateTimeStruct(baseLocalDateTime));
        case IS_EQUAL:
          return StructsConverter.fromLocalDateTimeStruct(localDateTime)
              .isEqual(StructsConverter.fromLocalDateTimeStruct(baseLocalDateTime));
      }
    } catch(IllegalArgumentException e) {
      LOGGER.error("chronologyMode '" + chronologyMode +
          "' is invalid - must be one of: 'IS_BEFORE','IS_AFTER','IS_EQUAL'",e);
    }
    return null;
  }

}
