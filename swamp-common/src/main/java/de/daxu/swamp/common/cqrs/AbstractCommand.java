package de.daxu.swamp.common.cqrs;

public abstract class AbstractCommand<AGGREGATE_ID extends AggregateId> {

    private AGGREGATE_ID aggregateId;

    private AbstractCommand() {
    }

    public AbstractCommand( AGGREGATE_ID aggregateId ) {
        this.aggregateId = aggregateId;
    }

    public AGGREGATE_ID getAggregateId() {
        return aggregateId;
    }
}
