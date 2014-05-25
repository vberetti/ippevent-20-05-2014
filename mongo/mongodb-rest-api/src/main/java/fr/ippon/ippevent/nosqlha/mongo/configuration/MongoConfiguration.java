package fr.ippon.ippevent.nosqlha.mongo.configuration;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import fr.ippon.ippevent.nosqlha.mongo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class MongoConfiguration {

    @Inject
    private MongoCongurationProperties properties;

    @Bean
    public Map<WriteConfiguration, MongoTemplate> writeTemplates(Mongo mongo) throws UnknownHostException {
        return asList(WriteConfiguration.values()).stream().collect(     //
                toMap( //
                        identity(),  //
                        (w) -> writeTemplate(mongo, w.getWriteConcern()) //
                )
        );
    }

    @Bean
    public Map<ReadConfiguration, MongoTemplate> readTemplates(Mongo mongo) throws UnknownHostException {
        return asList(ReadConfiguration.values()).stream().collect(     //
                toMap( //
                        identity(),  //
                        (r) -> readTemplate(mongo, r.getReadPreference()) //
                )
        );
    }

    private MongoTemplate writeTemplate(Mongo mongo, WriteConcern writeConcern) {
        MongoTemplate template = template(mongo);
        template.setWriteConcern(writeConcern);
        return template;
    }
    private MongoTemplate readTemplate(Mongo mongo, ReadPreference readPreference) {
        MongoTemplate template = template(mongo);
        template.setReadPreference(readPreference);
        return template;
    }

    private MongoTemplate template(Mongo mongo) {
        MongoTemplate template = new MongoTemplate(mongo, properties.getDatabaseName());
        return template;
    }

    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient(properties.getSeeds());
    }

}
