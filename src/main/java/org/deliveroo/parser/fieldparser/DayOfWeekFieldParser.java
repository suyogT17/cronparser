package org.deliveroo.parser.fieldparser;

import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.parser.fieldparser.CronFieldParser;
import org.deliveroo.util.CronFieldExpander;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOfWeekFieldParser extends CronFieldParser {
    private Map<String, Integer> daysToNumberMap;
    public DayOfWeekFieldParser(CronFieldExpander cronFieldExpander) {
        super(cronFieldExpander, 0, 6);
        daysToNumberMap = new HashMap<>();
        daysToNumberMap.put("SUN", 0);
        daysToNumberMap.put("MON", 1);
        daysToNumberMap.put("TUE", 2);
        daysToNumberMap.put("WED", 3);
        daysToNumberMap.put("THU", 4);
        daysToNumberMap.put("FRI", 5);
        daysToNumberMap.put("SAT", 6);
    }

    @Override
    public List<String> parse(String field) throws InvalidFieldValueException {
        field = parseFieldIfString(field);
        List<String> values = cronFieldExpander.expandField(field, min, max);
        if(!validate(values)){
            throw  new InvalidFieldValueException(String.format("%s is invalid field value", values));
        }
        return values;
    }

    private String parseFieldIfString(String field) {
        for(String days: daysToNumberMap.keySet()){
            field = field.replaceAll(days, String.valueOf(daysToNumberMap.get(days)));
        }
        return field;
    }
}