# Date & Time Functions for ksqlDB

This project provides custom [ksqlDB](https://ksqldb.io/) user-defined functions ([UDFs](https://docs.ksqldb.io/en/latest/developer-guide/ksqldb-reference/functions/)) which offer convenient date & time related functionality based on custom ksqlDB [STRUCT types](https://docs.ksqldb.io/en/latest/developer-guide/syntax-reference/#struct). These types are defined on top of the most important Java 11+ [Time API](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/package-summary.html) classes. Currently the following JDK-related classes with their corresponding STRUCT types are supported:

* **[Instant](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Instant.html)**: STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INTEGER>
* **[Duration](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Duration.html)**: STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INTEGER>
* **[Period](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Period.html)**: STRUCT<YEARS_FIELD INTEGER, MONTHS_FIELD INTEGER, DAYS_FIELD INTEGER>;
* **[LocalDate](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDate.html)**: STRUCT<YEAR_FIELD INTEGER, MONTH_FIELD INTEGER, DAY_FIELD INTEGER>
* **[LocalTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalTime.html)**: STRUCT<HOUR_FIELD INTEGER, MINUTE_FIELD INTEGER, SECOND_FIELD INTEGER, NANO_FIELD INTEGER>
* **[LocalDateTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html)**: STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INTEGER, MONTH_FIELD INTEGER, DAY_FIELD INTEGER>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INTEGER, MINUTE_FIELD INTEGER, SECOND_FIELD INTEGER, NANO_FIELD INTEGER>>

All processing and calculations are done based on the custom ksqlDB STRUCT types.

NOTE: further types, first and foremost, _OffsetTime, OffsetDateTime, ZonedDateTime_ will get introduced in future versions of this project!

## **Available Date & Time Functions**

The following UDFs expose a core subset of the available methods for each the supported underlying JDK Time API classes. They are named and grouped according to the types they are referring to.

### **Instant related UDFs**

#### DT_INSTANT(...) factory function to create Instant STRUCTS

```
Name        : DT_INSTANT
Version     : 0.1.0
Overview    : Factory functions for Instant struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_INSTANT(seconds BIGINT, nanoAdjustment BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create an Instant struct based on seconds since the epoch with(out) nano seconds adjustment
	seconds     : the seconds since the epoch
	nanoAdjustment: the nano seconds part

	Variation   : DT_INSTANT()
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create an Instant struct at the current local date time (default system UTC clock)

	Variation   : DT_INSTANT(millis BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create an Instant struct based on millis since the epoch
	millis      : the millis since the epoch

	Variation   : DT_INSTANT(text VARCHAR)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create an Instant struct from its string representation using the java.time.format.DateTimeFormatter#ISO_INSTANT format
	text        : the string representation of the Instant following java.time.format.DateTimeFormatter#ISO_INSTANT e.g. 2020-07-24T20:07:24.00Z
```

#### DT_INSTANT_CHRONOLOGY(...) method to check if an Instant is either before, after or equal to another Instant

```
Name        : DT_INSTANT_CHRONOLOGY
Version     : 0.1.0
Overview    : Chronology check of instants
Type        : SCALAR
Variations  : 

	Variation   : DT_INSTANT_CHRONOLOGY(baseInstant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, instant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, chronologyMode VARCHAR)
	Returns     : BOOLEAN
	Description : Check if an instant is either before, after or equal to another instant
	baseInstant : the instant to check against
	instant     : the instant to check whether it's before, after or equal
	chronologyMode: the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'
```

#### DT_INSTANT_MINUS(...) method to subtract a Duration from an Instant

```
Name        : DT_INSTANT_MINUS
Version     : 0.1.0
Overview    : Subtract from instants
Type        : SCALAR
Variations  : 

	Variation   : DT_INSTANT_MINUS(instant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, seconds BIGINT, nanos BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Subtract seconds and nanos from an instant
	instant     : the instant to subtract from
	seconds     : the second part to subtract
	nanos       : the nano part to subtract

	Variation   : DT_INSTANT_MINUS(instant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Subtract a duration from an instant
	instant     : the instant to subtract from
	duration    : the duration to subtract
```

#### DT_INSTANT_PLUS(...) method to add a Duration to an Instant

```
Name        : DT_INSTANT_PLUS
Version     : 0.1.0
Overview    : Add to instants
Type        : SCALAR
Variations  : 

	Variation   : DT_INSTANT_PLUS(baseInstant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, seconds BIGINT, nanos BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Add seconds and nanos to an instant
	baseInstant : the instant to add to
	seconds     : the second part to add
	nanos       : the nano part to add

	Variation   : DT_INSTANT_PLUS(baseInstant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Add a duration to an instant
	baseInstant : the instant to add to
	duration    : the duration to add
```

#### DT_INSTANT_STRINGIFY(...) method to create a human-readable string representation of an Instant using ISO-8601 format  

```
Name        : DT_INSTANT_STRINGIFY
Version     : 0.1.0
Overview    : Create a default formatted string representation of an Instant
Type        : SCALAR
Variations  : 

	Variation   : DT_INSTANT_STRINGIFY(instant STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : VARCHAR
	Description : Create a default formatted string representation of an Instant using ISO-8601 representation (java.time.format.DateTimeFormatter#ISO_INSTANT) at zone offset +00:00 i.e. UTC
	instant     : the Instant struct to stringify
```

### **LocalDate related UDFs**

#### DT_LOCALDATE(...) factory functions to create LocalDate STRUCTs

```
Name        : DT_LOCALDATE
Version     : 0.1.0
Overview    : Factory functions for LocalDate struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATE(text VARCHAR, format VARCHAR)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Create a LocalDate struct from its string representation using the specified java.time.format.DateTimeFormatter format string
	text        : the string representation of the LocalDate
	format      : the specified java.time.format.DateTimeFormatter format string

	Variation   : DT_LOCALDATE()
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Create a LocalDate struct based on the current date (system clock in the default time-zone)

	Variation   : DT_LOCALDATE(year INT, month INT, day INT)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Create a LocalDate struct from its components (year, month, day)
	year        : the year part of the LocalDate
	month       : the month part of the LocalDate
	day         : the day part of the LocalDate

	Variation   : DT_LOCALDATE(epochDays BIGINT)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Create a LocalDate struct from a UNIX date given as the number of days since the epoch
	epochDays   : the number of days since the epoch

	Variation   : DT_LOCALDATE(text VARCHAR)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Create a LocalDate struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_DATE
	text        : the string representation of the LocalDate
```

#### DT_LOCALDATE_CHRONOLOGY(...)  method to check if a LocalDate is either before, after or equal to another LocalDate

```
Name        : DT_LOCALDATE_CHRONOLOGY
Version     : 0.1.0
Overview    : Chronology check of local dates
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATE_CHRONOLOGY(baseLocalDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, localDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, chronologyMode VARCHAR)
	Returns     : BOOLEAN
	Description : Check if a local date is either before, after or equal to another local date
	baseLocalDate: the local date to check against
	localDate   : the local date to check whether it's before, after or equal
	chronologyMode: the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'
```

#### DT_LOCALDATE_FORMAT(...) method to create a string representation of the LocalDate 

```
Name        : DT_LOCALDATE_FORMAT
Version     : 0.1.0
Overview    : Create a string representation of the LocalDate struct
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATE_FORMAT(localDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, format VARCHAR)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalDate struct using the specified java.time.format.DateTimeFormatter format string
	localDate   : the LocalDate struct to create a string representation for
	format      : the java.time.format.DateTimeFormatter format string

	Variation   : DT_LOCALDATE_FORMAT(localDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalDate struct using the java.time.format.DateTimeFormatter#ISO_LOCAL_DATE format
	localDate   : the LocalDate struct to create a string representation for
```

#### DT_LOCALDATE_MINUS(...) method to subtract a Period or separate date components from a LocalDate

```
Name        : DT_LOCALDATE_MINUS
Version     : 0.1.0
Overview    : Subtract from local dates
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATE_MINUS(baseLocalDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, period STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Subtract a period from a local date
	baseLocalDate: the local date to subtract from
	period      : the period to subtract

	Variation   : DT_LOCALDATE_MINUS(baseLocalDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, minusYears INT, minusMonths INT, minusDays INT)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Subtract years, months and days from a local date
	baseLocalDate: the local date to subtract from
	minusYears  : the year part to subtract
	minusMonths : the month part to subtract
	minusDays   : the day part to subtract
```

#### DT_LOCALDATE_PLUS(...) method to add a Period or separate date components to a LocalDate

```
Name        : DT_LOCALDATE_PLUS
Version     : 0.1.0
Overview    : Add to local dates
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATE_PLUS(baseLocalDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, period STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Add a period to a local date
	baseLocalDate: the local date to add to
	period      : the period to add

	Variation   : DT_LOCALDATE_PLUS(baseLocalDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, addYears INT, addMonths INT, addDays INT)
	Returns     : STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>
	Description : Add years, months and days to a local date
	baseLocalDate: the local date to add to
	addYears    : the year part to add
	addMonths   : the month part to add
	addDays     : the day part to add
```

### **LocalTime related UDFs**

#### DT_LOCALTIME(...) factory functions to create LocalTime STRUCTs

```
Name        : DT_LOCALTIME
Version     : 0.1.0
Overview    : Factory functions for LocalTime struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALTIME(text VARCHAR, format VARCHAR)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Create a LocalTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string
	text        : the string representation of the LocalTime
	format      : the specified java.time.format.DateTimeFormatter format string

	Variation   : DT_LOCALTIME()
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Create a LocalTime struct based on the current time (system clock in the default time-zone)

	Variation   : DT_LOCALTIME(hour INT, month INT, second INT)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Create a LocalTime struct from its components (hour, minute, second, nano=0)
	hour        : the hour part of the LocalTime
	month       : the minute part of the LocalTime
	second      : the second part of the LocalTime

	Variation   : DT_LOCALTIME(text VARCHAR)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Create a LocalTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_TIME
	text        : the string representation of the LocalTime

	Variation   : DT_LOCALTIME(hour INT, month INT, second INT, nano INT)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Create a LocalTime struct from its components (hour, minute, second, nano)
	hour        : the hour part of the LocalTime
	month       : the minute part of the LocalTime
	second      : the second part of the LocalTime
	nano        : the nano part of the LocalTime
```

#### DT_LOCALTIME_CHRONOLOGY(...) method to to check if a LocalTime is either before, after or equal to another LocalTime
                                             
```
Name        : DT_LOCALTIME_CHRONOLOGY
Version     : 0.1.0
Overview    : Chronology check of local times
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALTIME_CHRONOLOGY(baseLocalTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, localTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, chronologyMode VARCHAR)
	Returns     : BOOLEAN
	Description : Check if a local time is either before, after or equal to another local time
	baseLocalTime: the local time to check against
	localTime   : the local time to check whether it's before, after or equal
	chronologyMode: the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'
```

#### DT_LOCALTIME_FORMAT(...) method to create a string representation of a LocalTime

```
Name        : DT_LOCALTIME_FORMAT
Version     : 0.1.0
Overview    : Create a string representation of the LocalTime struct
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALTIME_FORMAT(localTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalTime struct using the java.time.format.DateTimeFormatter#ISO_LOCAL_TIME format
	localTime   : the LocalTime struct to create a string representation for

	Variation   : DT_LOCALTIME_FORMAT(localTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, format VARCHAR)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalTime struct using the specified java.time.format.DateTimeFormatter format string
	localTime   : the LocalTime struct to create a string representation for
	format      : the java.time.format.DateTimeFormatter format string
```

#### DT_LOCALTIME_MINUS(...) method to subtract a Duration or separate time components from a LocalTime

```
Name        : DT_LOCALTIME_MINUS
Version     : 0.1.0
Overview    : Add to local times
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALTIME_MINUS(baseLocalTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Subtract a duration from a local time
	baseLocalTime: the local time to subtract from
	duration    : the duration to subtract

	Variation   : DT_LOCALTIME_MINUS(baseLocalTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, addHours INT, addMinutes INT, addSeconds INT, addNanos INT)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Subtract hours, minutes, seconds and nanos from a local time
	baseLocalTime: the local time to subtract from
	addHours    : the hour part to subtract
	addMinutes  : the minute part to subtract
	addSeconds  : the second part to subtract
	addNanos    : the nano part to subtract
```

#### DT_LOCALTIME_PLUS(...) method to add a Duration or separate time components to a LocalTime

```
Name        : DT_LOCALTIME_PLUS
Version     : 0.1.0
Overview    : Add to local times
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALTIME_PLUS(baseLocalTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Add a duration to a local time
	baseLocalTime: the local time to add to
	duration    : the duration to add

	Variation   : DT_LOCALTIME_PLUS(baseLocalTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, addHours INT, addMinutes INT, addSeconds INT, addNanos INT)
	Returns     : STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>
	Description : Add hours, minutes, seconds and nanos to a local time
	baseLocalTime: the local time to add to
	addHours    : the hour part to add
	addMinutes  : the minute part to add
	addSeconds  : the second part to add
	addNanos    : the nano part to add
```

### **LocalDateTime related UDFs**

#### DT_LOCALDATETIME(...) factory functions to create LocalDateTime STRUCTs

```
Name        : DT_LOCALDATETIME
Version     : 0.1.0
Overview    : Factory functions for LocalDateTime struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATETIME(text VARCHAR, format VARCHAR)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime struct from its string representation using the specified java.time.format.DateTimeFormatter format string
	text        : the string representation of the LocalDateTime
	format      : the specified java.time.format.DateTimeFormatter format string

	Variation   : DT_LOCALDATETIME()
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime struct based on the current datetime (system clock in the default time-zone)

	Variation   : DT_LOCALDATETIME(epochMillis BIGINT)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime from a UNIX timestamp given as the number of millis since the epoch (zone offset +00:00 i.e. UTC)
	epochMillis : the number of millis since the epoch

	Variation   : DT_LOCALDATETIME(year INT, month INT, day INT, hour INT, month INT, second INT, nano INT)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime struct from its components (year, month, day, hour, minute, second, nano)
	year        : the year part of the LocalDateTime
	month       : the month part of the LocalDateTime
	day         : the day part of the LocalDateTime
	hour        : the hour part of the LocalDateTime
	month       : the minute part of the LocalDateTime
	second      : the second part of the LocalDateTime
	nano        : the nano part of the LocalDateTime

	Variation   : DT_LOCALDATETIME(text VARCHAR)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime struct from its string representation using java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME
	text        : the string representation of the LocalDateTime

	Variation   : DT_LOCALDATETIME(localDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, localTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Create a LocalDateTime struct based on a LocalDate struct and LocalTime struct
	localDate   : the local date part
	localTime   : the local time part
```

#### DT_LOCALDATETIME_CHRONOLOGY(...) method to check if a LocalDateTime is either before, after or equal to another LocalDateTime

```
Name        : DT_LOCALDATETIME_CHRONOLOGY
Version     : 0.1.0
Overview    : Chronology check of local datetimes
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATETIME_CHRONOLOGY(baseLocalDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, localDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, chronologyMode VARCHAR)
	Returns     : BOOLEAN
	Description : Check if a local datetime is either before, after or equal to another local datetime
	baseLocalDateTime: the local datetime to check against
	localDateTime: the local datetime to check whether it's before, after or equal
	chronologyMode: the chronologyMode being either: 'IS_BEFORE','IS_AFTER','IS_EQUAL'
```

#### DT_LOCALDATETIME_FORMAT(...) method to create a string representation of the LocalDateTime 

```
Name        : DT_LOCALDATETIME_FORMAT
Version     : 0.1.0
Overview    : Create a string representation of the LocalDateTime struct
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATETIME_FORMAT(localDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalDateTime struct using the java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME format
	localDateTime: the LocalDateTime struct to create a string representation for

	Variation   : DT_LOCALDATETIME_FORMAT(localDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, format VARCHAR)
	Returns     : VARCHAR
	Description : Create a string representation of the LocalDateTime struct using the specified java.time.format.DateTimeFormatter format string
	localDateTime: the LocalDateTime struct to create a string representation for
	format      : the java.time.format.DateTimeFormatter format string
```

#### DT_LOCALDATETIME_MINUS(...) method to subtract either a Period and/or Duration or separate date and/or time components from a LocalDateTime

```
Name        : DT_LOCALDATETIME_MINUS
Version     : 0.1.0
Overview    : Subtract from local datetimes
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATETIME_MINUS(baseLocalDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, subtractYears INT, subtractMonths INT, subtractDays INT, subtractHours INT, subtractMinutes INT, subtractSeconds INT, subtractNanos INT)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Subtract years, months, days, hours, minutes, seconds and nanos from a local datetime
	baseLocalDateTime: the local datetime to subtract from
	subtractYears: the year part to subtract
	subtractMonths: the month part to subtract
	subtractDays: the day part to subtract
	subtractHours: the hour part to subtract
	subtractMinutes: the minute part to subtract
	subtractSeconds: the second part to subtract
	subtractNanos: the nano part to subtract

	Variation   : DT_LOCALDATETIME_MINUS(baseLocalDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, period STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Subtract a period and/or duration from a local datetime
	baseLocalDateTime: the local datetime to subtract from
	period      : the period to subtract (use the empty/zero Period in case only a duration should be subtracted)
	duration    : the duration to subtract (use the empty/zero Duration in case only a period should be subtracted)
```

#### DT_LOCALDATETIME_PLUS(...) method to add either a Period and/or Duration or separate date and/or time components to a LocalDateTime

```
Name        : DT_LOCALDATETIME_PLUS
Version     : 0.1.0
Overview    : Add to local datetimes
Type        : SCALAR
Variations  : 

	Variation   : DT_LOCALDATETIME_PLUS(baseLocalDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, addYears INT, addMonths INT, addDays INT, addHours INT, addMinutes INT, addSeconds INT, addNanos INT)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Add years, months, days, hours, minutes, seconds and nanos to a local datetime
	baseLocalDateTime: the local datetime to add to
	addYears    : the year part to add
	addMonths   : the month part to add
	addDays     : the day part to add
	addHours    : the hour part to add
	addMinutes  : the minute part to add
	addSeconds  : the second part to add
	addNanos    : the nano part to add

	Variation   : DT_LOCALDATETIME_PLUS(baseLocalDateTime STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>, period STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<LOCALDATE_FIELD STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, LOCALTIME_FIELD STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>>
	Description : Add a period and/or duration to a local datetime
	baseLocalDateTime: the local datetime to add to
	period      : the period to add (use the empty/zero Period in case only a duration should be added)
	duration    : the duration to add (use the empty/zero Duration in case only a period should be added)
```

### **Duration related UDFs**

#### DT_DURATION(...) factory functions to create Duration STRUCTs

```
Name        : DT_DURATION
Version     : 0.1.0
Overview    : Factory functions for Duration struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION(seconds BIGINT, nanoAdjustment BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create a Duration struct based on seconds with nano seconds adjustment
	seconds     : the seconds part of the Duration
	nanoAdjustment: the nano seconds part of the Duration

	Variation   : DT_DURATION()
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create the empty/zero Duration struct

	Variation   : DT_DURATION(seconds BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create a Duration struct based on seconds without nano seconds adjustment
	seconds     : the seconds part of the Duration

	Variation   : DT_DURATION(text VARCHAR)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Create a Duration struct from its string representation using the ISO-8601 duration format {PnDTnHnMn.nS}
	text        : the string representation of the Duration
```

#### DT_DURATION_BETWEEN(...) method to calculate Durations between LocalTimes

```
Name        : DT_DURATION_BETWEEN
Version     : 0.1.0
Overview    : Calculate durations between LocalTime structs
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_BETWEEN(localTimeFrom STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>, localTimeTo STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Calculate the duration between localTimeFrom and localTimeTo, producing a duration result composed of seconds and optional nano seconds adjustment. If localTimeFrom is after localTimeTo the resulting duration is negative.
	localTimeFrom: localTime marking the duration's start
	localTimeTo : localTime marking the duration's end

	Variation   : DT_DURATION_BETWEEN(localTime STRUCT<HOUR_FIELD INT, MINUTE_FIELD INT, SECOND_FIELD INT, NANO_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Calculate the duration between the given local time and the current local time, producing a duration result composed of seconds and optional nano seconds adjustment. If the given local time is after the current local time the resulting duration is negative.
	localTime   : the given local time based on which the duration is calculated towards current local time
```

#### DT_DURATION_DIVIDE(...) method to divide a Duration

```
Name        : DT_DURATION_DIVIDE
Version     : 0.1.0
Overview    : Divide a duration
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_DIVIDE(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, divisor BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Divide a duration by a divisor
	baseDuration: the duration struct to divide
	divisor     : the divisor to divide by

	Variation   : DT_DURATION_DIVIDE(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, divisorDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : BIGINT
	Description : Divide a duration by another duration, thereby returning the number of whole times a divisor duration occurs within the base duration
	baseDuration: the duration struct to divide
	divisorDuration: the divisor duration to divide by
```

#### DT_DURATION_MINUS(...) method to subtract one ore more Durations from another Duration

```
Name        : DT_DURATION_MINUS
Version     : 0.1.0
Overview    : Subtract durations
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_MINUS(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, subtractDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Subtract a duration from another duration
	baseDuration: the duration struct to subtract from
	subtractDuration: the duration struct to subtract with

	Variation   : DT_DURATION_MINUS(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, subtractDurations ARRAY<STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Subtract multiple durations to from another duration
	baseDuration: the duration struct to subtract from
	subtractDurations: multiple duration structs to subtract with
```

#### DT_DURATION_MULTIPLY(...) method to multiply a duration by a scalar

```
Name        : DT_DURATION_MULTIPLY
Version     : 0.1.0
Overview    : Multiply a duration by a scalar
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_MULTIPLY(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, scalar BIGINT)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Multiply a duration by a scalar (i.e. all its components are multiplied by that scalar)
	baseDuration: the duration struct to multiply
	scalar      : the scalar to multiply by
```

#### DT_DURATION_PLUS(...) method to add one ore more Durations from another Duration

```
Name        : DT_DURATION_PLUS
Version     : 0.1.0
Overview    : Add durations
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_PLUS(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, addDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Add a duration to another duration
	baseDuration: the duration struct which to add to
	addDuration : the duration struct to add

	Variation   : DT_DURATION_PLUS(baseDuration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>, addDurations ARRAY<STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>>)
	Returns     : STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>
	Description : Add multiple durations to another duration
	baseDuration: the duration struct which to add to
	addDurations: multiple duration structs to add
```

#### DT_DURATION_STRINGIFY(...) method to create a string representation of a Duration

```
Name        : DT_DURATION_STRINGIFY
Version     : 0.1.0
Overview    : Create a (default) string representation of a Duration struct
Type        : SCALAR
Variations  : 

	Variation   : DT_DURATION_STRINGIFY(duration STRUCT<SECONDS_FIELD BIGINT, NANOS_FIELD INT>)
	Returns     : VARCHAR
	Description : Create a (default) string representation of a Duration struct using the ISO-8601 duration seconds format, such as {@code PT8H6M12.345S}
	duration    : the Duration struct to stringify

```

### **Period related UDFs**

#### DT_PERIOD(...) factory functions to create Period STRUCTs

```
Name        : DT_PERIOD
Version     : 0.1.0
Overview    : Factory functions for Period struct creation
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD()
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Create the empty/zero Period struct

	Variation   : DT_PERIOD(years INT, months INT, days INT)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Create a Period struct from its components (years, months, days)
	years       : the years part of the Period
	months      : the months part of the Period
	days        : the days part of the Period

	Variation   : DT_PERIOD(text VARCHAR)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Create a Period struct from its string representation using the ISO-8601 period formats {PnYnMnD} and {PnW}
	text        : the string representation of the Period
```

#### DT_PERIOD_BETWEEN(...) method to calculate Periods between LocalDates

```
Name        : DT_PERIOD_BETWEEN
Version     : 0.1.0
Overview    : Calculate periods between LocalDate structs
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_BETWEEN(localDateFrom STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>, localDateTo STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Calculate the period between localDateFrom and localDateTo, producing a period result composed of years, months and days.  If localDateFrom is after 
                localDateTo the period is negative.
	localDateFrom: local date marking the period's start
	localDateTo : local date marking the period's end

	Variation   : DT_PERIOD_BETWEEN(localDate STRUCT<YEAR_FIELD INT, MONTH_FIELD INT, DAY_FIELD INT>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Calculate the period between the given local date and the current local date, producing a period result composed of years, months and days. If the given 
                local date is in the future the period is negative.
	localDate   : given local date based on which the period is calculated towards current date
```

#### DT_PERIOD_MINUS(...) method to subtract one or more Periods from another Period

```
Name        : DT_PERIOD_MINUS
Version     : 0.1.0
Overview    : Subtract periods
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_MINUS(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, subtractPeriods ARRAY<STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Subtract multiple periods to from another period
	basePeriod  : the period struct to subtract from
	subtractPeriods: multiple period structs to subtract with

	Variation   : DT_PERIOD_MINUS(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, subtractPeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Subtract a period from another period
	basePeriod  : the period struct to subtract from
	subtractPeriod: the period struct to subtract with
```

#### DT_PERIOD_MULTIPLY(...) method to multiply a period by a scalar

```
Name        : DT_PERIOD_MULTIPLY
Version     : 0.1.0
Overview    : Multiply a period by a scalar
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_MULTIPLY(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, scalar INT)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Multiply a period by a scalar (i.e. all its components are multiplied by that scalar)
	basePeriod  : the period struct to multiply
	scalar      : the scalar to multiply by
```

#### DT_PERIOD_NORMALIZE(...) method to normalize a period

```
Name        : DT_PERIOD_NORMALIZE
Version     : 0.1.0
Overview    : Normalize a period
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_NORMALIZE(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Normalize a period which might affect years and/or months but leaves days unchanged
	basePeriod  : the period struct to normalize

```

#### DT_PERIOD_PLUS(...) method to add one or more Periods to another Period

```
Name        : DT_PERIOD_PLUS
Version     : 0.1.0
Overview    : Add periods
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_PLUS(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, addPeriods ARRAY<STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Add multiple periods to another period
	basePeriod  : the period struct to add to
	addPeriods  : multiple period structs to add

	Variation   : DT_PERIOD_PLUS(basePeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>, addPeriod STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>
	Description : Add a period to another period
	basePeriod  : the period struct to add to
	addPeriod   : the period struct to add
```

#### DT_PERIOD_STRINGIFY(...) method to create a string representation of a Period

```
Name        : DT_PERIOD_STRINGIFY
Version     : 0.1.0
Overview    : Create a (default) string representation of a Period struct
Type        : SCALAR
Variations  : 

	Variation   : DT_PERIOD_STRINGIFY(period STRUCT<YEARS_FIELD INT, MONTHS_FIELD INT, DAYS_FIELD INT>)
	Returns     : VARCHAR
	Description : Create a (default) string representation of a Period struct using the ISO-8601 period format, such as {P6Y3M1D}
	period      : the Period struct to stringify
```

## **Installation / Deployment**

1. You can either build the Maven project from sources or download the latest build as self-contained jar from [here](https://drive.google.com/file/d/1u0vpL_N6L-HnlrO0r02EDRJg6jAsbP55/view?usp=sharing).
2. Move the _datetime-functions-0.1.0.jar_ file into a folder of your ksqlDB installation that is configured to load custom functions from during server bootstrap.
3. (Re)Start your ksqlDB server instance(s) to make it pick up and load the date/time functions.
4. Verify if the deployment was successful by opening a ksqlDB CLI session and running SHOW FUNCTIONS; which should amongst all other available functions list the following date/time-related UDFs:

```
 Function Name               | Type      
--------------------------------------
 ...
 DT_DURATION                 | SCALAR    
 DT_DURATION_BETWEEN         | SCALAR    
 DT_DURATION_DIVIDE          | SCALAR    
 DT_DURATION_MINUS           | SCALAR    
 DT_DURATION_MULTIPLY        | SCALAR    
 DT_DURATION_PLUS            | SCALAR    
 DT_DURATION_STRINGIFY       | SCALAR    
 DT_INSTANT                  | SCALAR    
 DT_INSTANT_CHRONOLOGY       | SCALAR    
 DT_INSTANT_MINUS            | SCALAR    
 DT_INSTANT_PLUS             | SCALAR    
 DT_INSTANT_STRINGIFY        | SCALAR    
 DT_LOCALDATE                | SCALAR    
 DT_LOCALDATETIME            | SCALAR    
 DT_LOCALDATETIME_CHRONOLOGY | SCALAR    
 DT_LOCALDATETIME_FORMAT     | SCALAR    
 DT_LOCALDATETIME_MINUS      | SCALAR    
 DT_LOCALDATETIME_PLUS       | SCALAR    
 DT_LOCALDATE_CHRONOLOGY     | SCALAR    
 DT_LOCALDATE_FORMAT         | SCALAR    
 DT_LOCALDATE_MINUS          | SCALAR    
 DT_LOCALDATE_PLUS           | SCALAR    
 DT_LOCALTIME                | SCALAR    
 DT_LOCALTIME_CHRONOLOGY     | SCALAR    
 DT_LOCALTIME_FORMAT         | SCALAR    
 DT_LOCALTIME_MINUS          | SCALAR    
 DT_LOCALTIME_PLUS           | SCALAR    
 DT_PERIOD                   | SCALAR    
 DT_PERIOD_BETWEEN           | SCALAR    
 DT_PERIOD_MINUS             | SCALAR    
 DT_PERIOD_MULTIPLY          | SCALAR    
 DT_PERIOD_NORMALIZE         | SCALAR    
 DT_PERIOD_PLUS              | SCALAR    
 DT_PERIOD_STRINGIFY         | SCALAR
 ... 
--------------------------------------
```

### **HAVE FUN working with 🗓 date & time 🕑 in 🚀ksqlDB🚀**
