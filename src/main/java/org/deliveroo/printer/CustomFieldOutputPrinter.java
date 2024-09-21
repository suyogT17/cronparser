package org.deliveroo.printer;

import java.util.List;

public class CustomFieldOutputPrinter implements CronJobFieldOutputPrinter{
    @Override
    public String print(String fieldName, List<String> values) {
        return String.format("%-14s%s\n", fieldName, String.join(" ", values.stream().toArray(String[]::new)));
    }
}
