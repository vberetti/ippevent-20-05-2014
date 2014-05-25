package fr.ippon.ippevent.nosqlha.cassandra.configuration;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class CassandraConfiguration {

    @Inject
    private CassandraCongurationProperties properties;

    @Bean
    public Cluster cluster(){
        Cluster cluster = Cluster.builder().addContactPoints(properties.getSeeds()).build();
        cluster.connect().execute("CREATE KEYSPACE IF NOT EXISTS ippevent_ksp  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : "+properties.getReplicationFactor()+" };");
        return cluster;
    }

    @Bean
    public Session session(Cluster cluster){
       Session session = cluster.connect("ippevent_ksp");
        session.execute("CREATE TABLE IF NOT EXISTS USER (\n" +
                "    USERNAME text PRIMARY KEY,\n" +
                "    AGE int );");
        return session;
    }

}
