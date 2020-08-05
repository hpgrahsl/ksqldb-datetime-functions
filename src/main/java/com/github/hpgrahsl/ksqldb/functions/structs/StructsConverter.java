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

package com.github.hpgrahsl.ksqldb.functions.structs;

import com.github.hpgrahsl.ksqldb.functions.schemas.DateTimeSchemas;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import org.apache.kafka.connect.data.Struct;

public class StructsConverter {

  public static Struct toInstantStruct(Instant i) {

    return new Struct(DateTimeSchemas.INSTANT_SCHEMA)
        .put(DateTimeSchemas.INSTANT_SCHEMA.field("SECONDS_FIELD"),i.getEpochSecond())
        .put(DateTimeSchemas.INSTANT_SCHEMA.field("NANOS_FIELD"),i.getNano());
  }

  public static Instant fromInstantStruct(Struct s) {
    return Instant.ofEpochSecond(
        s.getInt64("SECONDS_FIELD"),
        s.getInt32("NANOS_FIELD")
    );
  }

  public static Struct toDurationStruct(Duration d) {
    return new Struct(DateTimeSchemas.DURATION_SCHEMA)
        .put(DateTimeSchemas.DURATION_SCHEMA.field("SECONDS_FIELD"),d.getSeconds())
        .put(DateTimeSchemas.DURATION_SCHEMA.field("NANOS_FIELD"),d.getNano());
  }

  public static Duration fromDurationStruct(Struct s) {
    return Duration.ofSeconds(
        s.getInt64("SECONDS_FIELD"),
        s.getInt32("NANOS_FIELD")
    );
  }

  public static Struct toPeriodStruct(Period p) {
    return new Struct(DateTimeSchemas.PERIOD_SCHEMA)
        .put(DateTimeSchemas.PERIOD_SCHEMA.field("YEARS_FIELD"),p.getYears())
        .put(DateTimeSchemas.PERIOD_SCHEMA.field("MONTHS_FIELD"),p.getMonths())
        .put(DateTimeSchemas.PERIOD_SCHEMA.field("DAYS_FIELD"),p.getDays());
  }

  public static Period fromPeriodStruct(Struct s) {
    return Period.of(
        s.getInt32("YEARS_FIELD"),
        s.getInt32("MONTHS_FIELD"),
        s.getInt32("DAYS_FIELD")
    );
  }

  public static Struct toLocalDateStruct(LocalDate ld) {
    return new Struct(DateTimeSchemas.LOCALDATE_SCHEMA)
        .put(DateTimeSchemas.LOCALDATE_SCHEMA.field("YEAR_FIELD"),ld.getYear())
        .put(DateTimeSchemas.LOCALDATE_SCHEMA.field("MONTH_FIELD"),ld.getMonthValue())
        .put(DateTimeSchemas.LOCALDATE_SCHEMA.field("DAY_FIELD"),ld.getDayOfMonth());
  }

  public static LocalDate fromLocalDateStruct(Struct s) {
    return LocalDate.of(
        s.getInt32("YEAR_FIELD"),
        s.getInt32("MONTH_FIELD"),
        s.getInt32("DAY_FIELD")
    );
  }

  public static Struct toLocalTimeStruct(LocalTime lt) {
    return new Struct(DateTimeSchemas.LOCALTIME_SCHEMA)
        .put(DateTimeSchemas.LOCALTIME_SCHEMA.field("HOUR_FIELD"),lt.getHour())
        .put(DateTimeSchemas.LOCALTIME_SCHEMA.field("MINUTE_FIELD"),lt.getMinute())
        .put(DateTimeSchemas.LOCALTIME_SCHEMA.field("SECOND_FIELD"),lt.getSecond())
        .put(DateTimeSchemas.LOCALTIME_SCHEMA.field("NANO_FIELD"),lt.getNano());
  }

  public static LocalTime fromLocalTimeStruct(Struct s) {
    return LocalTime.of(
        s.getInt32("HOUR_FIELD"),
        s.getInt32("MINUTE_FIELD"),
        s.getInt32("SECOND_FIELD"),
        s.getInt32("NANO_FIELD")
    );
  }

  public static Struct toLocalDateTimeStruct(LocalDateTime ldt) {
    return new Struct(DateTimeSchemas.LOCALDATETIME_SCHEMA)
        .put(DateTimeSchemas.LOCALDATETIME_SCHEMA.field("LOCALDATE_FIELD"),toLocalDateStruct(ldt.toLocalDate()))
        .put(DateTimeSchemas.LOCALDATETIME_SCHEMA.field("LOCALTIME_FIELD"),toLocalTimeStruct(ldt.toLocalTime()));

  }

  public static LocalDateTime fromLocalDateTimeStruct(Struct s) {
    return LocalDateTime.of(
        fromLocalDateStruct(s.getStruct("LOCALDATE_FIELD")),
        fromLocalTimeStruct(s.getStruct("LOCALTIME_FIELD"))
    );
  }

}
