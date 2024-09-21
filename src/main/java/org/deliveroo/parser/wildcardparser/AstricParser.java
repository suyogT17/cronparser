package org.deliveroo.parser.wildcardparser;

import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
public class AstricParser extends WildCardParser {
    @Override
    public List<String> getResult() {
        List<String> values = new ArrayList<>();
        for (int i = min; i <= max; i+=step) {
            values.add(String.valueOf(i));
        }
        return values;
    }
}
