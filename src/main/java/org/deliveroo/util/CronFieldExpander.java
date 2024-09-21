package org.deliveroo.util;

import java.util.ArrayList;
import java.util.List;

public interface CronFieldExpander {
    List<String> expandField(String field, int min, int max);
}
