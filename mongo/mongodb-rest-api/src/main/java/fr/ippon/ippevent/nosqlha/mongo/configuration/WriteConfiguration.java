package fr.ippon.ippevent.nosqlha.mongo.configuration;

import com.mongodb.WriteConcern;

public enum WriteConfiguration {
    ERRORS_IGNORED(WriteConcern.ERRORS_IGNORED), //
    ACKNOWLEDGED(WriteConcern.ACKNOWLEDGED), //
    UNACKNOWLEDGED(WriteConcern.UNACKNOWLEDGED), //
    FSYNCED(WriteConcern.FSYNCED), //
    JOURNALED(WriteConcern.JOURNALED), //
    REPLICA_ACKNOWLEDGED(WriteConcern.REPLICA_ACKNOWLEDGED);

    private WriteConcern writeConcern;

    private WriteConfiguration(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }
}
