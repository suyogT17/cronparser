package org.deliveroo.parser.wildcardparser;

import lombok.experimental.SuperBuilder;
import org.deliveroo.exceptions.InvalidFieldValueException;

import java.util.List;

@SuperBuilder
public class DefaultParser extends WildCardParser {
    @Override
    public List<String> getResult() throws InvalidFieldValueException {
        return parseSinglePart(field);
    }
}
