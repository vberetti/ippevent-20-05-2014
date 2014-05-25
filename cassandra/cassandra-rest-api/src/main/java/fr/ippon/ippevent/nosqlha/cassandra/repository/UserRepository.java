package fr.ippon.ippevent.nosqlha.cassandra.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import fr.ippon.ippevent.nosqlha.cassandra.configuration.ReadConfiguration;
import fr.ippon.ippevent.nosqlha.cassandra.configuration.WriteConfiguration;
import fr.ippon.ippevent.nosqlha.cassandra.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@Setter
public class UserRepository {

    @Inject
    private Session session;


    public User getByUsername(String username, ReadConfiguration readConf) {
        ResultSet resultSet = session.execute(QueryBuilder.select("USERNAME","AGE").from("USER").where(QueryBuilder.eq("USERNAME", username)).setConsistencyLevel(readConf.getConsistencyLevel()));
        List<Row> results = resultSet.all();
        if(!results.isEmpty()){
                Row row = results.get(0);
            String rsUsername = row.getString("USERNAME");
            Integer rsAge = row.getInt("AGE");
            return new User().username(rsUsername).age(rsAge);
        }
        return null;
    }

    public void createUser(String username, Integer age, WriteConfiguration writeConf) {
        ResultSet resultSet = session.execute(QueryBuilder.update("USER") //
                .with(QueryBuilder.set("AGE",age)) //
                .where(QueryBuilder.eq("USERNAME", username)) //
                .setConsistencyLevel(writeConf.getConsistencyLevel()));
    }

    public List<User> findUsers(ReadConfiguration readConf) {
        List<User> returnedList = new ArrayList<>();

        ResultSet resultSet = session.execute(QueryBuilder.select("USERNAME", "AGE").from("USER").limit(10).setConsistencyLevel(readConf.getConsistencyLevel()));
        List<Row> results = resultSet.all();

        if(!results.isEmpty()){
            for(Row row : results) {
                String rsUsername = row.getString("USERNAME");
                Integer rsAge = row.getInt("AGE");
                returnedList.add(new User().username(rsUsername).age(rsAge));
            }
        }
        return returnedList;
    }
}