package org.deliveroo;

import org.deliveroo.enums.CronFieldType;
import org.deliveroo.exceptions.InvalidCronExpressionException;
import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.parser.wildcardparser.*;
import org.deliveroo.printer.CustomFieldOutputPrinter;
import org.deliveroo.service.CronJobParser;
import org.deliveroo.util.CronFieldExpander;
import org.deliveroo.util.DefaultCronFieldExpander;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class CronParserCmdTest {
    private CronJobParser cronJobParser;

    @Before
    public void setup(){
        Map<Predicate<String>, Function<String, WildCardParser.WildCardParserBuilder>> handlers = new LinkedHashMap<>();
        handlers.put("*"::equals, f -> AstricParser.builder().step(1));
        handlers.put(f -> f.contains("/"), f -> SlashParser.builder());
        handlers.put(f -> f.contains(","), f -> CommaParser.builder());
        handlers.put(f -> true, f -> DefaultParser.builder());

        CronFieldExpander cronFieldExpander = new DefaultCronFieldExpander(handlers);
        List<CronFieldType> cronFieldTypes = Arrays.asList(
                CronFieldType.MINUTE,
                CronFieldType.HOUR,
                CronFieldType.DAY_OF_MONTH,
                CronFieldType.MONTH,
                CronFieldType.DAY_OF_WEEK,
                CronFieldType.COMMAND
        );
        cronJobParser = CronJobParser.builder().typeOfFields(cronFieldTypes)
                .cronJobOutputPrinter(new CustomFieldOutputPrinter()).cronFieldExpander(cronFieldExpander)
                .build();
    }

    @Test
    public void testCronJobSuccessExpression() throws InvalidCronExpressionException, InvalidFieldValueException {
        String cronExpression = "15 0 11 * 1-5 /usr/bin/find";
        String expected ="minute        15\n" +
                "hour          0\n" +
                "day of month  11\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5\n" +
                "command       /usr/bin/find\n";
        String result = cronJobParser.evaluateCronJobExpression(cronExpression);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCronJobFailureInvalidCronjob() {
        String cronExpression = "15 0 11 * /usr/bin/find";
        Assert.assertThrows(InvalidCronExpressionException.class, () -> cronJobParser.evaluateCronJobExpression(cronExpression));
    }

    @Test
    public void testCronJobFailureInvalidFieldValue() throws InvalidCronExpressionException, InvalidFieldValueException {
        String cronExpression = "15 24 11 * 1 /usr/bin/find"; // hour value is invalid valid value 0-24
        Assert.assertThrows(InvalidFieldValueException.class, () -> cronJobParser.evaluateCronJobExpression(cronExpression));
    }

    @Test
    public void testCronJobWithArgSuccess() throws InvalidFieldValueException, InvalidCronExpressionException {
        String cronExpression = "11 23 11 * 1 /usr/bin/find -f";
        String expected ="minute        11\n" +
                "hour          23\n" +
                "day of month  11\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5\n" +
                "command       /usr/bin/find -f\n";

        String result = cronJobParser.evaluateCronJobExpression(cronExpression);

        Assert.assertEquals(expected, result);
    }

}
