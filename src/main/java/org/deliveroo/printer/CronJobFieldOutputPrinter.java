package org.deliveroo.printer;

import java.util.List;

public interface CronJobFieldOutputPrinter {
    String print(String fieldName, List<String> values);
}
