package org.deliveroo.util;

import lombok.AllArgsConstructor;
import org.deliveroo.exceptions.InvalidFieldValueException;
import org.deliveroo.parser.wildcardparser.WildCardParser;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


@AllArgsConstructor
public class DefaultCronFieldExpander implements CronFieldExpander {
    Map<Predicate<String>, Function<String, WildCardParser.WildCardParserBuilder>> handlers;
    public  List<String> expandField(String field, int min, int max) {
        List<String> values = handlers.entrySet().stream()
                .filter(entry -> entry.getKey().test(field))
                .findFirst()
                .map(entry -> {
                    try {
                        return entry.getValue().apply(field).field(field).min(min).max(max).build().getResult();
                    } catch (InvalidFieldValueException e) {
                        throw new RuntimeException(e);
                    }
                }).orElse(new ArrayList<>());
        return values;
    }


}

