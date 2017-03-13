package de.daxu.swamp.test.comparator;

import de.daxu.swamp.common.jpa.Identifiable;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.util.Comparator;

public class TestComparators {

    public static Comparator<Identifiable> ignoreId() {
        return (o1, o2) -> {
            String[] excludedFields = {"id"};
            return CompareToBuilder.reflectionCompare(o1, o2, excludedFields);
        };
    }

}
