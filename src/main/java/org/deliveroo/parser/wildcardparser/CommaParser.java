package org.deliveroo.parser.wildcardparser;

import lombok.experimental.SuperBuilder;
import org.deliveroo.exceptions.InvalidFieldValueException;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
public class CommaParser extends WildCardParser {

    @Override
    public List<String> getResult() throws InvalidFieldValueException {
        List<String> values = new ArrayList<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            values.addAll(parseSinglePart(part));
        }
        return values;
    }
}
