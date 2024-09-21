package org.deliveroo.parser;

import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.parser.fieldparser.CronFieldParser;
import org.deliveroo.util.CronFieldExpander;

import java.util.List;

public class MinuteFieldParser extends CronFieldParser {
    public MinuteFieldParser(CronFieldExpander cronFieldExpander) {
        super(cronFieldExpander, 0, 59);
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
