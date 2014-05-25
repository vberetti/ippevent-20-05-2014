package fr.ippon.ippevent.nosqlha.cassandra.configuration;

import com.datastax.driver.core.ConsistencyLevel;

public enum ReadConfiguration {

    ALL(ConsistencyLevel.ALL), //
    ONE(ConsistencyLevel.ONE), //
    ANY(ConsistencyLevel.ANY), //
    QUORUM(ConsistencyLevel.QUORUM);

    private ConsistencyLevel consistencyLevel;

    private ReadConfiguration(ConsistencyLevel consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
    }

    public ConsistencyLevel getConsistencyLevel() {
        return consistencyLevel;
    }
}
