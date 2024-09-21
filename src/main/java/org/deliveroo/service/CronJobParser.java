package org.deliveroo.service;

import lombok.Builder;
import org.deliveroo.enums.CronFieldType;
import org.deliveroo.exceptions.InvalidCronExpressionException;
import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.factory.CronFieldParserFactory;
import org.deliveroo.parser.fieldparser.CronFieldParser;
import org.deliveroo.printer.CronJobFieldOutputPrinter;
import org.deliveroo.util.CronFieldExpander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Builder
public class CronJobParser {
    private List<CronFieldType> typeOfFields;
    private static final String SEPARATOR = "\\s+";
    private CronJobFieldOutputPrinter cronJobOutputPrinter;
    CronFieldExpander cronFieldExpander;

    private boolean isValidCronJobExpression(String[] fields){
        return fields.length == getValidExpressionSize() || fields.length == getValidExpressionSize()+1;
    }

    private int getValidExpressionSize(){
        return typeOfFields.size();
    }

    public String evaluateCronJobExpression(String cronExpression) throws InvalidCronExpressionException, InvalidFieldValueException {
        StringBuilder expressionResult  = new StringBuilder();
        String[] fields = createFields(cronExpression);
        if(!isValidCronJobExpression(fields)){
            throw new InvalidCronExpressionException(String.format("%d arguments expected", typeOfFields.size()));
        }
        for (int i = 0;i< typeOfFields.size();i++) {
            CronFieldParser parser = CronFieldParserFactory.getParser(typeOfFields.get(i), cronFieldExpander);
            List<String> parsedValues = parser.parse(fields[i]);
            expressionResult.append(cronJobOutputPrinter.print(typeOfFields.get(i).name().toLowerCase().replace("_", " "), parsedValues));
        }
        return expressionResult.toString();
    }

    private String[] createFields(String cronExpression) {
        String[] fields = cronExpression.split(SEPARATOR);
        if(fields.length > 6) {
            fields[5] = fields[5] + " "+fields[6];
        }
        return Arrays.copyOf(fields, 6);
    }
}
