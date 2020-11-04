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

package com.github.hpgrahsl.ksqldb.functions.util;

import com.github.hpgrahsl.ksqldb.functions.schemas.DateTimeSchemas;
import javax.json.JsonObject;
import org.apache.kafka.connect.data.Struct;

public class JsonExtractor {

  public static Integer toInteger(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ? null : jo.getJsonNumber(fieldName).intValue();
  }

  public static Long toLong(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ? null : jo.getJsonNumber(fieldName).longValue();
  }

  public static Struct toInstantStruct(JsonObject jo,String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.INSTANT_SCHEMA)
        .put("SECONDS_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("seconds_field").longValue())
        .put("NANOS_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("nanos_field").intValue());
  }

  public static Struct toPeriodStruct(JsonObject jo,String fieldName) {
    return jo.isNull(fieldName)
        ? null
        : new Struct(DateTimeSchemas.PERIOD_SCHEMA)
            .put("YEARS_FIELD",
                jo.getJsonObject(fieldName)
                    .getJsonNumber("years_field").intValue())
            .put("MONTHS_FIELD",
                jo.getJsonObject(fieldName)
                    .getJsonNumber("months_field").intValue())
            .put("DAYS_FIELD",
                jo.getJsonObject(fieldName)
                    .getJsonNumber("days_field").intValue());
  }

  public static Struct toLocalDateStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.LOCALDATE_SCHEMA)
        .put("YEAR_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("year_field").intValue())
        .put("MONTH_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("month_field").intValue())
        .put("DAY_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("day_field").intValue());
  }

  public static Struct toLocalTimeStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.LOCALTIME_SCHEMA)
        .put("HOUR_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("hour_field").intValue())
        .put("MINUTE_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("minute_field").intValue())
        .put("SECOND_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("second_field").intValue())
        .put("NANO_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("nano_field").intValue());
  }

  public static Struct toLocalDateTimeStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.LOCALDATETIME_SCHEMA)
          .put("LOCALDATE_FIELD", new Struct(DateTimeSchemas.LOCALDATE_SCHEMA)
                  .put("YEAR_FIELD",
                      jo.getJsonObject(fieldName).getJsonObject("localdate_field")
                          .getJsonNumber("year_field").intValue())
                  .put("MONTH_FIELD",
                      jo.getJsonObject(fieldName).getJsonObject("localdate_field")
                          .getJsonNumber("month_field").intValue())
                  .put("DAY_FIELD",
                      jo.getJsonObject(fieldName).getJsonObject("localdate_field")
                          .getJsonNumber("day_field").intValue())
          )
          .put("LOCALTIME_FIELD", new Struct(DateTimeSchemas.LOCALTIME_SCHEMA)
              .put("HOUR_FIELD",
                  jo.getJsonObject(fieldName).getJsonObject("localtime_field")
                      .getJsonNumber("hour_field").intValue())
              .put("MINUTE_FIELD",
                  jo.getJsonObject(fieldName).getJsonObject("localtime_field")
                      .getJsonNumber("minute_field").intValue())
              .put("SECOND_FIELD",
                  jo.getJsonObject(fieldName).getJsonObject("localtime_field")
                      .getJsonNumber("second_field").intValue())
              .put("NANO_FIELD",
                  jo.getJsonObject(fieldName).getJsonObject("localtime_field")
                      .getJsonNumber("nano_field").intValue())
          );
  }

  public static Struct toDurationStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.DURATION_SCHEMA)
        .put("SECONDS_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("seconds_field").longValue())
        .put("NANOS_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("nanos_field").intValue());
  }

  public static Struct toZoneOffsetStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.ZONEOFFSET_SCHEMA)
        .put("TOTALSECONDS_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonNumber("totalseconds_field").intValue());
  }

  public static Struct toOffsetDateTimeStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.OFFSETDATETIME_SCHEMA)
        .put("DATETIME_FIELD", new Struct(DateTimeSchemas.LOCALDATETIME_SCHEMA)
            .put("LOCALDATE_FIELD", new Struct(DateTimeSchemas.LOCALDATE_SCHEMA)
                .put("YEAR_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("year_field").intValue())
                .put("MONTH_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("month_field").intValue())
                .put("DAY_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("day_field").intValue())
            )
            .put("LOCALTIME_FIELD", new Struct(DateTimeSchemas.LOCALTIME_SCHEMA)
                .put("HOUR_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("hour_field").intValue())
                .put("MINUTE_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("minute_field").intValue())
                .put("SECOND_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("second_field").intValue())
                .put("NANO_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("nano_field").intValue())
            )
        )
        .put("OFFSET_FIELD", new Struct(DateTimeSchemas.ZONEOFFSET_SCHEMA)
            .put("TOTALSECONDS_FIELD",jo.getJsonObject(fieldName)
                .getJsonObject("offset_field").getJsonNumber("totalseconds_field").intValue())
        );
  }

  public static Struct toZoneIdStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.ZONEID_SCHEMA)
        .put("ID_FIELD",
            jo.getJsonObject(fieldName)
                .getJsonString("id_field").getString());
  }

  public static Struct toZonedDateTimeStruct(JsonObject jo, String fieldName) {
    return jo.isNull(fieldName) ?
        null : new Struct(DateTimeSchemas.ZONEDDATETIME_SCHEMA)
        .put("DATETIME_FIELD", new Struct(DateTimeSchemas.LOCALDATETIME_SCHEMA)
            .put("LOCALDATE_FIELD", new Struct(DateTimeSchemas.LOCALDATE_SCHEMA)
                .put("YEAR_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("year_field").intValue())
                .put("MONTH_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("month_field").intValue())
                .put("DAY_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localdate_field").getJsonNumber("day_field").intValue())
            )
            .put("LOCALTIME_FIELD", new Struct(DateTimeSchemas.LOCALTIME_SCHEMA)
                .put("HOUR_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("hour_field").intValue())
                .put("MINUTE_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("minute_field").intValue())
                .put("SECOND_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("second_field").intValue())
                .put("NANO_FIELD",
                    jo.getJsonObject(fieldName).getJsonObject("datetime_field")
                        .getJsonObject("localtime_field").getJsonNumber("nano_field").intValue())
            )
        )
        .put("OFFSET_FIELD", new Struct(DateTimeSchemas.ZONEOFFSET_SCHEMA)
            .put("TOTALSECONDS_FIELD",jo.getJsonObject(fieldName)
                .getJsonObject("offset_field").getJsonNumber("totalseconds_field").intValue())
        )
        .put("ZONE_FIELD", new Struct(DateTimeSchemas.ZONEID_SCHEMA)
            .put("ID_FIELD",jo.getJsonObject(fieldName)
                .getJsonObject("zone_field").getJsonString("id_field").getString())
        );
  }



}
