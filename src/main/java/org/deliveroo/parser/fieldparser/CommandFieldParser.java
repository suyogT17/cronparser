package org.deliveroo.parser.fieldparser;

import org.deliveroo.util.CronFieldExpander;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandFieldParser extends  CronFieldParser{
    public CommandFieldParser(CronFieldExpander cronFieldExpander) {
        super(cronFieldExpander, 0, 0);
    }
    @Override
    public List<String> parse(String field) {
        return Arrays.asList(field);
    }
}
