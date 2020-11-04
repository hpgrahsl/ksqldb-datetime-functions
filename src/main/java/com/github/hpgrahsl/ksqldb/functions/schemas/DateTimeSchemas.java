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

package com.github.hpgrahsl.ksqldb.functions.schemas;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class DateTimeSchemas {

  public static final Schema INSTANT_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("SECONDS_FIELD", Schema.INT64_SCHEMA)
      .field("NANOS_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String INSTANT_SCHEMA_DESCRIPTOR =
      "STRUCT<SECONDS_FIELD BIGINT,NANOS_FIELD INTEGER>";

  public static final Schema DURATION_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("SECONDS_FIELD", Schema.INT64_SCHEMA)
      .field("NANOS_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String DURATION_SCHEMA_DESCRIPTOR =
      "STRUCT<SECONDS_FIELD BIGINT,NANOS_FIELD INTEGER>";

  public static final String DURATION_ARRAY_SCHEMA_DESCRIPTOR =
      "ARRAY<"+DURATION_SCHEMA_DESCRIPTOR+">";

  public static final Schema PERIOD_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("YEARS_FIELD", Schema.INT32_SCHEMA)
      .field("MONTHS_FIELD", Schema.INT32_SCHEMA)
      .field("DAYS_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String PERIOD_SCHEMA_DESCRIPTOR =
      "STRUCT<YEARS_FIELD INTEGER,MONTHS_FIELD INTEGER,DAYS_FIELD INTEGER>";

  public static final String PERIOD_ARRAY_SCHEMA_DESCRIPTOR =
      "ARRAY<"+PERIOD_SCHEMA_DESCRIPTOR+">";

  public static final Schema LOCALDATE_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("YEAR_FIELD", Schema.INT32_SCHEMA)
      .field("MONTH_FIELD", Schema.INT32_SCHEMA)
      .field("DAY_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String LOCALDATE_SCHEMA_DESCRIPTOR =
      "STRUCT<YEAR_FIELD INTEGER,MONTH_FIELD INTEGER,DAY_FIELD INTEGER>";

  public static final Schema LOCALTIME_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("HOUR_FIELD", Schema.INT32_SCHEMA)
      .field("MINUTE_FIELD", Schema.INT32_SCHEMA)
      .field("SECOND_FIELD", Schema.INT32_SCHEMA)
      .field("NANO_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String LOCALTIME_SCHEMA_DESCRIPTOR =
      "STRUCT<HOUR_FIELD INTEGER,MINUTE_FIELD INTEGER,SECOND_FIELD INTEGER,NANO_FIELD INTEGER>";

  public static final Schema LOCALDATETIME_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("LOCALDATE_FIELD", LOCALDATE_SCHEMA)
      .field("LOCALTIME_FIELD", LOCALTIME_SCHEMA)
      .build();

  public static final String LOCALDATETIME_SCHEMA_DESCRIPTOR =
      "STRUCT<LOCALDATE_FIELD "+LOCALDATE_SCHEMA_DESCRIPTOR+","
            +"LOCALTIME_FIELD "+LOCALTIME_SCHEMA_DESCRIPTOR+">";

  public static final Schema ZONEOFFSET_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("TOTALSECONDS_FIELD", Schema.INT32_SCHEMA)
      .build();

  public static final String ZONEOFFSET_SCHEMA_DESCRIPTOR =
      "STRUCT<TOTALSECONDS_FIELD INTEGER>";

  public static final Schema ZONEID_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("ID_FIELD", Schema.STRING_SCHEMA)
      .build();

  public static final String ZONEID_SCHEMA_DESCRIPTOR =
      "STRUCT<ID_FIELD VARCHAR>";

  public static final Schema OFFSETDATETIME_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("DATETIME_FIELD",LOCALDATETIME_SCHEMA)
      .field("OFFSET_FIELD",ZONEOFFSET_SCHEMA)
      .build();

  public static final String OFFSETDATETIME_SCHEMA_DESCRIPTOR =
      "STRUCT<DATETIME_FIELD "+LOCALDATETIME_SCHEMA_DESCRIPTOR+","
          +"OFFSET_FIELD "+ZONEOFFSET_SCHEMA_DESCRIPTOR+">";

  public static final Schema ZONEDDATETIME_SCHEMA = SchemaBuilder.struct()
      .optional()
      .field("DATETIME_FIELD",LOCALDATETIME_SCHEMA)
      .field("OFFSET_FIELD",ZONEOFFSET_SCHEMA)
      .field("ZONE_FIELD",ZONEID_SCHEMA)
      .build();

  public static final String ZONEDDATETIME_SCHEMA_DESCRIPTOR =
      "STRUCT<DATETIME_FIELD "+LOCALDATETIME_SCHEMA_DESCRIPTOR+","
          +"OFFSET_FIELD "+ZONEOFFSET_SCHEMA_DESCRIPTOR+","
          +"ZONE_FIELD "+ZONEID_SCHEMA_DESCRIPTOR+">";

}
