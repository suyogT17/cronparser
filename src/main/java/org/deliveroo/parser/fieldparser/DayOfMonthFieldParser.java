package org.deliveroo.parser.fieldparser;

import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.util.CronFieldExpander;

import java.util.List;

public class DayOfMonthFieldParser extends CronFieldParser {
    public DayOfMonthFieldParser(CronFieldExpander cronFieldExpander) {
        super(cronFieldExpander, 0, 30);
    }

    @Override
    public List<String> parse(String field) throws InvalidFieldValueException {
        List<String> values = cronFieldExpander.expandField(field, min, max);
        if(!validate(values)){
            throw  new InvalidFieldValueException(String.format("%s is invalid field value", values));
        }
        return values;
    }
}