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
    name = "dt_localdate_chronology",
    description = "Chronology check of local dates",
    author = "Hans-Peter Grahsl (follow @hpgrahsl)",
    version = "0.1.0"
    )
public class UdfLocalDateChronology {

  private static final Logger LOGGER = LoggerFactory.getLogger(UdfLocalDateChronology.class);

  @Udf(description = "Check if a local date is either before, after or equal to another local date")
  public Boolean check(
      @UdfParameter(
          value = "baseLocalDate",
          description = "the local date to check against",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct baseLocalDate,
      @UdfParameter(
          value = "localDate",
          description = "the local date to check whether it's before, after or equal",
          schema = DateTimeSchemas.LOCALDATE_SCHEMA_DESCRIPTOR)
      final Struct localDate,
      @UdfParameter(
          value = "chronologyMode",
          description = "the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'")
      final String chronologyMode
      ) {
    if (baseLocalDate == null || localDate == null || chronologyMode == null)
      return null;
    try {
      ChronologyMode cm = ChronologyMode.valueOf(chronologyMode);
      switch (cm) {
        case IS_BEFORE:
          return StructsConverter.fromLocalDateStruct(localDate)
              .isBefore(StructsConverter.fromLocalDateStruct(baseLocalDate));
        case IS_AFTER:
          return StructsConverter.fromLocalDateStruct(localDate)
              .isAfter(StructsConverter.fromLocalDateStruct(baseLocalDate));
        case IS_EQUAL:
          return StructsConverter.fromLocalDateStruct(localDate)
              .isEqual(StructsConverter.fromLocalDateStruct(baseLocalDate));
      }
    } catch(IllegalArgumentException e) {
      LOGGER.error("chronologyMode '" + chronologyMode +
          "' is invalid - must be one of: 'IS_BEFORE','IS_AFTER','IS_EQUAL'",e);
    }
    return null;
  }

}
