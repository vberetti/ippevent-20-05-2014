package fr.ippon.ippevent.nosqlha.mongo.configuration;

import com.mongodb.ReadPreference;

import static com.mongodb.ReadPreference.*;

public enum ReadConfiguration {

    PRIMARY(primary()), //
    PRIMARY_PREFERRED(primaryPreferred()), //
    SECONDARY(secondary()), //
    SECONDARY_PREFERRED(secondaryPreferred()); //

    private ReadPreference readPreference;

    private ReadConfiguration(ReadPreference readPreference) {
        this.readPreference = readPreference;
    }

    public ReadPreference getReadPreference() {
        return readPreference;
    }
}
