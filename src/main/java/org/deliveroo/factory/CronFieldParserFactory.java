package org.deliveroo.factory;

import org.deliveroo.enums.CronFieldType;
import org.deliveroo.parser.HourFieldParser;
import org.deliveroo.parser.MinuteFieldParser;
import org.deliveroo.parser.fieldparser.CommandFieldParser;
import org.deliveroo.parser.fieldparser.CronFieldParser;
import org.deliveroo.parser.fieldparser.DayOfMonthFieldParser;
import org.deliveroo.parser.fieldparser.DayOfWeekFieldParser;
import org.deliveroo.util.CronFieldExpander;

public class CronFieldParserFactory {
    public static CronFieldParser getParser(CronFieldType type, CronFieldExpander cronFieldExpander) {
        switch (type) {
            case MINUTE:
                return new MinuteFieldParser(cronFieldExpander);
            case HOUR:
                return new HourFieldParser(cronFieldExpander);
            case DAY_OF_MONTH:
                return new DayOfMonthFieldParser(cronFieldExpander);
            case MONTH:
                return new org.deliveroo.parser.MonthFieldParser(cronFieldExpander);
            case DAY_OF_WEEK:
                return new DayOfWeekFieldParser(cronFieldExpander);
            case COMMAND:
                return new CommandFieldParser(cronFieldExpander);
            default:
                throw new IllegalArgumentException("Invalid cron field type");
        }
    }
}
