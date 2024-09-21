package org.deliveroo.parser.wildcardparser;

import lombok.experimental.SuperBuilder;
import org.deliveroo.exceptions.InvalidFieldValueException;

import java.util.List;

@SuperBuilder
public class SlashParser extends WildCardParser {
    WildCardParser wildCardParser;
    @Override
    public List<String> getResult() throws InvalidFieldValueException {
        try {
            String[] parts = field.split("/");
            String rangePart = parts[0];
            int steps = Integer.parseInt(parts[1]);
            int rangeMin = min;
            int rangeMax = max;

            if (!rangePart.equals("*")) {
                String[] range = rangePart.split("-");
                rangeMin = Integer.parseInt(range[0]);
                rangeMax = Integer.parseInt(range[1]);
            }
            AstricParser astricParser = AstricParser.builder()
                    .field(field)
                    .min(rangeMin)
                    .max(rangeMax)
                    .step(steps)
                    .build();
            return astricParser.getResult();
        }catch (Exception e){
            throw new InvalidFieldValueException(String.format("%s is invalid field value", field));
        }
    }
}
