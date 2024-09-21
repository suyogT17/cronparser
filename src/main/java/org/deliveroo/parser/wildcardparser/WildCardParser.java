package org.deliveroo.parser.wildcardparser;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.deliveroo.exceptions.InvalidFieldValueException;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
public abstract class WildCardParser {
    String field;
    int min;
    int max;
    int step;

    public abstract List<String> getResult() throws InvalidFieldValueException;
    public List<String> parseSinglePart(String part) throws InvalidFieldValueException {
        try {
            List<String> values = new ArrayList<>();
            if (part.contains("-")) {
                String[] range = part.split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int i = start; i <= end; i++) {
                    values.add(String.valueOf(i));
                }
            } else {
                if (checkInteger(part)) {
                    values.add(part);
                }
            }
            return values;
        }catch (Exception e){
            throw new InvalidFieldValueException(String.format("%s is invalid field value", part));

        }
    }

    public boolean checkInteger(String value) throws InvalidFieldValueException {
        try{
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException exception){
            throw new InvalidFieldValueException(String.format("%s is invalid field value", value));
        }
    }
}
