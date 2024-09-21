package org.deliveroo.parser.fieldparser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.util.CronFieldExpander;

import java.util.List;

@AllArgsConstructor
@Getter
public abstract class CronFieldParser {
    protected CronFieldExpander cronFieldExpander;
    protected int min;
    protected int max;
    public abstract List<String> parse(String field) throws InvalidFieldValueException;
    public boolean validate(List<String> values){
        return values.stream().map(Integer::parseInt).allMatch(value -> value >= min && value <= max);
    }
}
