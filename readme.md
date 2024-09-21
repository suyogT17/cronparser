# CronParser
## Usage
```bash
Usage: java CronParserCmd [options]
Cron Field Usage:
  MINUTE:         Specifies the minute (0-59).
  HOUR:           Specifies the hour (0-23).
  DAY_OF_MONTH:   Specifies the day of the month (1-31).
  MONTH:          Specifies the month (1-12).
  DAY_OF_WEEK:    Specifies the day of the week (0-7) (Sunday=0 or 7).
  COMMAND:        Specifies the command to be executed.
```
## Run Application
1. go to <project_path>/cronparser/target
3. Run the application using the following command:

```bash
java -jar cronparser-1.0-SNAPSHOT.jar [options]
```

## Example
### Command
```
java -jar cronparser-1.0-SNAPSHOT.jar "*/15 0 11 * 1-5 /usr/bin/find"
```
### Output
```
minute        0 15 30 45
hour          0
day of month  11
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

