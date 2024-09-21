package org.deliveroo;

import org.deliveroo.enums.CronFieldType;
import org.deliveroo.parser.wildcardparser.*;
import org.deliveroo.printer.CustomFieldOutputPrinter;
import org.deliveroo.service.CronJobParser;
import org.deliveroo.util.CronFieldExpander;
import org.deliveroo.util.DefaultCronFieldExpander;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class CronParserCmd {

    public static void main(String[] args) throws Exception{
        if(args ==null || args.length == 0){
            System.out.println("Usage: java -jar cronparser-1.0-SNAPSHOT.jar <Cron-Expression> \nCron Expression Field Usage:\n" +
                    "  MINUTE:         Specifies the minute (0-59).\n" +
                    "  HOUR:           Specifies the hour (0-23).\n" +
                    "  DAY_OF_MONTH:   Specifies the day of the month (0-31).\n" +
                    "  MONTH:          Specifies the month (1-12).\n" +
                    "  DAY_OF_WEEK:    Specifies the day of the week (1-7).\n" +
                    "  COMMAND:        Specifies the command to be executed.");
            return;
        }
        String cronExpression = args[0];
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
        CronJobParser cronJobParserCmd = CronJobParser.builder().typeOfFields(cronFieldTypes)
                .cronJobOutputPrinter(new CustomFieldOutputPrinter()).cronFieldExpander(cronFieldExpander)
                .build();
        System.out.println(cronJobParserCmd.evaluateCronJobExpression(cronExpression));
    }

}
