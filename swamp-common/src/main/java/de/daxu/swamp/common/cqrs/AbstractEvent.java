package de.daxu.swamp.common.cqrs;

public abstract class AbstractEvent<AGGREGATE_ID extends AggregateId> implements Event {

    private AGGREGATE_ID aggregateId;

    private AbstractEvent() {
    }

    public AbstractEvent( AGGREGATE_ID aggregateId ) {
        this.aggregateId = aggregateId;
    }

    public AGGREGATE_ID getAggregateId() {
        return aggregateId;
    }
}
