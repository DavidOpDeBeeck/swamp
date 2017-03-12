package de.daxu.swamp.common.cqrs;

import java.io.Serializable;

public interface AggregateId extends Serializable {

    String getValue();

    default String value() {
        return getValue();
    }
}
